package com.torres.cristopher.solicitudesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.ViewHolder> {
    private List<Solicitud> solicitudes;

    public SolicitudesAdapter() {
        this.solicitudes = new ArrayList<>();
    }

    public void setProductos(List<Solicitud> productos) {
        this.solicitudes = solicitudes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView fotoImage;
        public TextView nombreText;
        public TextView precioText;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            nombreText = itemView.findViewById(R.id.nombre_text);
            precioText = itemView.findViewById(R.id.precio_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Solicitud solicitud = this.solicitudes.get(position);
        viewHolder.nombreText.setText(solicitud.getCorreo());
        viewHolder.precioText.setText(solicitud.getTipo());
        viewHolder.precioText.setText(solicitud.getMotivo());

        String url = ApiService.API_BASE_URL + "/solicitudes/images/" + solicitud.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);
    }

    @Override
    public int getItemCount() {
        return this.solicitudes.size();
    }
}

