package com.edopore.mymeals10.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edopore.mymeals10.R;
import com.edopore.mymeals10.modelo.Compras;
import com.edopore.mymeals10.modelo.Especiales;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCompras extends RecyclerView.Adapter<AdapterCompras.ComprasViewHolder> {

    private ArrayList<Compras> comprasList;
    private int resource;
    private Activity activity;

    public AdapterCompras(ArrayList<Compras> comprasList, int resource, Activity activity){
        this.comprasList = comprasList;
        this.resource = resource;
        this.activity = activity;
    }


    @Override
    public ComprasViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ComprasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterCompras.ComprasViewHolder holder, int position) {
        final Compras compras = comprasList.get(position);
        holder.bindCompras(compras, activity);
        holder.bMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"mas",Toast.LENGTH_SHORT).show();
            }
        });

        holder.bMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"menos",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comprasList.size();
    }

    public class ComprasViewHolder extends RecyclerView.ViewHolder {

        private TextView tRestcom, tPlatocom, tPreciocom, tCantcom;
        private CircleImageView iFotcom;
        private Button bMenos, bMas;

        public ComprasViewHolder(View itemView) {
            super(itemView);
            tRestcom = itemView.findViewById(R.id.tRestcom);
            tPlatocom = itemView.findViewById(R.id.tPlatocom);
            tPreciocom = itemView.findViewById(R.id.tPreciocom);
            tCantcom = itemView.findViewById(R.id.tCantcom);
            iFotcom = itemView.findViewById(R.id.iFotcom);
            bMas = itemView.findViewById(R.id.bMas);
            bMenos = itemView.findViewById(R.id.bMenos);
        }

        public void bindCompras(Compras compras, Activity activity) {
            tRestcom.setText(compras.getRestaurante());
            tPlatocom.setText(compras.getPlato());
            tPreciocom.setText(compras.getPrecio());
            Picasso.get().load(compras.getFoto()).into(iFotcom);
        }
    }

}
