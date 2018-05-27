package com.edopore.mymeals10;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edopore.mymeals10.Adapters.AdapterCompras;
import com.edopore.mymeals10.Adapters.AdapterEspeciales;
import com.edopore.mymeals10.modelo.Compras;
import com.edopore.mymeals10.modelo.Especiales;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComprasFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ArrayList<Compras> comprasList;
    private RecyclerView recyView;
    private RecyclerView.Adapter adapterCompras;
    private RecyclerView.LayoutManager layoutManager;



    public ComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compras, container, false);

        recyView = view.findViewById(R.id.recyView);
        recyView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyView.setLayoutManager(layoutManager);

        comprasList = new ArrayList<>();

        adapterCompras = new AdapterCompras(comprasList, R.layout.cardview_compras
                , getActivity());
        recyView.setAdapter(adapterCompras);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child("compras").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comprasList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Compras compras = snapshot.getValue(Compras.class);
                        comprasList.add(compras);
                    }
                    adapterCompras.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
