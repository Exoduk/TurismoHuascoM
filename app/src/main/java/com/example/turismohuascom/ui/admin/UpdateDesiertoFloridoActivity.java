package com.example.turismohuascom.ui.admin;

import android.annotation.SuppressLint;
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
import com.bumptech.glide.Glide;
import com.example.turismohuascom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Map;

public class UpdateDesiertoFloridoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;

    private EditText etDescripcion;
    private ImageView ivImagenCarrusel1, ivLamina1, ivLamina2;
    private Uri imagenCarruselUri, laminaUri, videoUri;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private String currentDescripcion;
    private String currentCarruselUrl, currentLaminaUrl1, currentLaminaUrl2, currentVideoUrl;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_desierto_florido);

        // Ocultar la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        etDescripcion = findViewById(R.id.et_descripcion);
        ivImagenCarrusel1 = findViewById(R.id.iv_imagen_carrusel1);
        ivLamina1 = findViewById(R.id.iv_lamina1);
        ivLamina2 = findViewById(R.id.iv_lamina2);
        Button btnSeleccionarImagenCarrusel = findViewById(R.id.btn_seleccionar_imagen_carrusel);
        Button btnSeleccionarLamina = findViewById(R.id.btn_seleccionar_lamina);
        Button btnSeleccionarVideo = findViewById(R.id.btn_seleccionar_video);
        Button btnGuardar = findViewById(R.id.btn_guardar);
        ImageButton btnAtras = findViewById(R.id.btn_back);

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        // Cargar datos actuales desde Firebase
        loadCurrentData();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSeleccionarImagenCarrusel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST);
            }
        });

        btnSeleccionarLamina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST);
            }
        });

        btnSeleccionarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_VIDEO_REQUEST);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        if (requestCode == PICK_VIDEO_REQUEST) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                imagenCarruselUri = data.getData();
                ivImagenCarrusel1.setImageURI(imagenCarruselUri);
            } else if (requestCode == PICK_VIDEO_REQUEST) {
                videoUri = data.getData();
                // Mostrar algún preview o información del video seleccionado
            }
        }
    }

    private void loadCurrentData() {
        db.collection("DesiertoFlorido").document("Descripcion").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();
                    currentDescripcion = document.getString("texto");
                    etDescripcion.setText(currentDescripcion);

                    // Cargar imágenes y videos
                    currentCarruselUrl = document.getString("imagenCarrusel1");
                    currentLaminaUrl1 = document.getString("lamina1");
                    currentLaminaUrl2 = document.getString("lamina2");
                    currentVideoUrl = document.getString("video");

                    Glide.with(UpdateDesiertoFloridoActivity.this).load(currentCarruselUrl).into(ivImagenCarrusel1);
                    Glide.with(UpdateDesiertoFloridoActivity.this).load(currentLaminaUrl1).into(ivLamina1);
                    Glide.with(UpdateDesiertoFloridoActivity.this).load(currentLaminaUrl2).into(ivLamina2);
                    // Aquí podrías cargar el video si tienes un preview o algo similar
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void saveData() {
        final String descripcion = etDescripcion.getText().toString().trim();

        if (imagenCarruselUri != null) {
            final StorageReference fileReference = storageReference.child("imagenesDesierto/" + System.currentTimeMillis()
                    + "." + getFileExtension(imagenCarruselUri));

            fileReference.putFile(imagenCarruselUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        currentCarruselUrl = downloadUri.toString();
                                        updateFirebase(descripcion);
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateDesiertoFloridoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            updateFirebase(descripcion);
        }
    }

    private void updateFirebase(String descripcion) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("texto", descripcion);
        updateData.put("imagenCarrusel1", currentCarruselUrl);
        updateData.put("lamina1", currentLaminaUrl1);
        updateData.put("lamina2", currentLaminaUrl2);
        updateData.put("video", currentVideoUrl);

        db.collection("DesiertoFlorido").document("Descripcion")
                .set(updateData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateDesiertoFloridoActivity.this, "Desierto Florido actualizado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdateDesiertoFloridoActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
