package com.battleship;

import com.battleship.boarding.BoardProcessing;
import com.battleship.boarding.FoggedBoard;
import com.battleship.boarding.ResultingBoard;
import com.battleship.utils.Coordinates;
import com.battleship.utils.ShipProcessing;

import java.util.Scanner;

import static com.battleship.utils.ValidityChecking.isEveryShipSunk;
import static com.battleship.utils.ValidityChecking.isShipHit;
import static com.battleship.utils.ValidityChecking.isShipValid;
import static com.battleship.utils.ValidityChecking.isSingleShipSunk;


public class Main {

    public final static char SHIP_COMPONENT = 'O';
    public final static char FOG = '~';
    public final static char HIT = 'X';
    public final static char MISSED = 'M';

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ResultingBoard resultingBoard = new ResultingBoard();
        ResultingBoard baseBoardOutcome = new ResultingBoard();
        FoggedBoard foggedBoardOutcome = new FoggedBoard();

        char[][] board = new char[10][10];
        char[][] foggedBoard = new char[10][10];
        char[][] baseBoard = new char[10][10];
        int[] coordinates;
        int numberOfCells = 5;
        var checkIndexForThreeCells = 0;

        BoardProcessing.drawEmptyBoard();

        for (; numberOfCells >= 2; numberOfCells--) {

            if (numberOfCells == 3 && ShipProcessing.getNumberOfPreviousShips(resultingBoard) == 9
                    || ShipProcessing.getNumberOfPreviousShips(resultingBoard) == 12) {
                System.out.println("Enter the coordinates of the " + ShipProcessing.getShipType(numberOfCells, resultingBoard)
                        + " (" + numberOfCells + " cells):");
                checkIndexForThreeCells++;
            } else {
                System.out.println("Enter the coordinates of the " + ShipProcessing.getShipType(numberOfCells, resultingBoard)
                        + " (" + numberOfCells + " cells):");
            }
            coordinates = Coordinates.enterCoordinates(scanner);

            while (!isShipValid(coordinates, numberOfCells, resultingBoard)) {
                coordinates = Coordinates.enterCoordinates(scanner);
            }

            /* this is an index for 2 consecutive ships with three cells, so we need to set numberOfCells to 4 after
            the first three-cell ship as the numberOfCells iterates backwards,
            and we want to get the value of three again */
            if (checkIndexForThreeCells == 1) numberOfCells = 4;

            BoardProcessing.createBoard(coordinates, board, baseBoard, resultingBoard, baseBoardOutcome);

            BoardProcessing.drawBoard(resultingBoard);
        }

        System.out.println("The game starts!");
        BoardProcessing.drawEmptyBoard();

        System.out.println("Take a shot!");

        while (!isEveryShipSunk(foggedBoardOutcome)) {

            coordinates = Coordinates.enterCoordinatesForShooting(scanner);

            if (!isShipHit(coordinates, board, resultingBoard)) {
                BoardProcessing.drawBoard(BoardProcessing.createBoard(coordinates, foggedBoard, resultingBoard, foggedBoardOutcome, MISSED));
                System.out.println("You missed!");
            } else if (isSingleShipSunk(coordinates, resultingBoard, baseBoardOutcome)) {
                    BoardProcessing.drawBoard(BoardProcessing.createBoard(coordinates, foggedBoard, resultingBoard, foggedBoardOutcome, HIT));
                    System.out.println("You sank a ship! Specify a new target:");
            } else {
                BoardProcessing.drawBoard(BoardProcessing.createBoard(coordinates, foggedBoard, resultingBoard, foggedBoardOutcome, HIT));
                    System.out.println("You hit a ship! Try again:");
                }
            }
        }

}