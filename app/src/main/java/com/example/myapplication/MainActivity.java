package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String url1="http://10.0.2.2:9000";
    List<Action> Actions = new ArrayList<>();
    String res;//获取的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.planList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button addPlan = findViewById(R.id.addPlan);
        Button recommendPlan = findViewById(R.id.button4);

        GlobalData.plans = Utils.loadPlans(this);

        PlanAdapter adapter = new PlanAdapter(this, GlobalData.plans);
        recyclerView.setAdapter(adapter);

        addPlan.setOnClickListener(v -> addPlan());
        recommendPlan.setOnClickListener(v ->recommendplan());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.savePlans(this, GlobalData.plans);
    }

    private void addPlan(){
        // 添加一些 Action 对象到列表
        

        
        OkHttpClient client = new OkHttpClient();
        //JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        Actions.add(new Action(1, 50, 10));
        Actions.add(new Action(2, 75, 15));
        Actions.add(new Action(3, 40, 8));

        try {
            for (Action action : Actions) {
                // 获取每个 Action 对象的属性
                int actionId = action.getActionId();
                double weight = action.getWeight();
                int count = action.getCount();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userid", "user");
                jsonObject.put("age", "21");
                jsonObject.put("height", "180");
                jsonObject.put("weight", "130");
                jsonObject.put("sex", "1");
                jsonObject.put("actionid", actionId);
                jsonObject.put("actionweight", weight);
                jsonObject.put("actionnumber", count);
                jsonArray.put(jsonObject);


            }

             }catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.i("111", jsonArray.toString());//测试发送的文件
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonArray.toString());

        Request request = new Request.Builder()
                .url(url1)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res = response.body().string(); // 获取数据
                Log.e("response", res);
                try {
                    // 使用 JSONObject 来解析 JSON 字符串
                    JSONObject jsonObject1 = new JSONObject(res);

                    // 提取数据
                    String status = jsonObject1.getString("status");
                    String receivedData = jsonObject1.getString("received_data");
                    saveRes(receivedData);
                    // receivedData 已经自动转化为中文字符
                    Log.d("Received Data", receivedData);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        GlobalData.addNewPlan();
        runOnUiThread(() -> {
            PlanAdapter adapter = (PlanAdapter)
                    ((RecyclerView)findViewById(R.id.planList)).getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void saveRes(String res){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

// 存储数据
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("res", res);
        editor.apply();
    }
    private void recommendplan(){
        Intent intent = new Intent(MainActivity.this, RecomActivity.class);
        startActivity(intent);


    }
}