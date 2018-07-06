package com.example.android.got;

import com.example.android.got.RetrofitClasses.EntireBody;
import com.example.android.got.RetrofitClasses.EntireBody1;
import com.example.android.got.RetrofitClasses.results;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    public final String BASE_URL = "https://api.got.show/api/";

    @GET
    Call<EntireBody> getCharacter(@Url String Name);

    @GET("episodes")
    Call<List<results>> getEpisodes();

    @GET
    Call<EntireBody1> getPlaces(@Url String Name1);



}
