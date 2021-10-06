package com.example.firstproject3.DarkMode;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtil {

  static final String THEME_KEY = "theme_value";

  public static void applyTheme(@NonNull Context context) {
    int option = SharedPrefsUtil.getInt(context, THEME_KEY, 0);
    applyTheme(context, option);
  }

  // 0 : light, 1 : dark, 2 : daytime
  public static void applyTheme(@NonNull Context context, int option) {
    // 테마 값 저장
    SharedPrefsUtil.putInt(context, THEME_KEY, option);

    if (option == 0) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
      return;
    }

    if (option == 1) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
      return;
    }

    //안드로이드 버전이 10인 경우
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
      Log.d("Hi", "버전 10이상 성공!");
    } else {  //안드로이드 버전이 9이하인 경우
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
      Log.d("Hi", "버전 9이하 성공!");
    }
  }

}
