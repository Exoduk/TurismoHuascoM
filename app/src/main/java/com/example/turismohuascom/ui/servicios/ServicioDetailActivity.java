package com.example.turismohuascom.ui.servicios;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.turismohuascom.R;

public class ServicioDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_detail);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        hideNavigationBar();

        // Busca el botón de retroceso
        ImageButton backButton = findViewById(R.id.buttonBack);

        // Maneja el clic en el botón de retroceso para terminar la actividad actual
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView textViewServicioTitulo = findViewById(R.id.textViewServicioTituloDetail);
        TextView textViewDetallesServicio = findViewById(R.id.textViewDetallesServicio);
        TextView textViewDireccion = findViewById(R.id.textViewDireccion);
        TextView textViewCorreo = findViewById(R.id.textViewCorreo);
        TextView textViewTelefono = findViewById(R.id.textViewTelefono);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textViewServicioTitulo.setText(extras.getString("titulo"));
            textViewDetallesServicio.setText(extras.getString("descripcion"));
            textViewDireccion.setText(extras.getString("direccion"));
            textViewCorreo.setText(extras.getString("correo"));
            textViewTelefono.setText(extras.getString("telefono"));
        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}

