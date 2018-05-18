package com.edopore.mymeals10;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edopore.mymeals10.Adapters.AdapterEspeciales;
import com.edopore.mymeals10.Adapters.AdapterRestaurantes;
import com.edopore.mymeals10.modelo.Especiales;
import com.edopore.mymeals10.modelo.Restaurantes;
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

    public EspecialesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_especiales, container, false);

        recyclView = view.findViewById(R.id.recyclView);
        recyclView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclView.setLayoutManager(layoutManager);

        especialesList = new ArrayList<>();

        adapterEspeciales = new AdapterEspeciales(especialesList, R.layout.cardview_especiales
                ,getActivity());
        recyclView.setAdapter(adapterEspeciales);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("menu").child("Amazonico").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                especialesList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
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

        return view;
    }

}
