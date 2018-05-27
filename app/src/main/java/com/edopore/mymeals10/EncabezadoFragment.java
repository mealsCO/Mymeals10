package com.edopore.mymeals10;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edopore.mymeals10.modelo.Restaurantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class EncabezadoFragment extends Fragment {


    private DatabaseReference databaseReference;
    private TextView tRes, tTel, tDir;
    private ImageView iRes;
    private String rest;


    public EncabezadoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_encabezado, container, false);

        tRes = view.findViewById(R.id.tRest);
        tTel = view.findViewById(R.id.tTel);
        tDir = view.findViewById(R.id.tDir);
        iRes = view.findViewById(R.id.iRest);

        Bundle argu = this.getArguments();
        if (argu != null) {
            rest = argu.getString("nombre");
        }

        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (!String.valueOf(rest).equals("null")) {
            databaseReference.child("Restaurantes").child(rest).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        Restaurantes restaurantes = dataSnapshot.getValue(Restaurantes.class);
                        tRes.setText(restaurantes.getNombre());
                        Picasso.get().load(restaurantes.getFoto()).into(iRes);
                        tDir.setText(restaurantes.getDireccion());
                        tTel.setText(restaurantes.getTelefono());
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
