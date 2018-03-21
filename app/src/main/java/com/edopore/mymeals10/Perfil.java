package com.edopore.mymeals10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Perfil extends AppCompatActivity {

    EditText eUs, ePas;
    String User, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ePas = findViewById(R.id.ePas);
        eUs = findViewById(R.id.eUs);

        Bundle extras = getIntent().getExtras();
        User = extras.getString("USER");
        Password = extras.getString("PASS");
        eUs.setText(User);
        ePas.setText(Password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mPrin){

            Intent intent = new Intent(Perfil.this, MainActivity.class);
            intent.putExtra("USER", User);
            intent.putExtra("PASS", Password);
            startActivityForResult(intent,77);
            finish();


        }else if (id == R.id.mOuts){

            Intent intent = new Intent(Perfil.this, Login.class);
            intent.putExtra("us", User);
            intent.putExtra("co", Password);
            startActivityForResult(intent,1);
            setResult(RESULT_OK,intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Perfil.this, MainActivity.class);
        intent.putExtra("USER", User);
        intent.putExtra("PASS", Password);
        startActivityForResult(intent,77);
        finish();
        super.onBackPressed();
    }

}
