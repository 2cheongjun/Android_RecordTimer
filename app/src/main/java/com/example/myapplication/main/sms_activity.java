package com.example.myapplication.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


// 문자수신시 보이는 화면 (테스트)
public class sms_activity extends AppCompatActivity {

    EditText et_call_number;
    EditText et_receive_time;
    EditText et_contents;
    Button btn_sms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actilvity_sms);

        et_call_number=(EditText)findViewById(R.id.et_call_number);
        et_receive_time=(EditText)findViewById(R.id.et_receive_time);
        et_contents=(EditText)findViewById(R.id.et_contents);

        btn_sms = (Button) findViewById(R.id.btn_sms);
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 전달받은 인텐트를 확인하고, / passedIntent를 전달함.
        Intent passedIntent = getIntent();
        processCommand(passedIntent);
    }


    @Override
    protected void onNewIntent(Intent intent) {

        processCommand(intent);
        super.onNewIntent(intent);
    }

    private void processCommand(Intent intent) {

        if(intent != null){
             String sender = intent.getStringExtra("sender");
             String contents = intent.getStringExtra("contents");
             String receivedDate = intent.getStringExtra("receivedDate");

             // 액티비티 입력상자에 넣어주기
            et_call_number.setText(sender);
            et_receive_time.setText(contents);
            et_contents.setText(receivedDate);

        }

    }

}
