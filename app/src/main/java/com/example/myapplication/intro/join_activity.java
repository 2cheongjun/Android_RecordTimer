package com.example.myapplication.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


// 회원가입화면
public class join_activity extends AppCompatActivity {


    EditText et_input_email;
    EditText et_input_secret;
    EditText et_input_secret_check;
//    CheckBox cb_join_agree;

    // 작성한 문자열 담을 변수
    String email_str,secret_str,secret_check_str;
    Button  btn_join;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        et_input_email = findViewById(R.id.tv_input_email);
        et_input_secret = findViewById(R.id.tv_input_secret);
        et_input_secret_check = findViewById(R.id.tv_input_secret_check);

        sharedPreferences= getSharedPreferences("user_join_info", Context.MODE_PRIVATE);

        // 가입하기 버튼 누르면, 입력값 저장됨
        btn_join = findViewById(R.id. btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_str = et_input_email.getText().toString();
                secret_str = et_input_secret. getText().toString();
                secret_check_str = et_input_secret_check.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 문자열 넘겨주기
                editor.putString("email",email_str);
                editor.putString("password",secret_str);
                editor.putString("password_check",secret_check_str);
                editor.commit();

                Toast.makeText(join_activity.this,"저장",Toast.LENGTH_LONG);

                // 회원가입 완료 페이지로 이동
                Intent intent = new Intent(join_activity.this, join_result_activity.class);
                startActivity(intent);
            }
        });
    }
}
