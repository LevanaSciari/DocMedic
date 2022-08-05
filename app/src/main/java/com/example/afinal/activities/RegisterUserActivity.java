package com.example.afinal.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.example.afinal.User;
import com.example.afinal.activities.MainActivity;
import com.example.afinal.networking.Requests;

import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterUserActivity extends AppCompatActivity  {

    String[] employeeOptions = {"עובד פנימי", "עובד חיצוני", "סטודנט"};
    String[] jobTitles = {"מפתחת", "מתקשר", "מנקה"};

    private Spinner spinnerEmployeeType;
    private Spinner spinnerJobTitle;
    View layoutEtFirstName, layoutEtLastName, layoutEtMail, layoutEtPhoneNumber, layoutEtEmployeeNum, layoutEtIdNum;
    View layoutBtnSubmit;
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
        layoutEtFirstName = findViewById(R.id.layoutEtFirstName);
        layoutEtLastName = findViewById(R.id.layoutEtLastName);
        layoutEtMail = findViewById(R.id.layoutEtMail);
        layoutEtPhoneNumber = findViewById(R.id.layoutEtPhoneNumber);
        layoutEtEmployeeNum = findViewById(R.id.layoutEtEmployeeNum);
        layoutEtIdNum = findViewById(R.id.layoutEtIdNum);
        layoutBtnSubmit = findViewById(R.id.layoutBtnSubmit);
        firstName = layoutEtFirstName.findViewById(R.id.editText);
        lastName = layoutEtLastName.findViewById(R.id.editText);
        emailEditText = layoutEtMail.findViewById(R.id.editText);
        userId = layoutEtIdNum.findViewById(R.id.editText);
        cell_phone = layoutEtPhoneNumber.findViewById(R.id.editText);
        uniqueIdEt = layoutEtEmployeeNum.findViewById(R.id.editText);
        spinnerEmployeeType = findViewById(R.id.spinnerEmployeeType);
        spinnerJobTitle = findViewById(R.id.spinnerJobTitle);
        // Setting texts
        firstName.setHint("שם פרטי");
        lastName.setHint("שם משפחה");
        emailEditText.setHint("מייל");
        userId.setHint("מספר ת.ז");
        cell_phone.setHint("מספר טלפון");
        uniqueIdEt.setHint("מספר עובד");
        ((TextView) layoutBtnSubmit.findViewById(R.id.textView)).setText("המשך");
        // Setting listeners
        layoutBtnSubmit.setOnClickListener(v -> {
            attemptRegistration();
        });
        // Filling spinners with data
        ArrayAdapter aaEmployeeType = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, employeeOptions);
        aaEmployeeType.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerEmployeeType.setAdapter(aaEmployeeType);
        ArrayAdapter aaJobTitle = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, jobTitles);
        aaJobTitle.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerJobTitle.setAdapter(aaJobTitle);
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
                spinnerJobTitle.getSelectedItem().toString(),
                new Requests.OnServerResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                User.getInstance().parseUser(response);
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




