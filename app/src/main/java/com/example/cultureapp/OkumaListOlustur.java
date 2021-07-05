package com.example.cultureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cultureapp.Cerceve.AramaFragment;
import com.example.cultureapp.Cerceve.BildirimFragment;
import com.example.cultureapp.Cerceve.HomeFragment;
import com.example.cultureapp.Cerceve.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class OkumaListOlustur extends AppCompatActivity {
    EditText etKitapAd,etYazarAd,etKitapNot,etKitapNo;
    Button btnKitapKaydet,btnKitapGuncelle,btnKitapSil;
    SQLiteDatabase database;
    Fragment seciliCerceve = null;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okuma_list_olustur);
        etKitapAd = findViewById(R.id.etKitapAd);
        etYazarAd = findViewById(R.id.etYazarAd);
        etKitapNot = findViewById(R.id.etKitapNot);
        etKitapNo = findViewById(R.id.etKitapNo);
        btnKitapKaydet = findViewById(R.id.btnKitapKaydet);
        btnKitapSil = findViewById(R.id.btnKitapSil);
        btnKitapGuncelle = findViewById(R.id.btnKitapGuncelle);
        database = this.openOrCreateDatabase("kitap", MODE_PRIVATE, null);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if (info.matches("new")) {
            etKitapAd.setText("");
            etYazarAd.setText("");
            etKitapNot.setText("");
            btnKitapKaydet.setVisibility(View.VISIBLE);
            btnKitapGuncelle.setVisibility(View.INVISIBLE);
            btnKitapSil.setVisibility(View.INVISIBLE);


        } else {

            int kitapId = intent.getIntExtra("kitapId", 1);
            btnKitapKaydet.setVisibility(View.INVISIBLE);

            try {
                Cursor cursor = database.rawQuery("SELECT * FROM kitaplar WHERE id = ?", new String[]{String.valueOf(kitapId)});
                int kitapadIx = cursor.getColumnIndex("kitapad");
                int yazaradNameIx = cursor.getColumnIndex("yazarad");
                int kitapnotIx = cursor.getColumnIndex("kitapnot");
                int kitapnoIx = cursor.getColumnIndex("id");


                while (cursor.moveToNext()) {
                    etKitapAd.setText(cursor.getString(kitapadIx));
                    etYazarAd.setText(cursor.getString(yazaradNameIx));
                    etKitapNot.setText(cursor.getString(kitapnotIx));
                    etKitapNo.setText(cursor.getString(kitapnoIx));

                }
                cursor.close();
            } catch (Exception e) {

            }
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        Intent intent = new Intent(OkumaListOlustur.this,Anasayfa.class);

                        startActivity(intent);
                    }

                    return true;
                }
            };
    public void ClickKitapKaydet(View view) {
        String kitapAd = etKitapAd.getText().toString();
        String yazarAd = etYazarAd.getText().toString();
        String kitapNot = etKitapNot.getText().toString();

        try {
            database = this.openOrCreateDatabase("kitap", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS kitaplar (id INTEGER PRIMARY KEY,kitapad VARCHAR, yazarad VARCHAR, kitapnot VARCHAR)");


            String sqlString = "INSERT INTO kitaplar(kitapad,yazarad,kitapnot) VALUES (?,?,?)";


            SQLiteStatement sqliteStatement = database.compileStatement(sqlString);
            sqliteStatement.bindString(1, kitapAd);
            sqliteStatement.bindString(2, yazarAd);
            sqliteStatement.bindString(3, kitapNot);
            sqliteStatement.execute();

        } catch (Exception e) {

        }
    }

    public void ClickKitapListele(View view) {
        Intent intent = new Intent(OkumaListOlustur.this,OkumaListesi.class);
        startActivity(intent);
    }
    public void ClickKitapSil(View view) {

        int id = Integer.parseInt(etKitapNo.getText().toString());

        String whereCalue = "id" + " = ? ";
        String whereArgs []  = new String[]
                {String.valueOf(id)};

        int result =  database.delete("kitaplar",whereCalue,whereArgs);
        Toast.makeText(this, "Kitap Bilgileri silindi.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OkumaListOlustur.this,OkumaListesi.class);
        startActivity(intent);
    }

    public void ClickKitapGuncelle(View view) {
        String kitapAd = etKitapAd.getText().toString();
        String yazarAd = etYazarAd.getText().toString();
        String kitapNot = etKitapNot.getText().toString();

        ContentValues values = new ContentValues();
        values.put("kitapad", kitapAd);
        values.put("yazarad",yazarAd );
        values.put("kitapnot", kitapNot);
        int id = Integer.parseInt(etKitapNo.getText().toString());
        String whereCalue = "id" + " = ? ";
        String whereArgs[] = new String[]{String.valueOf(id)};

        long result = database.update("kitaplar", values, whereCalue, whereArgs);
        Toast.makeText(this, "Kitap bilgileri güncellendi.", Toast.LENGTH_SHORT).show();
    }

}