package com.example.begning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private WebView webViewChatGPT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find button and WebView
        Button btnChatGPT = view.findViewById(R.id.btnChatGPT);
        webViewChatGPT = view.findViewById(R.id.webViewChatGPT);

        // Enable JavaScript for WebView
        WebSettings webSettings = webViewChatGPT.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set button click listener
        btnChatGPT.setOnClickListener(v -> {
            // Show WebView and load ChatGPT URL
            webViewChatGPT.setVisibility(View.VISIBLE);
            webViewChatGPT.loadUrl("https://chat.openai.com/");

            // Optionally hide button once WebView is displayed
            btnChatGPT.setVisibility(View.GONE);
        });

        return view;
    }
}
