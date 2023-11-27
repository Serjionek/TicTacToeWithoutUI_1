package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    int[][] field = new int[3][3];
    int[][] supportArray = new int[8][3];
    boolean turn;
    boolean newElementHasBeenAdded;
    Point cursorCoordinates = new Point(0, 0);
    private boolean isGameOver;

    public void run() throws IOException {
        cursorPosition();
        printField();
        step();
    }

    public void cursorPosition() {
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field.length; x++) {
                if (field[x][y] == 0) {
                    cursorCoordinates.setLocation(x, y);
                    if (turn) {
                        field[x][y] = -2;
                    } else {
                        field[x][y] = -1;
                    }
                    return;
                }
            }
        }
    }

    public void printField() {
        for (int y = 0; y < field.length; y++) {
            System.out.print("|");
            for (int x = 0; x < field.length; x++) {
                if (field[x][y] == 0) {
                    System.out.print(" |");
                }
                if (field[x][y] == -1) {
                    System.out.print("v|");
                }
                if (field[x][y] == -2) {
                    System.out.print("o|");
                }
                if (field[x][y] == 1) {
                    System.out.print("X|");
                }
                if (field[x][y] == 2) {
                    System.out.print("O|");
                }
            }
            System.out.println();
        }
        System.out.println("--------");
    }

    public void step() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = reader.readLine();
            if (input.equals("q")) {
                return;
            }

            int x = cursorCoordinates.x;
            int y = cursorCoordinates.y;
            field[x][y] = 0;

            switch (input) {
                case "a": {
                    x = reduceCoordinate(x);
                    for (int i = 0; i < 2; i++) {
                        if (field[x][y] == 1 || field[x][y] == 2) {
                            x = reduceCoordinate(x);
                        }
                    }
                    break;
                }
                case "d": {
                    x = increaseCoorinate(x);
                    for (int i = 0; i < 2; i++) {
                        if (field[x][y] == 1 || field[x][y] == 2) {
                            x = increaseCoorinate(x);
                        }
                    }
                    break;
                }
                case "s": {
                    y = increaseCoorinate(y);
                    for (int i = 0; i < 2; i++) {
                        if (field[x][y] == 1 || field[x][y] == 2) {
                            y = increaseCoorinate(y);
                        }
                    }
                    break;
                }
                case "w": {
                    y = reduceCoordinate(y);
                    for (int i = 0; i < 2; i++) {
                        if (field[x][y] == 1 || field[x][y] == 2) {
                            y = reduceCoordinate(y);
                        }
                    }
                    break;
                }
                case "c": {
                    addElement(x, y);
                    break;
                }
            }
            if (newElementHasBeenAdded) {
                cursorPosition();
                newElementHasBeenAdded = false;
            } else {
                cursorCoordinates.setLocation(x, y);
                if (turn) {
                    field[x][y] = -2;
                } else {
                    field[x][y] = -1;
                }
            }
            printField();
        }
    }

    public void addElement(int x, int y) {
        if (turn) {
            field[x][y] = 2;
            addElementToSupportArray(x, y, 2);
            turn = false;
        } else {
            field[x][y] = 1;
            addElementToSupportArray(x, y, 1);
            turn = true;
        }
        newElementHasBeenAdded = true;
    }

    public int increaseCoorinate(int coordinate) {
        coordinate += 1;
        if (coordinate > 2) {
            coordinate = 0;
        }
        return coordinate;
    }

    public int reduceCoordinate(int coordinate) {
        coordinate -= 1;
        if (coordinate < 0) {
            coordinate = 2;
        }
        return coordinate;
    }

    public void addElementToSupportArray(int x, int y, int value) {
        if (x == 0 && y == 0) {
            supportArray[0][0] = value;
            supportArray[3][0] = value;
            supportArray[6][0] = value;
        }
        if (x == 0 && y == 1) {
            supportArray[0][1] = value;
            supportArray[4][0] = value;
        }
        if (x == 0 && y == 2) {
            supportArray[0][2] = value;
            supportArray[5][0] = value;
            supportArray[7][0] = value;
        }
        if (x == 1 && y == 0) {
            supportArray[1][0] = value;
            supportArray[3][1] = value;
        }
        if (x == 1 && y == 1) {
            supportArray[1][1] = value;
            supportArray[4][1] = value;
            supportArray[6][1] = value;
            supportArray[7][1] = value;
        }
        if (x == 1 && y == 2) {
            supportArray[1][2] = value;
            supportArray[5][1] = value;
        }
        if (x == 2 && y == 0) {
            supportArray[2][0] = value;
            supportArray[3][2] = value;
            supportArray[7][2] = value;
        }
        if (x == 2 && y == 1) {
            supportArray[2][1] = value;
            supportArray[4][2] = value;
        }
        if (x == 2 && y == 2) {
            supportArray[2][2] = value;
            supportArray[5][2] = value;
            supportArray[6][2] = value;
        }
    }

    public void printSupportArray() {
        for (int x = 0; x < supportArray.length; x++) {
            for (int y = 0; y < supportArray[x].length; y++) {
                System.out.print(supportArray[x][y] + " ");
            }
            System.out.println();
        }
    }

    public String winCheck() {
        String winner = null;
        if (nobodyWins()) {
            isGameOver = true;
            return "Никто не сражался лучше другого";
        }
        for (int x = 0; x < supportArray.length; x++) {
            if (supportArray[x][0] == supportArray[x][1] && supportArray[x][1] == supportArray[x][2] && supportArray[x][0] != 0) {
                isGameOver = true;
                if (supportArray[x][0] == 1) {
                    return "Выиграл храбрец, сражавшийся крестиками.";
                } else return "Выиграл храбрец, сражавшийся ноликами.";
            }
        }
        return winner;
    }

    public boolean nobodyWins() {
        boolean nobody = false;
        for (int x = 0; x < supportArray.length; x++) {
            boolean cross = false;
            boolean zero = false;
            for (int y = 0; y < supportArray[x].length; y++) {
                if (!cross) {
                    if (supportArray[x][y] == 1) {
                        cross = true;
                    }
                }
                if (!zero) {
                    if (supportArray[x][y] == 2) {
                        zero = true;
                    }
                }
            }
            if (cross && zero) {
                nobody = true;
            } else return false;
        }
        return nobody;
    }
}
