package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DTO.LoginResponse;
import com.example.myapplication.DTO.User;
import com.example.myapplication.Page.CreateAccountActivity;
import com.example.myapplication.RetrofitClient.RetrofitClient;
import com.example.myapplication.Service.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        TextInputEditText username = findViewById(R.id.username);
        TextInputEditText password = findViewById(R.id.password);

        Button btnSignIn = findViewById(R.id.signIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Button clicked");
                System.out.println("Username : " + username.getText());
                System.out.println("Password : " + password.getText());
                Call<LoginResponse> listCall = apiService.getByEmailAndPassword(Objects.requireNonNull(username.getText()).toString(),
                        Objects.requireNonNull(password.getText()).toString());

                listCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        System.out.println("response: " + response);

                        if(response.body().getMessage().equals("User with email not found")){
                            Toast.makeText(MainActivity.this,
                                    "User with email not found",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            if (response.isSuccessful() && response.body() != null) {

                                LoginResponse loginResponse = response.body();

                                User user = loginResponse.getDetails();
                                System.out.println(user.getFirstName() + " this is the first name");

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("error input:", t.getMessage());
                    }
                });
            }
        });

        Button btnSignUp = findViewById(R.id.signUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}