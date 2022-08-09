package com.example.afinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.Signature;
import com.example.afinal.adapters.SignatureAdapter;
import com.example.afinal.networking.Requests;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignatureListActivity extends AppCompatActivity {

    public static final int FILTER_NAME = 0;
    public static final int FILTER_ID = 1;
    public static final int FILTER_TITLE = 2;

    View layoutEtSearch;
    EditText etSearch;
    Button btnName, btnId, btnTitle;
    int selectedFilter;
    SignatureAdapter adapter;
    List<Signature> signatures = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_list);
        // Finding views
        layoutEtSearch = findViewById(R.id.layoutEtSearch);
        etSearch = ((EditText) layoutEtSearch.findViewById(R.id.editText));
        btnName = findViewById(R.id.btnName);
        btnId = findViewById(R.id.btnId);
        btnTitle = findViewById(R.id.btnTitle);
        // Init selected filter
        selectedFilter = FILTER_NAME;
        btnName.setBackgroundColor(getResources().getColor(R.color.teal_200));
        btnId.setBackgroundColor(getResources().getColor(R.color.white));
        btnTitle.setBackgroundColor(getResources().getColor(R.color.white));
        // Getting data from server and then showing it
        getTermsData(new Requests.OnServerResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                signatures = parseSignatures(response);
                RecyclerView recyclerView = findViewById(R.id.rvSignatures);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new SignatureAdapter(getApplicationContext(), new ArrayList<>(signatures));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
        // Init listeners
        btnName.setOnClickListener(v -> {
            selectedFilter = FILTER_NAME;
            if (adapter != null) {
                adapter.mData = filterSignatures(selectedFilter, signatures);
                adapter.notifyDataSetChanged();
                btnName.setBackgroundColor(getResources().getColor(R.color.teal_200));
                btnId.setBackgroundColor(getResources().getColor(R.color.white));
                btnTitle.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        btnId.setOnClickListener(v -> {
            selectedFilter = FILTER_ID;
            if (adapter != null) {
                adapter.mData = filterSignatures(selectedFilter, signatures);
                adapter.notifyDataSetChanged();
                btnName.setBackgroundColor(getResources().getColor(R.color.white));
                btnId.setBackgroundColor(getResources().getColor(R.color.teal_200));
                btnTitle.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        btnTitle.setOnClickListener(v -> {
            selectedFilter = FILTER_TITLE;
            if (adapter != null) {
                adapter.mData = filterSignatures(selectedFilter, signatures);
                adapter.notifyDataSetChanged();
                btnName.setBackgroundColor(getResources().getColor(R.color.white));
                btnId.setBackgroundColor(getResources().getColor(R.color.white));
                btnTitle.setBackgroundColor(getResources().getColor(R.color.teal_200));
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (adapter != null) {
                    adapter.mData = filterSignatures(selectedFilter, signatures);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private List<Signature> filterSignatures(int selectedFilter, List<Signature> signatures) {
        String filter = etSearch.getText().toString().toLowerCase();
        List<Signature> filteredResults = new ArrayList<>();
        for (Signature signature : signatures) {
            boolean didMatch = false;
            switch (selectedFilter) {
                case FILTER_NAME:
                    didMatch = signature.name.toLowerCase().contains(filter);
                    break;
                case FILTER_ID:
                    didMatch = signature.id.toLowerCase().contains(filter);
                    break;
                case FILTER_TITLE:
                    didMatch = signature.jobTitle.toLowerCase().contains(filter);
                    break;
            }
            if (didMatch)
                filteredResults.add(signature);
        }
        return filteredResults;
    }

    private void getTermsData(Requests.OnServerResponse onServerResponse) {
        Requests.sendGetTerms(this, onServerResponse);
    }

    private List<Signature> parseSignatures(JSONObject response) {
        try {
            List<Signature> signatures = new ArrayList<>();
            JSONArray jsonArray = response.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                signatures.add(new Signature(obj));
            }
            return signatures;
        } catch (Exception e) {
             e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

}
