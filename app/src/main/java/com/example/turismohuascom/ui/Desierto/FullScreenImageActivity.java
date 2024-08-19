package com.example.turismohuascom.ui.Desierto;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.turismohuascom.R;
import android.widget.ImageView;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView imageView = findViewById(R.id.fullScreenImageView);

        // Obtener la URL de la imagen desde el Intent
        String imageUrl = getIntent().getStringExtra("image_url");

        // Cargar la imagen usando Glide
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(imageView);
        }
    }
}
