//package com.example.begning;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
//public class HomeFragment extends Fragment {
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//}

package com.example.begning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find button by ID
        Button btnChatGPT = view.findViewById(R.id.btnChatGPT);

        // Set button click listener
        btnChatGPT.setOnClickListener(v -> {
            // Open ChatGPT in a browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.openai.com/"));
            startActivity(intent);
        });

        return view;
    }
}
