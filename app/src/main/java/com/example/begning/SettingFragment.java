////
////package com.example.begning;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.os.Handler;
////import android.os.Looper;
////import android.provider.MediaStore;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.ImageView;
////import android.widget.TextView;
////import androidx.annotation.NonNull;
////import androidx.annotation.Nullable;
////import androidx.fragment.app.Fragment;
////
////public class SettingFragment extends Fragment {
////
////    private TextView textView;
////    private ImageView cameraIcon;
////    private Handler handler = new Handler(Looper.getMainLooper());
////
////    @Nullable
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_setting, container, false);
////
////        textView = view.findViewById(R.id.settingText);
////        cameraIcon = view.findViewById(R.id.cameraIcon);
////
////        // Ensure the camera icon is hidden initially
////        cameraIcon.setVisibility(View.GONE);
////
////        // Simulating settings loading
////        handler.postDelayed(() -> {
////            textView.setText("Settings Loaded!");
////            cameraIcon.setVisibility(View.VISIBLE); // Ensure visibility update happens on the main thread
////        }, 3000); // 3 seconds delay
////
////        // Set camera icon click listener
////        cameraIcon.setOnClickListener(v -> openCamera());
////
////        return view;
////    }
////
////    private void openCamera() {
////        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
////            startActivity(intent);
////        }
////    }
////}
//package com.example.begning;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class SettingFragment extends Fragment {
//
//    private static final int CAMERA_REQUEST_CODE = 100;
//    private ImageView capturedImage, cameraIcon;
//    private LinearLayout actionButtons;
//    private Button deleteButton, shareButton;
//    private Uri imageUri; // Store image URI for sharing
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_setting, container, false);
//
//        cameraIcon = view.findViewById(R.id.cameraIcon);
//        capturedImage = view.findViewById(R.id.capturedImage);
//        actionButtons = view.findViewById(R.id.actionButtons);
//        deleteButton = view.findViewById(R.id.deleteButton);
//        shareButton = view.findViewById(R.id.shareButton);
//
//        cameraIcon.setOnClickListener(v -> openCamera());
//
//        deleteButton.setOnClickListener(v -> deleteImage());
//        shareButton.setOnClickListener(v -> shareImage());
//
//        return view;
//    }
//
//    private void openCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
//            startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE && data != null) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            if (photo != null) {
//                capturedImage.setImageBitmap(photo);
//                capturedImage.setVisibility(View.VISIBLE);
//                actionButtons.setVisibility(View.VISIBLE);
//                imageUri = saveImageToStorage(photo); // Save image
//            }
//        }
//    }
//
//    private Uri saveImageToStorage(Bitmap bitmap) {
//        Uri uri = null;
//        try {
//            String fileName = "captured_image_" + System.currentTimeMillis() + ".jpg";
//            OutputStream outputStream;
//            uri = Uri.fromFile(requireActivity().getFileStreamPath(fileName));
//            outputStream = requireActivity().openFileOutput(fileName, requireActivity().MODE_PRIVATE);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return uri;
//    }
//
//    private void deleteImage() {
//        capturedImage.setVisibility(View.GONE);
//        actionButtons.setVisibility(View.GONE);
//        imageUri = null;
//    }
//
//    private void shareImage() {
//        if (imageUri != null) {
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("image/*");
//            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//            startActivity(Intent.createChooser(shareIntent, "Share Image"));
//        }
//    }
//}
package com.example.begning;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.OutputStream;

public class SettingFragment extends Fragment {

    private static final int CAMERA_REQUEST_CODE = 100;
    private ImageView capturedImage, cameraIcon;
    private LinearLayout actionButtons;
    private Button deleteButton, shareButton;
    private Uri imageUri; // Store the URI for the saved image

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        cameraIcon = view.findViewById(R.id.cameraIcon);
        capturedImage = view.findViewById(R.id.capturedImage);
        actionButtons = view.findViewById( R.id.actionButtons);
        deleteButton = view.findViewById(R.id.deleteButton);
        shareButton = view.findViewById(R.id.shareButton);

        cameraIcon.setOnClickListener(v -> openCamera());

        deleteButton.setOnClickListener(v -> deleteImage());
        shareButton.setOnClickListener(v -> shareImage());

        return view;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == requireActivity().RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            if (photo != null) {
                capturedImage.setImageBitmap(photo); // Show image directly
                capturedImage.setVisibility(View.VISIBLE);
                actionButtons.setVisibility(View.VISIBLE); // Ensure buttons are visible

                imageUri = saveImageToStorage(photo); // Save image and get URI
            }
        }
    }


//    private Uri saveImageToStorage(Bitmap bitmap) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put(MediaStore.Images.Media.DISPLAY_NAME, "captured_" + System.currentTimeMillis() + ".jpg");
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
//
//            Uri uri = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            if (uri != null) {
//                OutputStream outputStream = requireActivity().getContentResolver().openOutputStream(uri);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                outputStream.flush();
//                outputStream.close();
//                Toast.makeText(requireContext(), "Image saved successfully!", Toast.LENGTH_SHORT).show();
//                return uri;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    private Uri saveImageToStorage(Bitmap bitmap) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "captured_" + System.currentTimeMillis() + ".jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");

            Uri uri = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                OutputStream outputStream = requireActivity().getContentResolver().openOutputStream(uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                return uri; // Return valid URI
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void deleteImage() {
        if (imageUri != null) {
            requireActivity().getContentResolver().delete(imageUri, null, null);
            capturedImage.setVisibility(View.GONE);
            actionButtons.setVisibility(View.GONE);
            imageUri = null;
            Toast.makeText(requireContext(), "Image deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareImage() {
        if (imageUri != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        }
    }
}
