package com.example.firstproject3;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;


public class Todo_Item {


    private String todo_category;
    private String todo_title, todo_id;

    private Drawable img ;
    private String title ;
    private String record ;


    public Todo_Item() {

    }

    public Todo_Item(String todo_category, String todo_title, String todo_id){
        this.todo_category = todo_category;
        this.todo_title = todo_title;
        this.todo_id = todo_id;
    }



    public String getTodo_category() {
        return todo_category;
    }

    public void setTodo_category(String todo_category) {
        this.todo_category = todo_category;
    }

    public String getTodo_title() {
        return todo_title;
    }

    public void setTodo_title(String todo_title) {
        this.todo_title = todo_title;
    }

    public String getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(String todo_id) {
        this.todo_id = todo_id;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
