package com.example.swd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://swapi.dev/api/";
    private List<Planets> planets;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Planets> planetsList = appHaveDataFromCache();

        if(planetsList!=null){
            showList(planetsList);
        }else{
            makeApiCall();
        }

    }

    private List<Planets> appHaveDataFromCache() {
        String jsonPlanets = sharedPreferences.getString(Constants.KEY_PLANETS_LIST, null);

        if(jsonPlanets==null){
            return null;
        } else {
            Type listType = new TypeToken<List<Planets>>(){}.getType();
            return gson.fromJson(jsonPlanets,listType);
        }


    }





    private void showList(List<Planets> planetsList){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(planetsList);
        recyclerView.setAdapter(mAdapter);

    }


    private void makeApiCall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SWAPI swapi = retrofit.create(SWAPI.class);

        Call<RestSWResponse> call = swapi.getResponse();
        call.enqueue(new Callback<RestSWResponse>() {
            @Override
            public void onResponse(Call<RestSWResponse> call, Response<RestSWResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    planets = response.body().getResults();
                    saveList(planets);
                    showList(planets);
                } else {
                    showError();
                }

            }

            @Override
            public void onFailure(Call<RestSWResponse> call, Throwable t) {
                showError();

            }
        });

    }

    private void saveList(List<Planets> planets) {
        String jsonString = gson.toJson(planets);
        //.putInt("cle_integer", 3)
        sharedPreferences
                .edit()
                .putString(Constants.KEY_PLANETS_LIST, jsonString)
                .apply();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_SHORT).show();
        


    }
}
