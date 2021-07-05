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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cultureapp.Cerceve.AramaFragment;
import com.example.cultureapp.Cerceve.BildirimFragment;
import com.example.cultureapp.Cerceve.HomeFragment;
import com.example.cultureapp.Cerceve.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

public class GeziEkleme extends AppCompatActivity{
    EditText etSehir,etSebep,etSehirNo;
    Button btnKaydet,btnListele,btnGeziSil,btnGeziGunc;
    TextView textView,textView2;
    FirebaseAuth firebaseAuth;
    Fragment seciliCerceve = null;
    SQLiteDatabase database;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gezi_ekleme);

        etSehir = findViewById(R.id.etSehir);
        etSebep = findViewById(R.id.etSebep);
        btnKaydet = findViewById(R.id.btnKaydet);
        btnListele = findViewById(R.id.btnGeziList);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        etSehirNo = findViewById(R.id.etSehirNo);
        btnGeziSil = findViewById(R.id.btnGeziSil);
        btnGeziGunc = findViewById(R.id.btnGeziGuncelle);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

            firebaseAuth=FirebaseAuth.getInstance();

        database = this.openOrCreateDatabase("GeziListem",MODE_PRIVATE,null);
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if(info.matches("new")) {
            etSehir.setText("");
            etSebep.setText("");
            btnGeziGunc.setVisibility(View.INVISIBLE);
            btnGeziSil.setVisibility(View.INVISIBLE);
        }
        else {
            int gezid = intent.getIntExtra("gezid",1);
            btnKaydet.setVisibility(View.INVISIBLE);

            try {
                Cursor cursor = database.rawQuery("SELECT * FROM GeziListem WHERE id = ?",new String[] {String.valueOf(gezid)});
                int sehirnoIx = cursor.getColumnIndex("id");
                int sehirIx = cursor.getColumnIndex("yeradi");
                int sebepIx = cursor.getColumnIndex("sebep");

                while (cursor.moveToNext()) {
                    etSehir.setText(cursor.getString(sehirIx));
                    etSebep.setText(cursor.getString(sebepIx));
                    etSehirNo.setText(cursor.getString(sehirnoIx));
                }
                cursor.close();
            }

            catch (Exception e) {

            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        Intent intent = new Intent(GeziEkleme.this,Anasayfa.class);

                        startActivity(intent);
                    }
                    return true;
                }
            };
    public void save(View view) {
        String yeradi = etSehir.getText().toString();
        String sebep = etSebep.getText().toString();
        if(yeradi.isEmpty() || sebep.isEmpty()) {
            Toast.makeText(GeziEkleme.this,"Please enter your city",Toast.LENGTH_LONG).show();
        }
        else {
            try {
                database = this.openOrCreateDatabase("GeziListem", MODE_PRIVATE, null);
                database.execSQL("CREATE TABLE IF NOT EXISTS GeziListem (id INTEGER PRIMARY KEY,yeradi VARCHAR, sebep VARCHAR)");
                String sqlString = "INSERT INTO GeziListem(yeradi,sebep)VALUES(?,?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);

                sqLiteStatement.bindString(1, yeradi);
                sqLiteStatement.bindString(2, sebep);
                sqLiteStatement.execute();

                Toast.makeText(GeziEkleme.this, "successfully added", Toast.LENGTH_LONG).show();


            } catch (Exception e) {
                Toast.makeText(GeziEkleme.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }


        }
      /*  */
    }
    public void ClickGeziList(View view) {

        Intent intent = new Intent(GeziEkleme.this,GeziListesi.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void ClickGeziSil(View view) {
        int id = Integer.parseInt(etSehirNo.getText().toString());

        String whereCalue = "id" + " = ? ";
        String whereArgs []  = new String[]
                {String.valueOf(id)};

        int result =  database.delete("GeziListem",whereCalue,whereArgs);
        Toast.makeText(this, "Gezi Bilgileri silindi.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GeziEkleme.this,GeziListesi.class);
        startActivity(intent);
    }

    public void ClickGeziGunc(View view) {
        String sehir = etSehir.getText().toString();
        String sebep = etSebep.getText().toString();


        ContentValues values = new ContentValues();
        values.put("yeradi", sehir);
        values.put("sebep",sebep );

        int id = Integer.parseInt(etSehirNo.getText().toString());
        String whereCalue = "id" + " = ? ";
        String whereArgs[] = new String[]{String.valueOf(id)};

        long result = database.update("GeziListem", values, whereCalue, whereArgs);
        Toast.makeText(this, "Gezi bilgileri güncellendi.", Toast.LENGTH_SHORT).show();
    }






}
