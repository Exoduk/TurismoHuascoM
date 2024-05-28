package com.example.turismohuascom.ui.rutas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismohuascom.R;

import java.util.List;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.RutaViewHolder> {
    private List<String> rutasList;

    public RutasAdapter(List<String> rutasList) {
        this.rutasList = rutasList;
    }

    @NonNull
    @Override
    public RutaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_ruta, parent, false);
        return new RutaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutaViewHolder holder, int position) {
        String ruta = rutasList.get(position);
        holder.textViewRuta.setText(ruta);
    }

    @Override
    public int getItemCount() {
        return rutasList.size();
    }

    public static class RutaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRuta;

        public RutaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRuta = itemView.findViewById(R.id.textViewRuta);
        }
    }
}
