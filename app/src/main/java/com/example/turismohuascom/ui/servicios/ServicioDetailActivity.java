package com.example.turismohuascom.ui.servicios;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
        ImageView imageViewServicio = findViewById(R.id.imageViewServicio);
        TextView textViewDireccion = findViewById(R.id.textViewDireccion);
        TextView textViewCorreo = findViewById(R.id.textViewCorreo);
        TextView textViewTelefono = findViewById(R.id.textViewTelefono);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //Button buttonMapa = findViewById(R.id.buttonVerEnMapa);
        ImageButton buttonMapa2 = findViewById(R.id.buttonVerEnMapa);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textViewServicioTitulo.setText(extras.getString("titulo"));
            textViewDetallesServicio.setText(extras.getString("descripcion"));
            textViewDireccion.setText(extras.getString("direccion"));
            textViewCorreo.setText(extras.getString("correo"));
            textViewTelefono.setText(extras.getString("telefono"));

            // Cargar imagen usando Glide
            Glide.with(this)
                    .load(extras.getString("imagen"))
                    .into(imageViewServicio);


        }


        // Añadir funcionalidad para abrir Google Maps al hacer clic en la dirección
        buttonMapa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = extras.getString("direccion");
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

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

