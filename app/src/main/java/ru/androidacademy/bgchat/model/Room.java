package ru.androidacademy.bgchat.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Room {
    private String id;
    private List<String> users;

    public Room(String id, String... userIds) {
        this.id = id;
        users = Arrays.asList(userIds);
    }

    public Room() {
        users = new ArrayList<>();
    }

    public void addUser(String userId) {

    }

    public void removeUser(String userId) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
