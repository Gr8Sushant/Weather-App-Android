package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView cityInput;
    TextView result;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String key = "8dce4766d6f9c35c446ac011da3a3c67";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        result = findViewById(R.id.result);


    }
    public void showWeather(View v){
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi api = rf.create(weatherapi.class);
        Call<Data> data = api.showWeather(cityInput.getText().toString().trim(),key);
        data.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.code() == 404){
                    Toast.makeText(MainActivity.this, "Please Enter a valid City Name", Toast.LENGTH_LONG).show();

                }
                else if(!(response.isSuccessful())){
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
                }
                Data mydata = response.body();
                Main main = mydata.getMain();
                Double temp = main.getTemp();
                Double lowtemp = main.getTempMin();
                Double hightemp = main.getTempMax();
                Integer humidity = main.getHumidity();

                Integer temperature = (int)(temp-273.15);
                Integer mintemp = (int)(lowtemp - 273.15);
                Integer maxtemp = (int)(hightemp - 273.15);
                result.setText("Temperature: "+String.valueOf(temperature)+"℃\n" +"Humidity: "+String.valueOf(humidity)+"%");

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });

    }
}