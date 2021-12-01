package com.idowedo.firstproject3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.idowedo.firstproject3.R;

import java.util.ArrayList;

public class CustomChoiceListViewAdapter extends BaseAdapter {

    private ArrayList<Todo_Item> listViewItemList = new ArrayList<Todo_Item>() ;

    //Adapter에 사용되는 데이터의 개수 반환
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    //데이터를 화면에 출력하는데 사용될 화면 반환
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        Todo_Item listViewItem = listViewItemList.get(position);

        return convertView;
    }

    //지정한 위치에 있는 데이터와 관계된 아이템 ID 반환
    @Override
    public long getItemId(int position) {
        return position ;
    }

    //지정한 위치에 있는 데이터 반환
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    //아이템 데이터 추가 메소드
    public void addItem(Drawable icon, String text) {
        Todo_Item item = new Todo_Item();

        listViewItemList.add(item);
    }
}