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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
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

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;

    private SignInButton btnSignInGoogle;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    EditText user, pass;
    String Usuario="", Password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.eUser);
        pass = findViewById(R.id.ePass);

        btnSignInGoogle = findViewById(R.id.btnSignInGoogle);

        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,12);
            }
        });

        inicializar();

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent i = new Intent(Login.this, MainActivity.class);
                setResult(3,i);
                Toast.makeText(Login.this,R.string.registered,Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
                finish();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this, "error al registrarse face",Toast.LENGTH_SHORT).show();
            }
        });


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


        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().
                build();
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this,this).
                addApi(Auth.GOOGLE_SIGN_IN_API,gso).
                build();
    }
//***************************????????????????????????????????????????************************

    private void signInGoogle(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(),null);

            firebaseAuth.signInWithCredential(authCredential).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivityForResult(i,11);
                                Toast.makeText(Login.this,R.string.registered,Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(Login.this, "error al registrarse",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Usuario = data.getStringExtra("USER");
            Password = data.getStringExtra("PASS");
            user.setText(Usuario);
        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
                Toast.makeText(Login.this, R.string.noreg, Toast.LENGTH_SHORT).show();
        }else {
            if (requestCode == 12) {
                GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.
                        getSignInResultFromIntent(data);
                signInGoogle(googleSignInResult);
            } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
