package com.example.turismohuascom.ui.rutas;

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

public class RutaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_detail);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
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

        TextView textViewRutaTitulo = findViewById(R.id.textViewRutaTituloDetail);
        TextView textViewDetallesLugar = findViewById(R.id.textViewDetallesLugar);
        TextView textViewPoseeBano = findViewById(R.id.textViewPoseeBano);
        TextView textViewAptoCamping = findViewById(R.id.textViewAptoCamping);
        ImageView imageViewExtra = findViewById(R.id.imageViewExtra);
        TextView textViewDireccion = findViewById(R.id.textViewDireccion);  // Dirección

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textViewRutaTitulo.setText(extras.getString("titulo"));
            textViewDetallesLugar.setText(extras.getString("descripcion"));
            textViewPoseeBano.setText(extras.getString("bano"));
            textViewAptoCamping.setText(extras.getString("camping"));

            // Cargar imagen usando Glide
            Glide.with(this)
                    .load(extras.getString("imagen"))
                    .into(imageViewExtra);

            textViewDireccion.setText(extras.getString("direccion"));

            // Añadir funcionalidad para abrir Google Maps al hacer clic en la dirección
            textViewDireccion.setOnClickListener(new View.OnClickListener() {
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
}
