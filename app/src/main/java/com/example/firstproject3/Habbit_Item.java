package com.example.firstproject3;

public class Habbit_Item {
    
    private String habbit_category;
    private String habbit_title;
    private String habbit_id;

    public String getHabbit_id() {
        return habbit_id;
    }

    public void setHabbit_id(String habbit_id) {
        this.habbit_id = habbit_id;
    }

    public Habbit_Item(String habbit_category, String habbit_title, String habbit_id){
        this.habbit_category = habbit_category;
        this.habbit_title = habbit_title;
        this.habbit_id = habbit_id;
    }

    public String getHabbit_category() {
        return habbit_category;
    }

    public void setHabbit_category(String habbit_category) {
        this.habbit_category = habbit_category;
    }

    public String getHabbit_title() {
        return habbit_title;
    }

    public void setHabbit_title(String habbit_title) {
        this.habbit_title = habbit_title;
    }
}
