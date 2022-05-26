package com.example.project.Model;

public class ModelOfToDo {
    private int id, status, done;
    private String task, description, stage;

    public int getId(){ return id;}
    public void setId (int id) {this.id = id; }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {return task;}
    public void setTask(String task){ this.task = task;}

    public String getDescription() {return description;}
    public void setDescription(String description){ this.description = description;}

    public String getStage() {return stage;}
    public void setStage(String stage){ this.stage = stage;}

    public int getDone() {
        return done;
    }
    public void setDone(int done) {
        this.done = done;
    }








}
