package com.example.cultureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;




public class GeziListesi extends AppCompatActivity {
    ListView listView;
   // SQLiteDatabase database;
    ArrayList<String> geziYeriArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gezi_listesi);

        listView = findViewById(R.id.lvListe);
        geziYeriArray = new ArrayList<String>();
        idArray = new ArrayList<Integer>();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        // database = this.openOrCreateDatabase("GeziListem", MODE_PRIVATE, null);
        //database.execSQL("CREATE TABLE IF NOT EXISTS GeziListem (id INTEGER PRIMARY KEY, yeradi VARCHAR, sebep VARCHAR)");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, geziYeriArray);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GeziListesi.this,GeziEkleme.class);
                intent.putExtra("gezid",idArray.get(position));
                intent.putExtra("info","old");
                startActivity(intent);
            }
        });
        getData();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        Intent intent = new Intent(GeziListesi.this,Anasayfa.class);

                        startActivity(intent);
                    }

                    return true;
                }
            };
    public void getData() {

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("GeziListem",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM GeziListem", null);
            int nameIx = cursor.getColumnIndex("yeradi");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                geziYeriArray.add(cursor.getString(nameIx));
                idArray.add(cursor.getInt(idIx));

            }

            arrayAdapter.notifyDataSetChanged();

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*public void Show(View view) {
        //Cursor cursor = database.rawQuery("SELECT * FROM GeziListem",null);
            try {
                database = this.openOrCreateDatabase("GeziListem", MODE_PRIVATE, null);
                Cursor cursor = database.rawQuery("SELECT * FROM GeziListem", null);
                int yer = cursor.getColumnIndex("yeradi");
                int sebep = cursor.getColumnIndex("sebep");


                while (cursor.moveToNext()) {
                    etSehir.setText(cursor.getString(yer));
                    etSebep.setText(cursor.getString(sebep));
                }
                cursor.close();

            }
            catch (Exception e) {

            }


    }*/
}
