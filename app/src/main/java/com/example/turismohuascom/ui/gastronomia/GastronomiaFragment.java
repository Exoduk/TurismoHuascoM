package com.example.turismohuascom.ui.gastronomia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismohuascom.databinding.FragmentGastronomiaBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GastronomiaFragment extends Fragment {

    private FragmentGastronomiaBinding binding;
    private GastronomiaAdapter adapter;
    private List<Gastronomia> gastronomiaList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGastronomiaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        RecyclerView recyclerView = binding.recyclerViewGastronomia;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener datos de Firestore
        gastronomiaList = new ArrayList<>();
        adapter = new GastronomiaAdapter(gastronomiaList, new GastronomiaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Gastronomia gastronomia) {
                // Intent para abrir GastronomiaDetailActivity
                Intent intent = new Intent(getContext(), GastronomiaDetailActivity.class);
                intent.putExtra("titulo", gastronomia.getId());
                intent.putExtra("direccion", gastronomia.getDireccion());
                intent.putExtra("telefono", gastronomia.getTelefono());
                intent.putExtra("correo", gastronomia.getCorreo());
                intent.putExtra("imagen", gastronomia.getImagen());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Gastronomia").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Gastronomia gastronomia = new Gastronomia(
                            document.getId(),
                            document.getString("direccion"),
                            document.getString("telefono"),
                            document.getString("correo"),
                            document.getString("imagen")
                    );
                    gastronomiaList.add(gastronomia);
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
