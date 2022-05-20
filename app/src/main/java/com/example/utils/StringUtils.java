package com.example.utils;

import java.util.Locale;



// 시간값 문자열로 바꿔주기
public class StringUtils {

    public static String getTimeText(long time) {

        int hours = (int) (time / 1000) / 3600;
        int minutes = (int) ((time / 1000) % 3600) / 60;
        int seconds = (int) (time / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d시간%02d분%02d초", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d분%02d초", minutes, seconds);
        }

        return timeLeftFormatted;
    }

}
