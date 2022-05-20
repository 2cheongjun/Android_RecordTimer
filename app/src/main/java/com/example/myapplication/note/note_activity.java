package com.example.myapplication.note;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.main.main_item;
import com.example.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.myapplication.Constant.LIST_INFO;
import static com.example.myapplication.Constant.TASK_LIST;


// 첫번째 리스트 목표 노트기록 화면 (기록, 수정, 삭제)
// 메뉴 타이틀
// 시작일, 종료일 달력
// 진행률 프로그래스바
// 체크박스
// 사진업로드

public class note_activity extends AppCompatActivity {

    private static final String DATE_START_KEY = "start_key";
    private static final String DATE_END_KEY = "end_key";
    private static final String NOTE_KEY = "note_key";

    // 노트 타이틀
    TextView tv_note_title;
    // 뒤로가기 버튼
    ImageView tv_back_white;
    // 저장하기 버튼
    TextView tv_save;
    TextView tv_memo_del;

    // 노트내용
    EditText tv_note_write;


    // 시작일
    private TextView tv_start_title;
    private TextView tv_start_day;
    // 종료일
    private TextView tv_end_title;
    private TextView tv_end_day;
    //데이트 피커 리스너
    private DatePickerDialog.OnDateSetListener mDateSetListener_start;
    private DatePickerDialog.OnDateSetListener mDateSetListener_end;

//    // 체크 리스트뷰
//    ListView listViewData;
//    ArrayAdapter<String> adapter;
//    String[] array_checkbox = {"1", "2", "3"};

    public main_item item = null;

    public int index = 0;

    final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView materialCalendarView;


    // onCreate 시작 ///////////////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // 리스트 눌러서 노트로 이동시
        // 인텐트 데이터 받아오기 / position 인덱스값
        if (getIntent() != null) {
            index = getIntent().getExtras().getInt("index");
        }

        // 달력값 가져오기 //////////////////////////////////////////////////////////////////////////////////

        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendar_view);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator,
                // 내가 선택한날 표기
                // 점찍기, 알람을 작동한 날짜에 점찍히게 하기???????
                new EventDecorator(Color.BLACK, Collections.singleton(CalendarDay.today()),this)
        );



        // 유틸에서 메인아이템 포지션값만 가져온다
        item = PrefUtils.getMainItem(this, index);

        Log.e("on", "노트 " + index);
//        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        tv_note_title = findViewById(R.id.tv_note_title);
        // 목표명 타이틀 가져오기
        tv_note_title.setText(item.get_goal());

        // 노트 Editbox
        EditText note_et = (EditText) findViewById(R.id.tv_note_write);

        // 시작일
        tv_start_day = (TextView) findViewById(R.id.tv_start_day);

        // 종료일
        tv_end_day = (TextView) findViewById(R.id.tv_end_day);


        // main 아이템에 있는 노트 가져오기
        note_et.setText(item.getNote());
        // 시작일, 종료일 가져오기
        tv_start_day.setText(item.getStart_day());
        tv_end_day.setText(item.getEnd_day());


        //  시작일 타이틀 -> 클릭시 달력 불러옴
        tv_start_title = (TextView) findViewById(R.id.tv_start_title);
        tv_start_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(note_activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener_start, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // 시작일 datepicker
        mDateSetListener_start = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String start_date = year + "/" + month + "/" + day;
                tv_start_day.setText(start_date);

            }
        };

        // 종료일 타이틀
        tv_end_title = (TextView) findViewById(R.id.tv_end_title);
        tv_end_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(note_activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener_end, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // 종료일 datepicker
        mDateSetListener_end = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String end_date = year + "/" + month + "/" + day;
                tv_end_day.setText(end_date);
            }
        };


        // 노트, 시작일, 종료일 저장하기 버튼
        tv_save = findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 노트 내용 저장하기 원래 사용하던 셰어드에 똑같이 저장한다.
                // 셰여드 사용하던것 가져오기
                SharedPreferences sharedPreferences = getSharedPreferences(LIST_INFO, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString(TASK_LIST, null);
                Type type = new TypeToken<ArrayList<main_item>>() {
                }.getType();
                ArrayList<main_item> items = gson.fromJson(json, type);

                // 위치값 찾아오기
                int row = 0;
                for (main_item each : items) {

                    if (row == index) {
                        //시작일
                        //종료일
                        //체크리스트 저장
                        each.setEnd_day(tv_end_day.getText().toString());
                        each.setStart_day(tv_start_day.getText().toString());
                        each.setNote(note_et.getText().toString());
                        break;
                    }
                    row++;
                }

                // 데이터 업데이트
                // 데이터를 로드해, 원래 있던 메모 내용을 새로 갱신해준다.
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 저장할 객체를 String 값으로 변환
                String updateJson = gson.toJson(items);
                Log.e("lee", "updateJson" + updateJson);
                // 키값, 변수명
                editor.putString(TASK_LIST, updateJson);
                editor.apply();

             Toast.makeText(note_activity.this, "노트가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        // 노트 내용 삭제 버튼
        tv_memo_del = findViewById(R.id.tv_memo_del);
        tv_memo_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note_et.setText("");
            }
        });


        // 뒤로가기 버튼 초기화
        tv_back_white = findViewById(R.id.tv_back_white);
        tv_back_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        // 체크박스 리스트뷰
//        listViewData = findViewById(R.id.listView_data);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, array_checkbox);
//        listViewData.setAdapter(adapter);
//
//        String list = PrefUtils.getPref(this, TASK_LIST);
//
//
//        Type type = new TypeToken<ArrayList<main_item>>() {
//        }.getType();
//        ArrayList<main_item> items = new Gson().fromJson(list, type);
//
//        items.get(0).getNote();
    }


    ///////////////////////////////////////////////////////////////////////////////////// onCreate 끝





    // 메인에 저장된 값 가져오기
    private main_item loadData(int position) {

        SharedPreferences sharedPreferences = getSharedPreferences(TASK_LIST, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Log.e("lee", "load : " + json);
        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        ArrayList<main_item> items = gson.fromJson(json, type);

        return items.get(position);
    }



    @Override
    protected void onResume() {
        super.onResume();

        // 작성중이던 노트가 있다면, 임시저장한 내용을 가져온다.

        // 저장되었습니다. 토스트 메시지
//        Toast.makeText(img_note_activity.this, "노트가 저장되었습니다.", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 작성중이던 노트 내용이 있다면, 다시 가져온다.
        Toast.makeText(note_activity.this, "작성중이던 노트가 남아있습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 다른화면, 혹은 종료전에 변경사항 임시 저장이 필요
        // 사용하지 않는 리소스 해제
        // 사용중인 리소스 정지 준비 - 카메라 기능 정지, 애니메이션 정지, <receiver> BroadcastReceiver(시스템이벤트, 배터리정보)
        // 앱간의 데이터를 주고 받는, 노트 공유 기능 정지 <Provider>

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 전체 종료

    }
}
