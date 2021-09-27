package com.example.firstproject3.daily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject3.CustomHabbitAdapter;
import com.example.firstproject3.Habbit_Item;
import com.example.firstproject3.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomCalendar extends RecyclerView.Adapter<CustomCalendar.CustomViewHolder> {



    private ArrayList<CalListActivity> arrayList;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";


    public CustomCalendar(ArrayList<CalListActivity> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_daily_cal_list, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cal_title.setText(arrayList.get(position).getCal_title());
        holder.cal_time.setText(arrayList.get(position).getCal_time());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView cal_title, cal_time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cal_title = itemView.findViewById(R.id.callist1);
            this.cal_time = itemView.findViewById(R.id.callist2);
        }
    }
}

