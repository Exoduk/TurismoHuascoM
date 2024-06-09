package com.example.turismohuascom.ui.gastronomia;

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

public class GastronomiaAdapter extends RecyclerView.Adapter<GastronomiaAdapter.GastronomiaViewHolder> {
    private List<Gastronomia> gastronomiaList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Gastronomia gastronomia);
    }

    public GastronomiaAdapter(List<Gastronomia> gastronomiaList, OnItemClickListener listener) {
        this.gastronomiaList = gastronomiaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GastronomiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_gastronomia, parent, false);
        return new GastronomiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastronomiaViewHolder holder, int position) {
        Gastronomia gastronomia = gastronomiaList.get(position);
        holder.textViewGastronomiaTitulo.setText(gastronomia.getId());
        holder.textViewGastronomiaDireccion.setText(gastronomia.getDireccion());

        // Cargar imagen usando Glide
        Glide.with(holder.imageViewGastronomia.getContext())
                .load(gastronomia.getImagen())
                .into(holder.imageViewGastronomia);

        holder.bind(gastronomia, listener);
    }

    @Override
    public int getItemCount() {
        return gastronomiaList.size();
    }

    public static class GastronomiaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGastronomia;
        TextView textViewGastronomiaTitulo;
        TextView textViewGastronomiaDireccion;

        public GastronomiaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGastronomia = itemView.findViewById(R.id.imageViewGastronomia);
            textViewGastronomiaTitulo = itemView.findViewById(R.id.textViewGastronomiaTitulo);
            textViewGastronomiaDireccion = itemView.findViewById(R.id.textViewGastronomiaDireccion);
        }

        public void bind(final Gastronomia gastronomia, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(gastronomia);
                }
            });
        }
    }
}
