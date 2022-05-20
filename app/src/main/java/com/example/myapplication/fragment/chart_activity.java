package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class chart_activity extends AppCompatActivity {


    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chart_tab);


        ViewPager viewPager = (ViewPager)findViewById(R.id.view_paser_area);
        fragmentPagerAdapter = new View_pager_adapter(getSupportFragmentManager());

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
