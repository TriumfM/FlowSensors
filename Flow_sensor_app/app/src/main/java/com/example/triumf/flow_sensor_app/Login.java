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

public class Login extends Activity {

    private TextView loginView;

    private TextView login_log, register;
    private EditText password, email ;
    private String email_, password_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);



        Button submitBtn = (Button) findViewById(R.id.login_btn);
        login_log = (TextView) findViewById(R.id.login_log);
        register = (TextView) findViewById(R.id.create_new_user);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    email_ = email.getText().toString().trim();
                    password_ = password.getText().toString().trim();

                    if (!email_.isEmpty() && !password_.isEmpty()) {
                        loginUser(email_, password_);
                        login_log.setText("");
                        SensorListView();

                    } else {
                        login_log.setText("Fill all fields!");

                    }
                } catch (Exception e) {
                    login_log.setText("Error!");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }

        });

    }

    private void SensorListView() {
        Intent intentSensorList = new Intent(this, SensorsList.class);
        startActivity(intentSensorList);

    }

    private void Register() {
        Intent intentRegister = new Intent(this, Register.class);
        startActivity(intentRegister);

    }
    public void loginUser(String email, String password) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        UserInterface jsonUser = APIservice.createService(UserInterface.class);

        Call<JsonObject> call = jsonUser.createSensor(jsonObject);

        Log.d("Sensoraaaaaaaa", String.valueOf(jsonObject));
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    Toast.makeText(getApplicationContext(), String.valueOf(response.body()),  Toast.LENGTH_LONG).show();
                    login_log.setText(String.valueOf(response.body()));
                    emptyFiels();
                    SensorListView();


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("123123123",t.getMessage());
            }
        });
    }

    public void emptyFiels() {
        EditText email = (EditText) findViewById(R.id.login_email);
        EditText password = (EditText) findViewById(R.id.login_password);

        email.setText("");
        password.setText("");

    }

}
