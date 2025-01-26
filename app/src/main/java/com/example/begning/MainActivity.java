
package com.example.begning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    TextView signupText;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        btnLogin = findViewById(R.id.btnLogin);
        signupText = findViewById(R.id.Signup_text);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users"); // "users" node

        // Navigate to Signup Screen
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Signup.class);
            startActivity(intent);
        });

        // Login Button Listener
        btnLogin.setOnClickListener(v -> checkUser());
    }

    // Validate Email
    public boolean validateEmail() {
        String val = email.getText().toString().trim();
        if (val.isEmpty()) {
            email.setError("Email cannot be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    // Validate Password
    public boolean validatePassword() {
        String val = password.getText().toString().trim();
        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    // Check User Credentials in Firebase
    public void checkUser() {
        if (!validateEmail() || !validatePassword()) return;

        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        Query checkDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            showToast("Login Successful");

                            // Redirect to Main Screen
                            Intent intent = new Intent(MainActivity.this, Main.class);
                            startActivity(intent);
                            finish();
                        } else {
                            password.setError("Invalid password");
                            password.requestFocus();
                        }
                    }
                } else {
                    email.setError("No user found with this email");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("Database Error: " + error.getMessage());
            }
        });
    }

    // Helper method for showing Toast messages
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }
}

