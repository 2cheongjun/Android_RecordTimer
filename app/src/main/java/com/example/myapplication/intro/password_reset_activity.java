package com.example.myapplication.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

// 인트로 - > 비밀번호 재설정 페이지
public class password_reset_activity extends AppCompatActivity {

    // 유저가 작성한 이메일 주소값
    EditText et_email_input;
    // 다음 버튼
    TextView tv_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        // 유저가 작성한 이메일 주소값
        et_email_input = findViewById(R.id.et_email_input);
        tv_next = findViewById(R.id.tv_next);


        tv_next.setClickable(true);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 주소값을 담아 보냄
                String email = et_email_input.getText().toString();

                // 이메일로 비밀번호 전송하기 페이지로 이동 ( 유저 이메일 주소를 담아 -> password_reset_spend로 값을 보냄)
                Intent intent = new Intent(password_reset_activity.this, email_spend_activity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

      // 작성중이던 이메일 주소가 있다면, 가져오기

    }

    @Override
    protected void onStop() {
        super.onStop();
        // onCreate에 작성된 내용 중지
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 강제종료
    }

}

