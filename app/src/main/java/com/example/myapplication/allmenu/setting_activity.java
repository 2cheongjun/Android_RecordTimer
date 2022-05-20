package com.example.myapplication.allmenu;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


// 세팅 상세페이지
// 알람, 진동, 위젯 설정
// 버전표기
// 데이터 초기화를 할수 있다.

public class setting_activity extends AppCompatActivity {

    // 뒤로가기 버튼
    ImageView iv_back;
    Switch sw_alarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 뒤로가기 버튼
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 설정 온오프
        sw_alarm = findViewById(R.id.sw_alarm);
        sw_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            private Object main_activity;

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    optionState.setText("옵션 활성화");]

                } else {
                   // 메인에 있는 알람끄기
//                    NotificationManagerCompat.from((Context) main_activity).cancel(1);

                }
            }
        });
    }
}