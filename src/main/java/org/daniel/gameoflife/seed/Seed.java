package org.daniel.gameoflife.seed;

import java.awt.Point;
import java.util.List;

public interface Seed {

    int getXDimension();

    int getYDimension();

    List<Point> getCoordinatesForLiveCells();
}
