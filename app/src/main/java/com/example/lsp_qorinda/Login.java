package com.example.lsp_qorinda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DatabaseHelper db;
    Button login, registrasi;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_login);

        db = new DatabaseHelper(this);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btnLoginOnLogin);
        registrasi = (Button)findViewById(R.id.btnRegisterOnLogin);

        Boolean checkSession = db.sessionCheck("ada");
        if(checkSession == true){
            Intent loginIntent = new Intent(Login.this,MainActivity.class);
            startActivity(loginIntent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                Boolean auth = db.cekLogin(strUsername, strPassword);
                if(auth == true) {
                    int totalData = db.sessionCount();
                    Boolean updateSess = false;
                    Boolean createSess = false;
                    if (totalData < 1) {
                        createSess = db.createSession("ada", 1);
                    } else {
                        updateSess = db.updateSession("ada", 1);
                    }
                    if (updateSess == true || createSess == true) {
                        Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(Login.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Login Gagal, Username atau Password Salah !!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registIntent = new Intent(Login.this, Registrasi.class);
                startActivity(registIntent);
                finish();
            }
        });

    }
}
