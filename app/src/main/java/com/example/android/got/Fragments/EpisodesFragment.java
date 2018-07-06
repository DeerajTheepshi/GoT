package com.example.android.got.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.got.Api;
import com.example.android.got.Episodes;
import com.example.android.got.R;
import com.example.android.got.RetrofitClasses.results;
import com.example.android.got.data.customAdapter1;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EpisodesFragment extends Fragment{

    public EpisodesFragment() {
    }

    customAdapter1 adapter;
    ListView epList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.activity_main, container, false);
        epList = (ListView) root.findViewById(R.id.history);
        View emptyView = root.findViewById(R.id.emptyView);
        emptyView.setVisibility(View.GONE);
        final ProgressBar loader = (ProgressBar) root.findViewById(R.id.progress2);

        loader.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        Api service = retrofit.create(Api.class);

        Call<List<results>> output = service.getEpisodes();

        output.enqueue(new Callback<List<results>>() {
            @Override
            public void onResponse(Call<List<results>> call, Response<List<results>> response) {
                adapter = new customAdapter1(getActivity(), response.body());
                epList.setAdapter(adapter);
                loader.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<results>> call, Throwable t) {

            }
        });

        return root;
    }

}
