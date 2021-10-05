package com.example.firstproject3.setting;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
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
    PreferenceCategory preferenceCategory;

    //PreferenceScreen questiongoPreference;
    SwitchPreference switchPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.setting_preference);
            //questiongoPreference = (PreferenceScreen) findPreference("question");   //문의하기
            pd = new ProgressDialog(getActivity());

            db = FirebaseFirestore.getInstance();
            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        } // onCreate


    private void uploadData(String title, String data, String time, String memo, String category) {
        pd.setTitle("Loading...");

        pd.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(pd != null && pd.isShowing()){
            pd.dismiss();
        }

    }
}