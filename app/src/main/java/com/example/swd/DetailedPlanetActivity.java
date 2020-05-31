package com.example.swd;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class DetailedPlanetActivity extends AppCompatActivity {
    private Planets currentPlanet;
    private ImageView planetPic;
    private TextView planetName;
    private TextView rotationPeriod;
    private TextView orbitalPeriod;
    private TextView diameter;
    private TextView climate;
    private TextView gravity;
    private TextView terrain;
    private TextView population;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_planet);

        planetPic = (ImageView) findViewById(R.id.img_planet);
        planetName = (TextView) findViewById(R.id.name_of_planet);
        rotationPeriod = (TextView) findViewById(R.id.rotation_period);
        orbitalPeriod = (TextView) findViewById(R.id.orbital_period);
        diameter = (TextView) findViewById(R.id.diameter);
        climate = (TextView) findViewById(R.id.climate);
        gravity = (TextView) findViewById(R.id.gravity);
        terrain = (TextView) findViewById(R.id.terrain);
        population = (TextView) findViewById(R.id.population);

        Gson gson = new Gson();
        currentPlanet = gson.fromJson(getIntent().getStringExtra("myjson"), Planets.class);
        setDataFromPlanet(currentPlanet);

        
    }

    private void setDataFromPlanet(Planets currentPlanet) {
        int index;
        for(index=0 ; index < Constants.namePlanets.length; index++){
            if(Constants.namePlanets[index].equals(currentPlanet.getName())){
                planetPic.setImageResource(Constants.images[index]);
            }
            
        }

        planetName.setText(currentPlanet.getName());
        rotationPeriod.setText(currentPlanet.getRotation_period() + " hours");
        orbitalPeriod.setText(currentPlanet.getOrbital_period()+ " jours");
        diameter.setText(currentPlanet.getDiameter() + " km");
        climate.setText(currentPlanet.getClimate());
        gravity.setText(currentPlanet.getGravity());
        terrain.setText(currentPlanet.getTerrain());
        population.setText(currentPlanet.getPopulation());



    }
}
