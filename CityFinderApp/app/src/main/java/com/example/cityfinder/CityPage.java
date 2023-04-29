package com.example.cityfinder;
import static com.example.cityfinder.MainActivity.TEXT_INPUT;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityPage extends AppCompatActivity {
    private TextView city_name, population, population_text, area, area_text, population_density, population_density_text, timezone, timezone_text, airport, airport_text, currency, currency_text, attractions, attractions_text, best_known, best_known_text, temperature, temperature_text, landform, landform_text;
    private EditText target_city;
    private Button compute, back;
    private TextView result;
    private JSONArray cities;
    private JSONObject city1,city2;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ICityFinder service = ICityFinder.Stub.asInterface(iBinder);
            try {
                service.RetrieveInfo(new ICallback.Stub() {
                    @Override
                    public void OnSuccess(String responseString) {
                        try {
                            cities = new JSONArray(responseString);
                            for (int i = 0; i < cities.length(); i++) {
                                Log.d("CityName",cities.getJSONObject(i).getString("name") + " " + i + getCityName());
                                if (cities.getJSONObject(i).getString("name").equals(getCityName())){
                                    city1 = cities.getJSONObject(i);
                                    int finalI = i;
                                    runOnUiThread(() -> {

                                        try {
                                            city_name.setText(cities.getJSONObject(finalI).getString("name"));
                                            String placeholder = cities.getJSONObject(finalI).getJSONArray("population").getString(0) + " / " + cities.getJSONObject(finalI).getJSONArray("population").getString(1);
                                            population_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getJSONArray("area_occupied").getString(0) + " / " + cities.getJSONObject(finalI).getJSONArray("area_occupied").getString(1) + " km^2";
                                            area_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getString("population_density") + " /km^2";
                                            population_density_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getString("timezone");
                                            timezone_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getString("airport");
                                            airport_text.setText(placeholder);
                                            placeholder =  cities.getJSONObject(finalI).getString("currency");
                                            currency_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getJSONArray("attractions").getString(0) + ", " + cities.getJSONObject(finalI).getJSONArray("attractions").getString(1) + ", " + cities.getJSONObject(finalI).getJSONArray("attractions").getString(2);
                                            attractions_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getString("best_known");
                                            best_known_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getJSONArray("temperature").getString(0) + " / " + cities.getJSONObject(finalI).getJSONArray("temperature").getString(1) + " Celsius";
                                            temperature_text.setText(placeholder);
                                            placeholder = cities.getJSONObject(finalI).getString("landform");
                                            landform_text.setText(placeholder);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }

                                    });
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                });


                runOnUiThread(() -> {
                    compute.setOnClickListener(v -> {
                        boolean correct = false;
                        for (int i = 0; i < cities.length(); i++) {
                            try {
                                if (cities.getJSONObject(i).getString("name").equals(target_city.getText().toString()) && target_city.getText() != null){
                                    city2 = cities.getJSONObject(i);
                                    correct = true;
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        try {
                            if (correct){
                                String text = "Result: " + service.getDistance(city1.getJSONArray("coords").getDouble(0), city1.getJSONArray("coords").getDouble(1), city2.getJSONArray("coords").getDouble(0), city2.getJSONArray("coords").getDouble(1)) + " km";
                                result.setText(text);
                            } else
                                Toast.makeText(CityPage.this, "Incorrect city name", Toast.LENGTH_SHORT).show();


                        } catch (RemoteException | JSONException e) {
                            throw new RuntimeException(e);
                        }

                    });

                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_page);
        city_name = (TextView) findViewById(R.id.city_name);
        population = (TextView) findViewById(R.id.population);
        population_text = (TextView) findViewById(R.id.population_text);
        area = (TextView) findViewById(R.id.area);
        area_text = (TextView) findViewById(R.id.area_text);
        population_density = (TextView) findViewById(R.id.population_density);
        population_density_text = (TextView) findViewById(R.id.population_density_text);
        timezone = (TextView) findViewById(R.id.timezone);
        timezone_text = (TextView) findViewById(R.id.timezone_text);
        airport = (TextView) findViewById(R.id.airport);
        airport_text = (TextView) findViewById(R.id.airport_text);
        currency = (TextView) findViewById(R.id.currency);
        currency_text = (TextView) findViewById(R.id.currency_text);
        attractions = (TextView) findViewById(R.id.attractions);
        attractions_text = (TextView) findViewById(R.id.attractions_text);
        best_known = (TextView) findViewById(R.id.best_known);
        best_known_text = (TextView) findViewById(R.id.best_known_text);
        temperature = (TextView) findViewById(R.id.temperature);
        temperature_text = (TextView) findViewById(R.id.temperature_text);
        landform = (TextView) findViewById(R.id.landform);
        landform_text = (TextView) findViewById(R.id.landform_text);
        target_city = (EditText) findViewById(R.id.target_city);
        compute = (Button) findViewById(R.id.compute_distance);
        result = (TextView) findViewById(R.id.result);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(view -> openMainActivity());

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public String getCityName() {
        Intent intent = getIntent();
        String name = intent.getStringExtra(TEXT_INPUT);
        return name;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, CityFinderService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }
}