package com.example.turismohuascom.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.turismohuascom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EliminarAlojamientoActivity extends AppCompatActivity {

    private Spinner spinnerAlojamientos;
    private Button btnEliminar;
    private FirebaseFirestore db;
    private List<String> alojamientosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_alojamiento);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spinnerAlojamientos = findViewById(R.id.spinner_alojamientos);
        btnEliminar = findViewById(R.id.btn_eliminar);
        ImageButton btnatras = findViewById(R.id.btn_back);
        db = FirebaseFirestore.getInstance();
        alojamientosList = new ArrayList<>();

        loadAlojamientos();

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EliminarAlojamientoActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedAlojamiento = spinnerAlojamientos.getSelectedItem().toString();
                eliminarAlojamiento(selectedAlojamiento);
            }
        });
    }

    private void loadAlojamientos() {
        db.collection("Alojamientos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        alojamientosList.add(document.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EliminarAlojamientoActivity.this, android.R.layout.simple_spinner_item, alojamientosList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAlojamientos.setAdapter(adapter);
                } else {
                    Toast.makeText(EliminarAlojamientoActivity.this, "Error al cargar los alojamientos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eliminarAlojamiento(String alojamientoId) {
        db.collection("Alojamientos").document(alojamientoId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EliminarAlojamientoActivity.this, "Alojamiento eliminado", Toast.LENGTH_SHORT).show();
                    alojamientosList.remove(alojamientoId);
                    ((ArrayAdapter) spinnerAlojamientos.getAdapter()).notifyDataSetChanged();
                } else {
                    Toast.makeText(EliminarAlojamientoActivity.this, "Error al eliminar el alojamiento", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
