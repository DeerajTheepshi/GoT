package com.example.android.got;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.got.RetrofitClasses.EntireBody;
import com.example.android.got.data.contractClass;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fileExists {
    File inputFile ;
    String characName;
    ImageView charImg;
    Context context;
    long id;

    public fileExists(File inputFile, String characName, ImageView img,Context context,long id) {
        this.inputFile = inputFile;
        this.characName = characName;
        this.charImg = img;
        this.id = id;
        this.context = context;
    }

    public void fileReDownload() {
        Log.v("1234", !inputFile.exists() + "");
        if (!inputFile.exists()) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

            Api service = retrofit.create(Api.class);

            Log.v("1234", characName);

            Call<EntireBody> result = service.getCharacter("characters/" + characName);

            result.enqueue(new Callback<EntireBody>() {
                @Override
                public void onResponse(Call<EntireBody> call, Response<EntireBody> response) {
                    String imageUrl = "https://api.got.show/"+response.body().getData().getImageLink();
                    createAFile();
                    Picasso.get().load(imageUrl).placeholder(R.drawable.miss_image).transform(new TransformCircle()).into(charImg);
                    if (imageUrl != null) {
                        Picasso.get().load(imageUrl).into(target());
                    }
                    ContentValues values = new ContentValues();
                    values.put(contractClass.historytable.C_IMAGE, FileName);
                    Uri updateUri = ContentUris.withAppendedId(contractClass.historytable.CONTENT_URI, id);
                    int changedRows = context.getContentResolver().update(updateUri, values, null, null);
                    if (changedRows == 0) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EntireBody> call, Throwable t) {

                }
            });
        } else {
            Log.v("1234","ELSE");
            Picasso.get().load(inputFile).placeholder(R.drawable.miss_image).transform(new TransformCircle()).into(charImg);
        }
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
                        File directory = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getPath()+"/GOT");
                        File image = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getPath()+"/GOT/"+FileName);
                        if(!directory.exists())
                            directory.mkdir();
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
