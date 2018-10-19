package com.example.android.bakingapp.network;

import com.example.android.bakingapp.pojo.Receipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Receipe>> getAllReceipe();
}
