package org.daniel.gameoflife.board;

import org.daniel.gameoflife.seed.Seed;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FixedDimensionGameBoard implements GameBoard {

    private final boolean[][] board;

    private final int yDimension;
    private final int xDimension;

    private final Seed seed;

    public FixedDimensionGameBoard(Seed seed) {
        this.yDimension = seed.getYDimension();
        this.xDimension = seed.getXDimension();
        this.board = new boolean[xDimension][yDimension];
        this.seed = seed;

        seed.getCoordinatesForLiveCells().forEach(this::reviveCell);
    }

    public FixedDimensionGameBoard(FixedDimensionGameBoard gameBoard) {
        this.yDimension = gameBoard.getYDimension();
        this.xDimension = gameBoard.getXDimension();
        this.board = new boolean[xDimension][yDimension];
        this.seed = gameBoard.seed;

        this.seed.getCoordinatesForLiveCells().forEach(this::reviveCell);
    }

    @Override
    public int getYDimension() {
        return yDimension;
    }

    @Override
    public int getXDimension() {
        return xDimension;
    }

    @Override
    public Optional<Boolean> getCell(Point point) {
        return isPointInRange(point) ? Optional.of(board[point.x][point.y])
                                     : Optional.empty();
    }

    @Override
    public List<Boolean> getNeighbours(Point cellCoordinates) {
        if (!isPointInRange(cellCoordinates)) {
            return Collections.emptyList();
        }

        var neighbourCoordinates = new Point();
        var neighbours = new ArrayList<Boolean>();

        for (int y=cellCoordinates.y-1; y<cellCoordinates.y+2; y++) {
            for (int x=cellCoordinates.x-1; x<cellCoordinates.x+2; x++) {
                neighbourCoordinates.setLocation(x, y);

                var neighbour = getCell(neighbourCoordinates);
                if (neighbour.isPresent() && !neighbourCoordinates.equals(cellCoordinates)) {
                    neighbours.add(neighbour.get());
                }
            }
        }

        return neighbours;
    }

    @Override
    public boolean killCell(Point point) {
        if (isPointInRange(point)) {
            board[point.x][point.y] = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean reviveCell(Point point) {
        if (isPointInRange(point)) {
            board[point.x][point.y] = true;
            return true;
        }

        return false;
    }

    @Override
    public GameBoard clone() {
        return new FixedDimensionGameBoard(this);
    }

    private boolean isPointInRange(Point point) {
        return point.y >= 0 &&
               point.y < yDimension &&
               point.x >= 0 &&
               point.x < xDimension;
    }
}
