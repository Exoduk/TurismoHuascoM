package com.example.turismohuascom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Presentacion extends AppCompatActivity {

    private static final int Duracion = 5000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ocultar la barra de acción (ActionBar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Ocultar la barra de estado
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_presentacion);

        // Handler para redirigir a MainActivity después de 3 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Presentacion.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar la actividad de presentación
            }
        }, Duracion);
    }
}
