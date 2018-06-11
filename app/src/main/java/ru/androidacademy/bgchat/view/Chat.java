package ru.androidacademy.bgchat.view;

/**
 * Created by User on 11.06.2018.
 */

public class Chat {

    private String avatarUrl;
    private String userName;
    private String firstHobby;
    private String secondHobby;

    public Chat() {}

    public Chat(String avatarUrl, String userName, String firstHobby, String secondHobby) {
        this.avatarUrl = avatarUrl;
        this.userName = userName;
        this.firstHobby = firstHobby;
        this.secondHobby = secondHobby;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setFirstHobby(String firstHobby) {
        this.firstHobby = firstHobby;
    }

    public String getFirstHobby() {
        return firstHobby;
    }

    public void setSecondHobby(String secondHobby) {
        this.secondHobby = secondHobby;
    }

    public String getSecondHobby() {
        return secondHobby;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
