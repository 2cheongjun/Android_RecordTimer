package com.example.myapplication.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


// 작성한 이메일 주소를 받아온뒤 이메일 인증하기
public class email_spend_activity extends AppCompatActivity {

    TextView textView;
    // 가져온 이메일 주소
    TextView tv_email_get;
    // 인증버튼
    TextView tv_email_spend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_spend);

        // 유저 이메일 정보
        tv_email_get = findViewById(R.id.tv_email_get);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");
        tv_email_get.setText(email);


        // 이메일 인증하기 버튼 누르면 -> 인증메일이 발송되고, email_finish로 이동
        tv_email_spend = (TextView) findViewById(R.id.tv_email_spend);
        tv_email_spend.setClickable(true);
        tv_email_spend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_intent = new Intent(email_spend_activity.this, email_finish_activity.class);
                startActivity(btn_intent);

                Toast.makeText(email_spend_activity.this, "인증메일 전송", Toast.LENGTH_SHORT).show();

            }
        });
    }
}


