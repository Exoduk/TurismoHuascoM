package com.example.turismohuascom.ui.rutas;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.turismohuascom.R;

public class RutaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_detail);
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
        TextView textViewRutaDescripcion = findViewById(R.id.textViewRutaDescripcionDetail);
        TextView textViewRutaUbicacion = findViewById(R.id.textViewRutaUbicacionDetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textViewRutaTitulo.setText(extras.getString("titulo"));
            textViewRutaDescripcion.setText(extras.getString("descripcion"));
            textViewRutaUbicacion.setText(extras.getString("ubicacion"));

        }
    }
}
