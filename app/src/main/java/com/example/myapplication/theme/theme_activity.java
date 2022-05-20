package com.example.myapplication.theme;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

// 메뉴 - 테마 설정
// 유저가  배경 컬러를 선택할 수 있다.
// 리사이클러뷰 사용
public class theme_activity extends AppCompatActivity {


    RecyclerView recyclerView;
    theme_adapter adapter;
    ImageView iv_back;
    TextView tv_circle_purple;
    TextView tv_circle_pink;
    TextView tv_circle_orange;
    TextView tv_circle_yellow;
    TextView tv_circle_blue;
    TextView tv_circle_green;

    // 리스트 추가 카운트
    int num = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);


        // 뒤로가기버튼 클릭시
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //리사이클러뷰 설정
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // 리사이클러뷰 매니저 설정 (세로방향 리스트)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //theme_adapter 생성
        adapter = new theme_adapter(getApplicationContext());

        // 초기에 만들어져있는 리스트
        adapter.addItem(new theme_item("기본컬러", R.drawable.circle_purple));

        recyclerView.setAdapter(adapter);


        // 한번 클릭시 수정
        adapter.setOnItemClickListener(new theme_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(theme_adapter.ViewHolder holder, View view, int position) {

                // 아이템의 위치를 받아온다.
                theme_item item = adapter.getItem(position);

                // 수정하기 팝업 생성
                AlertDialog.Builder modify_pop = new AlertDialog.Builder(theme_activity.this);
                modify_pop.setTitle("리스트 수정");
                modify_pop.setMessage("리스트를 수정하세요.");

                // 목표명 적는 edit
                EditText et_modify = new EditText(theme_activity.this);
                modify_pop.setView(et_modify);

                Log.e(" 디버깅.", "positon : " + position + "  ");


                // 확인 버튼
                modify_pop.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Log.e(" 디버깅.", "positon : " + position + "  ");


                        // 수정하기 작성창에 작성한 내용.
                        String title = et_modify.getText().toString();

                        // 수정내용 + 현재 클릭한 위치값의 이미지가져와서 넣기
                        theme_item data = new theme_item(title, (adapter.getItem(position).resId));
                        adapter.modify(position, data);

                        // 어답터 갱신
                        adapter.notifyDataSetChanged();

                        // 창끄기
                        dialogInterface.dismiss();
                    }
                });


                // 취소하기 버튼
                modify_pop.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                modify_pop.show();
            }
        });


        // 롱클릭시 삭제
        adapter.setOnItemLongClickListener(new theme_adapter.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(int position) {
                // 삭제할지 팝업으로 묻기
                AlertDialog.Builder del_pop = new AlertDialog.Builder(theme_activity.this);
                del_pop.setTitle("리스트 삭제");
                del_pop.setMessage("삭제하시겠습니까?");


                // 확인 버튼
                del_pop.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // 리스트 삭제
                        adapter.delete(theme_adapter.position);
                        // 어답터 갱신
                        adapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                });


                // 취소하기 버튼
                del_pop.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        // 시간을 기록하는 코드넣기
                    }
                });
                del_pop.show();
                // 롱클릭후 클릭은 처리 안되도록함, 중복사용하려면 false
                return true;
            }
        });



        // 하단 컬러 추가 버튼 모음 ------------------------------------------------------------------------------------------------------------
        // 보라색 컬러 추가 버튼
        tv_circle_purple = (TextView) findViewById(R.id.tv_circle_purple);
        tv_circle_purple.setClickable(true);
        tv_circle_purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(" 디버깅.", "positon : " + theme_adapter.position + "  ");

                num++;

                // 리스트를 추가하고, 갱신
                adapter.addItem(new theme_item("추가" + num, R.drawable.circle_purple));
                adapter.notifyDataSetChanged();

                // 토스트 메시지 - 수정완료
                //theme_item item = adapter.getItem(theme_adapter.position);
                Toast.makeText(getApplicationContext(), "보라 추가", Toast.LENGTH_LONG).show();
            }
        });

        // 그린컬러 추가 버튼
        tv_circle_green = (TextView) findViewById(R.id.tv_circle_green);
        tv_circle_green.setClickable(true);
        tv_circle_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(" 디버깅.", "positon : " + theme_adapter.position + "  ");

                num++;

                // 리스트를 추가하고, 갱신
                adapter.addItem(new theme_item("추가" + num, R.drawable.circle_green));
                adapter.notifyDataSetChanged();

                // 토스트 메시지 - 수정완료
                //theme_item item = adapter.getItem(theme_adapter.position);
                Toast.makeText(getApplicationContext(), "녹색 추가", Toast.LENGTH_LONG).show();
            }
        });

        // 노랑 컬러 추가 버튼
        tv_circle_yellow = (TextView) findViewById(R.id.tv_circle_yellow);
        tv_circle_yellow.setClickable(true);
        tv_circle_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(" 디버깅.", "positon : " + theme_adapter.position + "  ");

                num++;

                // 리스트를 추가하고, 갱신
                adapter.addItem(new theme_item("추가" + num, R.drawable.circle_yellow));
                adapter.notifyDataSetChanged();

                // 토스트 메시지 - 수정완료
                //theme_item item = adapter.getItem(theme_adapter.position);
                Toast.makeText(getApplicationContext(), "노랑 추가", Toast.LENGTH_LONG).show();
            }
        });

        // 주황 컬러 추가 버튼
        tv_circle_orange = (TextView) findViewById(R.id.tv_circle_orange);
        tv_circle_orange.setClickable(true);
        tv_circle_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(" 디버깅.", "positon : " + theme_adapter.position + "  ");

                num++;

                // 리스트를 추가하고, 갱신
                adapter.addItem(new theme_item("추가" + num, R.drawable.circle_orange));
                adapter.notifyDataSetChanged();

                // 토스트 메시지 - 수정완료
                //theme_item item = adapter.getItem(theme_adapter.position);
                Toast.makeText(getApplicationContext(), "오렌지 추가", Toast.LENGTH_LONG).show();
            }
        });

        // 핑크 컬러 추가 버튼
        tv_circle_pink = (TextView) findViewById(R.id.tv_circle_pink);
        tv_circle_pink.setClickable(true);
        tv_circle_pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(" 디버깅.", "positon : " + theme_adapter.position + "  ");

                num++;

                // 리스트를 추가하고, 갱신
                adapter.addItem(new theme_item("추가" + num, R.drawable.circle_pink));
                adapter.notifyDataSetChanged();

                // 토스트 메시지 - 수정완료
                //theme_item item = adapter.getItem(theme_adapter.position);
                Toast.makeText(getApplicationContext(), "핑크 추가", Toast.LENGTH_LONG).show();
            }
        });

        // 블루 컬러 추가 버튼
        tv_circle_blue = (TextView) findViewById(R.id.tv_circle_blue);
        tv_circle_blue.setClickable(true);
        tv_circle_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(" 디버깅.", "positon : " + theme_adapter.position + "  ");

                num++;

                // 리스트를 추가하고, 갱신
                adapter.addItem(new theme_item("추가" + num, R.drawable.circle_blue));
                adapter.notifyDataSetChanged();

                // 토스트 메시지 - 수정완료
                //theme_item item = adapter.getItem(theme_adapter.position);
                Toast.makeText(getApplicationContext(), "블루 추가", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 테먀가 ~로 설정되었습니다. 토스트
        // 테마 컬러를 ~로 변경하시겠습니까? 팝업
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 저장안된 변경 내용들을 저장한다.
        // 테마변경 사항 저장

    }

    @Override
    protected void onStop() {
        super.onStop();
        // onCreate에 작성된 내용 종료
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 강제종료
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 마지막으로 설정된 컬러 테마를 가져옴
    }
}