package org.daniel.gameoflife.seed.oscillator;

import org.daniel.gameoflife.seed.Seed;

import java.awt.Point;
import java.util.List;

public class BlinkerSeed implements Seed {

    @Override
    public int getXDimension() {
        return 5;
    }

    @Override
    public int getYDimension() {
        return 5;
    }

    @Override
    public List<Point> getCoordinatesForLiveCells() {
        return List.of(new Point(1, 2),
                       new Point(2, 2),
                       new Point(3, 2));
    }
}
