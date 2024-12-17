package com.example.begning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);

        View signupText = findViewById(R.id.Signup_text);
        signupText.setOnClickListener(v -> {
            Intent intent =new Intent(MainActivity.this,Signup.class);
            startActivity(intent);
        });
        btn = findViewById(R.id.btnLogin);
        btn.setOnClickListener(v -> {
            Intent intent =new Intent(MainActivity.this,Main.class);
            startActivity(intent);
        });
    }
    public Boolean validateEmail(){
        String val= email.getText().toString();
        if (val.isEmpty()){
            email.setError("Email cannot be Empty");
            return false;
        }else {
            email.setError(null);
            return true;
        }
    }
    public Boolean validatepassword(){
        String val= password.getText().toString();
        if (val.isEmpty()){
            password.setError("Password cannot be Empty");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }
    public void checkUser() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        // Ensure inputs are not empty
        if (Email.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        // Reference to Firebase Realtime Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users"); // Assuming "users" is your database node
        Query checkDatabase = reference.orderByChild("email").equalTo(Email);

        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get user data
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        if (passwordFromDB != null && passwordFromDB.equals(Password)) {

                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                            // Redirect or perform desired action
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
                Toast.makeText(MainActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}