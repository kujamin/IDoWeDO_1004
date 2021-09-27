package com.example.firstproject3.setting;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firstproject3.HabbitMakeActivity;
import com.example.firstproject3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingPreferenceFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;
    ProgressDialog pd;
    FirebaseFirestore db;

    ListPreference themePreference;     //테마 변경 리스트 preference변수
    ListPreference soundChangePreference;  // 알림음 변경 리스트 preference변수
    //PreferenceScreen questiongoPreference;
    SwitchPreference switchPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.setting_preference);
            themePreference = (ListPreference)findPreference("theme_list");  //테마 변경 리스트 가져오기
            soundChangePreference = (ListPreference) findPreference("sound_list"); // 알림음의 리스트 가져오기
            //questiongoPreference = (PreferenceScreen) findPreference("question");   //문의하기
            pd = new ProgressDialog(getActivity());

            db = FirebaseFirestore.getInstance();
            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

            if(!prefs.getString("theme_list", "").equals("")){
                themePreference.setSummary(prefs.getString("theme_list", "Basic"));
            }

            if(!prefs.getString("sound_list", "").equals("")){
                soundChangePreference.setSummary(prefs.getString("sound_list", "띵띵"));
            }

            prefs.registerOnSharedPreferenceChangeListener(prefListener);
        } // onCreate

    //클릭 이벤트 처리 메소드
    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("theme_list")) {
                themePreference.setSummary(prefs.getString("theme_list", "Basic"));
            }

            if (key.equals("sound_list")) {
                soundChangePreference.setSummary(prefs.getString("sound_list", "띵띵"));
            }

            //2뎁스 PreferenceScreen 내부에서 발생한 환경설정 내용을 2뎁스 PreferenceScreen에 적용하기 위한 소스
            ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
        }
    };

    private void uploadData(String title, String data, String time, String memo, String category) {
        pd.setTitle("Loading...");

        pd.show();

        String sound = (String) themePreference.getSummary();
        String color = (String) soundChangePreference.getSummary();

        Map<String, Object> doc = new HashMap<>();
        doc.put("sound", sound);
        doc.put("color", color);

        db.collection("user setting").document("Timer").update(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(getContext(), sound  + " 설정되었습니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(pd != null && pd.isShowing()){
            pd.dismiss();
        }

    }
}