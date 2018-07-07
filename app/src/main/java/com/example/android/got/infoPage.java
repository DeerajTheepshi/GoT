package com.example.android.got;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.got.Fragments.PagerAdapter;
import com.example.android.got.Fragments.PagerAdapter1;
import com.example.android.got.data.contractClass;
import com.example.android.got.data.contractClass.historytable;
import com.squareup.picasso.Picasso;

import java.io.File;

public class infoPage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    Uri dataUri;
    /*TextView characName,spouseName,houseName,clubName, titleName;*/
    ImageView characImg;

    /*@Override
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

    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{historytable._ID,historytable.C_IMAGE,historytable.C_NAME};
        return new CursorLoader(this, dataUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       if(data.moveToFirst()) {
           String Cname = data.getString(data.getColumnIndex(historytable.C_NAME));long id = data.getLong(data.getColumnIndex(historytable._ID));
           File imageUri = new File(Environment.getExternalStoragePublicDirectory(
                   Environment.DIRECTORY_PICTURES).getPath()+"/GOT/"+data.getString(data.getColumnIndex(contractClass.historytable.C_IMAGE)));
           fileExists checkingExistance = new fileExists(imageUri,Cname,characImg,this,id);
           checkingExistance.fileReDownload();

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity2);
        characImg = (ImageView)findViewById(R.id.characArt);

        dataUri = (Uri) getIntent().getExtras().get("Data");


        getLoaderManager().initLoader(0,null,this);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter1 adapter = new PagerAdapter1(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }
}
