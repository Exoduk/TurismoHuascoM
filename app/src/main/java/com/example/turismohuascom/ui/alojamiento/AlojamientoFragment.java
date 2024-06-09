package com.example.turismohuascom.ui.alojamiento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismohuascom.databinding.FragmentAlojamientosBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AlojamientoFragment extends Fragment {

    private FragmentAlojamientosBinding binding;
    private AlojamientoAdapter adapter;
    private List<Alojamiento> alojamientosList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAlojamientosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerViewAlojamientos;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        alojamientosList = new ArrayList<>();
        adapter = new AlojamientoAdapter(alojamientosList, getContext());
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Alojamientos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Alojamiento alojamiento = new Alojamiento(
                            document.getId(),
                            document.getString("direccion"),
                            document.getString("imagen"),
                            document.getString("correo"),
                            document.getString("telefono")
                    );
                    alojamientosList.add(alojamiento);
                }
                adapter.notifyDataSetChanged();
            } else {
                // Manejar error si es necesario
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
