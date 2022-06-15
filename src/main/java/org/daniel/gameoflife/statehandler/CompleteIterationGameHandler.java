package org.daniel.gameoflife.statehandler;

import org.daniel.gameoflife.board.GameBoard;
import org.daniel.gameoflife.seed.Seed;

import java.awt.Point;
import java.util.List;

public class CompleteIterationGameHandler extends GameStateHandler {

    public CompleteIterationGameHandler(GameBoard gameBoard) {
        super(gameBoard);
    }

    public void tick() {
        var activeBoard = getActiveBoard();
        var inactiveBoard = getInactiveBoard();
        var currCoordinates = new Point();

        for (int y=0; y<activeBoard.getYDimension(); y++) {
            for (int x=0; x<activeBoard.getXDimension(); x++) {
                currCoordinates.setLocation(x, y);
                var currCell = activeBoard.getCell(currCoordinates);

                if (currCell.isEmpty()) {
                    throw new IllegalStateException(String.format("Invalid cell coordinate: %s. Aborting tick!", currCoordinates));
                }

                var neighbours = activeBoard.getNeighbours(currCoordinates);

                if (shouldCellDie(currCell.get(), neighbours)) {
                    inactiveBoard.killCell(currCoordinates);
                } else {
                    inactiveBoard.reviveCell(currCoordinates);
                }
            }
        }

        switchActiveBoard();
    }

    private static boolean shouldCellDie(boolean isCellAlive, List<Boolean> neighbours) {
        var liveNeighbourCount = neighbours.stream().filter(isAlive -> isAlive).count();

        if (isCellAlive) {
            return liveNeighbourCount < 2 || liveNeighbourCount > 3;
        }

        return liveNeighbourCount != 3;
    }
}
