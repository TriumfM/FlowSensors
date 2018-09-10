package com.example.triumf.flow_sensor_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triumf.flow_sensor_app.API.APIservice;
import com.example.triumf.flow_sensor_app.API.SensorInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSensor extends Activity {
    private static final String TAG = NewSensor.class.getSimpleName();

    private TextView mResponseTv;
    private EditText name, type, diameter, description, input, output, status;
    private String name_, type_, diameter_, description_;
    private Integer input_ = 0, output_ = 0, status_ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sensor);

        name = (EditText) findViewById(R.id.sensor_details_value_name);
        type = (EditText) findViewById(R.id.sensor_details_value_type);
        diameter = (EditText) findViewById(R.id.sensor_details_value_diameter);
        description = (EditText) findViewById(R.id.sensor_details_value_description);
        input = (EditText) findViewById(R.id.sensor_details_value_inputPin);
        output = (EditText) findViewById(R.id.sensor_details_value_outputPin);
        status = (EditText) findViewById(R.id.sensor_details_value_statusPin);


        Button submitBtn = (Button) findViewById(R.id.sensor_details_save);
        mResponseTv = (TextView) findViewById(R.id.mResponseTv);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    name_ = name.getText().toString().trim();
                    type_ = type.getText().toString().trim();
                    diameter_ = diameter.getText().toString().trim();
                    description_ = description.getText().toString().trim();
                    input_ = Integer.valueOf(input.getText().toString().trim());
                    output_ = Integer.valueOf(output.getText().toString().trim());
                    status_= Integer.valueOf(status.getText().toString().trim());

                    if(!name_.isEmpty() && !type_.isEmpty() && !diameter_.isEmpty() && !description_.isEmpty() && input_ != null && output_ != null && status_ != null){
                        sendPost(input_, output_, status_,name_, type_,diameter_, description_);
                        mResponseTv.setText("");
                    }
                    else{
                        mResponseTv.setText("Fill all fields!");

                    }
                }
                catch(Exception e) {
                    mResponseTv.setText("Error!");
                }
            }
        });
    }

    public void sendPost(Integer inputPin, Integer outputPin, Integer statusPin, final String name, final String type, final String diameter, final String description) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("diameter", diameter);
        jsonObject.addProperty("description", description);
        jsonObject.addProperty("input", inputPin);
        jsonObject.addProperty("output", outputPin);
        jsonObject.addProperty("status", statusPin);

        SensorInterface jsonSensors = APIservice.createService(SensorInterface.class);
        Call<JsonObject> call = jsonSensors.createSensor(jsonObject);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    Toast.makeText(getApplicationContext(), "Successfully added! ", Toast.LENGTH_LONG).show();
                    mResponseTv.setText("");
                    emptyFiels();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Not successfully added! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void emptyFiels() {
        EditText name = (EditText) findViewById(R.id.sensor_details_value_name);
        EditText type = (EditText) findViewById(R.id.sensor_details_value_type);
        EditText diameter = (EditText) findViewById(R.id.sensor_details_value_diameter);
        EditText description = (EditText) findViewById(R.id.sensor_details_value_description);
        EditText input = (EditText) findViewById(R.id.sensor_details_value_inputPin);
        EditText output = (EditText) findViewById(R.id.sensor_details_value_outputPin);
        EditText status = (EditText) findViewById(R.id.sensor_details_value_statusPin);

        name.setText("");
        type.setText("");
        diameter.setText("");
        description.setText("");
        input.setText("");
        output.setText("");
        status.setText("");
    }
}
