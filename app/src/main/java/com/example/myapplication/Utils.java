package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    public static Integer convertPrefixToInt(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        StringBuilder numberPart = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) {
                numberPart.append(ch);
            } else {
                break;
            }
        }

        if (numberPart.length() == 0) {
            return 0;
        }

        try {
            return Integer.parseInt(numberPart.toString());
        } catch (NumberFormatException e) {
            System.out.println("数字超出范围");
            return 0;
        }
    }

    private static final String PREFS_NAME = "app_prefs";
    private static final String PLANS_KEY = "plans_key";

    public static void savePlans(Context context, ArrayList<Plan> plans) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(plans);

        editor.putString(PLANS_KEY, json);
        editor.apply();
    }

    public static ArrayList<Plan> loadPlans(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(PLANS_KEY, null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
