package com.example.turismohuascom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class Presentacion extends AppCompatActivity {

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
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videopresentacion);
        videoView.setVideoURI(videoUri);

        // Listener para ajustar el video y reproducirlo automáticamente
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                videoView.start();
            }
        });

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
