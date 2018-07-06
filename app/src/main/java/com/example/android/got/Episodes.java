package com.example.android.got;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.got.data.customAdapter1;
import com.example.android.got.RetrofitClasses.results;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Episodes extends AppCompatActivity {

    customAdapter1 adapter;
    ListView epList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        epList = (ListView) findViewById(R.id.history);
        View emptyView = findViewById(R.id.emptyView);
        emptyView.setVisibility(View.GONE);
        final ProgressBar loader = (ProgressBar) findViewById(R.id.progress2);

        loader.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        Api service = retrofit.create(Api.class);

        Call<List<results>> output = service.getEpisodes();

        output.enqueue(new Callback<List<results>>() {
            @Override
            public void onResponse(Call<List<results>> call, Response<List<results>> response) {
                adapter = new customAdapter1(Episodes.this,response.body());
                epList.setAdapter(adapter);
                loader.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<results>> call, Throwable t) {

            }
        });


    }
}
