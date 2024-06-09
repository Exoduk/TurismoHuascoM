package com.example.turismohuascom.ui.servicios;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismohuascom.databinding.FragmentServiciosBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ServiciosFragment extends Fragment {

    private FragmentServiciosBinding binding;
    private ServiciosAdapter adapter;
    private List<Servicio> serviciosList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServiciosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        RecyclerView recyclerView = binding.recyclerViewServicios;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener datos de Firestore
        serviciosList = new ArrayList<>();
        adapter = new ServiciosAdapter(serviciosList, new ServiciosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Servicio servicio) {
                // Intent para abrir ServicioDetailActivity
                Intent intent = new Intent(getContext(), ServicioDetailActivity.class);
                intent.putExtra("titulo", servicio.getId());
                intent.putExtra("descripcion", servicio.getDescripcion());
                intent.putExtra("direccion", servicio.getDireccion());
                intent.putExtra("correo", servicio.getCorreo());
                intent.putExtra("telefono", servicio.getTelefono());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Servicios").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Servicio servicio = new Servicio(
                            document.getId(),
                            document.getString("descripcion"),
                            document.getString("direccion"),
                            document.getString("correo"),
                            document.getString("telefono")
                    );
                    serviciosList.add(servicio);
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
