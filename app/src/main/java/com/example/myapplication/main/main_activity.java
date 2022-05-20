package com.example.myapplication.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.allmenu.all_menu_activity;
import com.example.myapplication.fragment.chart_activity;
import com.example.myapplication.note.note_activity;
import com.example.utils.StringUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.myapplication.Constant.LIST_INFO;
import static com.example.myapplication.Constant.TASK_LIST;
import static com.example.myapplication.main.main_adapter.position;

// 메인화면

// 포도는 시간기록 누적앱으로 목표를 설정하고, 타이머를 사용해 시간을 기록한다.
// 누적시간을 시각화해 목표달성을 의욕적으로 할 수 있게 도와준다.

// 1. 상단 타이머
// 2. 그래프 아이콘, 메뉴 아이콘
// 3. 하단 목표리스트


public class

main_activity extends AppCompatActivity implements main_adapter.ListItemClickListener, main_adapter.OnStartDragListener {


    public final int PICKER_ACTIVITY = 0;

    FloatingActionButton fab;
    ImageView iv_graph;
    ImageView iv_menu;
//  ImageView iv_big_view;

    // 집중음악 토글
    ToggleButton iv_music_play;

    private Intent intent;
    private RecyclerView mrecycler_view;
    public ArrayList<main_item> items = new ArrayList<>();

    // 리스트선택 다이얼로그
    LinearLayout dialogView;
    TextView tv_goal_add;

    int count = 0;

    // 타이머
    private CountDownTimer mCountDownTimer;
    // 타이머 진행중 / 멈춤
    private boolean mTimerRunning;

    private long mStartTime; // 시작시간
    private long mTimeLeft;  // 남은시간
    private long mEndTime;   // 끝나는 시간
    private long mtime_study; // (시작시간 - 남은 시간) =총 공부시간

    private TextView tv_timer_goal_time; // 시간 표기
    private TextView tv_counter_big; // 시간 표기
    private Button btn_timer_save_stop; // 시간 저장버튼
    private Button btn_timer_set;  // 타임세팅 버튼
    private Button btn_timer_start;// 시작버튼/일시정지 버튼

    private EditText et_input_times; // 목표추가 눌렀을때 작성창

    // 쓰레드
    private Button button;
    private ProgressBar progress_bar;
    boolean is_thread = false;
    // 프로그래스바 쓰레드 핸들러
    Handler handler = new Handler();

    // 어댑터, 터치헬퍼 설정
    private main_adapter adapter;
    private ItemTouchHelper itemTouchHelper;

    //노트 컬러설정
    private String select_timer_color;
    //
    private View timer_area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 데이터 가져오기
        loadData();

        //기본컬러 보라
        select_timer_color = "#7A62CC";
        // 바텀 컬러피커
        init_color_pop();
        set_timer_color();
        // 타이머 bg
        timer_area = (View) findViewById(R.id.timer_area);

        // 리사이클러뷰
        mrecycler_view = (RecyclerView) findViewById(R.id.recyclerview_test);
        mrecycler_view.setHasFixedSize(true);
        mrecycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // set adapter

        adapter = new main_adapter(items, (main_adapter.ListItemClickListener) this, this);
        mrecycler_view.setAdapter(adapter);

        // 아이템 헬퍼가 있어야, 아이템 드래그나 삭제가 가능하다.(만들어어 논 것 가져옴)
        itemTouchHelper = new ItemTouchHelper(new SimpleTextItemTouchHelperCallback(adapter));
        // 아이템 헬퍼에 리사이클러뷰 붙이기
        itemTouchHelper.attachToRecyclerView(mrecycler_view);

        // 타이머 숫자
        tv_counter_big = findViewById(R.id.tv_counter_big);
        // 입력창
        et_input_times = findViewById(R.id.et_input_times);
        // 설정
        btn_timer_set = findViewById(R.id.btn_timer_set);
        // 타이머 시작/일시정지
        btn_timer_start = findViewById(R.id.btn_timer_start_pause);
        // 타이머 저장버튼
        btn_timer_save_stop = findViewById(R.id.btn_timer_reset);
        // 목표시작시간 표기
        tv_timer_goal_time = findViewById(R.id.tv_timer_goal_time);


        // 진행률 프로그래스바
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        // 시작 버튼
        btn_timer_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ProgressThread thread = new ProgressThread();
                        thread.start();
                    }
                }, 0);
            }
        });

        // 정지/ 저장버튼
        btn_timer_save_stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 타이머 일시정지
                pauseTimer();
//                zeroTimer();

                // 프로그래스바 쓰레드 종료 // but 일시 정지했다가 시작하면 프로그래스바 안감.
                is_thread = false;
                // 정지버튼 누르면, 시작버튼 못누름
                btn_timer_start.setEnabled(false);

                // 공부시간
                mtime_study = mStartTime - mTimeLeft;
                String moo = StringUtils.getTimeText(mtime_study);
                Toast.makeText(main_activity.this, moo + "집중했습니다.\n항목을 눌러 시간을 누적하세요.", Toast.LENGTH_SHORT).show();
                // 타이머 리셋
                zeroTimer();
            }

        });


        //시간설정하기


        btn_timer_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et_input_times.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(main_activity.this, "집중시간을 설정하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(main_activity.this, "0보다 큰수를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                et_input_times.setText("");

                tv_timer_goal_time.setText("목표시간 " + StringUtils.getTimeText(millisInput));

                String goalt = StringUtils.getTimeText(millisInput);

                // 이전쓰레드 종료
                is_thread = false;

                Toast.makeText(main_activity.this, "목표시간 " + goalt + " 을 설정했습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        });


        // +목표 추가하기 버튼 클릭시 리스트가 추가됨
        tv_goal_add = findViewById(R.id.tv_goal_add);
        tv_goal_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레이아웃을 가져올때 액티비티가 아니면 인플레이터를 써야함
//                LayoutInflater inflater = getLayoutInflater();
//                dialogView = (LinearLayout) inflater.inflate(R.layout.dialog, null,false);
                dialogView = (LinearLayout) View.inflate(main_activity.this, R.layout.dialog, null);
                // 다이얼로그 입력창
                EditText line1 = (EditText) dialogView.findViewById(R.id.et_input_goal);
                TextView line2 = (TextView) dialogView.findViewById(R.id.et_input_time);

                AlertDialog.Builder dlg = new AlertDialog.Builder(main_activity.this);
                dlg.setTitle("목표명, 시간 적기");
                dlg.setView(dialogView);
                dlg.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 입력값 받아오는 창
                        items.add(new main_item(line1.getText().toString(), 0));

                        // 받아온 데이터를 저장한다.
                        adapter.notifyDataSetChanged();
                        saveData();

                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });


        //(서비스) 음악 시작 집중음악
        intent = new Intent(this, music_service.class);

        // 상단 음악아이콘 터치시
        iv_music_play = (ToggleButton) findViewById(R.id.iv_music_play);
        iv_music_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 음악버튼 토글 / 온상태이면 음악 플레이, 오프이면 정지
                if (iv_music_play.isChecked()) {
                    startService(new Intent(getApplicationContext(), music_service.class));
                    Log.i("music_start_service", "온");

                    Toast.makeText(main_activity.this, "Sound On", Toast.LENGTH_SHORT).show();
                    // 음악재생시 알림설정
                    noti();
                } else {
                    stopService(new Intent(getApplicationContext(), music_service.class));
                    Log.i("music_start_service", "오프");

                    Toast.makeText(main_activity.this, "Sound Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // 그래프 아이콘 누르면 통계 팝업이 하단에서 올라옴.
        iv_graph = findViewById(R.id.iv_graph);
        // 전체메뉴보기
        iv_menu = findViewById(R.id.iv_menu);
//        // 크게보기 버튼
//        iv_big_view = findViewById(R.id.iv_big_view);
        // 메뉴 아이콘
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(main_activity.this, all_menu_activity.class);
                startActivity(intent);
            }
        });
//        // 크게보기 버튼 누르면, 타이머가 화면을 꽉채우며 확대
//        iv_big_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(main_activity.this, time_activity.class);
//                startActivity(intent);
//            }
//        });

        // 그래프 아이콘 하단 바텀 팝업에서 진행률, 통계 내역 확인가능////////////////////////////////////////////////////////
        iv_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                long saveTime = load_time_data(position);

                // 어답터 갱신              새로 저장하는 값 + 이전 저장된 시간값
                updateData(position, saveTime + mtime_study);
                adapter.notifyItemChanged(position);
                saveData();
                loadData();


                Intent intent = new Intent(main_activity.this, chart_activity.class);
                startActivity(intent);

                Log.e(" 노트디버깅.", "positon :");
            }
        });

    } // onCreate 끝 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 컬러피커
    private void init_color_pop() {
        final LinearLayout color_pop = findViewById(R.id.color_pop);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(color_pop);
        color_pop.findViewById(R.id.color_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                saveData();
            }
        });

        final ImageView color_purple = color_pop.findViewById(R.id.image_color_purple);
        final ImageView color_pink = color_pop.findViewById(R.id.image_color_pink);
        final ImageView color_blue = color_pop.findViewById(R.id.image_color_blue);
        final ImageView color_yellow = color_pop.findViewById(R.id.image_color_yellow);
        final ImageView color_orange = color_pop.findViewById(R.id.image_color_orange);
        final ImageView color_green = color_pop.findViewById(R.id.image_color_green);

        // 보라선택
        color_pop.findViewById(R.id.view_color_purple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                select_timer_color = "#7A62CC";
                color_purple.setImageResource(R.drawable.ic_done);
                color_pink.setImageResource(0);
                color_blue.setImageResource(0);
                color_yellow.setImageResource(0);
                color_orange.setImageResource(0);
                color_green.setImageResource(0);
                set_timer_color();
                saveData();
            }
        });

        //핑크선택
        color_pop.findViewById(R.id.view_color_pink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_timer_color = "#f57d97";
                color_purple.setImageResource(0);
                color_pink.setImageResource(R.drawable.ic_done);
                color_blue.setImageResource(0);
                color_yellow.setImageResource(0);
                color_orange.setImageResource(0);
                color_green.setImageResource(0);
                set_timer_color();
                saveData();
            }
        });

        //블루
        color_pop.findViewById(R.id.view_color_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_timer_color = "#0386f3";
                color_purple.setImageResource(0);
                color_pink.setImageResource(0);
                color_blue.setImageResource(R.drawable.ic_done);
                color_yellow.setImageResource(0);
                color_orange.setImageResource(0);
                color_green.setImageResource(0);
                set_timer_color();
                saveData();
            }
        });

        //노랑
        color_pop.findViewById(R.id.view_color_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_timer_color = "#ffc94a";
                color_purple.setImageResource(0);
                color_pink.setImageResource(0);
                color_blue.setImageResource(0);
                color_yellow.setImageResource(R.drawable.ic_done);
                color_orange.setImageResource(0);
                color_green.setImageResource(0);
                set_timer_color();
                saveData();
            }
        });

        // 주황
        color_pop.findViewById(R.id.view_color_orange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_timer_color = "#ff7561";
                color_purple.setImageResource(0);
                color_pink.setImageResource(0);
                color_blue.setImageResource(0);
                color_yellow.setImageResource(0);
                color_orange.setImageResource(R.drawable.ic_done);
                color_green.setImageResource(0);
                set_timer_color();
                saveData();
            }
        });

        // 그린
        color_pop.findViewById(R.id.view_color_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_timer_color = "#10ad92";
                color_purple.setImageResource(0);
                color_pink.setImageResource(0);
                color_blue.setImageResource(0);
                color_yellow.setImageResource(0);
                color_orange.setImageResource(0);
                color_green.setImageResource(R.drawable.ic_done);
                set_timer_color();
                saveData();
            }
        });


    }

    private void set_timer_color() {
        if (timer_area != null) {
            GradientDrawable gradientDrawable = (GradientDrawable) timer_area.getBackground();
            gradientDrawable.setColor(Color.parseColor(select_timer_color));
        }
    }


    // 알람매니저 (음악실행중에 실행되는 noti)
    public void noti() {
        // 알람매니저 시작
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelID = "channel_01";
            String channelName = "MyChannel01";

            //알림채널 객체 만들기
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);

            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(main_activity.this, channelID);

        } else {
            //알림 객체 생성
            builder = new NotificationCompat.Builder(main_activity.this, null);
        }

        // 알림 아이콘 설정
//                builder.setSmallIcon(android.R.drawable.ic_menu_view);

        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        builder.setContentTitle("시간 기록앱 포도");
        builder.setContentText("음악 실행중 입니다....");
//                //알림창의 큰 이미지, 비트맵
//                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.circle_orange);
//                builder.setLargeIcon(bm);


        // 알림창 클릭시 다시 메인액티비티로 이동
        Intent intent = new Intent(String.valueOf(main_activity.this));

        // 지금 실행하는것이 아니라 잠시 보류시키는 인텐트 객체 필요
        PendingIntent pendingIntent = PendingIntent.getActivity(main_activity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // 알림창 클릭시 알림제거
        builder.setAutoCancel(true);

        Notification notification = builder.build();

        //알림매니저에게 알림(Notify) 요청
        notificationManager.notify(1, notification);

        //알림 요청시에 사용한 번호를 알림제거 할 수 있음.
        //notificationManager.cancel(1);

        // 알람 매니저 끝
    }


    // 진행바 쓰레드
    class ProgressThread extends Thread {

        int value = 0;
        // 타이머 시작시간 만큼 쓰레드 실행
        long time = mStartTime; // 시작시간 ex) 20분 = 1,200,000초

        long sleep = time / 100; // 1,200,000 / 프로그래스바 100 나눈 값만큼 씩 증가하기

        public void run() {
            is_thread = true;
            while (is_thread) {
                Log.e("lee", "run" + time);

                if (value >= time) {
                    break;
                }
                value += 1;


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progress_bar.setProgress(value);
                    }
                });
                //~초에 만큼
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("쓰레드", "run" + value);
            }
        }
    }


    // 시간 set
    private void setTime(long milliseconds) {
        mStartTime = milliseconds;
        resetTimer();
        closeKeyboard();

        // 프로그래스바 쓰레드 종료 // but 일시 정지했다가 시작하면 프로그래스바 안감.
        is_thread = false;
    }


    // 시작타이머
    private void startTimer() {

        mEndTime = System.currentTimeMillis() + mTimeLeft;
        mCountDownTimer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeft = millisUntilFinished;
                updateCountDownText();
            }

            // 알람종료시 ->알람울림, 쓰레드 종료
            @Override
            public void onFinish() {

                // 프로그래스바 쓰레드 종료 // but 일시 정지했다가 시작하면 프로그래스바 안감.
                is_thread = false;

                mTimerRunning = false;
                updateWatchInterface();

                tv_counter_big.setText("목표시간달성!");

                // 음악종료시 알람동작
                timer_end_noti();

                // 공부시간
                mtime_study = mStartTime - mTimeLeft;
                String time_notice = StringUtils.getTimeText(mtime_study);
                Toast.makeText(main_activity.this, time_notice + " 집중!\n 항목을 눌러 시간을 누적하세요.", Toast.LENGTH_SHORT).show();

            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }


    // 일시정지 타이머
    private void pauseTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mTimerRunning = false;
            updateWatchInterface();
        }
    }


    // 리셋 타이머
    private void resetTimer() {
        mTimeLeft = mStartTime;
        updateCountDownText();
        updateWatchInterface();
    }


    // 타이머 초기화
    private void zeroTimer() {
        if (mCountDownTimer != null) {
            mTimeLeft = 0;
            updateCountDownText();
            updateWatchInterface();
        }
    }


    // 타이머 문자열 업데이트
    private void updateCountDownText() {
        int hours = (int) (mTimeLeft / 1000) / 3600;
        int minutes = (int) ((mTimeLeft / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeft / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d시간%02d분%02d초", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d분%02d초", minutes, seconds);
        }
        tv_counter_big.setText(timeLeftFormatted);
    }


    // 타이머 버튼 보이기, 안보이기 설정
    private void updateWatchInterface() {
        // 타이머가 진행중일때
        if (mTimerRunning) {
            et_input_times.setVisibility(View.INVISIBLE);
            btn_timer_set.setVisibility(View.INVISIBLE);
            btn_timer_save_stop.setEnabled(true);
            btn_timer_start.setEnabled(false);
            // 타이머 진행중 아닐때
        } else {
            et_input_times.setVisibility(View.VISIBLE);
            btn_timer_set.setVisibility(View.VISIBLE);
            btn_timer_start.setText("시작");
            // 남은 시간이 1초보다 작으면 온, 크면 오프
            if (mTimeLeft < 1000) {
                btn_timer_start.setEnabled(false);
            } else {
                btn_timer_start.setEnabled(true);
            }
            //  시작하고 난뒤
            //  남은 시간 30 < 시작시간 60
            if (mTimeLeft < mStartTime) {
                btn_timer_save_stop.setEnabled(true);
            } else {
                btn_timer_save_stop.setEnabled(false);
            }
        }
    }

    // 키보드 설정
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 셰어드 저장
    // 저장 정보 가져와서 어레이리스트에 넣기
    // 목표명과, 시간을 기록,  두가지 값을 넣고 불러옴
    private void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(LIST_INFO, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASK_LIST, null);
        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        items = gson.fromJson(json, type);

        if (items == null) {
            this.items = new ArrayList<main_item>();
        }
    }


    // 데이타 로드 /  // long 포지션을 가져와 저장한다.(시간값 가져오기)
    private long load_time_data(int position) {

        SharedPreferences sharedPreferences = getSharedPreferences(LIST_INFO, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Type type = new TypeToken<ArrayList<main_item>>() {
        }.getType();
        items = gson.fromJson(json, type);

        // 아이템이 널 이면? 메인 아이템 리스트를 가져온다?
        if (items == null) {
            this.items = new ArrayList<main_item>();
        }
        // 저장한 시간값을 / get_time에 리턴한다.
        return items.get(position).get_time();
    }


    // 데이터 업데이트
    private void updateData(int position, long time) {

        // 시간 업데이트하기 // 선택한포지션의 아이템의 시간을 다시 가져와 시간 값을 다시 넣는다.
        adapter.notifyDataSetChanged();
        items.get(position).set_time(time);

        SharedPreferences sharedPreferences = getSharedPreferences(LIST_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        // 저장할 객체를 String 값으로 변환
        String json = gson.toJson(items);
//        Log.e("lee", json);
        // 키값, 변수명
        editor.putString(TASK_LIST, json);
        editor.apply();

    }

    // 값을 저장할때 지손과 셰어드를 이용해 객체를 저장
    private void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences(LIST_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        // 저장할 객체를 String 값으로 변환
        String json = gson.toJson(items);
        Log.e("lee", json);
        // 키값, 변수명
        // 컬러값 저장하려면?
//        editor.putInt("time_color", Integer.parseInt(json));
        editor.putString(TASK_LIST, json);
        editor.apply();
    }


    // 터치 드래그 설정
    @Override
    public void onStartDrag(main_adapter.my_ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }


    // 리사이클러뷰 이벤트 -> 다이얼로그 생성 (이전 시간값 가져와서 + 추가한 시간에 더한다.)
    @Override
    public void listItemClick(int position) {


        // 수정하기 팝업 생성
        AlertDialog.Builder modify_pop = new AlertDialog.Builder(main_activity.this);

        Log.e(" 디버깅.", "positon : " + position + "  ");

        //쓰레드 정지
        is_thread = false;

        // 저장한 시간 값 가져와서 saveTime에 저장(long)
        long saveTime = load_time_data(position);

        // 공부시간
        mtime_study = mStartTime - mTimeLeft;


        //공부시간 long값을  StringUtils로 시간변환한다.
        String msg = StringUtils.getTimeText(mtime_study);

        // 메인아이템 위치값 가져오기
        main_item data = adapter.getItem(position);

        modify_pop.setTitle(data.get_goal() + "에 시간 더하기");
        modify_pop.setMessage("집중시간 : " + msg);


        // 확인 버튼
        modify_pop.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.e(" 디버깅.", "positon : " + position + "  ");

                //saveTime + time

                // 어답터 갱신              새로 저장하는 값 + 이전 저장된 시간값
                updateData(position, saveTime + mtime_study);

                // 아이템을 다시 세팅함
                adapter.setItem(position, saveTime + mtime_study);
                adapter.notifyDataSetChanged();

                // 타이머값 0
                zeroTimer();

                // 공부시간
                mStartTime = 0;
                // 목표시간
                tv_timer_goal_time.setText("목표시간 " + StringUtils.getTimeText(0));
                //
                is_thread = false;

                // 다시 0으로 세팅
                int value = 0;
                progress_bar.setProgress(value);

                // 창끄기
                dialog.dismiss();
            }
        });


        // 취소하기 버튼
        modify_pop.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // 노트보기 버튼 // 누르면 포지션값을 노트에 전달
        modify_pop.setNeutralButton("노트보기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(main_activity.this, note_activity.class);
                // 포지션값의 인덱스를 노트로 가지고 감
                intent.putExtra("index", position);
                Log.e(" 노트디버깅.", "positon : " + position + "  ");
                startActivity(intent);
            }
        });
        modify_pop.show();
    }


    // 타이머 종료 알람 넣기 ?? 설정으로 어떻게?
    public void timer_end_noti() {

        final NotificationManager notificationManager =
                (NotificationManager) main_activity.this.getSystemService(NOTIFICATION_SERVICE);

        //푸시 알림 터치시 실행할 작업 설정(여기선 MainActivity로 이동하도록 설정)
        final Intent intent = new Intent(main_activity.this.getApplicationContext(), main_activity.class);

        //Notification 객체 생성
        final Notification.Builder builder = new Notification.Builder(getApplicationContext());


        //푸시 알림을 터치하여 실행할 작업에 대한 Flag 설정 (현재 액티비티를 최상단으로 올린다 | 최상단 액티비티를 제외하고 모든 액티비티를 제거한다)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


        //앞서 생성한 작업 내용을 Notification 객체에 담기 위한 PendingIntent 객체 생성
        PendingIntent pendnoti = PendingIntent.getActivity(main_activity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //푸시 알림에 대한 각종 설정
        builder.setSmallIcon(R.drawable.ic_launcher_background).setTicker("Ticker").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("타이머 알람 종료").setContentText("설정된 시간이 완료되었습니다.")
//                .setDefaults(Notification.DEFAULT_VIBRATE).setContentIntent(pendnoti).setAutoCancel(true).setOngoing(true);
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendnoti).setAutoCancel(true).setOngoing(true);


        //NotificationManager를 이용하여 푸시 알림 보내기
        notificationManager.notify(1, builder.build());
    }

    // SMS 권한이 없을 경우 요청함
    private void requirePerms() {
        String[] permissions = {Manifest.permission.RECEIVE_SMS};
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", mStartTime);
        editor.putLong("millisLeft", mTimeLeft);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTime = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeft = prefs.getLong("millisLeft", mStartTime);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeft = mEndTime - System.currentTimeMillis();
            if (mTimeLeft < 0) {
                mTimeLeft = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();

            } else {
                startTimer();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


        // 저장한 시간 값 가져와서 saveTime에 저장(long)
        long saveTime = load_time_data(position);

        // 어답터 갱신              새로 저장하는 값 + 이전 저장된 시간값
        updateData(position, saveTime + mtime_study);

        // 아이템을 다시 세팅함
        adapter.setItem(position, saveTime + mtime_study);
        adapter.notifyDataSetChanged();

        saveData();

        // 다른화면, 혹은 종료전에 변경사항 저장필요.
        // 필요없는 리소스 해제
        // 누적시간 정보저장.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 저장한 시간 값 가져와서 saveTime에 저장(long)
        long saveTime = load_time_data(position);

        // 어답터 갱신              새로 저장하는 값 + 이전 저장된 시간값
        updateData(position, saveTime + mtime_study);

        // 아이템을 다시 세팅함
        adapter.setItem(position, saveTime + mtime_study);
        adapter.notifyDataSetChanged();

        saveData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 작성중이던 아이디 내용이 있다면, 다시 가져온다.
        // 로그인이 했다면, 로그인 정보를 가져온다.
    }
}



