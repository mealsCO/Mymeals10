package com.edopore.mymeals10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Registro extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    EditText User, Pass, CPass;
    String user, pass, cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        User = findViewById(R.id.eUser);
        Pass = findViewById(R.id.ePass);
        CPass = findViewById(R.id.eCpass);

        inicializar();

    }


    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.d("FirebaseUser","usuario logueado: "+ firebaseUser.getEmail());
                    Toast.makeText(Registro.this,"Usuario logueado",Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("FirebaseUser", "El ususario ha cerrado sesión");
                    Toast.makeText(Registro.this,"Usuario no logueado",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }
    public void onRegistrarClick(View view) {

        user = User.getText().toString();
        pass = Pass.getText().toString();
        cpass = CPass.getText().toString();
        if (user.isEmpty()||pass.isEmpty()||cpass.isEmpty()){
            Toast.makeText(this,R.string.nodata,Toast.LENGTH_SHORT).show();
        }else if(!user.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
            Toast.makeText(this, "Correo no Valido",Toast.LENGTH_SHORT).show();
        }else{
            if (pass.equals(cpass)){
                firebaseAuth.createUserWithEmailAndPassword(user,pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent();
                                    intent.putExtra("USER", user);
                                    intent.putExtra("PASS", pass);
                                    setResult(RESULT_OK, intent);
                                    Toast.makeText(Registro.this, R.string.registered, Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(Registro.this, "error al crear la cuenta",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                Pass.setText("");
                CPass.setText("");
                Toast.makeText(this,R.string.nopass,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
