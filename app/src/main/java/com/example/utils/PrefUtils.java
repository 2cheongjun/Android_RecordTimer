package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.main.main_item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.Constant.TASK_LIST;

public class PrefUtils {

    public static String getPref(Context context, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("list_info", MODE_PRIVATE);
        String json = sharedPreferences.getString(key, null);

        return json;
    }


    // 리스트 전체 가져다쓸때
    public static ArrayList<main_item> getMainItems(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("list_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASK_LIST, null);

        Log.e("lee", "load : " + json);

        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        ArrayList<main_item> items = gson.fromJson(json, type);

        return items;
    }


    // 아이템 한개만 가져다쓸때
    public static main_item getMainItem(Context context, int position) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("list_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASK_LIST, null);

        Log.e("lee", "load : " + json);

        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        ArrayList<main_item> items = gson.fromJson(json, type);

        return items.get(position);
    }
}
