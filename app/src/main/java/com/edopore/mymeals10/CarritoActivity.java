package com.edopore.mymeals10;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class CarritoActivity extends AppCompatActivity {

    private FrameLayout compras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        compras = findViewById(R.id.compras);

        Bundle ex = getIntent().getExtras();

        ComprasFragment compFrag = new ComprasFragment();
        compFrag.setArguments(ex);
        FragmentManager fragManagcom = getSupportFragmentManager();
        FragmentTransaction fragTransaccom = fragManagcom.beginTransaction();
        fragTransaccom.add(R.id.compras, compFrag).commit();
    }
}
