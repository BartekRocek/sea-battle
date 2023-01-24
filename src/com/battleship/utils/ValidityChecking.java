package com.battleship.utils;

import com.battleship.boarding.FoggedBoard;
import com.battleship.boarding.ResultingBoard;
import com.battleship.interfaces.SettingShipExtremes;

import static com.battleship.Main.SHIP_COMPONENT;
import static com.battleship.Main.FOG;
import static com.battleship.Main.HIT;
import static com.battleship.Main.MISSED;


public class ValidityChecking {

    protected static boolean isShipLocationCorrect(int[] coordinates) {
        //At least one pair of coordinates must be equal for the ship to be either horizontal or vertical
        if (!(coordinates[0] == coordinates[2] || coordinates[1] == coordinates[3])) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        } else return true;
    }

    protected static boolean isShipSizeCorrect(int[] coordinates, int numberOfCells, ResultingBoard resultingBoard) {

        if (!(coordinates[2] - coordinates[0] == numberOfCells - 1
                || coordinates[3] - coordinates[1] == numberOfCells - 1)) {
            System.out.println("Error! Wrong length of the " + ShipProcessing.getShipType(numberOfCells, resultingBoard) + "! Try again:");
            return false;
        } else return true;
    }

    protected static boolean isShipDistanceCorrect(int[] coordinates, ResultingBoard resultingBoard, int numberOfCells) {
        /*
         * A new object to create the surrounding area around the ship to check that there is a shipComponent
         * within set up previously
         *  ***
         *  *s*
         *  *h*
         *  *i*
         *  *p*
         *  ***
         */
        ResultingBoard checkArraySurroundingBoard = new ResultingBoard();
        char[][] board = new char[10][10];

        //horizontal ship
        if (coordinates[0] == coordinates[2]) {
            for (int i = coordinates[0] - 1; i <= coordinates[0] + 1; i++) {
                for (int j = coordinates[1] - 1; j <= coordinates[1] + numberOfCells; j++) {
                    try {
                        board[i][j] = SHIP_COMPONENT;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.print("");
                    }
                }
            }
            //vertical ship
        } else if (coordinates[1] == coordinates[3]) {
            for (int i = coordinates[0] - 1; i <= coordinates[0] + numberOfCells; i++) {
                for (int j = coordinates[1] - 1; j <= coordinates[1] + 1; j++) {
                    try {
                        board[i][j] = SHIP_COMPONENT;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.print("");
                    }
                }
            }
        }
        //checking that shipComponents overlap
        checkArraySurroundingBoard.setBoardData(board);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (resultingBoard.getBoardData()[i][j] == SHIP_COMPONENT
                        && checkArraySurroundingBoard.getBoardData()[i][j] == SHIP_COMPONENT) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isShipValid(int[] coordinates, int numberOfCells, ResultingBoard resultingBoard) {
        return isShipLocationCorrect(coordinates) && isShipSizeCorrect(coordinates, numberOfCells, resultingBoard)
                && isShipDistanceCorrect(coordinates, resultingBoard, numberOfCells);
    }

    public static boolean isShipHit(int[] coordinates, char[][] board, ResultingBoard resultingBoard) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (coordinates[0] == i && coordinates[1] == j && resultingBoard.getBoardData()[i][j] == SHIP_COMPONENT
                        || coordinates[0] == i && coordinates[1] == j && resultingBoard.getBoardData()[i][j] == HIT) {
                    board[i][j] = HIT;
                    resultingBoard.setBoardData(board);
                    return true;
                } else if (coordinates[0] == i && coordinates[1] == j && resultingBoard.getBoardData()[i][j] != SHIP_COMPONENT) {
                    board[i][j] = MISSED;
                    resultingBoard.setBoardData(board);
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isCoordinatesAscending(int[] coordinates) {
        return coordinates[0] <= coordinates[2] && coordinates[1] <= coordinates[3];
    }

    public static boolean isSingleShipSunk(int[] coordinates, ResultingBoard resultingBoard, ResultingBoard baseBoardOutcome) { //!THIS IS WHAT WE'RE WORKING ON AT THE MOMENT!

        int[] shipProw = {0, 0};
        int[] shipStern = {0, 0};

        // ooO
        SettingShipExtremes horizontalShipStern = () -> {
            for (int k = coordinates[1]; k <= 9; k++) {
                if (baseBoardOutcome.getBoardData()[coordinates[0]][k] == FOG) {
                    shipStern[1] = k - 1;
                    break;
                } else if (baseBoardOutcome.getBoardData()[coordinates[0]][k] == SHIP_COMPONENT && k == 9) {
                    shipStern[1] = k;
                    break;
                }
            }
            return shipStern[1];
        };

        // Ooo
        SettingShipExtremes horizontalShipProw = () -> {
            for (int k = coordinates[1]; k >= 0; k--) {
                if (baseBoardOutcome.getBoardData()[coordinates[0]][k] == FOG) {
                    shipProw[1] = k + 1;
                    break;
                } else if (baseBoardOutcome.getBoardData()[coordinates[0]][k] == SHIP_COMPONENT && k == 0) {
                    shipProw[1] = k;
                    break;
                }
            }
            return shipProw[1];
        };

        /*  o
        *   o
        *   O
        */
        SettingShipExtremes verticalShipStern = () -> {
            for (int k = coordinates[0]; k <= 9; k++) {
                if (baseBoardOutcome.getBoardData()[k][coordinates[1]] == FOG) {
                    shipStern[0] = k - 1;
                    break;
                } else if (baseBoardOutcome.getBoardData()[k][coordinates[1]] == SHIP_COMPONENT && k == 9) {
                    shipStern[0] = k;
                    break;
                }
            }
            return shipStern[0];
        };

        /*  O
         *  o
         *  o
         */
        SettingShipExtremes verticalShipProw = () -> {
            for (int k = coordinates[0]; k >= 0; k--) {
                if (baseBoardOutcome.getBoardData()[k][coordinates[1]] == FOG) {
                    shipProw[0] = k + 1;
                    break;
                } else if (baseBoardOutcome.getBoardData()[k][coordinates[1]] == SHIP_COMPONENT && k == 0) {
                    shipProw[0] = k;
                    break;
                }
            }
            return shipProw[0];
        };

        if (isShipComponentPlacedAtExtremes(coordinates)) { //uppermost and leftmost extremes
            try {
                if (baseBoardOutcome.getBoardData()[coordinates[0]][coordinates[1] + 1] == SHIP_COMPONENT) { // checking that ship is horizontal
                    for (int i = 0; i <= horizontalShipStern.apply(); i++) {
                        if (baseBoardOutcome.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT
                                && resultingBoard.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT) return false;
                    }
                } else { //otherwise it is vertical
                    for (int i = 0; i <= verticalShipStern.apply(); i++) {
                        if (baseBoardOutcome.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT
                                && resultingBoard.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT) return false;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                if (baseBoardOutcome.getBoardData()[coordinates[0]][coordinates[1] - 1] == SHIP_COMPONENT) {
                    for (int i = 0; i <= horizontalShipStern.apply(); i++) {
                        if (baseBoardOutcome.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT
                                && resultingBoard.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT) return false;
                    }
                } else { //otherwise it is vertical
                    for (int i = 0; i <= verticalShipStern.apply(); i++) {
                        if (baseBoardOutcome.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT
                                && resultingBoard.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT) return false;
                    }
                }
            }
        } else {    //NOT uppermost and leftmost extremes
            try {
                if (resultingBoard.getBoardData()[coordinates[0] - 1][coordinates[1]] == FOG        /* checking horizontal ships */
                        && resultingBoard.getBoardData()[coordinates[0] + 1][coordinates[1]] == FOG) {
                    for (int i = coordinates[1]; i >= horizontalShipProw.apply(); i--) {
                        if (resultingBoard.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT
                                && baseBoardOutcome.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT) {
                            return false;
                        }
                    }
                    for (int i = coordinates[1]; i <= horizontalShipStern.apply(); i++) {
                        if (resultingBoard.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT
                            && baseBoardOutcome.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT) {
                            return false;
                        }
                    }

                } else {    /* checking vertical ships*/
                    for (int i = coordinates[0]; i >= verticalShipProw.apply(); i--) {
                        if (resultingBoard.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT
                            && baseBoardOutcome.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT) {
                            return false;
                        }
                    }

                    for (int i = coordinates[0]; i <= verticalShipStern.apply(); i++) {
                        if (resultingBoard.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT
                                && baseBoardOutcome.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT) {
                            return false;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                if (resultingBoard.getBoardData()[coordinates[0] - 1][coordinates[1]] == FOG) {        /* checking horizontal ships */
                    for (int i = coordinates[1]; i >= horizontalShipProw.apply(); i--) {
                        if (resultingBoard.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT
                                && baseBoardOutcome.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT) {
                            return false;
                        }
                    }
                    for (int i = coordinates[1]; i <= horizontalShipStern.apply(); i++) {
                        if (resultingBoard.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT
                                && baseBoardOutcome.getBoardData()[coordinates[0]][i] == SHIP_COMPONENT) {
                            return false;
                        }
                    }

                } else {    /* checking vertical ships*/
                    for (int i = coordinates[0]; i >= verticalShipProw.apply(); i--) {
                        if (resultingBoard.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT
                                && baseBoardOutcome.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT) {
                            return false;
                        }
                    }

                    for (int i = coordinates[0]; i <= verticalShipStern.apply(); i++) {
                        if (resultingBoard.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT
                                && baseBoardOutcome.getBoardData()[i][coordinates[1]] == SHIP_COMPONENT) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    protected static boolean
    isShipComponentPlacedAtExtremes(int[] coordinates) {
        return coordinates[0] == 0 || coordinates[1] == 0;
    }

    public static boolean isEveryShipSunk(FoggedBoard foggedBoardOutcome) {

        int shipComponentsHitCounter = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (foggedBoardOutcome.getFoggedBoardData()[i][j] == HIT) {
                    shipComponentsHitCounter++;
                }
            }
        }

        if (shipComponentsHitCounter == 17) {
            System.out.println("You sank the last ship. You won. Congratulations!");
            return true;
        }
        return false;
    }
}