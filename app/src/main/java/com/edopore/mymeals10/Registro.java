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

public class Registro extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;

    private SignInButton btnSignInGoogle;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    EditText User, Pass, CPass;
    String user, pass, cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        User = findViewById(R.id.eUser);
        Pass = findViewById(R.id.ePass);
        CPass = findViewById(R.id.eCpass);
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle);

        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,1);
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
                Intent i = new Intent(Registro.this, Login.class);
                setResult(3,i);
                Toast.makeText(Registro.this,R.string.registered,Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
                finish();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Registro.this, "error al registrarse face",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.
                    getSignInResultFromIntent(data);
            signInGoogle(googleSignInResult);
        }else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void signInGoogle(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(),null);

            firebaseAuth.signInWithCredential(authCredential).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent i = new Intent(Registro.this, MainActivity.class);
                        startActivityForResult(i,11);
                        setResult(RESULT_FIRST_USER,i);
                        Toast.makeText(Registro.this,R.string.registered,Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Registro.this, "error al registrarse",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

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


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
