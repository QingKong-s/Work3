package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.planList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GlobalData.initSampleData();
        PlanAdapter adapter = new PlanAdapter(this, GlobalData.plans);
        recyclerView.setAdapter(adapter);
    }
}