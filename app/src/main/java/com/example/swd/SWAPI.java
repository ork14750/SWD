package com.example.swd;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SWAPI {

    @GET("planets/")
    Call<RestSWResponse> getResponse();
}
