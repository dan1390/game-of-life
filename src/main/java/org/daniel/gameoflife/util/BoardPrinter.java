package org.daniel.gameoflife.util;

import org.daniel.gameoflife.board.GameBoard;

import java.awt.Point;

public class BoardPrinter {

    private static final char ALIVE = 'O';
    private static final char DEAD = '-';
    private static final char ERROR = 'E';

    public static void drawBoard(GameBoard board) {
        var currCoordinates = new Point();
        System.out.println();
        for (int x=0; x<board.getXDimension(); x++) {
            for (int y=0; y<board.getXDimension(); y++) {
                currCoordinates.setLocation(x, y);
                var currCell = board.getCell(currCoordinates);

                if (currCell.isEmpty()) {
                    System.out.print(ERROR + " ");
                    continue;
                }

                var marker = currCell.get() ? ALIVE : DEAD;
                System.out.print(marker + " ");
            }
            System.out.println();
        }
    }
}
