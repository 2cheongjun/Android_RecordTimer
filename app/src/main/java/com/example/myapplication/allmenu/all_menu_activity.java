package com.example.myapplication.allmenu;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;
import com.example.myapplication.main.main_item;
import com.example.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.myapplication.Constant.LIST_INFO;
import static com.example.myapplication.Constant.TASK_LIST;
import static com.example.myapplication.main.main_adapter.position;


// 우측 메뉴 페이지
public class all_menu_activity extends AppCompatActivity {


    public final static int TAKE_PICTURE = 1;
    final String TAG = getClass().getSimpleName();

    ImageView iv_close;
    TextView tv_menu_set;
    TextView tv_color_theme;
    TextView tv_term;
    TextView tv_ic_share;
    TextView tv_all_time;

    // 카메라 찍을때 가져오는 이미지영역
    ImageView iv_picture_area;
    private ImageView iv_profile;
    String shared_email = "file";

    // 로그인정보
    TextView tv_login_info;
    SharedPreferences sharedPreferences;

    // onCreate 시작
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allmenu);


//        // 로그인텍스트 자리
//        tv_login_info = (TextView)findViewById(R.id.tv_login_info);

//        // 저장값 가져오기
//        sharedPreferences = getSharedPreferences(shared_email, 0);
//        String email = sharedPreferences.getString("email", "");

//        // 텍스트뷰자리에 가져온값 넣어주기
//        tv_login_info.setText(email);


        // 노트 내용 저장하기 원래 사용하던 셰어드에 똑같이 저장한다.
        // 셰여드 사용하던것 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences(LIST_INFO, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASK_LIST, null);
        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        ArrayList<main_item> items = gson.fromJson(json, type);


        tv_all_time = findViewById(R.id.tv_all_time);

        // 합계 넣기
        int sum = 0;
        for (int i = 0; i < items.size(); i++) {
            sum += items.get(i).get_time();
//            sum = sum+i;
        }

             main_item data = items.get(position);
            tv_all_time.setText(StringUtils.getTimeText(sum));




            // 데이터 업데이트
            // 데이터를 로드해, 원래 있던 메모 내용을 새로 갱신해준다.
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // 저장할 객체를 String 값으로 변환
            String updateJson = gson.toJson(items);
            Log.e("lee", "updateJson" + updateJson);
            // 키값, 변수명
            editor.putString(TASK_LIST, updateJson);
            editor.apply();


            // 프로필 이미지
            iv_profile = (ImageView) findViewById(R.id.iv_profile);
            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.iv_profile:

                            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraintent, TAKE_PICTURE);
                            break;
                    }
                }
            });

            // 카메라로 찍고 불러오는 이미지영역 화면전체 보이는 사진
            iv_picture_area = (ImageView) findViewById(R.id.iv_picture_area);


            // 카메라 이미지 뷰
            // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.d("00", "권한 설정 완료");
                } else {
                    Log.d("00", "권한 설정 요청");
                    ActivityCompat.requestPermissions(all_menu_activity.this, new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }


            // 메뉴 닫기 버튼
            iv_close = findViewById(R.id.iv_close);
            // 세팅
            tv_menu_set = findViewById(R.id.tv_menu_set);

            //  terms of use
            tv_term = findViewById(R.id.tv_term);

            // 공유하기 버튼
            tv_ic_share = findViewById(R.id.tv_ic_share);

            // 공유 버튼을 눌렀을때- > 메일, 메시지로 보내기 인텐트
            tv_ic_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, "weaponsecret@naver.com");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "시간기록 앱 포도, 앱주소 공유");
                    intent.putExtra(Intent.EXTRA_TEXT, "시간기록앱 포도 : 시간을 누적해보세요.");
                    startActivity(Intent.createChooser(intent, "Choose Email"));
                }

            });


            //  닫기 버튼
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                }
            });
            // 세팅 페이지로 이동
            tv_menu_set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(all_menu_activity.this, setting_activity.class);
                    startActivity(intent);
                }
            });


            // 약관 및 개인정보 내역 고지 페이지로 이동
            tv_term.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(all_menu_activity.this, term_of_use_activity.class);
                    startActivity(intent);
                }
            });
        }


        //onCreate 끝 ///////////////////////////////////////////////////////////////////////////////////////////////


        //  dmd카메라로 촬영한 영상을 가져오는 부분
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent intent){
            super.onActivityResult(requestCode, resultCode, intent);
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (resultCode == RESULT_OK) {
                        assert intent != null;
                        if (intent.hasExtra("data")) {
                            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                            if (bitmap != null) {
                                iv_profile.setImageBitmap(bitmap);

                            }
                        }
                    }
                    break;
            }

        }


        // 카메라 권한허용
        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            Log.d("tab", "onRequestPermissionsResult");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d("tag", "Permission: " + permissions[0] + "was " + grantResults[0]);
            }
        }


        @Override
        protected void onStart () {
            super.onStart();

            // 로그인 했던 사람이라면, 로그아웃하지 않았을 경우 프로필 정보를 가지고 있음
            // 누적시간 정보 가져오기
        }


        @Override
        protected void onResume () {
            super.onResume();
            // 각메뉴 이동 인텐트
            // 임시저장된 정보를 복구
            // 저장된 세팅, 테마,약관, 로그인 정보를 가져옴


        }

        @Override
        protected void onPause () {
            super.onPause();
            // 다른화면, 혹은 종료전에 변경사항 저장필요.
            // 필요없는 리소스 해제
            // 누적시간 정보저장.
        }


        @Override
        protected void onStop () {
            super.onStop();
            // 누적시간 데이터, 정보저장하기 기능 정지
        }


        @Override
        protected void onDestroy () {
            super.onDestroy();
            // 전체 종료

        }
    }

