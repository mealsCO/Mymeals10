package com.edopore.mymeals10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    EditText User, Pass, CPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        User = findViewById(R.id.eUser);
        Pass = findViewById(R.id.ePass);
        CPass = findViewById(R.id.eCpass);
    }

    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }
    public void onRegistrarClick(View view) {
        String user, pass, cpass;
        user = User.getText().toString();
        pass = Pass.getText().toString();
        cpass = CPass.getText().toString();
        if (user.isEmpty()||pass.isEmpty()||cpass.isEmpty()){
            Toast.makeText(this,R.string.nodata,Toast.LENGTH_SHORT).show();
        }else{
            if (pass.equals(cpass)){
                Intent intent = new Intent();
                intent.putExtra("USER",user);
                intent.putExtra("PASS",pass);
                setResult(RESULT_OK,intent);
                Toast.makeText(this,R.string.registered,Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Pass.setText("");
                CPass.setText("");
                Toast.makeText(this,R.string.nopass,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
