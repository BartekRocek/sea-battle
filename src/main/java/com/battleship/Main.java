package com.battleship;

import com.battleship.boarding.BoardProcessing;
import com.battleship.boarding.FoggedBoard;
import com.battleship.boarding.ResultingBoard;
import com.battleship.players.Player;
import com.battleship.utils.Coordinates;

import java.util.Scanner;

import static com.battleship.utils.ValidityChecking.isEveryShipSunk;
import static com.battleship.utils.ValidityChecking.isShipHit;
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

        //Player ONE enters coordinates
        ResultingBoard resultingBoard1 = new ResultingBoard();
        FoggedBoard foggedBoardOutcome1 = new FoggedBoard();
        Player playerOne = new Player(resultingBoard1, foggedBoardOutcome1);
        playerOne.setNumber("1");
        BoardProcessing.enterCoordinatesToPlaceAllShips(scanner, baseBoardOutcome, board, baseBoard,
                numberOfCells, checkIndexForThreeCells, playerOne);

        //Player TWO enters coordinates
        ResultingBoard resultingBoard2 = new ResultingBoard();
        FoggedBoard foggedBoardOutcome2 = new FoggedBoard();
        Player playerTwo = new Player(resultingBoard2, foggedBoardOutcome2);
        playerTwo.setNumber("2");
        BoardProcessing.enterCoordinatesToPlaceAllShips(scanner, baseBoardOutcome, board, baseBoard,
                numberOfCells, checkIndexForThreeCells, playerTwo);

        System.out.println("The game starts!");

        BoardProcessing.drawEmptyBoard();

        System.out.println("Take a shot!");

        while (!isEveryShipSunk(foggedBoardOutcome)) {

            coordinates = Coordinates.enterCoordinatesForShooting(scanner);

            if (!isShipHit(coordinates, board, resultingBoard)) {
                BoardProcessing.drawBoard(BoardProcessing.createBoard(coordinates, foggedBoard, resultingBoard,
                        foggedBoardOutcome, MISSED));
                System.out.println("You missed!");
            } else if (isSingleShipSunk(coordinates, resultingBoard, baseBoardOutcome)) {
                BoardProcessing.drawBoard(BoardProcessing.createBoard(coordinates, foggedBoard, resultingBoard,
                        foggedBoardOutcome, HIT));
                System.out.println("You sank a ship! Specify a new target:");
            } else {
                BoardProcessing.drawBoard(BoardProcessing.createBoard(coordinates, foggedBoard, resultingBoard,
                        foggedBoardOutcome, HIT));
                System.out.println("You hit a ship! Try again:");
            }
        }
    }

}