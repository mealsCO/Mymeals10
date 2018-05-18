package com.edopore.mymeals10.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edopore.mymeals10.MapsActivity;
import com.edopore.mymeals10.R;
import com.edopore.mymeals10.modelo.Especiales;
import com.edopore.mymeals10.modelo.Restaurantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterEspeciales extends RecyclerView.Adapter<AdapterEspeciales.EspecialesViewHolder>{


    private ArrayList<Especiales> especialesList;
    private int resource;
    private Activity activity;


    public AdapterEspeciales(ArrayList<Especiales> especialesList, int resource, Activity activity){
        this.especialesList = especialesList;
        this.resource = resource;
        this.activity = activity;
    }


    @Override
    public EspecialesViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(activity, res, Toast.LENGTH_SHORT).show();
            }
        });

        return new EspecialesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterEspeciales.EspecialesViewHolder holder, int position) {

        final Especiales especiales = especialesList.get(position);
        holder.bindEspeciales(especiales, activity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(activity,"abre: "+especiales.getNombre(),Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(activity, MapsActivity.class);
//                i.putExtra("nombre",restaurante.getNombre());
//                i.putExtra("lat",restaurante.getLatitud());
//                i.putExtra("lon",restaurante.getLongitud());
//                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return especialesList.size();
    }

    public class EspecialesViewHolder extends RecyclerView.ViewHolder {

        private TextView tPlato, tTiempo;
        private CircleImageView iFot;
        private RatingBar rCalif;

        public EspecialesViewHolder(View itemView) {
            super(itemView);
            tPlato = itemView.findViewById(R.id.tPlato);
            tTiempo = itemView.findViewById(R.id.tTiempo);
            rCalif = itemView.findViewById(R.id.rCalif);
            iFot = itemView.findViewById(R.id.iFot);
        }

        public void bindEspeciales(Especiales especiales, Activity activity) {
            tPlato.setText(especiales.getNombre());
            tTiempo.setText(especiales.getTiempo());
            rCalif.setRating(especiales.getCalificacion());
            Picasso.get().load(especiales.getFoto()).into(iFot);
        }
    }

}
