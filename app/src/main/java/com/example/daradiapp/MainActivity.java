package com.example.daradiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.daradiapp.Model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputLayout passwordField;
    TextInputLayout usernameField;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordField = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        usernameField = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        ApiList apis = RetrofitClient.getRetrofitClient().create(ApiList.class);
        String username = usernameField.getEditText().getText().toString().trim();
        String password = passwordField.getEditText().getText().toString().trim();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("CMD", "login_user")
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build();

        Call<ArrayList<User>> call = apis.getAllUser(requestBody);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if(response.isSuccessful()) {
                    ArrayList<User> data = response.body();
                    if(data.get(0).getUsername().equals(username) && data.get(0).getPassword().equals(password)) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to send / get data from server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register() {

    }
}