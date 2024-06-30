package com.example.turismohuascom.ui.servicios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.turismohuascom.R;

import java.util.List;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ServicioViewHolder> {
    private List<Servicio> serviciosList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Servicio servicio);
    }

    public ServiciosAdapter(List<Servicio> serviciosList, OnItemClickListener listener) {
        this.serviciosList = serviciosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_servicio, parent, false);
        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicioViewHolder holder, int position) {
        Servicio servicio = serviciosList.get(position);
        holder.textViewServicioTitulo.setText(servicio.getId());
        holder.textViewServicioDireccion.setText(servicio.getDireccion());

        // Cargar imagen usando Glide
        Glide.with(holder.imageViewServicio.getContext())
                .load(servicio.getImagen())
                .into(holder.imageViewServicio);

        holder.bind(servicio, listener);
    }

    @Override
    public int getItemCount() {
        return serviciosList.size();
    }

    public static class ServicioViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewServicio;
        TextView textViewServicioTitulo;
        TextView textViewServicioDireccion;

        public ServicioViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewServicio = itemView.findViewById(R.id.imageViewServicio);
            textViewServicioTitulo = itemView.findViewById(R.id.textViewServicioTitulo);
            textViewServicioDireccion = itemView.findViewById(R.id.textViewServicioDireccion);
        }

        public void bind(final Servicio servicio, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(servicio);
                }
            });
        }
    }
}

