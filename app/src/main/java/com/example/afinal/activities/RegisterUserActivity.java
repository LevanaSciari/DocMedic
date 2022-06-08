package com.example.afinal.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.example.afinal.activities.MainActivity;
import com.example.afinal.networking.Requests;

import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterUserActivity extends AppCompatActivity  {

    String[] employeeOptions = {"Internal", "External", "Student"};

    private Spinner spinnerEmployeeType;
    Button signUpBtn;
    EditText firstName;
    EditText lastName;
    EditText emailEditText;
    EditText userId;
    EditText cell_phone;
    EditText uniqueIdEt;
    Uri imageUri;
    String activity;
    String selectedRegion;
    String email_;

    ArrayList<String> regionsArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        // Finding views
        signUpBtn = findViewById(R.id.signUpBtn);
        firstName = (EditText) findViewById(R.id.FirstName);
        lastName = (EditText) findViewById(R.id.LastName);
        emailEditText = (EditText) findViewById(R.id.mail);
        userId = (EditText) findViewById(R.id.user_id);
        cell_phone = (EditText) findViewById(R.id.cell_phone);
        uniqueIdEt = (EditText) findViewById(R.id.NumberWork);
        spinnerEmployeeType = findViewById(R.id.spinnerEmployeeType);

        // Setting listeners
        signUpBtn.setOnClickListener(v -> {
            attemptRegistration();
        });
        // Filling spinner with data
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, employeeOptions);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerEmployeeType.setAdapter(aa);
    }

    private void attemptRegistration() {
        Requests.sendRegisterRequest(getApplicationContext(),
                firstName.getText().toString(),
                lastName.getText().toString(),
                cell_phone.getText().toString(),
                emailEditText.getText().toString(),
                uniqueIdEt.getText().toString(),
                userId.getText().toString(),
                spinnerEmployeeType.getSelectedItemPosition(),
                new Requests.OnServerResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                onRegisterSuccessful();
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    private void onRegisterSuccessful() {
        Intent i = new Intent(RegisterUserActivity.this, SignTermsActivity.class);
        startActivity(i);
    }

    public void Return(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}




