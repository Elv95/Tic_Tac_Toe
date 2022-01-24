package com.gambelli.tris;

public class Player{

    private String name;
    private Status status;

    public Player(String name, Status status){
        setName(name);
        setStatusPlayer(status);
    }

    public void setStatusPlayer(Status status) {
        this.status = status;
    }

    public Status getStatusPlayer(){
        return this.status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return getName();
    }
}
