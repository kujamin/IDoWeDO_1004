package com.example.firstproject3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Company implements Parcelable {
    ArrayList<Person> persons;

    public Company(){
    }
    protected Company(Parcel in) {
        persons = new ArrayList<>();
        in.readTypedList(persons,Person.CREATOR);
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(persons);
    }
    public ArrayList<Person> getPersons() {
        return persons;
    }
    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}


