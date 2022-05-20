package com.example.myapplication.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.main.main_activity;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


// 인트로 화면
// 스플래시 화면 다음에 보이는 보이는 화면 (로그인, 비번재설정, 회원가입하기)
// sms 권한 설정

public class intro_activity extends AppCompatActivity {


    private static final String TAG = "intro_activity";

    TextView tv_login; // 로그인 버튼
    ConstraintLayout ConstraintLayout_main;


    private ImageView iv_login;
    private Button btn_logout;
    private TextView tv_nickname;
    private ImageView iv_profile;
    private TextView  tv_go_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        iv_login = findViewById(R.id.iv_login);
        btn_logout = findViewById(R.id.btn_logout);
        tv_nickname = findViewById(R.id.tv_nickname);
        iv_profile = findViewById(R.id.iv_profile);

        tv_go_main=findViewById(R.id.tv_go_main);
        tv_go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 타이머로 이동
                Intent intent = new Intent(intro_activity.this, main_activity.class);
                startActivity(intent);
            }
        });


        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    //
                }
                if (throwable != null) {
                    //
                }
                updateKakaoLoginUi();
                return null;
            }
        };

        // 로그인 버튼
        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoginClient.getInstance().isKakaoTalkLoginAvailable(intro_activity.this)) {
                    LoginClient.getInstance().loginWithKakaoTalk(intro_activity.this, callback);
                } else {
                    LoginClient.getInstance().loginWithKakaoAccount(intro_activity.this, callback);

                }
            }
        });

        // 로그아웃 버튼
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {

                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });
        // 카카오로그인
        updateKakaoLoginUi();
    }


        // OnCreate끝 //////////////////////////////////////////////////////////////////////////////////

        // 카톡로그인 설정
        private void updateKakaoLoginUi () {
            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                @Override
                public Unit invoke(User user, Throwable throwable) {
                    //로그인 상태
                    if (user != null) {
                        Log.i(TAG, "invoke:id=" + user.getId());
                        Log.i(TAG, "invoke:email=" + user.getKakaoAccount().getEmail());
                        Log.i(TAG, "invoke:nickname=" + user.getKakaoAccount().getProfile().getNickname());
                        Log.i(TAG, "invoke:gender=" + user.getKakaoAccount().getGender());
                        Log.i(TAG, "invoke:age =" + user.getKakaoAccount().getAgeRange());

                        tv_nickname.setText(user.getKakaoAccount().getProfile().getNickname());
                        // 글라이드 라이브러리 사용.
                        Glide.with(iv_profile).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl())
                                .circleCrop().into(iv_profile);

                        iv_login.setVisibility(View.GONE);
                        btn_logout.setVisibility(View.VISIBLE);

                        Intent intent = new Intent(intro_activity.this, main_activity.class);
                        startActivity(intent);

                        // 로그아웃상태
                    } else {
                        tv_nickname.setText(null);
                        iv_profile.setImageBitmap(null);
                        iv_login.setVisibility(View.VISIBLE);
                        btn_logout.setVisibility(View.GONE);
                    }
                    return null;
                }
            });
        }


        @Override
        protected void onPause () {
            super.onPause();
            // 다른화면, 혹은 종료전에 변경사항 임시저장
            // 로그인 정보 저장
            // 사용중인 리소스 정지 준비
        }



        @Override
        protected void onRestart () {
            super.onRestart();
            // 작성중이던 아이디 내용이 있다면, 다시 가져온다.
            // 로그인이 했다면, 로그인 정보를 가져온다.

        }


        @Override
        protected void onDestroy () {
            super.onDestroy();


        }
    }
