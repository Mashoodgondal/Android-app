package com.example.begning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText username, email, password, confirmPassword;
    Button btnSignUp;
    TextView loginText;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        username = findViewById(R.id.username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.conform_password);
        btnSignUp = findViewById(R.id.btnSignIn);
        loginText = findViewById(R.id.Login_text);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users"); // Store users under a "users" node

        // Sign Up Button Listener
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userConfirmPassword = confirmPassword.getText().toString().trim();

                // Validate input fields
                if (name.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userConfirmPassword.isEmpty()) {
                    Toast.makeText(Signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!userPassword.equals(userConfirmPassword)) {
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save user data to Firebase
                HelperClass helperClass = new HelperClass(name, userEmail, userPassword);
                reference.child(name).setValue(helperClass)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(Signup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            // Redirect to Main screen
                            Intent intent = new Intent(Signup.this, Main.class);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(Signup.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        // Login Text Listener
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
