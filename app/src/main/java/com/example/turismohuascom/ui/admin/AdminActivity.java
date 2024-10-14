package com.example.turismohuascom.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.turismohuascom.MainActivity;
import com.example.turismohuascom.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button btnAlojamientos = findViewById(R.id.btn_alojamientos);
        Button btnAlojamientos2 = findViewById(R.id.btn_alojamientos2);
        Button btnGastronomia = findViewById(R.id.btn_gastronomia);
        Button btnGastronomia2 = findViewById(R.id.btn_gastronomia2);
        Button btnRutas = findViewById(R.id.btn_rutas);
        Button btnRutas2 = findViewById(R.id.btn_rutas2);
        Button btnServicios = findViewById(R.id.btn_servicios);
        Button btnServicios2 = findViewById(R.id.btn_servicios2);
        Button btnUpdateDesierto = findViewById(R.id.btn_update_desierto);
        ImageButton btnAtras = findViewById(R.id.btn_back);

        btnServicios2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EliminarServicioActivity.class);
                startActivity(intent);
            }
        });

        btnGastronomia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EliminarGastronomiaActivity.class);
                startActivity(intent);
            }
        });

        btnRutas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EliminarRutaActivity.class);
                startActivity(intent);
            }
        });

        btnAlojamientos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EliminarAlojamientoActivity.class);
                startActivity(intent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, FormularioRutaActivity.class);
                startActivity(intent);
            }
        });

        btnServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, FormularioServicioActivity.class);
                startActivity(intent);
            }
        });
        btnGastronomia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, FormularioGastronomiaActivity.class);
                startActivity(intent);
            }
        });

        btnAlojamientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, FormularioAlojamientoActivity.class);
                startActivity(intent);
            }
        });

        // Nuevo botón para actualizar Desierto Florido
        btnUpdateDesierto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, UpdateDesiertoFloridoActivity.class);
                startActivity(intent);
            }
        });
    }
}
