package com.example.wahdahpharmacy.ui.customer.activities.customerAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wahdahpharmacy.R;
import com.example.wahdahpharmacy.util.Constants;
import com.example.wahdahpharmacy.util.Preferences;
import com.example.wahdahpharmacy.util.ProgressDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomerRegister extends AppCompatActivity {
    Toolbar toolbar;
    EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextAddress;
    Button mRegisterBtn;
    TextView mCreateText;
    ProgressDialog progressDialog;
    Preferences preferences;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        toolbar = findViewById(R.id.customer_register_toolbar);
        setSupportActionBar(toolbar);
        preferences = new Preferences(this);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        toolbarSetter(Objects.requireNonNull(getSupportActionBar()));
        editTextEmail = findViewById(R.id.customer_register_editText_email);
        editTextPassword = findViewById(R.id.customer_register_editText_password);
        editTextName = findViewById(R.id.customer_register_editText_name);
        editTextPhone = findViewById(R.id.customer_register_editText_phone);
        mRegisterBtn = findViewById(R.id.customer_register__register_button);
        editTextAddress = findViewById(R.id.customer_register_editText_address);
        mCreateText = findViewById(R.id.customer_register_textView_login_here);
        progressDialog = new ProgressDialog(this);

        mRegisterBtn.setOnClickListener(view -> {

            final String email = editTextEmail.getText().toString().trim();
            final String password = editTextPassword.getText().toString().trim();
            final String fullName = editTextName.getText().toString().trim();
            final String phone = editTextPhone.getText().toString().trim();
            final String address = editTextAddress.getText().toString().trim();


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                editTextEmail.setError("Invalid Email");
                editTextEmail.setFocusable(true);
            } else if (password.length() < 8) {

                editTextPassword.setError("Password length at least 6 characters");
                editTextPassword.setFocusable(true);
            } else if (password.length() > 10) {

                editTextPassword.setError("Password length maximum 10 characters");
                editTextPassword.setFocusable(true);
            } else if (phone.length() > 10) {
                editTextPhone.setError("Enter only 10 Digit Phone Number");
                editTextPhone.setFocusable(true);

            } else if (address.length() < 20) {
                editTextAddress.setError("Address too short");
                editTextAddress.setFocusable(true);
            } else {
                registerUser(email, password, fullName, phone, address);
            }

        });

        mCreateText.setOnClickListener(view -> startActivity(new Intent(CustomerRegister.this, CustomerLogin.class)));
    }

    private void registerUser(String email, String password, String fullName, String phone, String address) {
        progressDialog.show("We are registering you....");
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> userData = new HashMap<>();
                userData.put(Constants.currentUserEmail, email);
                userData.put(Constants.currentUserName, fullName);
                userData.put(Constants.currentUserPhone, phone);
                userData.put(Constants.currentUserAddress, address);
                userData.put(Constants.currentUserType, Constants.uTypeCustomer);
                userData.put(Constants.currentUserPhotoUrl, Constants.noPhotoUrl);
                firestore.collection(Constants.users).document(task.getResult().getUser().getUid()).set(userData).addOnCompleteListener(
                        task1 -> {
                            if (task1.isSuccessful()) {
                                progressDialog.dismiss();
                                startActivity(new Intent(CustomerRegister.this, CustomerLogin.class));
                                finish();
                                Toast.makeText(this, "Customer Registered successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            } else {
                progressDialog.dismiss();
                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    editTextEmail.requestFocus();
                    editTextEmail.setError("Email already in use");
                }
            }
        });
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
        toolbar.setTitle(getString(R.string.customer_register));
    }
}