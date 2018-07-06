package com.example.android.got.Fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.got.R;
import com.example.android.got.TransformCircle;
import com.example.android.got.data.contractClass;
import com.squareup.picasso.Picasso;

import java.io.File;

public class infoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    Uri dataUri;
    TextView characName,spouseName,houseName,clubName, titleName;
    ImageView characImg;

    public infoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.info_page,container,false);
        dataUri = (Uri) getActivity().getIntent().getExtras().get("Data");


        characName = (TextView)root.findViewById(R.id.nameInfo);
        spouseName = (TextView)root.findViewById(R.id.spouseInfo);
        houseName =  (TextView)root.findViewById(R.id.houseInfo);
        clubName = (TextView)root.findViewById(R.id.cultureInfo);
        titleName = (TextView)root.findViewById(R.id.titlesInfo);


        getLoaderManager().initLoader(0,null,this);

        return root;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = new String[]{contractClass.historytable._ID, contractClass.historytable.C_NAME, contractClass.historytable.C_SPOUSE,
                contractClass.historytable.C_HOUSE, contractClass.historytable.C_CUL, contractClass.historytable.C_TITLES, contractClass.historytable.C_IMAGE};
        return new CursorLoader(getContext(), dataUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()) {
            characName.setText(data.getString(data.getColumnIndex(contractClass.historytable.C_NAME)));
            spouseName.setText(data.getString(data.getColumnIndex(contractClass.historytable.C_SPOUSE)));
            houseName.setText(data.getString(data.getColumnIndex(contractClass.historytable.C_HOUSE)));
            clubName.setText(data.getString(data.getColumnIndex(contractClass.historytable.C_CUL)));
            titleName.setText(data.getString(data.getColumnIndex(contractClass.historytable.C_TITLES)));


        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
