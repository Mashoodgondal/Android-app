////package com.example.begning;
////
////import android.os.Bundle;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.webkit.WebSettings;
////import android.webkit.WebView;
////import android.widget.Button;
////
////import androidx.fragment.app.Fragment;
////
////public class HomeFragment extends Fragment {
////
////    private WebView webViewChatGPT;
////
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_home, container, false);
////
////        // Find button and WebView
////        Button btnChatGPT = view.findViewById(R.id.btnChatGPT);
////        webViewChatGPT = view.findViewById(R.id.webViewChatGPT);
////
////        // Enable JavaScript for WebView
////        WebSettings webSettings = webViewChatGPT.getSettings();
////        webSettings.setJavaScriptEnabled(true);
////
////        // Set button click listener
////        btnChatGPT.setOnClickListener(v -> {
////            // Show WebView and load ChatGPT URL
////            webViewChatGPT.setVisibility(View.VISIBLE);
////            webViewChatGPT.loadUrl("https://chat.openai.com/");
////
////            // Optionally hide button once WebView is displayed
////            btnChatGPT.setVisibility(View.GONE);
////        });
////
////        return view;
////    }
////}
//package com.example.begning;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//
//import androidx.fragment.app.Fragment;
//
//public class HomeFragment extends Fragment {
//
//    private WebView webViewChatGPT;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        // Find button and WebView
//        Button btnChatGPT = view.findViewById(R.id.btnChatGPT);
//        webViewChatGPT = view.findViewById(R.id.webViewChatGPT);
//
//        // Enable JavaScript for WebView
//        WebSettings webSettings = webViewChatGPT.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true); // Enable local storage support
//
//        // Set WebViewClient to keep navigation inside the WebView
//        webViewChatGPT.setWebViewClient(new WebViewClient());
//
//        // Set button click listener
//        btnChatGPT.setOnClickListener(v -> {
//            // Show WebView and load ChatGPT URL inside the app
//            webViewChatGPT.setVisibility(View.VISIBLE);
//            webViewChatGPT.loadUrl("https://chat.openai.com/");
//
//            // Optionally hide button once WebView is displayed
//            btnChatGPT.setVisibility(View.GONE);
//        });
//
//        return view;
//    }
//}
package com.example.begning;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private WebView webViewChatGPT;
    private Button btnChatGPT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find button and WebView
        btnChatGPT = view.findViewById(R.id.btnChatGPT);
        webViewChatGPT = view.findViewById(R.id.webViewChatGPT);

        // Enable JavaScript and local storage
        WebSettings webSettings = webViewChatGPT.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Set WebViewClient to keep navigation inside WebView
        webViewChatGPT.setWebViewClient(new WebViewClient());

        // Handle back button inside WebView
        webViewChatGPT.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && webViewChatGPT.canGoBack()) {
                webViewChatGPT.goBack();
                return true;
            }
            return false;
        });

        // Set button click listener
        btnChatGPT.setOnClickListener(v -> {
            webViewChatGPT.setVisibility(View.VISIBLE);
            webViewChatGPT.loadUrl("https://chat.openai.com/");
            btnChatGPT.setVisibility(View.GONE);
        });

        return view;
    }
}
