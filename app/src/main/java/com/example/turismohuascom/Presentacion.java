package com.example.turismohuascom;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Presentacion extends AppCompatActivity {

    private static final int Duracion = 2000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_presentacion);

        VideoView videoView = findViewById(R.id.videoView);
        Button btnSkip = findViewById(R.id.btnSkip);

        // Establecer la ruta del video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.videoPresentacion);
        videoView.setVideoURI(videoUri);

        // Reproducir el video automáticamente
        videoView.start();

        // Listener para saltar el video y abrir MainActivity
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        // Listener para abrir MainActivity al finalizar el video
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                openMainActivity();
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(Presentacion.this, MainActivity.class);
        startActivity(intent);
        finish(); // Cerrar la actividad de presentación
    }
}
