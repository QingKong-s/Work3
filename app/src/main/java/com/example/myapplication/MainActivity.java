package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String url1="http://10.0.2.2:9000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.planList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button addPlan = findViewById(R.id.addPlan);
        GlobalData.initSampleData();
        PlanAdapter adapter = new PlanAdapter(this, GlobalData.plans);
        recyclerView.setAdapter(adapter);
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlan();

            }
        });
    }

    private void addPlan(){
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "123");
            jsonObject.put("password", "123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Request request = new Request.Builder()
                .url(url1)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string(); // 获取图书数据
                Log.e("response", res);
                //Message msg = new Message();
                // msg.what = MSG_SHOP_OK;
                // msg.obj = res;
                //mHandler.sendMessage(msg);
            }
        });
        GlobalData.addNewPlan();
        runOnUiThread(() -> {
            PlanAdapter adapter = (PlanAdapter) ((RecyclerView) findViewById(R.id.planList)).getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });

    }
}