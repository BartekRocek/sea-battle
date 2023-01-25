package com.battleship.players;

import com.battleship.boarding.FoggedBoard;
import com.battleship.boarding.ResultingBoard;

public class Player {

    ResultingBoard resultingBoard;
    FoggedBoard foggedBoard;

    private String number;


    public Player(String number) {
        this.number = number;
        System.out.println("Player " + this.number + ", place your ships on the game field");
    }

    public Player(ResultingBoard resultingBoard, FoggedBoard foggedBoard) {
        this.resultingBoard = resultingBoard;
        this.foggedBoard = foggedBoard;
    }

    public ResultingBoard getResultingBoard() {
        return resultingBoard;
    }

    public void setResultingBoard(ResultingBoard resultingBoard) {
        this.resultingBoard = resultingBoard;
    }

    public FoggedBoard getFoggedBoard() {
        return foggedBoard;
    }

    public void setFoggedBoard(FoggedBoard foggedBoard) {
        this.foggedBoard = foggedBoard;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
