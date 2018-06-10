package ru.androidacademy.bgchat.interfaces;

public interface Consumer<T> {
    void accept(T t);
}