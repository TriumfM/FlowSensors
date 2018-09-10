package com.example.triumf.flow_sensor_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triumf.flow_sensor_app.API.APIservice;
import com.example.triumf.flow_sensor_app.API.UserInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends Activity {

    private TextView loginView;

    private TextView register_log;
    private EditText first_name, last_name, email, password, config_password ;
    private String first_name_, last_name_, email_, password_, config_password_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

            first_name = (EditText) findViewById(R.id.first_name);
            last_name = (EditText) findViewById(R.id.last_name);
            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
            config_password = (EditText) findViewById(R.id.confirm_password);


            Button submitBtn = (Button) findViewById(R.id.register_btn);
            register_log = (TextView) findViewById(R.id.register_log);

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        first_name_ = first_name.getText().toString().trim();
                        last_name_ = last_name.getText().toString().trim();
                        email_ = email.getText().toString().trim();
                        password_ = password.getText().toString().trim();
                        config_password_ = config_password.getText().toString().trim();

                        if (!first_name_.isEmpty() && !last_name_.isEmpty() && !email_.isEmpty() && !password_.isEmpty() && config_password_ != null) {
                            sendPost(first_name_, last_name_, email_, password_ );
                            register_log.setText("");
                        } else {
                            register_log.setText("Fill all fields!");

                        }
                    } catch (Exception e) {
                        Log.d("Errrrrrrrrrrrrror", e.getMessage());
                        register_log.setText("Error!");
                    }
                }
            });

    }


    private void SensorListView() {
        Intent intentRegister = new Intent(this, SensorsList.class);
        startActivity(intentRegister);
    }

    public void sendPost(String first_name, String last_name, String email, String password) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("first_name", first_name);
        jsonObject.addProperty("last_name", last_name);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        UserInterface jsonSensors = APIservice.createService(UserInterface.class);
        Call<JsonObject> call = jsonSensors.createUser(jsonObject);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    Toast.makeText(getApplicationContext(), "Successfully added! ", Toast.LENGTH_LONG).show();
                    register_log.setText("");
                    emptyFiels();
                    SensorListView();

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
        EditText first_name = (EditText) findViewById(R.id.first_name);
        EditText last_name = (EditText) findViewById(R.id.last_name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirm_password = (EditText) findViewById(R.id.confirm_password);

        first_name.setText("");
        last_name.setText("");
        email.setText("");
        password.setText("");
        confirm_password.setText("");

    }
}
