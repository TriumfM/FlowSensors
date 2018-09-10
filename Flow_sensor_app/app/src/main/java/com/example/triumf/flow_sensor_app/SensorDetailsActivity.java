package com.example.triumf.flow_sensor_app;

import android.app.Activity;
import android.database.Observable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.triumf.flow_sensor_app.API.APIservice;
import com.example.triumf.flow_sensor_app.API.SensorInterface;
import com.example.triumf.flow_sensor_app.Entiry.SensorData;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorDetailsActivity extends Activity {

    private SensorInterface api;
    private Observable sensorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);

        getIncomingInternet();
    try{
        final Integer sidSensor = getIntent().getIntExtra("sid",0);

        int delay = 0; // delay for 0 sec.
        int period = 5000; // repeat every 10 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                fetchInformation(sidSensor);
            }
        }, delay, period);



    }
    catch (Exception e ){
        Log.d("getIncomingInternet", e.getMessage());

    }

    }

    private void getIncomingInternet() {
        if(getIntent().hasExtra("sid")){

            Integer sidSensor = getIntent().getIntExtra("sid",0);
            String nameSensor = getIntent().getStringExtra("name");
            String typeSensor = getIntent().getStringExtra("type");
            String diameterSensor = getIntent().getStringExtra("diameter");
            String descriptionSensor = getIntent().getStringExtra("description");
            Integer inputPinSensor = getIntent().getIntExtra("inputPin",0);
            Integer outputPinSensor = getIntent().getIntExtra("outputPin",0);
            Integer statusPinSensor = getIntent().getIntExtra("statusPin",0);

            setName(
                    inputPinSensor,
                    outputPinSensor,
                    statusPinSensor,
                    nameSensor,
                    typeSensor,
                    diameterSensor,
                    descriptionSensor
            );
        }
    }

    private void setName(Integer inputPin, Integer outputPin, Integer statusPin, String name, String type, String diameter, String description) {
        TextView nameS = findViewById(R.id.sensor_details_value_name);
        TextView typeS = findViewById(R.id.sensor_details_value_type);
        TextView diameterS = findViewById(R.id.sensor_details_value_diameter);
        TextView descriptionS = findViewById(R.id.sensor_details_value_description);
        TextView inputPinS = findViewById(R.id.sensor_details_value_inputPin);
        TextView outputPinS = findViewById(R.id.sensor_details_value_outputPin);
        TextView statusPinS = findViewById(R.id.sensor_details_value_statusPin);

        nameS.setText(name);
        typeS.setText(type);
        diameterS.setText(diameter);
        descriptionS.setText(description);
        inputPinS.setText(inputPin.toString());
        outputPinS.setText(outputPin.toString());
        statusPinS.setText(statusPin.toString());
    }

    public void fetchInformation(Integer sensorId) {

            api = APIservice.createService(SensorInterface.class);
            Integer sensorIda = 1;
            Call<List<SensorData>> call = api.getSensorData(sensorId);
            call.enqueue(new Callback<List<SensorData>>() {
                @Override
                public void onResponse(Call<List<SensorData>> call, Response<List<SensorData>> response) {

                    Button literPerMin = (Button) findViewById(R.id.sensor_literpermin);
                    Button total = (Button) findViewById(R.id.sensor_total);
                    TextView dataTime = (TextView) findViewById(R.id.sensor_dateTime);

                    if(response.body().size() != 0) {
                        literPerMin.setText(String.valueOf(response.body().get(0).getLiterMin())+ " L/m");
                        total.setText(String.valueOf(response.body().get(0).getTotal())+ "L");
                        dataTime.setText("Last time update at: " + response.body().get(0).getDataTime());
                    }
                    else {
                        literPerMin.setText("No Data!");
                        total.setText("No Data!");
                    }



                }

                @Override
                public void onFailure(Call<List<SensorData>> call, Throwable t) {
                    Log.d("Error message: ",t.getMessage());
                }


            });


    }

}
