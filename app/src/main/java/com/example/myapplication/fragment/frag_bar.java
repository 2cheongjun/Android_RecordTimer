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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.myapplication.Constant.LIST_INFO;
import static com.example.myapplication.Constant.TASK_LIST;

public class frag_bar extends Fragment {


    private View view;

    public static frag_bar newInstance(){
        frag_bar bar = new frag_bar();
        return bar;
    }

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_bar,container,false);
        BarChart barChart = view.findViewById(R.id.bar_chart);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LIST_INFO,0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASK_LIST, null);
        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        ArrayList<main_item> items = gson.fromJson(json, type);



        // BarEntry에는 바에 표시될 데이터를 저장한다. // 원하는 데이터를 BarEntry로 만든뒤 이를 담는 리스트를 선언해 추가한다.
        // BarEntry에 들어가는 기본값은 float이다. int나,String으로 바꿀경우 label을 다는 작업을 거쳐야한다.

        ArrayList<BarEntry> visitors = new ArrayList<>();
        int valueSize = 10;
        for (main_item each : items) {
            visitors.add(new BarEntry(valueSize,(each.get_time()/1000) / 60)); // 분으로 표기
            // 총합
            valueSize += 10;
        }

//        int hours = (int) (time / 1000) / 3600;
//        visitors.add(new BarEntry(10,1f));
//        visitors.add(new BarEntry(20,2f));
//        visitors.add(new BarEntry(30,3f));
//        visitors.add(new BarEntry(40,4f));




        BarDataSet barDataSet = new BarDataSet(visitors,"목표");
        BarData  barData = new BarData(barDataSet);

//        BarDataSet barDataSet = new BarDataSet( visitors,"visitors");
//        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);


        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("목표달성 시간(분)");
        barChart.animateY(2000);

        // void가 아니면 꼭 리턴을 해준다.
        return view;
    }


}
