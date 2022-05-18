package com.example.wahdahpharmacy.ui.customer.activities.customerAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wahdahpharmacy.R;

import java.util.Objects;

public class CustomerScreen extends AppCompatActivity {
    Toolbar toolbar;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);
        register = findViewById(R.id.customer_screen_registerButton);
        login = findViewById(R.id.customer_screen_loginButton);
        toolbar = findViewById(R.id.customer_screen_toolbar);
        setSupportActionBar(toolbar);
        toolbarSetter(Objects.requireNonNull(getSupportActionBar()));
        login.setOnClickListener(v -> {
            startActivity(new Intent(CustomerScreen.this, CustomerLogin.class));
        });
        register.setOnClickListener(v -> startActivity(new Intent(CustomerScreen.this, CustomerRegister.class)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return false;
        }
    }

    private void toolbarSetter(ActionBar toolbar) {
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
        toolbar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24dp);
        toolbar.setTitle(getString(R.string.customer));
    }
}