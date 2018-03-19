package com.edopore.mymeals10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String usuario = "omar";
    double password = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menup,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.reg){

            Intent intent = new Intent(MainActivity.this, Perfil.class);
            intent.putExtra("usuar", usuario);
            intent.putExtra("contrasena", password);
            startActivityForResult(intent,369);

        }else if (id == R.id.out){

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 369 && resultCode == RESULT_OK){
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
