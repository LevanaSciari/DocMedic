package com.example.afinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.afinal.R;
import com.example.afinal.networking.Requests;
import com.example.afinal.utils.CSVManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdministratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
    }

    public void mashov(View view) {

    }

    public void signRule(View view) {
        exportSignatures();
    }

    public void diagram(View view) {

    }

    public void back(View view) {
        Intent i = new Intent(AdministratorActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void exportSignatures() {
        Requests.sendGetTerms(getApplicationContext(), new Requests.OnServerResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    CSVManager csvManager = new CSVManager(getApplicationContext());
                    csvManager.exportData((JSONArray) response.get("data"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
}
