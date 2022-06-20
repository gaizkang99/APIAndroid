package com.example.practicaexamen2.utils;

public class Usuario {
    private String user_id;
    private String title;
    private String due_on;
    private String status;

    public Usuario(String title, String due_on, String status){
        this.title = title;
        this.due_on = due_on;
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDue_on() {
        return due_on;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", due_on='" + due_on + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
