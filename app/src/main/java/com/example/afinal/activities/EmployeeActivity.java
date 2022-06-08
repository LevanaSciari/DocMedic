package com.example.afinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.afinal.R;
import com.example.afinal.User;

public class EmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
    }

    public void mashov(View view) {

    }

    public void signRuleEmployee(View view) {
        Intent i = new Intent(EmployeeActivity.this, SignTermsActivity.class);
        startActivity(i);
    }

    public void back(View view) {

    }
}
