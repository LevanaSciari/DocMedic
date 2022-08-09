package com.example.afinal.activities;

import static com.example.afinal.User.EMPLOYEE_TYPE_EXTERNAL;
import static com.example.afinal.User.EMPLOYEE_TYPE_INTERNAL;
import static com.example.afinal.User.EMPLOYEE_TYPE_STUDENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinal.R;
import com.example.afinal.User;
import com.example.afinal.utils.MashovConstants;

public class EmployeeActivity extends AppCompatActivity {

    View mashovIsuk, mashovCommun, mashovPhys;
    TextView tvEmployeeTitle, tvTitle;
    ImageView ivEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        // Finding views
        mashovPhys = findViewById(R.id.cvBtnMashovPhys);
        mashovCommun = findViewById(R.id.cvBtnMashovCommun);
        mashovIsuk = findViewById(R.id.cvBtnMashovIsuk);
        tvEmployeeTitle = findViewById(R.id.tvEmployeeTitle);
        tvTitle = findViewById(R.id.tvTitle);
        ivEmployee = findViewById(R.id.ivEmployee);
        // Showing proper ui according to employee type
        switch (User.getInstance().employeeType) {
            case EMPLOYEE_TYPE_INTERNAL:
            case EMPLOYEE_TYPE_EXTERNAL:
                tvEmployeeTitle.setVisibility(View.VISIBLE);
                ivEmployee.setVisibility(View.VISIBLE);
                break;
            case EMPLOYEE_TYPE_STUDENT:
                mashovPhys.setVisibility(View.VISIBLE);
                mashovCommun.setVisibility(View.VISIBLE);
                mashovIsuk.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    // Methods below start @WebViewActivity with passing the proper google form url

    public void mashovIsuk(View view) {
        Intent i = new Intent(EmployeeActivity.this, WebViewActivity.class);
        i.putExtra(WebViewActivity.KEY_URL, MashovConstants.ISUK_CLINIC_FORM_URL);
        startActivity(i);
    }

    public void mashovCommun(View view) {
        Intent i = new Intent(EmployeeActivity.this, WebViewActivity.class);
        i.putExtra(WebViewActivity.KEY_URL, MashovConstants.COMMUNICATION_CLINIC_FORM_URL);
        startActivity(i);
    }

    public void mashovPhys(View view) {
        Intent i = new Intent(EmployeeActivity.this, WebViewActivity.class);
        i.putExtra(WebViewActivity.KEY_URL, MashovConstants.PHYSIOTHERAPY_CLINIC_FORM_URL);
        startActivity(i);
    }
}
