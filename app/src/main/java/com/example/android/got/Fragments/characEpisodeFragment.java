package com.example.android.got.Fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.got.Api;
import com.example.android.got.Episodes;
import com.example.android.got.R;
import com.example.android.got.RetrofitClasses.EntireBody;
import com.example.android.got.RetrofitClasses.EntireBody1;
import com.example.android.got.RetrofitClasses.results;
import com.example.android.got.data.contractClass;
import com.example.android.got.data.customAdapter1;
import com.example.android.got.data.customAdapter2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class characEpisodeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    customAdapter2 adapter;
    ListView epList;
    Uri dataUri;
    String query;

    public characEpisodeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataUri = (Uri) getActivity().getIntent().getExtras().get("Data");
        View root = inflater.inflate(R.layout.activity_main, container, false);
        epList = (ListView) root.findViewById(R.id.history);
        View emptyView = root.findViewById(R.id.emptyView);
        emptyView.setVisibility(View.GONE);
        getLoaderManager().initLoader(0,null,this);
        return root;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = new String[]{contractClass.historytable._ID, contractClass.historytable.C_NAME};
        return new CursorLoader(getContext(), dataUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if(data.moveToFirst()) {
            query = data.getString(data.getColumnIndex(contractClass.historytable.C_NAME));
            final ProgressBar loaders = (ProgressBar) getActivity().findViewById(R.id.progress2);

            loaders.setVisibility(View.VISIBLE);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

            Api service = retrofit.create(Api.class);

            Call<EntireBody1> output = service.getPlaces("characters/locations/"+query);

           output.enqueue(new Callback<EntireBody1>() {
               @Override
               public void onResponse(Call<EntireBody1> call, Response<EntireBody1> response) {
                    List<String> res = response.body().getData().get(0).getLocations();
                    adapter = new customAdapter2(getActivity(),0,res);
                    epList.setAdapter(adapter);
                    loaders.setVisibility(View.GONE);
               }

               @Override
               public void onFailure(Call<EntireBody1> call, Throwable t) {
                    Log.v("1234",t+"");
                    Log.v("1234","Hi");
               }
           });

            Log.v("1234","Heyyyy");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
