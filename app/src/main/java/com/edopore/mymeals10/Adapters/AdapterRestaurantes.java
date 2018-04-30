package com.edopore.mymeals10.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edopore.mymeals10.R;
import com.edopore.mymeals10.modelo.Restaurantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRestaurantes extends RecyclerView.Adapter<AdapterRestaurantes.RestaurantesViewHolder> {

    private ArrayList<Restaurantes> restaurantesList;
    private int resource;
    private Activity activity;

    public AdapterRestaurantes (ArrayList<Restaurantes> restaurantesList){
        this.restaurantesList = restaurantesList;
    }

    public AdapterRestaurantes(ArrayList<Restaurantes> restaurantesList, int resource, Activity activity) {
        this.restaurantesList = restaurantesList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public RestaurantesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Abre actividad con detalle", Toast.LENGTH_SHORT).show();
            }
        });

        return new RestaurantesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantesViewHolder holder, int position) {

        Restaurantes restaurante = restaurantesList.get(position);
        holder.bindRestaurante(restaurante, activity);


    }

    @Override
    public int getItemCount() {
        return restaurantesList.size();
    }

    public class RestaurantesViewHolder extends RecyclerView.ViewHolder {

        private TextView tNombre, tDireccion, tTelefono, tCalificacion;
        private CircleImageView iFoto;

        public RestaurantesViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre);
            tDireccion = itemView.findViewById(R.id.tDireccion);
            tTelefono = itemView.findViewById(R.id.tTelefono);
            tCalificacion = itemView.findViewById(R.id.tCalificacion);
            iFoto = itemView.findViewById(R.id.iFoto);
        }

        public void bindRestaurante(Restaurantes restaurante, Activity activity) {
            tNombre.setText(restaurante.getNombre());
            tDireccion.setText(restaurante.getDireccion());
            tTelefono.setText(restaurante.getTelefono());
            tCalificacion.setText(String.valueOf(restaurante.getCalificacion()));
            Picasso.get().load(restaurante.getFoto()).into(iFoto);
        }
    }
}
