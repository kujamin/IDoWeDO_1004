package com.idowedo.firstproject3.daily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idowedo.firstproject3.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomCalendar extends RecyclerView.Adapter<CustomCalendar.CustomViewHolder> {



    private ArrayList<CalListActivity> arrayList;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";

    //클래스 생성자
    public CustomCalendar(ArrayList<CalListActivity> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    //뷰를 담을 holder를 만들어줌
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_daily_cal_list, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    //holder에 만들어진 클래스 arraylist를 담아준다.
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cal_title.setText(arrayList.get(position).getCal_title());
        holder.cal_time.setText(arrayList.get(position).getCal_time());
    }

    //item의 갯수를 세어서 arraylist에 반환해준다.
    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    //제목과 시간으로 담겨서 recyclerview를 이룰수 있도록 한다.
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView cal_title, cal_time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cal_title = itemView.findViewById(R.id.callist1);
            this.cal_time = itemView.findViewById(R.id.callist2);
        }
    }
}

