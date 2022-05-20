package com.example.myapplication.allmenu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

// 메뉴 - term_of_use
// 약관 내용
public class term_of_use_activity extends AppCompatActivity {

    // 뒤로가기버튼
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_of_use);

        // 뒤로가기버튼
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        // 약관내용 스크롤하기
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 약관정보 가져오기

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 약관내용가져오기 중지
    }


    @Override
    protected void onStop() {
        super.onStop();
        // onCreate 에 작성된 내용중지
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 전체 종료

    }
}
