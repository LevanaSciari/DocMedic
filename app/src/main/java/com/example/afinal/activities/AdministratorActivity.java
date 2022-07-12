package com.example.afinal.activities;

import static com.example.afinal.activities.WebViewActivity.KEY_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.afinal.R;
import com.example.afinal.networking.Requests;
import com.example.afinal.utils.CSVManager;
import com.example.afinal.utils.MashovConstants;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdministratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
    }

    public void exportSignatures(View view) {
        exportSignatures();
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

    public void showSignatures(View view) {
        Intent intent = new Intent(AdministratorActivity.this, SignatureListActivity.class);
        startActivity(intent);
    }

    public void mashovIsuk(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(MashovConstants.ISUK_CLINIC_RESPONSES_URL));
        startActivity(i);
    }

    public void mashovCommun(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(MashovConstants.COMMUNICATION_CLINIC_RESPONSES_URL));
        startActivity(i);
    }

    public void mashovPhys(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(MashovConstants.PHYSIOTHERAPY_CLINIC_RESPONSES_URL));
        startActivity(i);
    }
}
