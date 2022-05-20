package com.example.myapplication.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.main.main_item;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.myapplication.Constant.LIST_INFO;
import static com.example.myapplication.Constant.TASK_LIST;

public class frag_pie extends Fragment {


    private View view;

    public static frag_pie newInstance(){
        frag_pie pie = new frag_pie();
        return pie;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_pie,container,false);
        PieChart pieChart = view.findViewById(R.id.pie_chart);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LIST_INFO,0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASK_LIST, null);
        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        ArrayList<main_item> items = gson.fromJson(json, type);

        // BarEntry에는 바에 표시될 데이터를 저장한다. // 원하는 데이터를 BarEntry로 만든뒤 이를 담는 리스트를 선언해 추가한다.
        // BarEntry에 들어가는 기본값은 float이다. int나,String으로 바꿀경우 label을 다는 작업을 거쳐야한다.


        ArrayList<PieEntry> visitors = new ArrayList<>();
        int valueSize = 0;
        for (main_item each : items) {
            visitors.add(new PieEntry(((each.get_time()/1000) / 60), each.get_goal()));
            // 총합
            valueSize += each.get_time();
        }

//        ArrayList<PieEntry> visitors = new ArrayList<>();
//        visitors.add(new PieEntry(500,"2016"));
//        visitors.add(new PieEntry(600,"2017"));
//        visitors.add(new PieEntry(700,"2018"));
//        visitors.add(new PieEntry(800,"2096"));
//        visitors.add(new PieEntry(500,"2020"));


        PieDataSet pieDataSet = new PieDataSet(visitors,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("공부비율");
        pieChart.animateY(2000);

        return view;
    }

}
