package org.daniel.gameoflife.seed.oscillator;

import org.daniel.gameoflife.seed.Seed;

import java.awt.*;
import java.util.List;

public class ToadSeed implements Seed {

    @Override
    public int getXDimension() {
        return 6;
    }

    @Override
    public int getYDimension() {
        return 6;
    }

    @Override
    public List<Point> getCoordinatesForLiveCells() {
        return List.of(new Point(1, 2),
                       new Point(2, 2),
                       new Point(3, 2),
                       new Point(2, 3),
                       new Point(3, 3),
                       new Point(4, 3));
    }
}
