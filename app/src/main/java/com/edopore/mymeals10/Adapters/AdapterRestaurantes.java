package com.edopore.mymeals10.Adapters;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edopore.mymeals10.MapsActivity;
import com.edopore.mymeals10.Perfil;
import com.edopore.mymeals10.R;
import com.edopore.mymeals10.Registro;
import com.edopore.mymeals10.modelo.Restaurantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRestaurantes extends RecyclerView.Adapter<AdapterRestaurantes.RestaurantesViewHolder> {

    private ArrayList<Restaurantes> restaurantesList;
    private int resource;
    private Activity activity;



    public AdapterRestaurantes(ArrayList<Restaurantes> restaurantesList, int resource, Activity activity) {
        this.restaurantesList = restaurantesList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public RestaurantesViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(activity, res, Toast.LENGTH_SHORT).show();
            }
        });

        return new RestaurantesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantesViewHolder holder, int position) {

        final Restaurantes restaurante = restaurantesList.get(position);
        holder.bindRestaurante(restaurante, activity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MapsActivity.class);
                i.putExtra("nombre",restaurante.getNombre());
                i.putExtra("lat",restaurante.getLatitud());
                i.putExtra("lon",restaurante.getLongitud());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantesList.size();
    }

    public class RestaurantesViewHolder extends RecyclerView.ViewHolder {

        private TextView tNombre, tDireccion, tTelefono;
        private CircleImageView iFoto;
        private RatingBar rCalificacion;

        public RestaurantesViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre);
            tDireccion = itemView.findViewById(R.id.tDireccion);
            tTelefono = itemView.findViewById(R.id.tTelefono);
            rCalificacion = itemView.findViewById(R.id.rCalificacion);
            iFoto = itemView.findViewById(R.id.iFoto);
        }

        public void bindRestaurante(Restaurantes restaurante, Activity activity) {
            tNombre.setText(restaurante.getNombre());
            tDireccion.setText(restaurante.getDireccion());
            tTelefono.setText(restaurante.getTelefono());
            rCalificacion.setRating(restaurante.getCalificacion());
            Picasso.get().load(restaurante.getFoto()).into(iFoto);
        }
    }


}
