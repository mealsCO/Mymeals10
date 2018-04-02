package com.edopore.mymeals10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    EditText user, pass;
    String Usuario="", Password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.eUser);
        pass = findViewById(R.id.ePass);

        inicializar();

        Bundle ex = getIntent().getExtras();
        if (ex != null) {
            Usuario = ex.getString("us");
            Password = ex.getString("co");
        }
    }
//********************************??????????????????????????????*****************************
    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.d("FirebaseUser","usuario logueado: "+ firebaseUser.getEmail());
                }else {
                    Log.d("FirebaseUser", "El ususario ha cerrado sesión");
                }
            }
        };
    }
//***************************????????????????????????????????????????************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Usuario = data.getStringExtra("USER");
            Password = data.getStringExtra("PASS");
            user.setText(Usuario);
        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
                Toast.makeText(Login.this, R.string.noreg, Toast.LENGTH_SHORT).show();
        } else if (requestCode == 1 && requestCode == RESULT_FIRST_USER){
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onIngresarClick(View view) {
        final String usuario = user.getText().toString();
        final String contra = pass.getText().toString();

        if (user.getText().toString().isEmpty() && pass.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.logerror2, Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(usuario,contra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent l = new Intent().setClass(Login.this, MainActivity.class);
                        l.putExtra("USER",usuario);
                        l.putExtra("PASS",contra);
                        setResult(RESULT_OK,l);
                        startActivity(l);
                        finish();
                    }else {
                        Toast.makeText(Login.this, R.string.logerror, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onRegistrarClick(View view) {
        Intent l = new Intent().setClass(this, Registro.class);
        startActivityForResult(l, 1);
    }
}
