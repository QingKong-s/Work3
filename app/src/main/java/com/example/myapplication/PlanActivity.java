package com.example.myapplication;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {
    private Plan plan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        int idx = getIntent().getIntExtra("item_index", -1);
        plan = GlobalData.plans.get(idx);

        RecyclerView recyclerView = findViewById(R.id.mainList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpandableAdapter adapter = new ExpandableAdapter(plan.getActions());
        recyclerView.setAdapter(adapter);

        TextView tvName = findViewById(R.id.plan_name);
        tvName.setText(plan.getName());

        findViewById(R.id.addAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ActionItem> act = plan.getActions();
                ArrayList<SubItem> sub = new ArrayList<>();
                sub.add(new SubItem(10, 1, false));
                act.add(new ActionItem("新动作", 1, sub));
                adapter.notifyDataSetChanged();
            }
        });
    }
}