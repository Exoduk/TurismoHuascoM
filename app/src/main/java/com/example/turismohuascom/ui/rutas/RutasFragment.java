package com.example.turismohuascom.ui.rutas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.turismohuascom.databinding.FragmentRutasBinding;

public class RutasFragment extends Fragment {

    private FragmentRutasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RutasViewModel rutasViewModel =
                new ViewModelProvider(this).get(RutasViewModel.class);

        binding = FragmentRutasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRutas;
        rutasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
