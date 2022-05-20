package com.example.myapplication.theme;


// 테마 컬러 리스트에 대한 아이템 데이터
public class theme_item {
    String title; // 테마타이틀
    int resId;   // 상단 원 이미지


    public theme_item(String title, int resId) {
        this.title = title;
        this.resId = resId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getCircle() {
        return resId;
    }

    public void setCircle(int resId) {
        this.resId = resId;
    }


    @Override
    public String toString() {
        return "theme_item{" +
                "title='" + title + '\'' +
                '}';
    }

}
