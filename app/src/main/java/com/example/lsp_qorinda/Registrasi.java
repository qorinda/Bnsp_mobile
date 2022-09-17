package com.example.lsp_qorinda;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrasi extends AppCompatActivity {
    DatabaseHelper db;
    Button login, registrasi;
    EditText username, password, passwordKonfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        db = new DatabaseHelper(this);
        username = (EditText)findViewById(R.id.formUsernameRegistrasi);
        password = (EditText)findViewById(R.id.formPasswordRegistrasi);
        passwordKonfirm = (EditText)findViewById(R.id.formPasswordConfirmRegist);
        login = (Button)findViewById(R.id.btnLoginRegis);
        registrasi = (Button)findViewById(R.id.btnRegisterRegis);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Registrasi.this, Login.class);
                startActivity(loginIntent);
                finish();
            }
        });

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPass = password.getText().toString();
                String strPassConf = passwordKonfirm.getText().toString();
                if(strPass.equals(strPassConf)){
                    Boolean regist = db.tambahUser(strUsername, strPass);
                    if(regist == true){
                        Toast.makeText(getApplicationContext(), "Registrasi Berhasil",Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(Registrasi.this, Login.class);
                        startActivity(loginIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Registrasi Gagal, semua kolom harap wajib di isi !!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password tidak valid !!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}