package com.example.afinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.example.afinal.User;
import com.example.afinal.networking.Requests;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private View layoutPhoneNumber, layoutEmployeeNum; // EditTexts
    private View layoutBtnLogin, layoutBtnRegister; // Buttons
    private EditText cellPhoneEt;
    private EditText employeeNumEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Finding views
        layoutPhoneNumber = findViewById(R.id.layoutEtPhoneNumber);
        layoutEmployeeNum = findViewById(R.id.layoutEtEmployeeNum);
        layoutBtnLogin = findViewById(R.id.layoutBtnLogin);
        layoutBtnRegister = findViewById(R.id.layoutBtnRegister);
        cellPhoneEt = layoutPhoneNumber.findViewById(R.id.editText);
        employeeNumEt = layoutEmployeeNum.findViewById(R.id.editText);
        // Setting texts
        ((TextView)layoutBtnLogin.findViewById(R.id.textView)).setText("כניסה");
        ((TextView)layoutBtnRegister.findViewById(R.id.textView)).setText("הרשמה");
        cellPhoneEt.setHint("מספר טלפון");
        employeeNumEt.setHint("מספר עובד");
        // Setting listeners
        layoutBtnLogin.setOnClickListener(v -> {
            attemptLogin();
        });
        layoutBtnRegister.setOnClickListener(view -> {
            startRegistrationFlow();
        });
    }

    private void attemptLogin() {
        String cell_phone_str = cellPhoneEt.getText().toString().trim();
        String number_work_str = employeeNumEt.getText().toString().trim();
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
                Intent employeeIntent = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(employeeIntent);
            } else {
                Intent termsIntent = new Intent(MainActivity.this, SignTermsActivity.class);
                startActivity(termsIntent);
            }
        }
    }

    private void startRegistrationFlow() {
        Intent i = new Intent(MainActivity.this, RegisterUserActivity.class);
        startActivity(i);
    }

}