package com.example.turismohuascom.ui.rutas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismohuascom.R;

import java.util.List;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.RutaViewHolder> {
    private List<Ruta> rutasList;
    private OnItemClickListener listener;

    // Interfaz para manejar los clics
    public interface OnItemClickListener {
        void onItemClick(Ruta ruta);
    }

    // Constructor modificado para aceptar el listener
    public RutasAdapter(List<Ruta> rutasList, OnItemClickListener listener) {
        this.rutasList = rutasList;
        this.listener = listener;
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
        Ruta ruta = rutasList.get(position);
        holder.textViewRutaTitulo.setText(ruta.getTitulo());
        holder.textViewRutaDescripcion.setText(ruta.getDescripcion());
        holder.textViewRutaUbicacion.setText(ruta.getUbicacion());
        holder.imageViewRuta.setImageResource(ruta.getImagen());

        // Bind the click listener
        holder.bind(ruta, listener);
    }

    @Override
    public int getItemCount() {
        return rutasList.size();
    }

    public static class RutaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRuta;
        TextView textViewRutaTitulo;
        TextView textViewRutaDescripcion;
        TextView textViewRutaUbicacion;

        public RutaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRuta = itemView.findViewById(R.id.imageViewRuta);
            textViewRutaTitulo = itemView.findViewById(R.id.textViewRutaTitulo);
            textViewRutaDescripcion = itemView.findViewById(R.id.textViewRutaDescripcion);
            textViewRutaUbicacion = itemView.findViewById(R.id.textViewRutaUbicacion);
        }

        public void bind(final Ruta ruta, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(ruta);
                }
            });
        }
    }
}