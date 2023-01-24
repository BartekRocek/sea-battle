package com.battleship.boarding;

public class FoggedBoard {

    private char[][] foggedBoardData = new char[10][10];

    public char[][] getFoggedBoardData() {
        return foggedBoardData;
    }

    public void setFoggedBoardData(char[][] foggedBoardData) {
        this.foggedBoardData = foggedBoardData;
    }
}