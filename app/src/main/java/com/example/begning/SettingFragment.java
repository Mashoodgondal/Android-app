//
//package com.example.begning;
//
//import android.content.ContentValues;
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
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import java.io.OutputStream;
//
//public class SettingFragment extends Fragment {
//
//    private static final int CAMERA_REQUEST_CODE = 100;
//    private ImageView capturedImage, cameraIcon;
//    private LinearLayout actionButtons;
//    private Button deleteButton, shareButton;
//    private Uri imageUri; // Store the URI for the saved image
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_setting, container, false);
//
//        cameraIcon = view.findViewById(R.id.cameraIcon);
//        capturedImage = view.findViewById(R.id.capturedImage);
//        actionButtons = view.findViewById( R.id.actionButtons);
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
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == requireActivity().RESULT_OK && data != null) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//
//            if (photo != null) {
//                capturedImage.setImageBitmap(photo); // Show image directly
//                capturedImage.setVisibility(View.VISIBLE);
//                actionButtons.setVisibility(View.VISIBLE); // Ensure buttons are visible
//
//                imageUri = saveImageToStorage(photo); // Save image and get URI
//            }
//        }
//    }
//
//
//
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
//                return uri; // Return valid URI
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    private void deleteImage() {
//        if (imageUri != null) {
//            requireActivity().getContentResolver().delete(imageUri, null, null);
//            capturedImage.setVisibility(View.GONE);
//            actionButtons.setVisibility(View.GONE);
//            imageUri = null;
//            Toast.makeText(requireContext(), "Image deleted!", Toast.LENGTH_SHORT).show();
//        }
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

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.io.OutputStream;

public class SettingFragment extends Fragment {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE = 200;

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
        actionButtons = view.findViewById(R.id.actionButtons);
        deleteButton = view.findViewById(R.id.deleteButton);
        shareButton = view.findViewById(R.id.shareButton);

        cameraIcon.setOnClickListener(v -> checkCameraPermission());

        deleteButton.setOnClickListener(v -> deleteImage());
        shareButton.setOnClickListener(v -> shareImage());

        return view;
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(requireContext(), "No camera app found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(requireContext(), "Camera permission denied!", Toast.LENGTH_SHORT).show();
            }
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
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share Image via"));
        } else {
            Toast.makeText(requireContext(), "No image to share!", Toast.LENGTH_SHORT).show();
        }
    }
}
