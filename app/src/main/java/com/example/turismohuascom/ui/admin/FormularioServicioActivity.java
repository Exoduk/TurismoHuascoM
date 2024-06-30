package com.example.turismohuascom.ui.admin;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.turismohuascom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class FormularioServicioActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etNombreServicio, etCorreo, etDireccion, etTelefono, etDescripcion;
    private ImageView ivImagen;
    private Uri imagenUri;
    private FirebaseFirestore db;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_servicio);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        etNombreServicio = findViewById(R.id.s_nombre_servicio);
        etCorreo = findViewById(R.id.s_correo);
        etDireccion = findViewById(R.id.s_direccion);
        etTelefono = findViewById(R.id.s_telefono);
        etDescripcion = findViewById(R.id.s_descripcion);
        ivImagen = findViewById(R.id.ivg_imagen);
        Button btnSeleccionarImagen = findViewById(R.id.btnd_seleccionar_imagen);
        Button btnGuardar = findViewById(R.id.btns_guardar);
        ImageButton btnatras = findViewById(R.id.btn_back_servicio);

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("imagenesServicios");

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioServicioActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarServicio();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            ivImagen.setVisibility(View.VISIBLE);
            ivImagen.setImageURI(imagenUri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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

        if (imagenUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imagenUri));

            fileReference.putFile(imagenUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        Map<String, Object> servicio = new HashMap<>();
                                        servicio.put("nombre", nombreServicio);
                                        servicio.put("correo", correo);
                                        servicio.put("direccion", direccion);
                                        servicio.put("telefono", telefono);
                                        servicio.put("descripcion", descripcion);
                                        servicio.put("imagen", downloadUri.toString());

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
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FormularioServicioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }
}
