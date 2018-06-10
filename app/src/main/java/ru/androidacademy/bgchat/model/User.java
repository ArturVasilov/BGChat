package ru.androidacademy.bgchat.model;

import java.util.Collections;
import java.util.List;

public class User {
    private String email;
    private String id;
    private String name;
    private List<String> hobbies;

    public User(String email, String id, String name, List<String> hobbies) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.hobbies = hobbies;
    }

    public User() {
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }

    public void removeHobby(String hobby) {
        hobbies.remove(hobby);
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
