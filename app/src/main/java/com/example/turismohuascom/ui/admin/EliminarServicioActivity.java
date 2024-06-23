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

public class EliminarServicioActivity extends AppCompatActivity {

    private Spinner spinnerServicios;
    private Button btnEliminar;
    private FirebaseFirestore db;
    private List<String> serviciosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_servicio);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spinnerServicios = findViewById(R.id.spinner_servicios);
        btnEliminar = findViewById(R.id.btn_eliminar);
        ImageButton btnatras = findViewById(R.id.btn_back);
        db = FirebaseFirestore.getInstance();
        serviciosList = new ArrayList<>();

        loadServicios();

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EliminarServicioActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedServicio = spinnerServicios.getSelectedItem().toString();
                eliminarServicio(selectedServicio);
            }
        });
    }

    private void loadServicios() {
        db.collection("Servicios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        serviciosList.add(document.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EliminarServicioActivity.this, android.R.layout.simple_spinner_item, serviciosList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerServicios.setAdapter(adapter);
                } else {
                    Toast.makeText(EliminarServicioActivity.this, "Error al cargar los servicios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eliminarServicio(String servicioId) {
        db.collection("Servicios").document(servicioId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EliminarServicioActivity.this, "Servicio eliminado", Toast.LENGTH_SHORT).show();
                    serviciosList.remove(servicioId);
                    ((ArrayAdapter) spinnerServicios.getAdapter()).notifyDataSetChanged();
                } else {
                    Toast.makeText(EliminarServicioActivity.this, "Error al eliminar el servicio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
