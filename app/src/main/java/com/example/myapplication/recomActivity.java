package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class recomActivity extends AppCompatActivity {
    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_plan);
        TextView planShow=findViewById(R.id.PlanShow);
        Button back = findViewById(R.id.back_button);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // 获取存储的数据
        res = sharedPreferences.getString("res", res);
        planShow.setText(res);//展示推荐计划
        back.setOnClickListener(v -> back());

    }
    private void back(){
        Intent intent=new Intent(recomActivity.this,MainActivity.class);
        startActivity(intent);
    }
}