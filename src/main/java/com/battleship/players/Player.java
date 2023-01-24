package com.battleship.players;

import com.battleship.boarding.FoggedBoard;
import com.battleship.boarding.ResultingBoard;

public class Player {

    ResultingBoard resultingBoard;
    FoggedBoard foggedBoard;

    public Player(ResultingBoard resultingBoard, FoggedBoard foggedBoard) {
        this.resultingBoard = resultingBoard;
        this.foggedBoard = foggedBoard;
    }
}
