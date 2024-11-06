package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        int idx = getIntent().getIntExtra("item_index", -1);
        Plan plan = GlobalData.plans.get(idx);

        RecyclerView recyclerView = findViewById(R.id.mainList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpandableAdapter adapter = new ExpandableAdapter(plan.getActions());
        recyclerView.setAdapter(adapter);

        TextView tvName = findViewById(R.id.plan_name);
        tvName.setText(plan.getName());
    }
}