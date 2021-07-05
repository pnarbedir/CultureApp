package com.example.cultureapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.cultureapp.Adapter.KullaniciAdapter;
import com.example.cultureapp.Cerceve.AramaFragment;
import com.example.cultureapp.Cerceve.BildirimFragment;
import com.example.cultureapp.Cerceve.HomeFragment;
import com.example.cultureapp.Cerceve.ProfileFragment;
import com.example.cultureapp.model.Kullanici;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Anasayfa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    Kullanici kullanici;
    FirebaseUser firebaseUser;
    StorageReference storageRef;

    ArrayList<String> userEmailfromFB;
    ArrayList<String> userCommentfromFB;
    ArrayList<String> userImagefromFB;
    //RecyclerActivity recyclerActivity;
    BottomNavigationView bottomNavigationView;
    Fragment seciliCerceve = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        drawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userEmailfromFB = new ArrayList<>();
        userCommentfromFB = new ArrayList<>();
        userImagefromFB = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);






        Bundle intent = getIntent().getExtras();
        if(intent != null) {
            String gonderen = intent.getString("gonderenId");
            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
            editor.putString("profileId",gonderen);
            editor.apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayicisi,new ProfileFragment()).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayicisi,new HomeFragment()).commit();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        NavigationView navigationView = (NavigationView) findViewById(R.id.menusol_navigation);
        navigationView.setNavigationItemSelectedListener(this);

    }//hangi idye tıkladıgımda neyi göstersin





    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            seciliCerceve = new HomeFragment();
                            break;

                        case R.id.nav_arama:
                            seciliCerceve = new AramaFragment();
                            break;

                        case R.id.nav_ayarlar:
                            seciliCerceve = null;
                            startActivity(new Intent(Anasayfa.this,AyarlarEkrani.class));
                            break;


                        case R.id.nav_kalp:
                            seciliCerceve = new BildirimFragment();
                            break;
                        case R.id.nav_profil:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                            editor.putString("profileid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            seciliCerceve = new ProfileFragment();
                            break;
                    }

                    if(seciliCerceve != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayicisi,seciliCerceve).commit();
                    }



                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        final TextView idgirisyapan;
        idgirisyapan = findViewById(R.id.girisyapan);
        final ImageView profilfotosu;
        profilfotosu = findViewById(R.id.profilfotosu);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Kullanicilar").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Kullanici kullanici = dataSnapshot.getValue(Kullanici.class);
                idgirisyapan.setText(kullanici.getAdSoyad());
                Glide.with(getApplicationContext()).load(kullanici.getResimurl()).into(profilfotosu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.okuma_list) {
            Intent intent = new Intent(Anasayfa.this,OkumaListOlustur.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        if(item.getItemId() == R.id.gezi_list) {
            Intent intent = new Intent(Anasayfa.this,GeziEkleme.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.ayarlar_menu) {
            Intent intent = new Intent(Anasayfa.this,AyarlarEkrani.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.pp) {
            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
            editor.putString("profileid",FirebaseAuth.getInstance().getCurrentUser().getUid());
            editor.apply();
            seciliCerceve = new ProfileFragment();
            Intent intent = new Intent(Anasayfa.this,ProfileFragment.class);



        }
        else if (item.getItemId() == R.id.konum_list) {
            Intent intent = new Intent(Anasayfa.this, MapsActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.okuma_list) {
            Intent intent = new Intent(Anasayfa.this,OkumaListOlustur.class);
            intent.putExtra("info","new");
            startActivity(intent);

        }

        else if(item.getItemId() == R.id.cikis_menu) {
            firebaseAuth.signOut();
            Intent intent = new Intent(Anasayfa.this,GirisEkrani.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void git(View v) {
        startActivity(new Intent(Anasayfa.this,GonderiActivity.class));

    }

    public void ClickAlintiPaylas(View view) {
        Intent intent = new Intent(Anasayfa.this,AlintiActivity.class);
        startActivity(intent);
    }

    public void ClickAlintiGoruntule(View view) {
        Intent intent = new Intent(Anasayfa.this,AlintiListActivity.class);
        startActivity(intent);
    }

   /* public void ClickKonum(View view) {
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        intent.putExtra("info","new");
        startActivity(intent);
    }*/
}