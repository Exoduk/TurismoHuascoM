package com.example.turismohuascom.ui.rutas;

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

import java.util.ArrayList;
import java.util.List;

public class RutasFragment extends Fragment {

    private FragmentRutasBinding binding;
    private RutasAdapter adapter;
    private List<String> rutasList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RutasViewModel rutasViewModel =
                new ViewModelProvider(this).get(RutasViewModel.class);

        binding = FragmentRutasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        RecyclerView recyclerView = binding.recyclerViewRutas;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener datos (ejemplo de datos ficticios, reemplazar con datos de la BD)
        rutasList = obtenerDatosDeLaBaseDeDatos();

        // Configurar Adapter
        adapter = new RutasAdapter(rutasList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<String> obtenerDatosDeLaBaseDeDatos() {
        // Implementa tu lógica para obtener los datos de la base de datos
        // Aquí solo hay un ejemplo con datos ficticios
        List<String> datos = new ArrayList<>();
        datos.add("Ruta 1");
        datos.add("Ruta 2");
        datos.add("Ruta 3");
        return datos;
    }
}
