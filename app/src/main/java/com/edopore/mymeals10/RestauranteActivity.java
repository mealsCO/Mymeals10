package com.edopore.mymeals10;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
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

public class RestauranteActivity extends AppCompatActivity {

    private FrameLayout mapa, menurest, encabezado;

  /*  private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);


        encabezado = findViewById(R.id.encabezado);
        mapa = findViewById(R.id.mapa);
        menurest = findViewById(R.id.menurest);

        Bundle ex = getIntent().getExtras();                                             // redirigimos el intent

       /* mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.containerres);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsres);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        */


        EncabezadoFragment encFrag = new EncabezadoFragment();
        encFrag.setArguments(ex);
        FragmentManager enfragManag = getSupportFragmentManager();
        FragmentTransaction enfragTransac = enfragManag.beginTransaction();
        enfragTransac.add(R.id.encabezado, encFrag).commit();

        EspecialesFragment espFrag = new EspecialesFragment();
        espFrag.setArguments(ex);
        FragmentManager fragManag = getSupportFragmentManager();
        FragmentTransaction fragTransac = fragManag.beginTransaction();
        fragTransac.add(R.id.menurest, espFrag).commit();


        MapasFragment mapFrag = new MapasFragment();
        mapFrag.setArguments(ex);
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.add(R.id.mapa, mapFrag).commit();

    }

   /* public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position){
                case 0: EncabezadoFragment tab1 = new EncabezadoFragment();
                        //MapasFragment tab1 = new MapasFragment();
                    return tab1;
                case 1: EspecialesFragment tab2 = new EspecialesFragment();
                    return tab2;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }*/

}
