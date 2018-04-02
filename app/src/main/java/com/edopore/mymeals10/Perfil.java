package com.edopore.mymeals10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Perfil extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText eUs, ePas;
    String User, Password;

    private GoogleApiClient googleApiClient;

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

        inicializar();
    }

    private void inicializar() {
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
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        Intent intent = new Intent(Perfil.this, Login.class);
                        intent.putExtra("us", User);
                        intent.putExtra("co", Password);
                        startActivityForResult(intent, 1);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        Toast.makeText(Perfil.this, "no se pudo cerrar sesión",Toast.LENGTH_SHORT).show();
                    }
                }
            });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
