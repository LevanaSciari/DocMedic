package com.example.afinal.activities;
import static com.example.afinal.User.EMPLOYEE_TYPE_EXTERNAL;
import static com.example.afinal.User.EMPLOYEE_TYPE_INTERNAL;
import static com.example.afinal.User.EMPLOYEE_TYPE_STUDENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.afinal.R;
import com.example.afinal.User;
import com.example.afinal.networking.Requests;

import org.json.JSONObject;

public class SignTermsActivity extends AppCompatActivity {

    private View layoutBtnAgree;
    private TextView tvTitle;
    private TextView tvTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_terms);
        // Finding views
        tvTitle = findViewById(R.id.tvTitle);
        tvTerms = findViewById(R.id.tvTerms);
        layoutBtnAgree = findViewById(R.id.layoutBtnAgree);
        // Setting listeners
        layoutBtnAgree.setOnClickListener(v -> {
            sendAcceptTermsRequest();
        });
        // Setting texts
        ((TextView) layoutBtnAgree.findViewById(R.id.textView)).setText("מאשר");
        // Showing terms by employee type
        switch (User.getInstance().employeeType) {
            case EMPLOYEE_TYPE_INTERNAL:
                tvTitle.setText("הנחיות עבודה למטפלים");
                tvTerms.setText(Html.fromHtml(getResources().getString(R.string.scrollTextMetaplim)));
                break;
            case EMPLOYEE_TYPE_EXTERNAL:
                tvTitle.setText("הנחיות עבודה למטפלים חיצוניים");
                tvTerms.setText(Html.fromHtml(getResources().getString(R.string.scrollTextMetaplimHotz)));
                break;
            case EMPLOYEE_TYPE_STUDENT:
                tvTitle.setText("הנחיות עבודה לסטודנטים");
                tvTerms.setText(Html.fromHtml(getResources().getString(R.string.scrollTextStudent)));
                break;
        }
    }

    /**
     * Sends accept terms request, in case of success starts employee activity
     */
    private void sendAcceptTermsRequest() {
        Requests.sendTermsRequest(getApplicationContext(), User.getInstance().uniqueId, User.getInstance().jobTitle, new Requests.OnServerResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                User.getInstance().didAgreeTerms = true;
                Intent internalIntent = new Intent(SignTermsActivity.this, EmployeeActivity.class);
                startActivity(internalIntent);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

}
