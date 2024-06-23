package com.example.turismohuascom.ui.servicios;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.turismohuascom.MainActivity;
import com.example.turismohuascom.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.checkerframework.checker.nullness.qual.NonNull;

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
        BottomNavigationView navView = findViewById(R.id.nav_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textViewServicioTitulo.setText(extras.getString("titulo"));
            textViewDetallesServicio.setText(extras.getString("descripcion"));
            textViewDireccion.setText(extras.getString("direccion"));
            textViewCorreo.setText(extras.getString("correo"));
            textViewTelefono.setText(extras.getString("telefono"));
        }

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Obtener el ID del elemento seleccionado
                int id = item.getItemId();

                // Iniciar MainActivity y pasar el ID del elemento seleccionado
                Intent intent = new Intent(ServicioDetailActivity.this, MainActivity.class);
                intent.putExtra("selectedItemId", id);
                startActivity(intent);

                return true;
            }
        });


    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}

