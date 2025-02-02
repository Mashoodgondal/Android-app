//
//package com.example.begning;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//public class SettingFragment extends Fragment {
//
//    private TextView textView;
//    private Handler handler = new Handler(Looper.getMainLooper()); // Handler to update UI
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_setting, container, false);
//
//        textView = view.findViewById(R.id.settingText); // Get reference to TextView
//
//        // Start background thread
//        new Thread(() -> {
//            try {
//                Thread.sleep(3000); // Simulate background work (e.g., fetching settings)
//
//                // Update UI from the Main Thread using Handler
//                handler.post(() -> textView.setText("Settings Loaded!"));
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        return view;
//    }
//}
//package com.example.begning;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//public class SettingFragment extends Fragment {
//
//    private TextView textView;
//    private ImageView cameraIcon;
//    private Handler handler = new Handler(Looper.getMainLooper());
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_setting, container, false);
//
//        textView = view.findViewById(R.id.settingText);
//        cameraIcon = view.findViewById(R.id.cameraIcon);
//
//        // Background thread to simulate loading
//        new Thread(() -> {
//            try {
//                Thread.sleep(3000); // Simulate delay
//
//                // Update UI on the main thread
//                handler.post(() -> {
//                    textView.setText("Settings Loaded!");
//                    cameraIcon.setVisibility(View.VISIBLE); // Show camera icon
//                });
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        // Set camera icon click listener
//        cameraIcon.setOnClickListener(v -> openCamera());
//
//        return view;
//    }
//
//    private void openCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }
//}
package com.example.begning;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    private TextView textView;
    private ImageView cameraIcon;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        textView = view.findViewById(R.id.settingText);
        cameraIcon = view.findViewById(R.id.cameraIcon);

        // Ensure the camera icon is hidden initially
        cameraIcon.setVisibility(View.GONE);

        // Simulating settings loading
        handler.postDelayed(() -> {
            textView.setText("Settings Loaded!");
            cameraIcon.setVisibility(View.VISIBLE); // Ensure visibility update happens on the main thread
        }, 3000); // 3 seconds delay

        // Set camera icon click listener
        cameraIcon.setOnClickListener(v -> openCamera());

        return view;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
