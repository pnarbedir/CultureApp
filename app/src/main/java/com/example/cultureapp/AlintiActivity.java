package com.example.cultureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlintiActivity extends AppCompatActivity {
    EditText edt_Kitap_Adi,edt_Kitap_Yazari,edt_Kitap_Alinti,edt_AlintiSayfaNo;
    TextView txt_Gonder2;
    DatabaseReference databaseAlintilar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alinti);
        databaseAlintilar = FirebaseDatabase.getInstance().getReference("alintilar");
        edt_Kitap_Adi = findViewById(R.id.edt_Kitap_Adi);
        edt_Kitap_Yazari = findViewById(R.id.edt_Kitap_Yazari);
        edt_Kitap_Alinti = findViewById(R.id.edt_Kitap_Alinti);
        edt_AlintiSayfaNo =findViewById(R.id.edt_AlintiSayfaNo);
        txt_Gonder2 = findViewById(R.id.txt_Gonder2);

        txt_Gonder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlinti();
            }
        });
    }
    private void addAlinti() {
        String AlintiKitapAd= edt_Kitap_Adi.getText().toString().trim();
        String AlintiYazarAd= edt_Kitap_Yazari.getText().toString().trim();
        String AlintiKitapAlinti= edt_Kitap_Alinti.getText().toString().trim();
        String AlintiSayfoNo= edt_AlintiSayfaNo.getText().toString().trim();
        String kullaniciID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TextUtils.isEmpty(AlintiKitapAd) || TextUtils.isEmpty(AlintiYazarAd) || TextUtils.isEmpty(AlintiKitapAlinti) || TextUtils.isEmpty(AlintiSayfoNo)){
            Toast.makeText(this, "Enter all informations.", Toast.LENGTH_SHORT).show();
        }
        else{
            String id = databaseAlintilar.push().getKey();



            AlintiOzellik alintiOzellik = new AlintiOzellik(id,AlintiKitapAlinti,AlintiKitapAd,AlintiYazarAd,AlintiSayfoNo,kullaniciID);
            databaseAlintilar.child(id).setValue(alintiOzellik);
            Toast.makeText(this, "Alinti Added.", Toast.LENGTH_SHORT).show();


        }
    }
}
