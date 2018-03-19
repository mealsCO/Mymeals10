package com.edopore.mymeals10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    //hola loquitos 2
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.prin) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USER", User);
            intent.putExtra("PASS", Password);
            startActivity(intent);
            finish();

        } else if (id == R.id.out) {

            Intent intent = new Intent(this, Login.class);
            Intent intent1 = new Intent();
            startActivity(intent);
            setResult(RESULT_OK, intent1);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USER", User);
        intent.putExtra("PASS", Password);
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }

}