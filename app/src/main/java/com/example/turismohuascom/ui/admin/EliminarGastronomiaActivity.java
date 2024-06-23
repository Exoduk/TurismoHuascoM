package com.example.turismohuascom.ui.admin;

import android.annotation.SuppressLint;
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

public class EliminarGastronomiaActivity extends AppCompatActivity {

    private Spinner spinnerGastronomia;
    private Button btnEliminar;
    private FirebaseFirestore db;
    private List<String> gastronomiaList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_gastronomia);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spinnerGastronomia = findViewById(R.id.spinner_gastronomia);
        btnEliminar = findViewById(R.id.btn_eliminar);
        ImageButton btnatras = findViewById(R.id.btn_back);
        db = FirebaseFirestore.getInstance();
        gastronomiaList = new ArrayList<>();

        loadGastronomia();

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EliminarGastronomiaActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedGastronomia = spinnerGastronomia.getSelectedItem().toString();
                eliminarGastronomia(selectedGastronomia);
            }
        });
    }

    private void loadGastronomia() {
        db.collection("Gastronomia").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        gastronomiaList.add(document.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EliminarGastronomiaActivity.this, android.R.layout.simple_spinner_item, gastronomiaList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerGastronomia.setAdapter(adapter);
                } else {
                    Toast.makeText(EliminarGastronomiaActivity.this, "Error al cargar la gastronomía", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eliminarGastronomia(String gastronomiaId) {
        db.collection("Gastronomia").document(gastronomiaId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EliminarGastronomiaActivity.this, "Gastronomía eliminada", Toast.LENGTH_SHORT).show();
                    gastronomiaList.remove(gastronomiaId);
                    ((ArrayAdapter) spinnerGastronomia.getAdapter()).notifyDataSetChanged();
                } else {
                    Toast.makeText(EliminarGastronomiaActivity.this, "Error al eliminar la gastronomía", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
