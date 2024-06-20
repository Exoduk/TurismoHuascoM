package com.example.turismohuascom.ui.alojamiento;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.turismohuascom.R;

public class AlojamientoDetailActivity extends AppCompatActivity {

    private TextView tituloTextView;
    private TextView correoTextView;
    private TextView telefonoTextView;
    private ImageView imagenImageView;
    private TextView direccionTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamiento_detail);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Ocultar la barra de navegación
        hideNavigationBar();

        tituloTextView = findViewById(R.id.tituloTextView);
        correoTextView = findViewById(R.id.correoTextView);
        telefonoTextView = findViewById(R.id.telefonoTextView);
        imagenImageView = findViewById(R.id.imagenImageView);
        direccionTextView = findViewById(R.id.direccionTextView);
        ImageView imageViewMapa = findViewById(R.id.iconoMapa);

        // Obtener los datos del Intent
        String titulo = getIntent().getStringExtra("titulo");
        String correo = getIntent().getStringExtra("correo");
        String telefono = getIntent().getStringExtra("telefono");
        String imagen = getIntent().getStringExtra("imagen");
        String direccion = getIntent().getStringExtra("direccion");

        // Asegurarse de que los datos no sean null antes de usarlos
        if (titulo != null) {
            tituloTextView.setText(titulo);
        }
        if (correo != null) {
            correoTextView.setText(correo);
        }
        if (telefono != null) {
            telefonoTextView.setText(telefono);
        }
        if (direccion != null) {
            direccionTextView.setText(direccion);
        }
        if (imagen != null) {
            Glide.with(this)
                    .load(imagen)
                    .into(imagenImageView);
        } else {
            // Manejar el caso donde la imagen sea null
            imagenImageView.setImageResource(R.drawable.placeholder_image);
        }

        // Busca el botón de retroceso
        ImageButton backButton = findViewById(R.id.buttonBack);

        // Maneja el clic en el botón de retroceso para terminar la actividad actual
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            direccionTextView.setText(extras.getString("direccion"));

            // Añadir funcionalidad para abrir Google Maps al hacer clic en la dirección
            imageViewMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String address = extras.getString("direccion");
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        }
    }

    // Método para ocultar la barra de navegación
    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
