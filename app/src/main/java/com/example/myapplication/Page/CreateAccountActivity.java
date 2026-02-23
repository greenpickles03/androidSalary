package com.example.myapplication.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DTO.LoginResponse;
import com.example.myapplication.DTO.User;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient.RetrofitClient;
import com.example.myapplication.Service.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        Button btnCreate = findViewById(R.id.createSave);
        TextInputEditText firstname = findViewById(R.id.createFirstName);
        TextInputEditText lastname = findViewById(R.id.createLastName);
        TextInputEditText email = findViewById(R.id.createEmail);
        TextInputEditText password = findViewById(R.id.createPassword);
        TextInputEditText contact = findViewById(R.id.createContactNumber);

        Button btnCancel = findViewById(R.id.createCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentActivity();
            }
        });


        btnCreate.setOnClickListener(v -> {

            User user = new User();
            user.setFirstName(firstname.getText().toString());
            user.setLastName(lastname.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.setContactNumber(contact.getText().toString());
            user.setRole("Client");
            user.setStatus("Active");

            Call<LoginResponse> createUser = apiService.createUser(user);
            createUser.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    LoginResponse loginResponse = response.body();
                    User welcomeUser = loginResponse.getDetails();
                    if (response.isSuccessful()) {
                        System.out.println("User created successfully!");
                        Toast.makeText(CreateAccountActivity.this,
                                "Welcome " + welcomeUser.getFirstName() + " " + welcomeUser.getLastName()
                        ,Toast.LENGTH_LONG).show();
                        startIntentActivity();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    t.printStackTrace();
                    System.out.println("error: " + t.getMessage());
                }
            });


        });
    }

    private void startIntentActivity() {
        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
