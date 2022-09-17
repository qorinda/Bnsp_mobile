//                                                          package com.example.lsp_qorinda;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_1_login);
//    }
//}
package com.example.lsp_qorinda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper database;
    Cursor cursor, cursor2;
    TextView editPengeluaran, editPemasukan;
    ImageView imgPengaturan,tambahPemasukan,tambahPengeluaran,dataKeuangan;

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(this);

        tambahPemasukan = (ImageView)findViewById(R.id.imagePemasukan);
        tambahPengeluaran = (ImageView)findViewById(R.id.imagePengeluaran);
        dataKeuangan = (ImageView)findViewById(R.id.imageDetail);
        editPemasukan = (TextView)findViewById(R.id.pemasukan);
        editPengeluaran = (TextView)findViewById(R.id.pengeluaran);
        imgPengaturan = (ImageView)findViewById(R.id.imagePengaturan);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM keuangan WHERE kategori = 'Pemasukan'", null);
        cursor2 = db.rawQuery("SELECT * FROM keuangan WHERE kategori = 'Pengeluaran'", null);
        double pemasukan = 0;
        double pengeluaran = 0;
        while(cursor.moveToNext()){
            double nominal = Double.parseDouble(cursor.getString(2));
            pemasukan = pemasukan + nominal;
        }
        while(cursor2.moveToNext()){
            double nominal = Double.parseDouble(cursor2.getString(2));
            pengeluaran = pengeluaran + nominal;
        }

        String luaran = "Pengeluaran : " + formatRupiah(pengeluaran);
        String masukan = "Pemasukan : " + formatRupiah(pemasukan);

        editPemasukan.setText(String.valueOf(masukan));
        editPengeluaran.setText(String.valueOf(luaran));

//        Log.d("ADebugTag", "Value: " + String.valueOf(pemasukan) + " - " + String.valueOf(pengeluaran));

        // Session check
        Boolean checkSession = database.sessionCheck("kosong");
        if(checkSession == true){
            Intent loginIntent = new Intent(MainActivity.this,Login.class);
            startActivity(loginIntent);
            finish();
        }

        // tambah pemasukan
        tambahPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TambahPemasukan.class);
                startActivity(intent);
                finish();
            }
        });

        // tambah pemasukan
        tambahPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TambahPengeluaran.class);
                startActivity(intent);
                finish();
            }
        });

        // tambah pemasukan
        dataKeuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetailCashFlow.class);
                startActivity(intent);
                finish();
            }
        });

        // pengaturan
        imgPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Pengaturan.class);
                startActivity(intent);
                finish();
            }
        });
    }
}