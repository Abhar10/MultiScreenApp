package com.abhar.android.multiscreenapp.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private int id;

    private String name;

    public User()
    {
    }

    public User(String name, int id)
    {
        this.id = id;
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setRollNum(int id)
    {
        this.id= id;
    }

    public int getRollNo()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
}
