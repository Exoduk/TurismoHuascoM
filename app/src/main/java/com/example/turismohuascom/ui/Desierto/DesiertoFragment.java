package com.example.turismohuascom.ui.Desierto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.turismohuascom.R;
import com.example.turismohuascom.databinding.FragmentDesiertoBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DesiertoFragment extends Fragment {

    private FragmentDesiertoBinding binding;
    private List<String> imageUrls;
    private ImageCarouselAdapter adapter;
    private ViewPager viewPager;
    private Timer timer;
    private Handler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDesiertoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar la lista de URLs de imágenes
        imageUrls = new ArrayList<>();
        adapter = new ImageCarouselAdapter(imageUrls, getContext());

        // Configurar el ViewPager para el carrusel de imágenes
        viewPager = binding.viewPager;
        viewPager.setAdapter(adapter);

        // Configurar las demás vistas
        TextView tvTitle = binding.tvTitle;
        TextView tvDescripcion = binding.tvDescripcion;
        TextView tvPdfLink = binding.tvPdfLink;
        LinearLayout laminasContainer = binding.laminasContainer;
        VideoView videoView = binding.videoView;
        Button btnFullScreen = binding.btnFullScreen;
        ScrollView scrollView = binding.scrollView;  // Obtener el ScrollView

        // Configurar el título fijo
        tvTitle.setText("Desierto Florido");

        // Cargar imágenes y datos adicionales desde Firebase
        loadImagesFromFirebase();
        loadDescriptionFromFirebase(tvDescripcion);  // Cargar la descripción desde Firebase
        setupPdfLink(tvPdfLink);
        setupVerLaminasCompletasLink();  // Configurar el link de "Ver láminas completas"
        loadLaminasFromFirebase(laminasContainer);  // Cargar todas las láminas desde Firebase
        loadVideoFromFirebase(videoView, btnFullScreen);

        // Configurar el carrusel de imágenes para que cambie automáticamente cada 2 segundos
        setupImageCarousel();

        // Forzar el scroll al inicio
        scrollView.post(() -> scrollView.scrollTo(0, 0));

        // Escuchar la interacción del usuario con el ViewPager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // No se necesita implementar
            }

            @Override
            public void onPageSelected(int position) {
                // No se necesita implementar
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    stopImageCarousel();
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    startImageCarousel();
                }
            }
        });

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

    private void loadDescriptionFromFirebase(TextView tvDescripcion) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DesiertoFlorido").document("Descripcion").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                String descripcion = document.getString("texto");
                if (descripcion != null) {
                    tvDescripcion.setText(descripcion);
                }
            } else {
                tvDescripcion.setText("Error al cargar la descripción."); // Manejar errores de conexión a Firebase
            }
        });
    }

    private void setupPdfLink(TextView tvPdfLink) {
        tvPdfLink.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://imhuasco.cl/afiche/expresiones-del-desierto-florido%20(1).pdf"));
            startActivity(intent);
        });
    }

    private void setupVerLaminasCompletasLink() {
        TextView tvVerLaminasCompletas = binding.tvVerLaminasCompletas;
        tvVerLaminasCompletas.setOnClickListener(v -> {
            // Aquí puedes añadir la acción deseada para ver las láminas completas.
            // Por ejemplo, podrías abrir otra actividad que muestre todas las láminas en pantalla completa.
        });
    }

    private void loadLaminasFromFirebase(LinearLayout laminasContainer) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DesiertoFlorido").document("Laminas").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                for (String key : document.getData().keySet()) {
                    String laminaUrl = document.getString(key);
                    if (laminaUrl != null && !laminaUrl.isEmpty()) {
                        ImageView laminaImageView = new ImageView(getContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                500 // Altura fija para las imágenes, puedes ajustarla
                        );
                        layoutParams.setMargins(0, 0, 0, 20);  // Espacio entre las imágenes
                        laminaImageView.setLayoutParams(layoutParams);
                        laminaImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(this).load(laminaUrl).into(laminaImageView);

                        // Agregar el ImageView al contenedor de láminas
                        laminasContainer.addView(laminaImageView);

                        // Configurar el OnClickListener para mostrar la imagen en pantalla completa
                        laminaImageView.setOnClickListener(v -> {
                            Intent intent = new Intent(getContext(), FullScreenImageActivity.class);
                            intent.putExtra("image_url", laminaUrl);
                            startActivity(intent);
                        });
                    }
                }
            } else {
                // Manejar el error si es necesario
            }
        });
    }

    private void loadVideoFromFirebase(VideoView videoView, Button btnFullScreen) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DesiertoFlorido").document("videos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                String videoUrl = document.getString("link");

                if (videoUrl != null && !videoUrl.isEmpty()) {
                    // Configurar el VideoView
                    Uri uri = Uri.parse(videoUrl);
                    videoView.setVideoURI(uri);

                    // No configurar el MediaController para que el menú no aparezca
                    // videoView.setMediaController(null);

                    videoView.start();

                    // Configurar el botón de pantalla completa
                    btnFullScreen.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), FullScreenVideoActivity.class);
                        intent.putExtra("video_url", videoUrl);
                        startActivity(intent);
                    });
                }
            } else {
                // Manejar el error si es necesario
            }
        });
    }


    private void setupImageCarousel() {
        handler = new Handler();
        timer = new Timer();
        Runnable update = new Runnable() {
            public void run() {
                int currentPage = viewPager.getCurrentItem();
                int nextPage = (currentPage + 1) % imageUrls.size();
                viewPager.setCurrentItem(nextPage, true);
            }
        };
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2000, 2000); // Cambia cada 2 segundos
    }

    private void stopImageCarousel() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void startImageCarousel() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        setupImageCarousel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopImageCarousel();
        binding = null;
    }
}
