package com.example.turismohuascom.ui.gastronomia;

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

public class GastronomiaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastronomia_detail);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        hideNavigationBar();

        // Buscar el botón de retroceso
        ImageButton backButton = findViewById(R.id.buttonBack);

        // Manejar el clic en el botón de retroceso para terminar la actividad actual
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView textViewGastronomiaTitulo = findViewById(R.id.textViewGastronomiaTituloDetail);
        TextView textViewDetallesGastronomia = findViewById(R.id.textViewDetallesGastronomia);
        ImageView imageViewExtra = findViewById(R.id.imageViewExtra);
        TextView textViewDireccion = findViewById(R.id.textViewDireccion);  // Dirección
        TextView textViewTelefono = findViewById(R.id.textViewTelefono);  // Teléfono
        TextView textViewCorreo = findViewById(R.id.textViewCorreo);  // Correo electrónico
        ImageView imageViewMapa = findViewById(R.id.iconoMapa);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textViewGastronomiaTitulo.setText(extras.getString("titulo"));
            textViewDetallesGastronomia.setText(extras.getString("descripcion"));

            // Cargar imagen usando Glide
            Glide.with(this)
                    .load(extras.getString("imagen"))
                    .into(imageViewExtra);

            textViewDireccion.setText(extras.getString("direccion"));
            textViewTelefono.setText(extras.getString("telefono"));
            textViewCorreo.setText(extras.getString("correo"));

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

            // Añadir funcionalidad para llamar al teléfono al hacer clic en él
            /*
            textViewTelefono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phoneNumber = extras.getString("telefono");
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            });


            // Añadir funcionalidad para abrir el correo electrónico al hacer clic en él
            textViewCorreo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = extras.getString("correo");
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + email));
                    startActivity(intent);
                }
            });

            */
        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
