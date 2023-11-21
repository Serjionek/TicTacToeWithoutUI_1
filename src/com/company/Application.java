package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    int[][] field = new int[3][3];
    int turn;
    Point cursorCoordinates = new Point(0, 0);

    public void run() throws IOException {
        field[0][0] = -1;
        printField();
        step();
    }

    public void cursorPosition() {
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field.length; x++) {
                if (field[x][y] == -1) {
                    cursorCoordinates.setLocation(x, y);
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
                } else if (field[x][y] == -1) {
                    if (turn == 0) {
                        System.out.print("v|");
                    }
                    if (turn == 1){
                        System.out.print("o|");
                    }
                } else {
                    System.out.print(field[x][y] + "|");
                }
            }
            System.out.println();
        }
        System.out.println("--------");
    }

    public void step() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String s = reader.readLine();
            if (s.equals("q")) {
                return;
            }
            cursorPosition();
            int x = cursorCoordinates.x;
            int y = cursorCoordinates.y;
            field[x][y] = 0;
            switch (s) {
                case "a": {
                    x -= 1;
                    if (x < 0) {
                        x = 2;
                    }
                    break;
                }
                case "d": {
                    x += 1;
                    if (x > 2) {
                        x = 0;
                    }
                    break;
                }
                case "s": {
                    y += 1;
                    if (y > 2) {
                        y = 0;
                    }
                    break;
                }
                case "w": {
                    y -= 1;
                    if (y < 0) {
                        y = 2;
                    }
                    break;
                }
                case "c": {
                    addElement(x, y);
                }
            }
            cursorCoordinates.setLocation(x, y);
            field[x][y] = -1;
            printField();
        }
    }

    public void addElement(int x, int y) {
        field[x][y] = turn;
    }
}
