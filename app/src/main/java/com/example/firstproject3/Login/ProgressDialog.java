package com.example.firstproject3.Login;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.firstproject3.R;

public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context){
        super(context);

        //다이얼 로그 제목은 안 보이게
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
    }
}
