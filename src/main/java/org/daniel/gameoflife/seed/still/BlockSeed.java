package org.daniel.gameoflife.seed.still;

import org.daniel.gameoflife.seed.Seed;

import java.awt.*;
import java.util.List;

public class BlockSeed implements Seed {

    @Override
    public int getXDimension() {
        return 4;
    }

    @Override
    public int getYDimension() {
        return 4;
    }

    @Override
    public List<Point> getCoordinatesForLiveCells() {
        return List.of(new Point(1, 1),
                       new Point(2, 1),
                       new Point(1, 2),
                       new Point(2, 2));
    }
}
