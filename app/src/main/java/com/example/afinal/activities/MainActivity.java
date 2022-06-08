package com.example.afinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.example.afinal.User;
import com.example.afinal.networking.Requests;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText cellPhoneEt;
    private EditText uniqueIdEt;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Finding views
        cellPhoneEt = findViewById(R.id.cell_phone_login);
        uniqueIdEt = findViewById(R.id.number_work_login);
        loginBtn = findViewById(R.id.login);
        registerBtn = findViewById(R.id.register);
        // Setting listeners
        loginBtn.setOnClickListener(v -> {
            attemptLogin();
        });
    }

    private void attemptLogin() {
        String cell_phone_str = cellPhoneEt.getText().toString().trim();
        String number_work_str = uniqueIdEt.getText().toString().trim();
        Requests.sendLoginRequest(getApplicationContext(), cell_phone_str, number_work_str, new Requests.OnServerResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                onLoginSuccessful(response);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    private void onLoginSuccessful(JSONObject response) {
        User.getInstance().isLoggedIn = true;
        User.getInstance().parseUser(response);

        if (User.getInstance().isManager) {
            Intent i = new Intent(MainActivity.this, AdministratorActivity.class);
            startActivity(i);
        } else {
            if (User.getInstance().didAgreeTerms) {
                Intent internalIntent = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(internalIntent);
            } else {
                Intent termsIntent = new Intent(MainActivity.this, SignTermsActivity.class);
                startActivity(termsIntent);
            }
        }
    }

    public void startRegistrationFlow(View view) {
        Intent i = new Intent(MainActivity.this, RegisterUserActivity.class);
        startActivity(i);
    }

}