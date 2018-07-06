package com.example.android.got;

import android.Manifest;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.got.RetrofitClasses.EntireBody;
import com.example.android.got.RetrofitClasses.results;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.android.got.data.contractClass.historytable;
import com.example.android.got.data.contractClass.searchTable;
import com.squareup.picasso.Target;

public class SearchableActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    TextView characName,spouseName,houseName,clubName, titleName;
    ImageView characImg;
    String query;
    int status=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);


        if (ActivityCompat.checkSelfPermission(SearchableActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SearchableActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }else {
            getSearchResult(getIntent());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSearchResult(getIntent());
                }
                else{
                    Toast.makeText(this, "Permission not Granted",Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void getSearchResult(Intent intent){

        characName = (TextView)findViewById(R.id.nameInfo);
        spouseName = (TextView)findViewById(R.id.spouseInfo);
        houseName =  (TextView)findViewById(R.id.houseInfo);
        clubName = (TextView)findViewById(R.id.cultureInfo);
        titleName = (TextView)findViewById(R.id.titlesInfo);
        characImg = (ImageView)findViewById(R.id.characArt);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
           query = intent.getData().toString();

        }
        getLoaderManager().initLoader(0,null,this);
    }

    public boolean check(String spouse){
        if (spouse==null)
            return false;
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{historytable._ID,historytable.C_NAME,historytable.
                C_SPOUSE,historytable.C_CUL,historytable.C_HOUSE,historytable.C_IMAGE,historytable.C_TITLES};
        String selection = historytable.C_NAME + "=?";
        String[] selectionArgs = new String[]{query};
        return new CursorLoader(this, historytable.CONTENT_URI,projection,selection,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data.moveToFirst()){
            final ProgressBar loaderbar = (ProgressBar)findViewById(R.id.progress1);
            loaderbar.setVisibility(View.GONE);
            String Cname = data.getString(data.getColumnIndex(historytable.C_NAME)),
                    Sname = data.getString(data.getColumnIndex(historytable.C_SPOUSE)),
                    Hname = data.getString(data.getColumnIndex(historytable.C_HOUSE)),
                    CLname = data.getString(data.getColumnIndex(historytable.C_CUL)),
                    Tname =data.getString(data.getColumnIndex(historytable.C_TITLES));
            characName.setText(Cname);
            spouseName.setText(Sname);
            houseName.setText(Hname);
            clubName.setText(CLname);
            titleName.setText(Tname);

            File imageUri = new File(Environment.getExternalStorageDirectory().getPath()+"/"+data.getString(data.getColumnIndex(historytable.C_IMAGE)));
            Picasso.get().load(imageUri).placeholder(R.drawable.miss_image).transform(new TransformCircle()).into(characImg);
            status = 1;
        }

        if(status==0){
            final ProgressBar loaderbar = (ProgressBar)findViewById(R.id.progress1);
            ConnectivityManager connection = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkStauts = connection.getActiveNetworkInfo();
            if(!(networkStauts!=null && networkStauts.isConnectedOrConnecting())){
                new AlertDialog.Builder(this).setTitle("No Network").setCancelable(false).setMessage("Internet connection is required for the app").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }).create().show();
            }

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

            Api service = retrofit.create(Api.class);

            loaderbar.setVisibility(View.VISIBLE);

            Call<EntireBody> result = service.getCharacter("characters/" + query);

            result.enqueue(new Callback<EntireBody>() {
                @Override
                public void onResponse(Call<EntireBody> call, Response<EntireBody> response) {
                    results res=null;
                    if(response.body() !=null) {
                        res = response.body().getData();
                        String cname = res.getName(), sname = res.getSpouse(), hname = res.getHouse(), culName = res.getCulture(),
                                picUrl = "https://api.got.show/" + res.getImageLink(), titleString = "",idOfChar=res.get_id();
                        List<String> title = res.getTiles();

                        createAFile();
                        Picasso.get().load(picUrl).placeholder(R.drawable.miss_image).transform(new TransformCircle()).into(characImg, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                loaderbar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                        if(check(picUrl)){
                            Picasso.get().load(picUrl).into(target());
                        }

                        characName.setText(cname);
                        spouseName.setText(check(sname) ? sname : "Not Available");
                        houseName.setText(check(hname)? hname:"Not Available");
                        clubName.setText(check(culName)? culName:"Not Available");
                        for (int i = 0; i < title.size(); i++) {
                            titleString += "* " + title.get(i) + "\n";
                        }
                        titleName.setText(!titleString.isEmpty()? titleString:"Not Available");

                        ContentValues val = new ContentValues();
                        val.put(historytable.C_NAME, cname);
                        val.put(historytable.C_SPOUSE, check(sname) ? sname : "Not Available");
                        val.put(historytable.C_HOUSE, check(hname)? hname:"Not Available");
                        val.put(historytable.C_CUL, check(culName)? culName:"Not Available");
                        val.put(historytable.C_TITLES, !titleString.isEmpty()? titleString:"Not Available");
                        val.put(historytable.code,idOfChar);
                        val.put(historytable.C_IMAGE, FileName);
                        Uri uri = getContentResolver().insert(historytable.CONTENT_URI, val);
                        if (uri != null)
                            Toast.makeText(SearchableActivity.this, "New Entry has been added to: " + uri, Toast.LENGTH_LONG).show();

                        ContentValues searchVal = new ContentValues();
                        searchVal.put(searchTable.NAME, cname);
                        searchVal.put(searchTable.DATA, cname);
                        Uri uri1 = getContentResolver().insert(searchTable.CONTENT_URI,searchVal);
                        if(uri1!=null)
                            Toast.makeText(SearchableActivity.this, "New Entry has been added to: " + uri1, Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(SearchableActivity.this,"Sorry, No matching character found",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<EntireBody> call, Throwable t) {

                }
            });
            status=2;

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    String FileName;
    private String createAFile()  {
        String Stamp = new SimpleDateFormat("yyyy*MM*dd_HHmmss").format(new Date());
        FileName = "JPEG_" + Stamp + ".png";
        return FileName;
    }

    public Target target(){
        Target targetFile = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File image = new File(Environment.getExternalStorageDirectory().getPath()+"/"+FileName);
                        Log.v("1234",image+"");
                        try {
                            image.createNewFile();
                            FileOutputStream fileStream = new FileOutputStream(image);
                            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteStream);
                            fileStream.write(byteStream.toByteArray());
                            fileStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return targetFile;
    }

}
