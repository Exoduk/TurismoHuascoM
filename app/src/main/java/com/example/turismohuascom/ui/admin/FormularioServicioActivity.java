package com.example.turismohuascom.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.turismohuascom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormularioServicioActivity extends AppCompatActivity {

    private EditText etNombreServicio, etCorreo, etDireccion, etTelefono, etDescripcion;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_servicio);

        etNombreServicio = findViewById(R.id.s_nombre_servicio);
        etCorreo = findViewById(R.id.s_correo);
        etDireccion = findViewById(R.id.s_direccion);
        etTelefono = findViewById(R.id.s_telefono);
        etDescripcion = findViewById(R.id.s_descripcion);
        Button btnGuardar = findViewById(R.id.btns_guardar);

        db = FirebaseFirestore.getInstance();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarServicio();
            }
        });
    }

    private void guardarServicio() {
        final String nombreServicio = etNombreServicio.getText().toString().trim().toLowerCase();
        final String correo = etCorreo.getText().toString().trim().toLowerCase();
        final String direccion = etDireccion.getText().toString().trim().toLowerCase();
        final String telefono = etTelefono.getText().toString().trim().toLowerCase();
        final String descripcion = etDescripcion.getText().toString().trim().toLowerCase();

        if (nombreServicio.isEmpty() || correo.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(FormularioServicioActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> servicio = new HashMap<>();
        servicio.put("nombre", nombreServicio);
        servicio.put("correo", correo);
        servicio.put("direccion", direccion);
        servicio.put("telefono", telefono);
        servicio.put("descripcion", descripcion);

        db.collection("Servicios").document(nombreServicio)
                .set(servicio)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FormularioServicioActivity.this, "Servicio guardado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(FormularioServicioActivity.this, "Error al guardar el servicio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
