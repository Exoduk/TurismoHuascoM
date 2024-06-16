package com.example.turismohuascom.ui.rutas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismohuascom.databinding.FragmentRutasBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RutasFragment extends Fragment {

    private FragmentRutasBinding binding;
    private RutasAdapter adapter;
    private List<Ruta> rutasList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RutasViewModel rutasViewModel =
                new ViewModelProvider(this).get(RutasViewModel.class);

        binding = FragmentRutasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        RecyclerView recyclerView = binding.recyclerViewRutas;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener datos de Firestore
        rutasList = new ArrayList<>();
        adapter = new RutasAdapter(rutasList, new RutasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ruta ruta) {
                // Intent para abrir RutaDetailActivity
                Intent intent = new Intent(getContext(), RutaDetailActivity.class);
                intent.putExtra("titulo", ruta.getId());
                intent.putExtra("descripcion", ruta.getDescripcion());
                intent.putExtra("imagen", ruta.getImagen());
                intent.putExtra("direccion", ruta.getUbicacion());  // Pasar dirección
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Rutas").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Ruta ruta = new Ruta(
                            document.getId(),
                            document.getString("direccion"),
                            document.getString("imagen"),
                            document.getString("descripcion")
                    );
                    rutasList.add(ruta);
                }
                adapter.notifyDataSetChanged();
            } else {
                // Manejar error
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
