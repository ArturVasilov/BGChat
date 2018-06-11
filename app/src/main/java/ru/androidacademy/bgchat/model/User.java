package ru.androidacademy.bgchat.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String email;
    private String id;
    private String name;
    private List<String> hobbies;
    private List<String> rooms;

    public User(String email, String id, String name) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.hobbies = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public User() {
        this.hobbies = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public void addRoom(String room) {
        rooms.add(room);
    }

    public void removeRoom(String room) {
        rooms.remove(room);
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }

    public void removeHobby(String hobby) {
        hobbies.remove(hobby);
    }

    public List<String> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return Collections.unmodifiableList(hobbies);
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
