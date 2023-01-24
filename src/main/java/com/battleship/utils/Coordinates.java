package com.battleship.utils;

import java.util.HashMap;
import java.util.Scanner;

public class Coordinates {

    public static int[] enterCoordinates(Scanner scanner) {

        String[] coordinateOne = scanner.next().toUpperCase().split("");
        String[] coordinateTwo = scanner.next().toUpperCase().split("");
        int[] coordinates = new int[4];

        //Assigning column letter to the corresponding index number, e.g A -> 0, B -> 1 etc.
        HashMap<String, Integer> letterToIntMapping = new HashMap<>();
        char letter = 'A';
        for (int i = 0; i < 10; i++) {
            letterToIntMapping.put(String.valueOf(letter), i);
            letter++;
        }

        coordinates[0] = letterToIntMapping.get(coordinateOne[0]);
        // the if-statement here is to concatenate "1" and "0" and make it an integer, i.e. 10
        if (coordinateOne.length == 3) {
            coordinates[1] = Integer.parseInt(coordinateOne[1].concat(coordinateOne[2])) - 1;
        } else coordinates[1] = Integer.parseInt(coordinateOne[1]) - 1;

        // the if-statement here is to concatenate "1" and "0" and make it an integer, i.e. 10
        coordinates[2] = letterToIntMapping.get(coordinateTwo[0]);
        if (coordinateTwo.length == 3) {
            coordinates[3] = Integer.parseInt(coordinateTwo[1].concat(coordinateTwo[2])) - 1;
        } else coordinates[3] = Integer.parseInt(coordinateTwo[1]) - 1;

        //reversing the order of descending coordinates
        if (!ValidityChecking.isCoordinatesAscending(coordinates)) {
            return orderCoordinates(coordinates);
        }
        return coordinates;
    }

    public static int[] orderCoordinates(int[] coordinates) {
        int[] temp = new int[1];

        temp[0] = coordinates[0];
        coordinates[0] = coordinates[2];
        coordinates[2] = temp[0];

        temp[0] = coordinates[1];
        coordinates[1] = coordinates[3];
        coordinates[3] = temp[0];

        return coordinates;
    }

    public static int[] enterCoordinatesForShooting(Scanner scanner) {

        String[] coordinatesForShooting = scanner.next().toUpperCase().split("");
        int[] coordinates = new int[2];

        HashMap<String, Integer> letterToIntMapping = new HashMap<>();
        char letter = 'A';
        for (int i = 0; i < 10; i++) {
            letterToIntMapping.put(String.valueOf(letter), i);
            letter++;
        }

        //checking the first "letter" coordinate that is falls within the A - J range
        try {
            coordinates[0] = letterToIntMapping.get(coordinatesForShooting[0]);
        } catch (NullPointerException e) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return enterCoordinatesForShooting(scanner);
        }

        //checking the "number" coordinate that it does not exceed 10
        if (coordinatesForShooting.length >= 3 && Integer.parseInt(coordinatesForShooting[1]) >= 1
                && Integer.parseInt(coordinatesForShooting[2]) >= 1) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return enterCoordinatesForShooting(scanner);
        }
        //the if-statement here is to concatenate "1" and "0" and make it an integer, i.e. 10
        if (coordinatesForShooting.length == 3 && Integer.parseInt(coordinatesForShooting[1]) >= 1
                && Integer.parseInt(coordinatesForShooting[2]) == 0) {
            coordinates[1] = Integer.parseInt(coordinatesForShooting[1].concat(coordinatesForShooting[2])) - 1;
        } else coordinates[1] = Integer.parseInt(coordinatesForShooting[1]) - 1;

        return coordinates;
    }
}
