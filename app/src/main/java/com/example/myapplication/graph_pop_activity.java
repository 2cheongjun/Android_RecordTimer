package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


// 목표달성률 통계 팝업 (메인에서 그래프 버튼 클릭시 하단에서 창이 뜸)
public class graph_pop_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_pop);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 사용중인 리소스 정지 준비
    }


    @Override
    protected void onStop() {
        super.onStop();
        // 정보저장하기 기능 정지


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 전체 종료

    }
}
