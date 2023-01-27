package com.battleship.boarding;

import com.battleship.Main;
import com.battleship.players.Player;
import com.battleship.utils.Coordinates;
import com.battleship.utils.ShipProcessing;

import java.util.Scanner;
import java.util.stream.Stream;

import static com.battleship.utils.ValidityChecking.isShipValid;

public class BoardProcessing {

    public static void drawEmptyBoard() {

        //A variable assigned 'A' expressed in ASCII code to be incremented until 'J'
        int letter = 65;

        // Printing out the first line of numbers 1 to 10
        System.out.print(' ');
        Stream.of(" 1 2 3 4 5 6 7 8 9 10").forEach(System.out::print);
        System.out.println();

        /*
         *  Printing letters 'A' up to 'J' in a column with the use of ASCII codes incremented,
         *  and horizontal lines of fog '~'
         */
        for (int i = 0; i < 10; i++) {
            System.out.print(Character.toChars(letter));
            Stream.of(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~").forEach(System.out::println);
            letter++;
        }
    }

    public static void createBoard(int[] coordinates, char[][] board, char[][] baseBoard,
                                   ResultingBoard baseBoardOutcome, Player player) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //Placing the already existing ships
                if (player.getResultingBoard().getBoardData()[i][j] == Main.SHIP_COMPONENT) {
                    board[i][j] = Main.SHIP_COMPONENT;
                } else board[i][j] = Main.FOG;
                //Placing a new ship horizontally
                if (i == coordinates[0] && i == coordinates[2] && j >= coordinates[1] && j <= coordinates[3]) {
                    board[i][j] = Main.SHIP_COMPONENT;
                    //Placing a new ship vertically
                } else if (j == coordinates[1] && j == coordinates[3] && i >= coordinates[0] && i <= coordinates[2]) {
                    board[i][j] = Main.SHIP_COMPONENT;
                }
            }
        }

        ResultingBoard result = new ResultingBoard();
        result.setBoardData(board);

        player.setResultingBoard(result);


//        if (player.getNumber().equals("1")) {
//        } else if (player.getNumber().equals("2")){
//            ResultingBoard boardOfPlayerTwo = new ResultingBoard();
//            boardOfPlayerTwo.setBoardData(baseBoard);
//            player.setResultingBoard(boardOfPlayerTwo);
//        }
        copyBoard(board, baseBoard, baseBoardOutcome); // Copying the values of the "board" to a new array not
        // references
    }

    public static void drawBoard(ResultingBoard resultingBoard) {

        int letter = 65; //'A' expressed as ASCII
        Stream.of("\s\s1 2 3 4 5 6 7 8 9 10").forEach(System.out::println);

        for (int i = 0; i < 10; i++) {
            System.out.print(Character.toChars(letter));
            System.out.print(" ");
            for (int j = 0; j < 10; j++) {
                System.out.print(resultingBoard.getBoardData()[i][j] + " ");
            }
            System.out.println();
            letter++;
        }
    }

    public static void drawBoard(FoggedBoard foggedBoard) {

        int letter = 65; //'A' expressed as ASCII
        Stream.of("\s\s1 2 3 4 5 6 7 8 9 10").forEach(System.out::println);

        for (int i = 0; i < 10; i++) {
            System.out.print(Character.toChars(letter));
            System.out.print(" ");
            for (int j = 0; j < 10; j++) {
                System.out.print(foggedBoard.getFoggedBoardData()[i][j] + " ");
            }
            System.out.println();
            letter++;
        }
    }

    public static FoggedBoard createBoard(int[] coordinates, char[][] foggedBoard, ResultingBoard resultingBoard,
                                          FoggedBoard foggedBoardOutcome, char shot) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (coordinates[0] == i && coordinates[1] == j && resultingBoard.getBoardData()[i][j] == shot) {
                    foggedBoard[i][j] = shot;
                } else if (coordinates[0] == i && coordinates[1] == j && resultingBoard.getBoardData()[i][j] == shot) {
                    foggedBoard[i][j] = shot;
                }
                if (resultingBoard.getBoardData()[i][j] == Main.SHIP_COMPONENT ||
                        resultingBoard.getBoardData()[i][j] == Main.FOG && resultingBoard.getBoardData()[i][j] != shot) {
                    foggedBoard[i][j] = Main.FOG;
                }
            }
        }
        foggedBoardOutcome.setFoggedBoardData(foggedBoard);
        return foggedBoardOutcome;
    }

    /*
     *   The objective of this copyBoard method is to copy the array to a new array so no reference is used,
     *   so that the values remain always as originals
     */
    public static void copyBoard(char[][] board, char[][] baseBoard, ResultingBoard baseBoardOutcome) {
        for (int i = 0; i < 10; i++) {
            System.arraycopy(board[i], 0, baseBoard[i], 0, 10);
        }
        baseBoardOutcome.setBoardData(baseBoard);
    }

    public static void enterCoordinatesToPlaceAllShips(Scanner scanner,
                                                       ResultingBoard baseBoardOutcome,
                                                       char[][] board, char[][] baseBoard, int numberOfCells,
                                                       int checkIndexForThreeCells, Player player) {
        int[] coordinates;
//        player.getNumber().equals("1") ? player1 = new Player(resultingBoard, player.getFoggedBoard())
//        new Player(player.getNumber());
        player.playerCommandToPlay();
        BoardProcessing.drawEmptyBoard();
        for (; numberOfCells >= 2; numberOfCells--) {

            if (numberOfCells == 3 && ShipProcessing.getNumberOfPreviousShips(player.getResultingBoard()) == 9
                    || ShipProcessing.getNumberOfPreviousShips(player.getResultingBoard()) == 12) {
                System.out.println("Enter the coordinates of the " + ShipProcessing.getShipType(numberOfCells,
                        player.getResultingBoard()) + " (" + numberOfCells + " cells):");
                checkIndexForThreeCells++;
            } else {
                System.out.println("Enter the coordinates of the " + ShipProcessing.getShipType(numberOfCells,
                        player.getResultingBoard()) + " (" + numberOfCells + " cells):");
            }
            coordinates = Coordinates.enterCoordinates(scanner);

            while (!isShipValid(coordinates, numberOfCells, player.getResultingBoard())) {
                coordinates = Coordinates.enterCoordinates(scanner);
            }

            /* this is an index for 2 consecutive ships with three cells, so we need to set numberOfCells to 4 after
            the first three-cell ship as the numberOfCells iterates backwards,
            and we want to get the value of three again */
            if (checkIndexForThreeCells == 1) numberOfCells = 4;

            createBoard(coordinates, board, baseBoard, baseBoardOutcome, player);

            drawBoard(player.getResultingBoard());

        }
        if (!player.getNumber().equals("2")) {
            Scanner readKey = new Scanner(System.in);
            System.out.println("Press Enter and pass the move to another player");
            readKey.nextLine();
        }

    }
}
