package com.idowedo.firstproject3;

import android.content.Intent;
//업적 성공하고 넘어갈 때 값을 전닳주는 클래스
public class ActivityResultEvent {
    private int requestCode;
    private int resultCode;
    private Intent data;

    //업로드
    //hid

    public static ActivityResultEvent create(int requestCode, int resultCode, Intent intent) {
        return new ActivityResultEvent(requestCode, resultCode, intent);
    }

    private ActivityResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}
