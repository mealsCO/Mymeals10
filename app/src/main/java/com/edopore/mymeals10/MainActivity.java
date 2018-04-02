package com.edopore.mymeals10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    String User, Password;

    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            User = extras.getString("USER");
            Password = extras.getString("PASS");
        }
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
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result = opr.get();
            resultGoogle (result);
        }else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {  //si no se inicia sesion o
                    resultGoogle(googleSignInResult);                                   // expira la sesion
                }
            });
        }
    }

    private void resultGoogle(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            User = account.getEmail();
            Password = account.getId();
        }
//       else {
//            Intent intent = new Intent(MainActivity.this, Login.class);
//            startActivityForResult(intent,1);
//            finish();
//        }
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
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.putExtra("us", User);
                        intent.putExtra("co", Password);
                        startActivityForResult(intent, 1);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "no se pudo cerrar sesión",Toast.LENGTH_SHORT).show();
                    }
                }
            });


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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
