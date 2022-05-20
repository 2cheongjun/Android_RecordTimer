package com.example.myapplication.theme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;


// 테마 리사이클러뷰 어답터
public class theme_adapter extends RecyclerView.Adapter<theme_adapter.ViewHolder> {

    public static int position;
    Context context;

    ArrayList<theme_item> items = new ArrayList<theme_item>();

    OnItemClickListener listener;
    OnItemLongClickListener listener_long;


    // 인터페이스로 정의 (한번 클릭)
    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }
    // 인터페이스로 정의 (롱클릭)
    public static interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }


    public theme_adapter(Context context) {
        this.context = context;

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.theme_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        theme_item item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(listener);
        holder.setOnItemLongClickListener(listener_long);
    }


    public void addItem(theme_item item) {
        items.add(item);
    }

    public void addItems(ArrayList<theme_item> items) {
        this.items = items;
    }

    public theme_item getItem(int position) {
        return items.get(position);
    }


    // 삭제하기
    public void delete(int position) {
        items.remove(position);
    }

    // 수정하기
    // 현재 위치값과. 새로쓴 텍스트내용 가져옴
    public void modify(int position, theme_item data) {

        // 새로 작성한 내용을, 선택한 아이템의 포지션값에 넣는다.
        //   items.get(position).name = write.name;
        for (int i = 0; i < items.size(); i++) {
            if (i == (position)) {
                items.set(i, data);
            }
        }
    }


    // 리스너 등록하기
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener_long) {
        this.listener_long = listener_long;
    }





    // 뷰홀더 설정
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        ImageView tv_circle;

        // 클릭 리스너 등록하기
        OnItemClickListener listener;
        OnItemLongClickListener listener_long;


        public ViewHolder(View itemView) {
            super(itemView);

            // theme_item 에서 텍스트뷰 찾아오기
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_circle = (ImageView) itemView.findViewById(R.id.tv_circle);

            // theme_item 아이템뷰 박스 영역 아이디값을 정해주고 가져온다.
            itemView.findViewById(R.id.tv_container);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener_long != null) {
                        listener_long.onItemLongClick(position);
                        Log.e("lee", position + "");
                    }

                    return true;
                }
            });

        }


        // 데이터 담아주기
        public void setItem(theme_item item) {
            // 컬러텍스트
            tv_title.setText(item.getTitle());
//             컬러이미지
            tv_circle.setImageResource(item.getCircle());
        }


        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener listener_long) {
            this.listener_long = listener_long;
        }


    }
}



