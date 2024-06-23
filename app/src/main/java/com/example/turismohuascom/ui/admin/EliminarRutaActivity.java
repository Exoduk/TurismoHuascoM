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

public class EliminarRutaActivity extends AppCompatActivity {

    private Spinner spinnerRutas;
    private Button btnEliminar;
    private FirebaseFirestore db;
    private List<String> rutasList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_ruta);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spinnerRutas = findViewById(R.id.spinner_rutas);
        btnEliminar = findViewById(R.id.btn_eliminar);
        ImageButton btnatras = findViewById(R.id.btn_back);
        db = FirebaseFirestore.getInstance();
        rutasList = new ArrayList<>();

        loadRutas();

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EliminarRutaActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRuta = spinnerRutas.getSelectedItem().toString();
                eliminarRuta(selectedRuta);
            }
        });
    }

    private void loadRutas() {
        db.collection("Rutas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        rutasList.add(document.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EliminarRutaActivity.this, android.R.layout.simple_spinner_item, rutasList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRutas.setAdapter(adapter);
                } else {
                    Toast.makeText(EliminarRutaActivity.this, "Error al cargar las rutas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eliminarRuta(String rutaId) {
        db.collection("Rutas").document(rutaId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EliminarRutaActivity.this, "Ruta eliminada", Toast.LENGTH_SHORT).show();
                    rutasList.remove(rutaId);
                    ((ArrayAdapter) spinnerRutas.getAdapter()).notifyDataSetChanged();
                } else {
                    Toast.makeText(EliminarRutaActivity.this, "Error al eliminar la ruta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
