package com.example.myapplication.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


// 회원가입 결과화면 (추후 비밀번호는 ***로 표기하기)
public class join_result_activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_result);

        TextView tv_input_email;
        TextView tv_input_secret;

        tv_input_email = findViewById(R.id.tv_input_email);
        tv_input_secret = findViewById(R.id.tv_input_secret);


        // 저장값 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_join_info", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        String password = sharedPreferences.getString("password","");


        // 텍스트뷰자리에 가져온값 넣어주기
        tv_input_email.setText(email);
        tv_input_secret.setText(password);



        // Json 테스트
        Button btn_test;
        TextView tv_1;

        tv_1 =findViewById(R.id.tv_1);

        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 인트로화면 으로 이동
                Intent intent = new Intent(join_result_activity.this, intro_activity.class);
                startActivity(intent);





//                // 에셋폴더의 파일을 가져오기 위해서 에셋매니저가 필요함.
//                AssetManager assetManager = getAssets();
//
//                // 에셋안의 파일을 읽기위한 InputStream
//                try{
//                    InputStream is = assetManager.open("jsons/user_info.json");
//                    InputStreamReader isr = new InputStreamReader(is);
//                    BufferedReader reader = new BufferedReader(isr);
//
//                    StringBuffer buffer = new StringBuffer();
//                    String line = reader.readLine();
//                    while((line!=null)){
//                        buffer.append(line+"\n");
//                        line=reader.readLine();
//                    }
//
//                    // 읽어온 제이슨 문자열 확인
//                    String jsonData = buffer.toString();
//
////                    // 제이슨 객체 생성하기
////                    JSONObject jsonObject = new JSONObject(jsonData);
////                    String name = jsonObject.getString("name");
////                    String id_email = jsonObject.getString("id_email");
////
////                    tv_1.setText("이름 : "+name+"\n"+"이메일 :"+id_email);
//
//                    // 제이슨 데이터가 []로 시작하는 배열
//                    JSONArray jsonArray = new JSONArray(jsonData);
//
//                    String str ="";
//
//                    for(int i =0; i<jsonArray.length(); i++){
//                        JSONObject jsonObject =jsonArray.getJSONObject(i);
//
//                        String name = jsonObject.getString("name");
//                        String id_email = jsonObject.getString("id_email");
//                        JSONObject flag = jsonObject.getJSONObject("flag");
//                        int aa = flag.getInt("aa");
//                        int bb = flag.getInt("bb");
//
//                        str += name+":"+id_email+  "---->"+aa+","+bb+"\n";
//                    }
//                    tv_1.setText(str);
//
//
//                }catch (Exception e){
//                        e.printStackTrace();
//                }
            }
        });

    }
}