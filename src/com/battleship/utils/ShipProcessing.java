package com.battleship.utils;

import com.battleship.boarding.ResultingBoard;

public class ShipProcessing {
    public static int getNumberOfPreviousShips(ResultingBoard resultingBoard) {

        int count = 0;

        for (int i = 0; i < resultingBoard.getBoardData().length; i++) {
            for (int j = 0; j < resultingBoard.getBoardData().length; j++) {
                if (resultingBoard.getBoardData()[i][j] == 'O')
                    count++;
            }
        }
        return count;
    }

    public static String getShipType(int numberOfCells, ResultingBoard resultingBoard) {
        String shipType = "";
        switch (numberOfCells) {
            case 5 -> shipType = "Aircraft Carrier";
            case 4 -> shipType = "Battleship";
            case 3 -> {
                switch (getNumberOfPreviousShips(resultingBoard)) {
                    case 9 -> shipType = "Submarine";
                    case 12 -> shipType = "Cruiser";
                }
            }
            case 2 -> shipType = "Destroyer";
        }
        return shipType;
    }
}
