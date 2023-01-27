package com.battleship.players;

import com.battleship.boarding.FoggedBoard;
import com.battleship.boarding.ResultingBoard;

import java.util.function.Predicate;

public class Player {

    ResultingBoard resultingBoard;
    FoggedBoard foggedBoard;

    private String number;

    Predicate<String> isPlayerOne = x -> this.number.equals("1");

//    public Player(String number) {
//        this.number = number;
//    }

    public Player(ResultingBoard resultingBoard, FoggedBoard foggedBoard) {
        this.resultingBoard = resultingBoard;
        this.foggedBoard = foggedBoard;
    }

    public void playerCommandToPlay() {
         System.out.println(isPlayerOne.test("1") ? "Player " + this.number + ", place your ships on the game field)"
                 : "Player " + this.number + ", place your ships to the game field");
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
