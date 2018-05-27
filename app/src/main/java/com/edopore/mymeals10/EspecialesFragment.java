package com.edopore.mymeals10;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edopore.mymeals10.Adapters.AdapterEspeciales;
import com.edopore.mymeals10.modelo.Especiales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EspecialesFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ArrayList<Especiales> especialesList;
    private RecyclerView recyclView;
    private RecyclerView.Adapter adapterEspeciales;
    private RecyclerView.LayoutManager layoutManager;
    private float mayor, contador;
    private String nombre, llamada, rest;

    public EspecialesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle argu = this.getArguments();
        if (argu != null) {
            rest = argu.getString("nombre");
            llamada = argu.getString("llamada");
        }

        View view = inflater.inflate(R.layout.fragment_especiales, container, false);


        recyclView = view.findViewById(R.id.recyclView);
        recyclView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclView.setLayoutManager(layoutManager);

        especialesList = new ArrayList<>();

        adapterEspeciales = new AdapterEspeciales(especialesList, R.layout.cardview_especiales
                , getActivity());
        recyclView.setAdapter(adapterEspeciales);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (String.valueOf(llamada).equals("restaurante")) {
            databaseReference.child("menu").child(rest).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    especialesList.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Especiales especiales = snapshot.getValue(Especiales.class);
                            especialesList.add(especiales);
                        }
                        adapterEspeciales.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Log.d("nomb-Especiales3", String.valueOf(llamada));
            databaseReference.child("menu").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    especialesList.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            mayor = 0;
                            nombre = "";
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Especiales especiales = snapshot1.getValue(Especiales.class);
                                if (especiales.getCalificacion() > mayor) {
                                    mayor = especiales.getCalificacion();
                                    nombre = especiales.getNombre();
                                }
                            }
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Especiales especiales = snapshot1.getValue(Especiales.class);
                                if (especiales.getNombre() == nombre) {
                                    especialesList.add(especiales);
                                }
                            }

                        }
                        adapterEspeciales.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return view;
    }

}
