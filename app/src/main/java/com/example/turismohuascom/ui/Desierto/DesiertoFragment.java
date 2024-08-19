package com.example.turismohuascom.ui.Desierto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.turismohuascom.R;
import com.example.turismohuascom.databinding.FragmentDesiertoBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DesiertoFragment extends Fragment {

    private FragmentDesiertoBinding binding;
    private List<String> imageUrls;
    private ImageCarouselAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDesiertoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar la lista de URLs de imágenes
        imageUrls = new ArrayList<>();
        adapter = new ImageCarouselAdapter(imageUrls, getContext());

        // Configurar el ViewPager
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(adapter);

        // Configurar las demás vistas
        TextView tvTitle = binding.tvTitle;
        TextView tvDescripcion = binding.tvDescripcion;
        TextView tvLink = binding.tvLink;
        ImageView laminaImageView = binding.laminaImageView;

        // Configurar el título fijo
        tvTitle.setText("Desierto Florido");

        // Cargar imágenes y datos adicionales desde Firebase
        loadImagesFromFirebase();
        loadRouteDataFromFirebase(tvDescripcion, tvLink);
        loadLaminaFromFirebase(laminaImageView);

        return root;
    }

    private void loadImagesFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DesiertoFlorido").document("imagenes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                for (String key : document.getData().keySet()) {
                    String url = document.getString(key);
                    if (url != null && !url.isEmpty()) {
                        imageUrls.add(url);
                    }
                }
                adapter.notifyDataSetChanged(); // Actualizar el adapter cuando las imágenes están cargadas
            } else {
                // Manejar el error
            }
        });
    }

    private void loadRouteDataFromFirebase(TextView tvDescripcion, TextView tvLink) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DesiertoFlorido").document("rutas").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                String descripcion = document.getString("descripcion");
                String link = document.getString("link");

                tvDescripcion.setText(descripcion);
                tvLink.setTag(link); // Guardar el link en el tag

                // Hacer que el TextView del link sea clickable
                tvLink.setOnClickListener(v -> {
                    String url = (String) v.getTag();
                    if (url != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                    }
                });
            } else {
                // Manejar errores de conexión a Firebase
                tvDescripcion.setText("Error al cargar datos.");
                tvLink.setVisibility(View.GONE); // Ocultar el link si no se cargan los datos
            }
        });
    }

    private void loadLaminaFromFirebase(ImageView laminaImageView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DesiertoFlorido").document("Laminas").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                String laminaUrl = document.getString("link");

                if (laminaUrl != null && !laminaUrl.isEmpty()) {
                    // Cargar la imagen en el ImageView usando Glide
                    Glide.with(this).load(laminaUrl).into(laminaImageView);

                    // Configurar el OnClickListener para mostrar la imagen en pantalla completa
                    laminaImageView.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), FullScreenImageActivity.class);
                        intent.putExtra("image_url", laminaUrl);
                        startActivity(intent);
                    });
                }
            } else {
                // Manejar el error si es necesario
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
