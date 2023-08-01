package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity {

    FirebaseAuth auth;
    ImageButton button, button1,button2,button3,button4,button5;
    TextView textView, textView1 ,textView3;

    EditText editText;
    ImageSlider imageSlider;
    CardView cardView;
    FirebaseUser user;
    String url = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey = "93609261145cb55f8e16c1fb75a76a56";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        textView = findViewById(R.id.user_details);
        button = findViewById(R.id.btn_logout);
        textView1 = findViewById(R.id.user_city);
        textView3 = findViewById(R.id.tv);
        editText = findViewById(R.id.et);
        cardView = findViewById(R.id.card);
        imageSlider = findViewById(R.id.imageslider);
        button1 = findViewById(R.id.btn_navigation);
        button2 = findViewById(R.id.btn_about);
        button3 = findViewById(R.id.btn_feedback);
        button4 = findViewById(R.id.btn_map);
        button5 = findViewById(R.id.btn_exchange);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Navigation selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "About selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Feedback selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), feedback.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Map selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Gmap.class);
                startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Currency Exchange selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),exchange.class);
                startActivity(intent);
            }
        });

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.r1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.r3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.r4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.arr4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.aar5, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText("Welcome! " + user.getEmail());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomePage.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public  void getweather(View v)
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi = retrofit.create(weatherapi.class);
        Call<Example> exampleCall = myapi.getweather(editText.getText().toString().trim(),apikey);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.code()==404){
                    Toast.makeText(HomePage.this,"Please Enter a valid City",Toast.LENGTH_LONG).show();
                }
                else if(!(response.isSuccessful())){
                    Toast.makeText(HomePage.this,response.code()+" ",Toast.LENGTH_LONG).show();
                    return;
                }
                Example mydata=response.body();
                Main main=mydata.getMain();
                Double temp=main.getTemp();
                Integer humidity = main.getHumidity();
                Integer pressure = main.getPressure();
                Integer temperature=(int)(temp-273.15);
                textView3.setText("Temperature is:: " + String.valueOf(temperature)+" ℃\n" + "Humidity is:: " + humidity +"%"+ "\n Pressure is:: " +pressure + " hPa\n");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Toast.makeText(HomePage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}