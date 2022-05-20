package com.example.myapplication.main;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


// 리사이클러뷰 터치, 드래그 설정
public class SimpleTextItemTouchHelperCallback extends ItemTouchHelper.Callback {

    // 터치 리스너
    private OnItemTouchListener listener;


    public SimpleTextItemTouchHelperCallback(OnItemTouchListener listener) {
        this.listener = listener;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // 어댑터의 move_item 불러오기
        return listener.move_item(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 어댑터의 remove_item 불러오기
        listener.remove_item(viewHolder.getAdapterPosition());
    }

    // 터치로 이동시 컬러 바꾸기
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder.itemView.setBackgroundColor(Color.BLUE);
        }
    }
    // 다시 원래 컬러로 변경
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
         viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }
    // 스와이프 설정
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
//        return false; 스와이프 안됨
    }

    // 외부클래스에서는 어댑터를 바로 사용할수 없으므로, 인터페이스를 만들어줌
    public interface OnItemTouchListener{
                          // 시작위치, 움직일 위치
        boolean move_item(int frompostion, int toposition);
        void remove_item(int position);
    }


}
