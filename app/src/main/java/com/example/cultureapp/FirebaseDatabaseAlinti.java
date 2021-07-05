package com.example.cultureapp;

import android.widget.ListView;

import com.example.cultureapp.model.Kullanici;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseDatabaseAlinti {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private List <AlintiOzellik> alintilist = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List <AlintiOzellik> alintilist, List<String> keys);
        void DataInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseAlinti() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("alintilar");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void readAlinti(final DataStatus dataStatus) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                alintilist.clear();

                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    AlintiOzellik alintiOzellik = keyNode.getValue(AlintiOzellik.class);
                    alintilist.add(alintiOzellik);
                }


            dataStatus.DataIsLoaded(alintilist,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
