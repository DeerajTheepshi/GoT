package com.example.android.got.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.got.R;
import com.example.android.got.RetrofitClasses.results;

import java.util.List;

public class customAdapter1 extends ArrayAdapter<results> {

    Context context;
    List<results> objects;

    public customAdapter1(@NonNull Context context,@NonNull List<results> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.episode_list,parent,false);
        }

        results currentResult = objects.get(position);

        TextView epNam = (TextView) view.findViewById(R.id.epName);
        TextView dirNam = (TextView) view.findViewById(R.id.directorName);
        TextView relDate = (TextView) view.findViewById(R.id.relDate);
        TextView season = (TextView) view.findViewById(R.id.season);

        season.setText("Season : "+currentResult.getSeason());
        epNam.setText(currentResult.getName());
        dirNam.setText(currentResult.getDirector());
        relDate.setText(currentResult.getAirDate());

        return view;
    }
}
