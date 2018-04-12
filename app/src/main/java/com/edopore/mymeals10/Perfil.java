package com.edopore.mymeals10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Perfil extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;

    EditText eUs, ePas;
    String User;
    ImageView iFoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ePas = findViewById(R.id.ePas);
        eUs = findViewById(R.id.eUs);
        iFoto = findViewById(R.id.iFoto);


        inicializar();
    }

    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    eUs.setText(firebaseUser.getEmail());
                    ePas.setText(firebaseUser.getUid());
                    Picasso.get().load(firebaseUser.getPhotoUrl()).into(iFoto);
                }else {
                    goLogin();
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

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
        googleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
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
            startActivity(intent);
            finish();


        }else if (id == R.id.mOuts){
            firebaseAuth.signOut();
            if (Auth.GoogleSignInApi != null){
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()){
                            Intent intent = new Intent(Perfil.this, Login.class);
                            intent.putExtra("us", User);
                            startActivityForResult(intent, 1);
                            setResult(RESULT_OK, intent);
                            finish();
                            Toast.makeText(Perfil.this, "El ususario ha cerrado sesión", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Perfil.this, "error cerrando sesion con google", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            if (LoginManager.getInstance() != null){
                LoginManager.getInstance().logOut();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void goLogin(){
        Intent intent = new Intent(Perfil.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void goMainActivity(){
        Intent intent = new Intent(Perfil.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMainActivity();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
