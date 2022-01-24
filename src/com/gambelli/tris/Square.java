package com.gambelli.tris;

public class Square {

    private Status status;
    private int row;
    private int column;

    public Square(int row, int column){
        setStatus(Status.E);
        setSquareInfo(row, column);
    }

    private void setSquareInfo(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setStatus(Status stat){
        this.status = stat;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public Status getStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return "com.gambelli.tris.Square [" + getRow() + "][" + getColumn() + "] : " + getStatus();
    }
}
