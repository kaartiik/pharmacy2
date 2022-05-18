package com.example.wahdahpharmacy.ui.universalActivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wahdahpharmacy.R;
import com.example.wahdahpharmacy.ui.customer.activities.customerAuth.CustomerScreen;
import com.example.wahdahpharmacy.ui.pharmacist.pharmacistAuth.PharmacistScreen;

import java.util.Objects;

public class Intro extends AppCompatActivity {

    Button mCustomer;
    Button mPharmacist;
    Toolbar toolbar;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        mCustomer =findViewById(R.id.customer_btn);
        mPharmacist= findViewById(R.id.pharmacist_btn);
        toolbar = findViewById(R.id.toolbar_intro);
        setSupportActionBar(toolbar);
        toolbarSetter(Objects.requireNonNull(getSupportActionBar()));

        mCustomer.setOnClickListener(view -> startActivity(new Intent(Intro.this, CustomerScreen.class)));

        mPharmacist.setOnClickListener(view -> startActivity(new Intent(Intro.this, PharmacistScreen.class)));

    }

    private void toolbarSetter(ActionBar toolbar) {
        toolbar.setTitle(getString(R.string.app_name));
    }
}