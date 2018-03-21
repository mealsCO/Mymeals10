package com.edopore.mymeals10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String User, Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        User = extras.getString("USER");
        Password = extras.getString("PASS");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menup,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mReg){

            Intent intent = new Intent(MainActivity.this, Perfil.class);
            intent.putExtra("USER", User);
            intent.putExtra("PASS", Password);
            startActivityForResult(intent,369);
            finish();

        }else if (id == R.id.mOut){

            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.putExtra("us", User);
            intent.putExtra("co", Password);
            startActivityForResult(intent,1);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.putExtra("us", User);
        intent.putExtra("co", Password);
        startActivityForResult(intent,1);
        finish();
        super.onBackPressed();
    }
}
