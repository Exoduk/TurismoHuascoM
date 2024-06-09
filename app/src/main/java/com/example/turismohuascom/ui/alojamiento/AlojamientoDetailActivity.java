package com.example.turismohuascom.ui.alojamiento;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
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

        tituloTextView = findViewById(R.id.tituloTextView);
        correoTextView = findViewById(R.id.correoTextView);
        telefonoTextView = findViewById(R.id.telefonoTextView);
        imagenImageView = findViewById(R.id.imagenImageView);
        direccionTextView = findViewById(R.id.direccionTextView);

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
    }
}
