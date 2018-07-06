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

public class customAdapter2 extends ArrayAdapter<String> {

    Context context;
    List<String> objects;

    public customAdapter2(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects= objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.episode_list,parent,false);
        }

        String currentResult = objects.get(position);

        TextView epNam = (TextView) view.findViewById(R.id.epName);
        TextView dirNam = (TextView) view.findViewById(R.id.directorName);
        TextView relDate = (TextView) view.findViewById(R.id.relDate);

        epNam.setText(currentResult);
        dirNam.setVisibility(View.GONE);
        relDate.setVisibility(View.GONE);

        return view;
    }
}
