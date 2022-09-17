package com.example.lsp_qorinda;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TambahPemasukan extends AppCompatActivity {
    EditText editTanggal, formNom, formKet;
    DatePickerDialog.OnDateSetListener setListener;
    Button btnKembali, btnSimpanPemasukan;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_tambahpemasukan);

        db = new DatabaseHelper(this);
        editTanggal = findViewById(R.id.formDatePicker);
        formNom = findViewById(R.id.formNominal);
        formKet = findViewById(R.id.formKeterangan);
        btnKembali = findViewById(R.id.btnKembali);
        btnSimpanPemasukan = findViewById(R.id.btnSimpanPemasukan);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TambahPemasukan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        editTanggal.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        // back to menu
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(TambahPemasukan.this,MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        btnSimpanPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = editTanggal.getText().toString();
                String strNominal = formNom.getText().toString();
                String strKeterangan = formKet.getText().toString();
                if(strDate != "" && strNominal != "" && strKeterangan != ""){
                    Boolean addPemasukan = db.tambahDataKeuangan(strDate, strNominal, strKeterangan, "Pemasukan");
                    if(addPemasukan == true){
                        Toast.makeText(getApplicationContext(), "Berhasil menambah pemasukan",Toast.LENGTH_SHORT).show();
                        Intent refreshPage = new Intent(TambahPemasukan.this, TambahPemasukan.class);
                        startActivity(refreshPage);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Semua data wajib di isi !!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
