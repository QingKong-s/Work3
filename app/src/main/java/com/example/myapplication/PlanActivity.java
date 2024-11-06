package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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