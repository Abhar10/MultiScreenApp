package com.abhar.android.multiscreenapp.model;

public class UserPost {

    private int userId, id;
    private String title, body;

    public UserPost(int userId, int id, String title, String body)
    {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }


    public int getUserId() {
        return userId;
    }
    public int getId()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getBody()
    {
        return body;
    }

}
