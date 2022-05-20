package com.example.myapplication.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


// 브로드캐스트 리시버
// SMS 문자가 오면 화면에 띄워서 정보를 보여줌
public class sms_receiver extends BroadcastReceiver {


    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "sms_receiver";

    // 발신한 날짜와 시간 분
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(SMS_RECEIVED))

            Log.d(TAG, "onReceive()호출됨");

        // 번들을 이용해서 메시지를 가져옴
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        // 메시지 내용 출력
        if (messages.length > 0) {
            // 발신번호를 받아옴
            String sender = messages[0].getOriginatingAddress();
            Log.d(TAG, "sender: " + sender);
            // 메시지 내용
            String contents = messages[0].getMessageBody().toString();
            Log.d(TAG, "contents: " + contents);
            // 문자 발신 시간
            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.d(TAG, "received date: " + receivedDate);

            //sms 액티비티로 메시지 전달
            sendToActivity(context, sender, contents, receivedDate);
        }
    }

    //     액티비티로 메세지의 내용을 전달해줌
    private void sendToActivity(Context context, String sender, String contents, Date receivedDate) {
        // 인텐트 안에 넣어서 보냄
        Intent intent = new Intent(context, sms_activity.class);

//     Flag 설정
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//     메세지의 내용을 Extra에 넣어줌
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        // 문자열로 바꿈
        intent.putExtra("receivedDate", format.format(receivedDate));

        context.startActivity(intent);
    }


    private SmsMessage[] parseSmsMessage(Bundle bundle) {

        // sms관련데이터
        Object[] obJs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[obJs.length];

        //
        for (int i = 0; i < obJs.length; i++) {
            // M은 마쉬멜로버전
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) obJs[i], format);
                // 그 이전 버전일때
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) obJs[i]);
            }
        }
        return messages;
    }
}

