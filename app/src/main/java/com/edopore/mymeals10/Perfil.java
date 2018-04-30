package com.edopore.mymeals10;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edopore.mymeals10.modelo.Usuarios;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Perfil extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;

    private DatabaseReference databaseReference;

    private Bitmap bitmap;
    private String urlFoto;

    EditText eUs, ePas, eNam, eTel;//ePas es para saldo, eUs es para correo
    Button bEdit, bSave, bCancel;
    ImageView iFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ePas = findViewById(R.id.eSal);
        eUs = findViewById(R.id.eUs);
        eNam = findViewById(R.id.eNa);
        eTel = findViewById(R.id.eTel);
        iFoto = findViewById(R.id.iFoto);

        bEdit = findViewById(R.id.bEdit);
        bSave = findViewById(R.id.bSave);
        bSave.setVisibility(View.GONE);
        bCancel = findViewById(R.id.bCancel);
        bCancel.setVisibility(View.GONE);


        inicializar();
    }

    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    eUs.setText(firebaseUser.getEmail());
                    ePas.setText("20000");
                    eTel.setText(firebaseUser.getPhoneNumber());
                    eNam.setText(firebaseUser.getDisplayName());
                    if (firebaseUser.getPhotoUrl() != null) {
                        Picasso.get().load(firebaseUser.getPhotoUrl()).into(iFoto);
                    }
                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().
                build();
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this, this).
                addApi(Auth.GOOGLE_SIGN_IN_API, gso).
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
    public void onBackPressed() {
        super.onBackPressed();
        goMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mPrin) {
            Intent intent = new Intent(Perfil.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.mOuts) {
            firebaseAuth.signOut();
            if (Auth.GoogleSignInApi != null) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            goLogin();
                            Toast.makeText(Perfil.this, "Has cerrado la sesión", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Perfil.this, "Error cerrando sesión con google", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            if (LoginManager.getInstance() != null) {
                LoginManager.getInstance().logOut();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void goLogin() {
        Intent intent = new Intent(Perfil.this, Login.class);
        startActivity(intent);
        finish();

    }

    private void goMainActivity() {
        Intent intent = new Intent(Perfil.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void OnEditarClick(View view) {
        eUs.setEnabled(true);
        eNam.setEnabled(true);
        eTel.setEnabled(true);
        iFoto.isClickable();
        bEdit.setVisibility(View.GONE);
        bCancel.setVisibility(View.VISIBLE);
        bSave.setVisibility(View.VISIBLE);
    }

    public void OnSaveClicked(View view) {
        ePas.setEnabled(false);
        eUs.setEnabled(false);
        eNam.setEnabled(false);
        eTel.setEnabled(false);
        iFoto.isClickable();
        bEdit.setVisibility(View.VISIBLE);
        bCancel.setVisibility(View.GONE);
        bSave.setVisibility(View.GONE);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // comprimir foto
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        storageReference.child("Fotosusuarios").child(databaseReference.push().getKey())
                .putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                urlFoto = taskSnapshot.getDownloadUrl().toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error", e.getMessage().toString());
            }
        });

        Usuarios usuarios = new Usuarios(databaseReference.push().getKey(),
                eNam.getText().toString(),
                eTel.getText().toString(),
                eUs.getText().toString(),
                urlFoto,
                Integer.valueOf(ePas.getText().toString()));

        databaseReference.child("usuarios").child(usuarios.getId()).setValue(usuarios).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("entre1", "ok");
                } else {
                    Log.d("entre2", "ok");
                    Log.d("save", task.getException().toString());
                }
            }
        });
        Toast.makeText(Perfil.this, "almacenar", Toast.LENGTH_SHORT).show();
    }

    public void fotoClicked(View view){
        Intent fotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        fotoIntent.setType("image/*");
        startActivityForResult(fotoIntent,1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234 && resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(this, "ERROR CARGANDO FOTO", Toast.LENGTH_SHORT).show();
            }else {
                Uri imagen = data.getData();

                try {
                    InputStream is = getContentResolver().openInputStream(imagen);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bitmap = BitmapFactory.decodeStream(bis);

                    iFoto.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void OnCancelClicked(View view) {
        ePas.setEnabled(false);
        eUs.setEnabled(false);
        eNam.setEnabled(false);
        eTel.setEnabled(false);
        iFoto.isClickable();
        bEdit.setVisibility(View.VISIBLE);
        bCancel.setVisibility(View.GONE);
        bSave.setVisibility(View.GONE);
    }
}
