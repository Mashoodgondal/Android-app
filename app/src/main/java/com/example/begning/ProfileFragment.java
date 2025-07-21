package com.example.begning;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 100;
    private static final String CHANNEL_ID = "recipe_channel";

    private Button btnChooseImage, btnGetRecipe;
    private TextView tvRecipeResult;
    private Uri selectedImageUri;

    // === API INTERFACE ===
    interface RecipeApi {
        @Multipart
        @POST("/recipes/from_image")
        Call<RecipeResponse> getRecipeFromImage(@Part MultipartBody.Part image);
    }

    // === API MODELS ===
    public static class RecipeResponse {
        public boolean success;
        public Recipe recipe;
        public String message;
    }

    public static class Recipe {
        public String name;
        public String description;
        public List<String> ingredients;
        public List<String> steps;
        public int prep_time;
        public int cook_time;
        public int total_time;
        public int people;
        public String difficulty;
        public String style;
        public List<String> diet_info;
        public String nutrition;
        public List<String> tips;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnGetRecipe = view.findViewById(R.id.btnGetRecipe);
        tvRecipeResult = view.findViewById(R.id.tvRecipeResult);

        createNotificationChannel();

        btnChooseImage.setOnClickListener(v -> openImagePicker());

        btnGetRecipe.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                uploadImageAndGetRecipe(selectedImageUri);
            } else {
                Toast.makeText(getContext(), "Please choose an image first", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            Toast.makeText(getContext(), "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageAndGetRecipe(Uri imageUri) {
        try {
            File imageFile = createTempFileFromUri(imageUri);
            String mimeType = requireContext().getContentResolver().getType(imageUri);

            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), imageFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://reciepe-fastapi-backend.vercel.app")
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RecipeApi api = retrofit.create(RecipeApi.class);
            Call<RecipeResponse> call = api.getRecipeFromImage(body);

            call.enqueue(new Callback<RecipeResponse>() {
                @Override
                public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().success) {
                        showRecipe(response.body().recipe);
//                        showNotification(response.body().recipe.name);
                    } else {
                        tvRecipeResult.setText("Failed to get recipe.");
                    }
                }

                @Override
                public void onFailure(Call<RecipeResponse> call, Throwable t) {
                    tvRecipeResult.setText("Error: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            tvRecipeResult.setText("File error: " + e.getMessage());
        }
    }

    private File createTempFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
        String fileName = "temp_image." + MimeTypeMap.getSingleton().getExtensionFromMimeType(
                requireContext().getContentResolver().getType(uri));

        File tempFile = new File(requireContext().getCacheDir(), fileName);
        tempFile.createNewFile();

        OutputStream outputStream = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return tempFile;
    }

    private void showRecipe(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append("🍽️ **").append(recipe.name).append("**\n\n");
        sb.append("📝 ").append(recipe.description).append("\n\n");

        sb.append("🧂 Ingredients:\n");
        for (String ing : recipe.ingredients) sb.append("• ").append(ing).append("\n");

        sb.append("\n👨‍🍳 Steps:\n");
        for (String step : recipe.steps) sb.append("• ").append(step).append("\n");

        sb.append("\n⏱️ Time:\nPrep: ").append(recipe.prep_time)
                .append(" mins | Cook: ").append(recipe.cook_time)
                .append(" mins | Total: ").append(recipe.total_time).append(" mins\n");

        sb.append("\n👥 Serves: ").append(recipe.people);
        sb.append("\n🔥 Difficulty: ").append(recipe.difficulty);
        sb.append("\n🥗 Style: ").append(recipe.style);
        sb.append("\n🧠 Nutrition: ").append(recipe.nutrition).append("\n");

        sb.append("\n💡 Tips:\n");
        for (String tip : recipe.tips) sb.append("• ").append(tip).append("\n");

        tvRecipeResult.setText(sb.toString());
    }

//    private void showNotification(String title) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
////                .setSmallIcon(R.drawable.ic_notification) // Use your own icon
//                .setContentTitle("Recipe Ready!")
//                .setContentText("Recipe for: " + title)
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
//        notificationManager.notify(100, builder.build());
//    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Recipe Channel";
            String description = "Channel for recipe notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }
}