package com.edopore.mymeals10.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edopore.mymeals10.Perfil;
import com.edopore.mymeals10.R;
import com.edopore.mymeals10.modelo.Compras;
import com.edopore.mymeals10.modelo.Especiales;
import com.edopore.mymeals10.modelo.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterEspeciales extends RecyclerView.Adapter<AdapterEspeciales.EspecialesViewHolder> {


    private ArrayList<Especiales> especialesList;
    private int resource;
    private Activity activity;


    public AdapterEspeciales(ArrayList<Especiales> especialesList, int resource, Activity activity) {
        this.especialesList = especialesList;
        this.resource = resource;
        this.activity = activity;
    }


    @Override
    public EspecialesViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new EspecialesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterEspeciales.EspecialesViewHolder holder, int position) {

        final Especiales especiales = especialesList.get(position);
        holder.bindEspeciales(especiales, activity);
        holder.bAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child("compras").child(firebaseUser.getUid()).
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Log.d("usuario", "ok");
                                } else {
                                    Log.d("usuario", "NO");
                                    Compras compras = new Compras(especiales.getRestaurante(),
                                            especiales.getNombre(),
                                            especiales.getFoto(),
                                            especiales.getId(),
                                            especiales.getPrecio());

                                    databaseReference.child("compras").child(firebaseUser.getUid()).setValue(compras);
                                    Toast.makeText(activity, especiales.getNombre() + " agregado al carrito", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return especialesList.size();
    }


    public class EspecialesViewHolder extends RecyclerView.ViewHolder {

        private TextView tRestaurante, tPlato, tTiempo, tPrecio;
        private CircleImageView iFot;
        private RatingBar rCalif;
        private Button bAgregar;

        public EspecialesViewHolder(View itemView) {
            super(itemView);
            tRestaurante = itemView.findViewById(R.id.tRestaurante);
            tPlato = itemView.findViewById(R.id.tPlato);
            tTiempo = itemView.findViewById(R.id.tTiempo);
            tPrecio = itemView.findViewById(R.id.tPrecio);
            rCalif = itemView.findViewById(R.id.rCalif);
            iFot = itemView.findViewById(R.id.iFot);
            bAgregar = itemView.findViewById(R.id.bAgregar);
        }

        public void bindEspeciales(Especiales especiales, Activity activity) {
            tRestaurante.setText(especiales.getRestaurante());
            tPlato.setText(especiales.getNombre());
            tTiempo.setText(especiales.getTiempo());
            tPrecio.setText(especiales.getPrecio());
            rCalif.setRating(especiales.getCalificacion());
            Picasso.get().load(especiales.getFoto()).into(iFot);
        }
    }

}
