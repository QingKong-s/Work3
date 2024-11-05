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

        List<ActionItem> actionItems = new ArrayList<>();

// 创建一些子项数据（SubItem）
        List<SubItem> subItems1 = new ArrayList<>();
        subItems1.add(new SubItem("50", "10", false));
        subItems1.add(new SubItem("55", "8", false));
        subItems1.add(new SubItem("60", "6", true));

        List<SubItem> subItems2 = new ArrayList<>();
        subItems2.add(new SubItem("30", "12", false));
        subItems2.add(new SubItem("35", "10", false));

        List<SubItem> subItems3 = new ArrayList<>();
        subItems3.add(new SubItem("20", "15", true));
        subItems3.add(new SubItem("25", "12", true));
        subItems3.add(new SubItem("30", "10", false));

// 创建 ActionItem 并加入到主列表中
        actionItems.add(new ActionItem("动作1", 6, subItems1));
        actionItems.add(new ActionItem("动作2", 4, subItems2));
        actionItems.add(new ActionItem("动作3", 5, subItems3));
        actionItems.add(new ActionItem("动作4", 5, subItems3));
        actionItems.add(new ActionItem("动作5", 5, subItems3));

        RecyclerView recyclerView = findViewById(R.id.mainList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpandableAdapter adapter = new ExpandableAdapter(actionItems);
        recyclerView.setAdapter(adapter);

    }
}