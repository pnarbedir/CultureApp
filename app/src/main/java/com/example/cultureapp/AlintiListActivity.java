package com.example.cultureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class AlintiListActivity extends AppCompatActivity {
private RecyclerView mrecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alinti_list);
        mrecyclerView = findViewById(R.id.recyclerView_alinti);
        new FirebaseDatabaseAlinti().readAlinti(new FirebaseDatabaseAlinti.DataStatus() {
            @Override
            public void DataIsLoaded(List<AlintiOzellik> alintilist, List<String> keys) {
                new RecyclerView_alinti().setConfig(mrecyclerView,AlintiListActivity.this,alintilist,keys);
            }

            @Override
            public void DataInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}
