package com.example.myapplication.main;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.utils.StringUtils;

import java.util.ArrayList;


// 어댑터의 역할은? 데이터를 사용할수 있게 변한하고 -> 리스트에 전달
// 데이터 -> 어댑터(변환) -> 리스트뷰
public class main_adapter extends RecyclerView.Adapter<main_adapter.my_ViewHolder> implements SimpleTextItemTouchHelperCallback.OnItemTouchListener {


    public static int position;
    private ArrayList<main_item> items;
    // 리스너 메인액티비티에서 구현해준다.2개
    private final ListItemClickListener listItemClickListener;
    private OnStartDragListener onStartDragListener;
    private int selectedPosition =-1;


    // 클릭이벤트, 터치이벤트 파라미터 설정 // 어답터에서 아이템 값을 받는다.
    public main_adapter(ArrayList<main_item> items, ListItemClickListener listItemClickListener, OnStartDragListener onStartDragListener) {
        this.items = items;
        this.listItemClickListener = listItemClickListener;
        this.onStartDragListener = onStartDragListener;
    }


    @NonNull
    @Override
    public my_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_item, viewGroup, false);
        return new my_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(my_ViewHolder holder, int position) {

        main_item data = items.get(position);
        holder.goal_line.setText(data.get_goal());
//        holder.time_line.setText(String.valueOf(data.getLine2()));
        holder.time_line.setText(StringUtils.getTimeText(data.get_time()));
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    // 1.터치 헬퍼 데이터를 움직이기 위한 설정
    @Override
    public boolean move_item(int fromposition, int toposition) {
        main_item text = items.get(fromposition);
        items.remove(fromposition);
        items.add(toposition, text);
        notifyItemMoved(fromposition, toposition);
        return true;
    }

    // 2.터치 헬퍼 아이템 삭제
    @Override
    public void remove_item(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public main_item getItem(int position) {
        return items.get(position);
    }


    // 위치값, 누적시간값 세팅
    public void setItem(int position, long time) {

        int idx = 0;
        for (main_item each : items) {

            if(idx == position) {
                each.set_time(time);
            }

            idx++;
        }
    }


    public class my_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, GestureDetector.OnGestureListener {

        public TextView goal_line;
        public TextView time_line;
        private GestureDetector gestureDetector;
        public ImageView iv_drag;


        @SuppressLint("ClickableViewAccessibility")
        public my_ViewHolder(View itemView) {
            super(itemView);

            // main_item 아이템뷰 박스 영역 아이디값을 정해주고 가져온다.
            itemView.findViewById(R.id.main_item_container);
            goal_line = itemView.findViewById(R.id.tv_goal_input);
            time_line = itemView.findViewById(R.id.tv_time_input);
            iv_drag = itemView.findViewById(R.id.iv_drag);
            itemView.setOnClickListener(this);

            // = 버튼을 눌러야만 드래그가 가능하도록 설정 / 드래그 버튼 이벤트 설정
            gestureDetector = new GestureDetector(itemView.getContext(), this);
            iv_drag = (ImageView) itemView.findViewById(R.id.iv_drag);
            iv_drag.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
        }

        @Override
        public void onClick(View v) {
            listItemClickListener.listItemClick(getAdapterPosition());
        }


        // 제스처 콜백 함수
        @Override
        public boolean onDown(MotionEvent e) {
            onStartDragListener.onStartDrag(this);
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    // 터치 리스너 인터페이스
    public interface OnStartDragListener {
        void onStartDrag(my_ViewHolder viewHolder);
    }

    // 인터페이스로 정의 (한번 클릭)
    public interface ListItemClickListener {
        void listItemClick(int position);
    }

}

