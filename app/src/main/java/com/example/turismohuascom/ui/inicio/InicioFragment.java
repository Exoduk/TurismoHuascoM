package com.example.turismohuascom.ui.inicio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.turismohuascom.R;
import com.example.turismohuascom.databinding.FragmentInicioBinding;
import com.example.turismohuascom.ui.admin.AdminActivity;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar el botón de admin
        ImageButton adminButton = root.findViewById(R.id.adminButton);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordDialog(v);
            }
        });

        return root;
    }

    private void showPasswordDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Crear un TextView personalizado para el título
        TextView title = new TextView(getContext());
        title.setText("Ingrese contraseña de administrador");
        title.setPadding(20, 30, 20, 10);
        title.setTextSize(18);  // Cambiar el tamaño de la letra aquí
        title.setTypeface(null, Typeface.BOLD);  // Opcional: Para hacer el texto en negrita
        title.setGravity(Gravity.CENTER);  // Opcional: Para centrar el título

        builder.setCustomTitle(title);

        // Configurar el input
        final EditText input = new EditText(getContext());
        builder.setView(input);

        // Configurar los botones
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = input.getText().toString();
                validatePassword(password, view);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void validatePassword(String password, View view) {
        if (password.equals("1234")) { // Reemplaza "1234" con la contraseña que quieras
            Intent intent = new Intent(getActivity(), AdminActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
