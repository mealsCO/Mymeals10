package com.edopore.mymeals10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText user, pass;
    String Usuario, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.eUser);
        pass = findViewById(R.id.ePass);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            Usuario = data.getStringExtra("USER");
            Password = data.getStringExtra("PASS");
            user.setText(Usuario);
        } else {
            if (requestCode == 1234 && resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.noreg, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onIngresarClick(View view) {
        if (Usuario.isEmpty() || Password.isEmpty()) {
            Toast.makeText(this, R.string.logerror2, Toast.LENGTH_SHORT).show();
        }
        if (Usuario.equals(user.getText().toString()) && Password.equals(pass.getText().toString())) {
            Intent l = new Intent().setClass(this, MainActivity.class);
            l.putExtra("USER",Usuario);
            l.putExtra("PASS",Password);
            startActivityForResult(l, 4321);
            finish();
        } else {
            Toast.makeText(this, R.string.logerror, Toast.LENGTH_SHORT).show();
        }
        
    }

    public void onRegistrarClick(View view) {
        Intent l = new Intent().setClass(this, Registro.class);
        startActivityForResult(l, 1234);
    }
}
