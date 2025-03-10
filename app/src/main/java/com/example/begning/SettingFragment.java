//
//package com.example.begning;
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
//
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
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import java.io.IOException;
//
//public class SettingFragment extends Fragment {
//
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private ImageView selectedImage, galleryIcon;
//    private LinearLayout actionButtons;
//    private Button deleteButton, shareButton;
//    private Uri imageUri;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_setting, container, false);
//
//        galleryIcon = view.findViewById(R.id.cameraIcon); // Change icon action to open gallery
//        selectedImage = view.findViewById(R.id.capturedImage);
//        actionButtons = view.findViewById(R.id.actionButtons);
//        deleteButton = view.findViewById(R.id.deleteButton);
//        shareButton = view.findViewById(R.id.shareButton);
//
//        galleryIcon.setOnClickListener(v -> openGallery());
//
//        deleteButton.setOnClickListener(v -> deleteImage());
//        shareButton.setOnClickListener(v -> shareImage());
//
//        return view;
//    }
//
//    private void openGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == requireActivity().RESULT_OK && data != null) {
//            imageUri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
//                selectedImage.setImageBitmap(bitmap);
//                selectedImage.setVisibility(View.VISIBLE);
//                actionButtons.setVisibility(View.VISIBLE);
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void deleteImage() {
//        if (imageUri != null) {
//            selectedImage.setVisibility(View.GONE);
//            actionButtons.setVisibility(View.GONE);
//            imageUri = null;
//            Toast.makeText(requireContext(), "Image removed!", Toast.LENGTH_SHORT).show();
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

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.io.IOException;

public class SettingFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImage, galleryIcon;
    private LinearLayout actionButtons;
    private Button deleteButton, shareButton;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        galleryIcon = view.findViewById(R.id.cameraIcon); // Open gallery on click
        selectedImage = view.findViewById(R.id.capturedImage);
        actionButtons = view.findViewById(R.id.actionButtons);
        deleteButton = view.findViewById(R.id.deleteButton);
        shareButton = view.findViewById(R.id.shareButton);

        galleryIcon.setOnClickListener(v -> openGallery());

        deleteButton.setOnClickListener(v -> deleteImage());
        shareButton.setOnClickListener(v -> shareImage());

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == requireActivity().RESULT_OK && data != null) {
            imageUri = data.getData();

            // Show loading message
            Toast.makeText(requireContext(), "Loading image...", Toast.LENGTH_SHORT).show();

            // Run image processing in a background thread
            new Thread(() -> {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);

                    // Update UI on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        selectedImage.setImageBitmap(bitmap);
                        selectedImage.setVisibility(View.VISIBLE);
                        actionButtons.setVisibility(View.VISIBLE);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
                    );
                }
            }).start();
        }
    }

    private void deleteImage() {
        if (imageUri != null) {
            selectedImage.setVisibility(View.GONE);
            actionButtons.setVisibility(View.GONE);
            imageUri = null;
            Toast.makeText(requireContext(), "Image removed!", Toast.LENGTH_SHORT).show();
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
