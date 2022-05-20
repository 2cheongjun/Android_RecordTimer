package com.example.myapplication.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


// 비밀번호 인증 보내기 화면

public class email_finish_activity extends AppCompatActivity {

    // 로그인으로 돌아가기 버튼
    TextView tv_back_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_finish);

        // 비번인증 마지막 페이지에서 인증메일을 보낸뒤 다시 로그인화면으로 돌아감.
        tv_back_login =(TextView)findViewById(R.id.tv_back_login);
        tv_back_login.setClickable(true);
        tv_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(email_finish_activity.this, intro_activity.class);
                startActivity(intent);

                finish();
            }
        });

    }
}