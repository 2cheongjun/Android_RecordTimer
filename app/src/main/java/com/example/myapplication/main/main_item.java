package com.example.myapplication.main;


public class main_item {

    private String goal_line;
    private long time_line;

    private String note;
    private String noteTitle;

    private String start_day;
    private String end_day;




    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // 아이템에 목표명과 시간이 들어감.
    public main_item(String g_line, long t_line) {
        goal_line = g_line;
        time_line = t_line;
    }

    public String get_goal() {
        return goal_line;
    }

    public long get_time() {
        return time_line;
    }

    public String getNoteTitle() {
        return noteTitle;
    }


    public void set_time(long time_line) {
        this.time_line = time_line;
    }



}
//
//public class main_item {
//    String name; // 목표명
//    String time; // 누적시간
//    int resId; // 원 이미지
//
//    // 목표명, 누적시간, 아이콘이미지 선언
//    public main_item(String name, String time, int resId) {
//        this.name = name;
//        this.time = time;
//        this.resId = resId;
//    }
//
//    // 게터 세터 설정
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public int getResId() {
//        return resId;
//    }
//
//    public void setResId(int resId) {
//        this.resId = resId;
//    }
//
//
//    // toString
//    @Override
//    public String toString() {
//        return "goal_item{" +
//                "name='" + name + '\'' +
//                ", time='" + time + '\'' +
//                '}';
//    }
//
//
//}
