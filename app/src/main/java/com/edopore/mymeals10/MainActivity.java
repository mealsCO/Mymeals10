package com.edopore.mymeals10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String Usuario, Password;
    //EditText prim, seg;

    String usuario = "omar";
    double password = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extra = getIntent().getExtras();
        if (extra != null){
           Usuario = extra.getString("USER");
           Password = extra.getString("PASS");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mReg: {
                Intent intent = new Intent(MainActivity.this, Perfil.class);
                intent.putExtra("USER", Usuario);
                intent.putExtra("PASS", Password);
                startActivityForResult(intent, 369);
            }
            break;
            case R.id.mOut: {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
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
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}
