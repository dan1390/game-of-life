package org.daniel.gameoflife.board;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

public interface GameBoard extends Cloneable {

    int getYDimension();

    int getXDimension();

    Optional<Boolean> getCell(Point point);

    List<Boolean> getNeighbours(Point cellCoordinates);

    boolean killCell(Point point);

    boolean reviveCell(Point point);

    GameBoard clone();
}
