package com.example.android.got;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.got.data.contractClass.historytable;
import com.squareup.picasso.Picasso;

import java.io.File;

public class infoPage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    Uri dataUri;
    TextView characName,spouseName,houseName,clubName, titleName;
    ImageView characImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        dataUri = (Uri) getIntent().getExtras().get("Data");

        Log.v("1234",dataUri+"");

        characName = (TextView)findViewById(R.id.nameInfo);
        spouseName = (TextView)findViewById(R.id.spouseInfo);
        houseName =  (TextView)findViewById(R.id.houseInfo);
        clubName = (TextView)findViewById(R.id.cultureInfo);
        titleName = (TextView)findViewById(R.id.titlesInfo);
        characImg = (ImageView)findViewById(R.id.characArt);

        getLoaderManager().initLoader(0,null,this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{historytable._ID,historytable.C_NAME,historytable.C_SPOUSE,
                historytable.C_HOUSE,historytable.C_CUL,historytable.C_TITLES,historytable.C_IMAGE};
        return new CursorLoader(this, dataUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()) {
            characName.setText(data.getString(data.getColumnIndex(historytable.C_NAME)));
            spouseName.setText(data.getString(data.getColumnIndex(historytable.C_SPOUSE)));
            houseName.setText(data.getString(data.getColumnIndex(historytable.C_HOUSE)));
            clubName.setText(data.getString(data.getColumnIndex(historytable.C_CUL)));
            titleName.setText(data.getString(data.getColumnIndex(historytable.C_TITLES)));

            File imageUri = new File(Environment.getExternalStorageDirectory().getPath()+"/"+data.getString(data.getColumnIndex(historytable.C_IMAGE)));
            Picasso.get().load(imageUri).placeholder(R.drawable.miss_image).transform(new TransformCircle()).into(characImg);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
