
package com.example.begning;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private TextView welcomeText;
    private String textToType = "Welcome to Our App!";
    private int index = 0;
    private long delay = 150; // Typing speed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        welcomeText = findViewById(R.id.welcomeText);

        // Start typing effect
        autoType();

        // Delay transition for 3 seconds (3000ms)
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash.this, MainActivity.class); // Change to your next activity
            startActivity(intent);
            finish(); // Close SplashActivity
        }, 3000);
    }

    private void autoType() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < textToType.length()) {
                    welcomeText.setText(welcomeText.getText().toString() + textToType.charAt(index));
                    index++;
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }
}
