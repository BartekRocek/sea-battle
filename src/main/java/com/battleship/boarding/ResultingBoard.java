package com.battleship.boarding;

public class ResultingBoard {

    private char[][] boardData = new char[10][10];

    public char[][] getBoardData() {
        return boardData;
    }

    public void setBoardData(char[][] boardData) {
        this.boardData = boardData;
    }
}