//package com.example.begning;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//
//import androidx.fragment.app.Fragment;
//
//public class ProfileFragment extends Fragment {
//
//    private Spinner spinnerProfile;
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
//        // Find Spinner by ID
//        spinnerProfile = view.findViewById(R.id.spinnerProfile);
//
//        // Create an array of items to display in the spinner
//        String[] spinnerItems = {"Item 1", "Item 2", "Item 3", "Item 4"};
//
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_spinner_item, spinnerItems);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        spinnerProfile.setAdapter(adapter);
//
//        return view;
//
//    }
//}
package com.example.begning;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private Spinner spinnerProfile;
    private Button btnShare;
    private static final int DELAY_TIME = 1000; // 1 seconds delay

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find Spinner and Button by ID
        spinnerProfile = view.findViewById(R.id.spinnerProfile);
        btnShare = view.findViewById(R.id.btnShare);

        // Create an array of items to display in the spinner
        String[] spinnerItems = {"Item 1", "Item 2", "Item 3", "Item 4"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, spinnerItems);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerProfile.setAdapter(adapter);

        // Set click listener for the Share button
        btnShare.setOnClickListener(v -> {
            // Delay execution using Handler
            new Handler().postDelayed(this::shareContent, DELAY_TIME);
        });

        return view;
    }

    private void shareContent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // Specify content type
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing content!");

        // Show the share dialog
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
