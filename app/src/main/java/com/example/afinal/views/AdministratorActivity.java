package com.example.afinal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.afinal.R;
import com.example.afinal.controller.Requests;
import com.example.afinal.models.CSVManager;
import com.example.afinal.models.MashovConstants;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdministratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
    }

    /**
     * Gets all terms signatures and exports them in a csv file format
     */
    public void exportSignatures(View view) {
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

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    // Starts signature list view activity
    public void showSignatures(View view) {
        Intent intent = new Intent(AdministratorActivity.this, SignatureListActivity.class);
        startActivity(intent);
    }

    // Methods below open the google form web page with the proper form url
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
