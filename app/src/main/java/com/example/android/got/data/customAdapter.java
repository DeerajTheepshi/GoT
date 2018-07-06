package com.example.android.got.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.got.TransformCircle;
import com.example.android.got.data.contractClass.historytable;

import com.example.android.got.R;
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
        TextView charname = (TextView) view.findViewById(R.id.NameonList);
        ImageView charimg = (ImageView) view.findViewById(R.id.characHomeArt);
        TextView gender = (TextView) view.findViewById(R.id.GenderonList);

        gender.setText(cursor.getString(cursor.getColumnIndex(historytable.code)));

        charname.setText(cursor.getString(cursor.getColumnIndex(historytable.C_NAME)));

        File imageUri = new File(Environment.getExternalStorageDirectory().getPath()+"/"+cursor.getString(cursor.getColumnIndex(historytable.C_IMAGE)));
        Picasso.get().load(imageUri).placeholder(R.drawable.miss_image).transform(new TransformCircle()).into(charimg);
    }
}
