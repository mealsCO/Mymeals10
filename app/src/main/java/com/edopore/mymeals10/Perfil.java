package com.edopore.mymeals10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Perfil extends AppCompatActivity {

    EditText eUs, ePas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ePas = findViewById(R.id.ePas);
        eUs = findViewById(R.id.eUs);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            eUs.setText(extras.getString("usuar"));
            ePas.setText(String.valueOf(extras.getDouble("contrasena")));
        }
    }
//hola loquitos 2
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.prin){

            Intent intent = new Intent(Perfil.this, MainActivity.class);
            startActivity(intent);
            finish();

        }else if (id == R.id.out){

            Intent intent = new Intent(Perfil.this, Login.class);
            startActivity(intent);
            Intent intent1 = new Intent();
            setResult(RESULT_OK,intent1);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }

}
