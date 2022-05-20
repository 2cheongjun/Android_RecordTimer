package com.example.myapplication.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.allmenu.all_menu_activity;


// 타이머 크게 보기 화면 (메인에 있는 타이머를 크게보기 버튼을 눌렀을떄, 화면을 꽉채우는 시계화면)
// 리스트메인 상단의 타이머가 펼쳐진 형태로 일시정지 버튼이 추가로 있으며, 시간기록을 여러번 할수 있다.
// 직접 시간 기록 기능이 있다.


// 시간기록,시작,일시정지,정지,기록 기능 필요.
// 시간기록 완료 토스트 필요.

public class time_activity extends AppCompatActivity {

    //작게보기 버튼
    TextView tv_small_view;
    ImageView iv_menu;
    //정지버튼
    ImageView iv_stop_normal;
    //기록버튼
    ImageView iv_record_normal;
    // 시간기록하는 영역
    TextView tv_record_area;
    // 시간기록하기 버튼
    TextView tv_record;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        // 시간기록 리스트
        tv_record_area = findViewById(R.id.tv_record_area);
        // 메뉴로 이동
        iv_menu = findViewById(R.id.iv_menu);
        // 정지 버튼
        iv_stop_normal = findViewById(R.id.iv_stop_normal);
        // 기록버튼
        iv_record_normal = findViewById(R.id.iv_record_normal);
        // 하단 시간 기록하기 버튼
        tv_record = findViewById(R.id.tv_record);
        // 작게버튼 아이콘 클릭 -> 리스트메인으로 돌아감
        tv_small_view = findViewById(R.id.tv_small_view);


    }


    @Override
    protected void onResume() {
        super.onResume();

        // 시간이 완료되었습니다 토스트
        // 알람이 -분 경과되었습니다. 토스트
        // 시간이 완료되었습니다. 팝업
        // 기록, 일시정지, 중지 버튼
        // 타이머 사용중  전화가 오거가 등등으로 임시저장 했던것을 복구


        // 작게보기 버튼  -> 누르면 창꺼지고 다시 listActivity로 이동
        tv_small_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // 메뉴 버튼
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(time_activity.this, all_menu_activity.class);
                startActivity(intent);

                finish();
            }
        });


        // 정지 버튼
        iv_stop_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder stop_pop = new AlertDialog.Builder(time_activity.this);
                stop_pop.setTitle("타이머 중지하기");
                stop_pop.setMessage("시간기록을 완료하시겠습니까?");

                // 직접 시간기록하기 Edit창
                final EditText et = new EditText(time_activity.this);
                stop_pop.setView(et);


                // 확인 버튼
                stop_pop.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 직접시간기록시, 팝업이 꺼지고, 버튼위에 시간기록 리스트에 기록됨.
                        String result = et.getText().toString();
                        tv_record_area.setText("공부시간: " + result);
                        // 누적된 시간 기록 가져오기
                    }

                });
                // 팝업 취소 버튼
                stop_pop.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                stop_pop.show();
            }
        });

        // 기록 버튼 이벤트
        iv_record_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder record_pop = new AlertDialog.Builder(time_activity.this);
                //record_pop.setTitle("기록시작");
                record_pop.setMessage("타이머 시작하기");

                // 확인 버튼
                record_pop.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        // 시간을 기록하는 코드넣기
                    }
                });

                // 취소버튼
                record_pop.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                record_pop.show();
            }
        });

        // 일시정지버튼
//        Toast.makeText(timeActivity.this, "일시정지", Toast.LENGTH_SHORT).show();

        // 가로로 긴 기록 버튼 이벤트
        tv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder record_btn_pop = new AlertDialog.Builder(time_activity.this);
                record_btn_pop.setTitle("타이머 중지하기");
                record_btn_pop.setMessage("시간기록을 완료하시겠습니까?");


                // 확인 버튼
                record_btn_pop.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        // 시간을 기록하는 코드넣기
                    }
                });
                // 취소하기 버튼
                record_btn_pop.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        // 시간을 기록하는 코드넣기
                    }
                });
                record_btn_pop.show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 저장안된 변경 내용들을 저장한다.
        // 시간기록 정지
    }

    @Override
    protected void onStop() {
        super.onStop();
        //  실행중인 시간 기록 중지

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 강제종료
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 임시저장중이던 누적시간을 다시 가져온다.
        // 마지막으로 저장된 시간기록 리스트를 가져온다.
    }
}