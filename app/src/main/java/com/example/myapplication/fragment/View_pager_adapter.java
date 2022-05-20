package com.example.myapplication.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class View_pager_adapter extends FragmentPagerAdapter {
    public View_pager_adapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    // 프레그먼트를 스위칭되는 것을 보여주는 곳
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return frag_pie.newInstance();

            case 1:
                return frag_bar.newInstance();

//            case 2:
//                return frag_circle.newInstance();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // 뷰페이저 갯수
        return 2;
    }

    // 상단 탭 레이아웃 타이틀 선언
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "파이그래프";

            case 1:
                return "막대그래프";

//            case 2:
//                return "원그래프";

            default:
                return null;
        }
    }
}
