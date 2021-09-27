package com.example.firstproject3;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    String name;
    int age;

    public Person() { }
    //객체를 받아올때 호출
    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //객체를 전달할때 호출
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}


