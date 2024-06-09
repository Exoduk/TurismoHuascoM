package com.example.turismohuascom.ui.alojamiento;

import android.content.Context;
import android.content.Intent;
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

public class AlojamientoAdapter extends RecyclerView.Adapter<AlojamientoAdapter.AlojamientoViewHolder> {

    private List<Alojamiento> alojamientosList;
    private Context context;

    public AlojamientoAdapter(List<Alojamiento> alojamientosList, Context context) {
        this.alojamientosList = alojamientosList;
        this.context = context;
    }

    @NonNull
    @Override
    public AlojamientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_alojamiento, parent, false);
        return new AlojamientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlojamientoViewHolder holder, int position) {
        Alojamiento alojamiento = alojamientosList.get(position);
        holder.tituloTextView.setText(alojamiento.getId());
        holder.direccionTextView.setText(alojamiento.getDireccion());
        Glide.with(context).load(alojamiento.getImagen()).into(holder.imagenImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AlojamientoDetailActivity.class);
            intent.putExtra("titulo", alojamiento.getId());
            intent.putExtra("correo", alojamiento.getCorreo());
            intent.putExtra("telefono", alojamiento.getTelefono());
            intent.putExtra("imagen", alojamiento.getImagen());
            intent.putExtra("direccion", alojamiento.getDireccion());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return alojamientosList.size();
    }

    public static class AlojamientoViewHolder extends RecyclerView.ViewHolder {

        TextView tituloTextView;
        TextView direccionTextView;
        ImageView imagenImageView;

        public AlojamientoViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.textViewAlojamientoTitulo);
            direccionTextView = itemView.findViewById(R.id.textViewAlojamientoDireccion);
            imagenImageView = itemView.findViewById(R.id.imageViewAlojamiento);
        }
    }
}
