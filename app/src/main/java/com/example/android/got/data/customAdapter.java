package com.example.android.got.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.got.TransformCircle;
import com.example.android.got.data.contractClass.historytable;

import com.example.android.got.R;
import com.example.android.got.fileExists;
import com.squareup.picasso.Picasso;

import java.io.File;

public class customAdapter extends CursorAdapter {

    public customAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.history_list, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView charName = (TextView) view.findViewById(R.id.NameonList);
        ImageView characImg = (ImageView) view.findViewById(R.id.characHomeArt);
        TextView gender = (TextView) view.findViewById(R.id.GenderonList);

        gender.setText(cursor.getString(cursor.getColumnIndex(historytable.code)));

        String Cname = cursor.getString(cursor.getColumnIndex(historytable.C_NAME));
        charName.setText(Cname);

        File imageUri = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getPath()+"/GOT/"+cursor.getString(cursor.getColumnIndex(historytable.C_IMAGE)));
        long id = cursor.getLong(cursor.getColumnIndex(historytable._ID));
        fileExists checkingExistance = new fileExists(imageUri,Cname,characImg,context,id);
        checkingExistance.fileReDownload();
    }
}
