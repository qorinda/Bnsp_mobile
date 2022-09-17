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

public class TambahPengeluaran extends AppCompatActivity {
    EditText editDate, formNominal, formKeterangan;
    DatePickerDialog.OnDateSetListener setListener;
    Button backToMenu, btnSimpanPengeluaran;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_tambahpengeluaran);

        db = new DatabaseHelper(this);
        editDate = findViewById(R.id.formDatePickerPengeluaran);
        formNominal = findViewById(R.id.formNominalPengeluaran);
        formKeterangan = findViewById(R.id.formKeteranganPengeluaran);
        backToMenu = findViewById(R.id.btnKembali);
        btnSimpanPengeluaran = findViewById(R.id.btnSimpanPengeluaran);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TambahPengeluaran.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        editDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        // tambah pemasukan
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(TambahPengeluaran.this,MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        btnSimpanPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = editDate.getText().toString();
                String strNominal = formNominal.getText().toString();
                String strKeterangan = formKeterangan.getText().toString();
                if(strDate != "" && strNominal != "" && strKeterangan != ""){
                    Boolean addPengeluaran = db.tambahDataKeuangan(strDate, strNominal, strKeterangan, "Pengeluaran");
                    if(addPengeluaran == true){
                        Toast.makeText(getApplicationContext(), "Berhasil menambah pengeluaran",Toast.LENGTH_SHORT).show();
                        Intent refreshPage = new Intent(TambahPengeluaran.this, TambahPengeluaran.class);
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
