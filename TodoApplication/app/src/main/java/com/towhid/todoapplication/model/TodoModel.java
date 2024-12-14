package com.towhid.todoapplication.model;

import java.util.Date;

public class TodoModel {

    private int id;
    private String title;
    private String description;
    private String date;
    private String todotype;
    private String priority;

    public TodoModel() {
    }

    public TodoModel(int id, String title, String description, String date, String todotype, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.todotype = todotype;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTodotype() {
        return todotype;
    }

    public void setTodotype(String todotype) {
        this.todotype = todotype;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
