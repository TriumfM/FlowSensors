package com.example.triumf.flow_sensor_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.triumf.flow_sensor_app.API.APIservice;
import com.example.triumf.flow_sensor_app.API.SensorInterface;
import com.example.triumf.flow_sensor_app.Adapters.SensorsAdapter;
import com.example.triumf.flow_sensor_app.Entiry.Sensors;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SensorsList extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Sensors> sensors;

    private SensorsAdapter adapter;
    private SensorInterface api;

    private Button add_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        int delay = 0; // delay for 0 sec.
        int period = 10000; // repeat every 10 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                fetchInformation();
            }
        }, delay, period);

        add_new = findViewById(R.id.add_new_sensor);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSensor();
            }
        });

    }

    private void AddSensor() {
        Intent intentRegister = new Intent(this, NewSensor.class);
        startActivity(intentRegister);
    }

    public void fetchInformation() {
        api = APIservice.createService(SensorInterface.class);

        Call<List<Sensors>> call = api.getSensors();

        call.enqueue(new Callback<List<Sensors>>() {
            @Override
            public void onResponse(Call<List<Sensors>> call, Response<List<Sensors>> response) {
                sensors = response.body();

                adapter = new SensorsAdapter(sensors, SensorsList.this);

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Sensors>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
