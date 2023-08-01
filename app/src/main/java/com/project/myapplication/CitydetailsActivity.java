package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CitydetailsActivity extends AppCompatActivity {


   ImageButton btn_Hotel,btn_Restaurant,btn_Famousplaces,btn_Busstation,btn_History,btn_nearby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citydetails);

        //To get city name
        Intent intent=getIntent();
        String cityname=intent.getStringExtra("cityname");

        btn_Hotel=(ImageButton) findViewById(R.id.btnHotelrooms);
        btn_Restaurant=(ImageButton)findViewById(R.id.btnRestaurant);
        btn_Famousplaces=(ImageButton)findViewById(R.id.btnFamousplaces);
        btn_Busstation=(ImageButton)findViewById(R.id.btnBusstation);
        btn_History=(ImageButton)findViewById(R.id.btnHistory);
        btn_nearby=(ImageButton)findViewById(R.id.nearby);


        btn_Hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HotelActivity.class);
                intent.putExtra("cityname",cityname);
                startActivity(intent);

            }
        });
        btn_nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Gmap.class);
                intent.putExtra("cityname",cityname);
                startActivity(intent);
            }
        });

        btn_Restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RestaurantActivity.class);
                intent.putExtra("cityname",cityname);
                startActivity(intent);
            }
        });


        btn_Famousplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),PlacesActivity.class);
                intent.putExtra("cityname",cityname);
                startActivity(intent);
            }
        });


        btn_Busstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),BusstationActivity.class);
                intent.putExtra("cityname",cityname);
                startActivity(intent);

            }
        });

        btn_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HistoryActivity.class);
                intent.putExtra("cityname",cityname);
                startActivity(intent);
            }
        });

    }
}