package org.daniel.gameoflife.board;


import org.daniel.gameoflife.seed.still.BlockSeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FixedDimensionGameBoardTest {

    private final FixedDimensionGameBoard gameBoard = new FixedDimensionGameBoard(new BlockSeed());

    @Test
    public void shouldReturnCellIfInRange() {
        // Given
        var pointInRange = new Point(1, 1);

        // When
        var cell = gameBoard.getCell(pointInRange);

        // Then
        assertTrue(cell.isPresent());
        assertTrue(cell.get());
    }

    @Test
    public void shouldReturnCellEmptyIfOutOfRange() {
        // Given
        var pointOutOfRange = new Point(4, 4);

        // When
        var cell = gameBoard.getCell(pointOutOfRange);

        // Then
        assertTrue(cell.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"1, 1, 8, 3",
                "0, 0, 3, 1",
                "0, 2, 5, 2"})
    public void shouldReturnNeighbours(int xCoordinate,
                                       int yCoordinate,
                                       int expectedNeighbourCount,
                                       int expectedLiveCount) {
        // Given
        var point = new Point(xCoordinate, yCoordinate);

        // When
        var neighbours = gameBoard.getNeighbours(point);

        // Then
        assertEquals(expectedNeighbourCount, neighbours.size());
        assertEquals(expectedLiveCount, neighbours.stream().filter(isAlive -> isAlive).count());
        assertEquals(expectedNeighbourCount - expectedLiveCount, neighbours.stream().filter(isAlive -> !isAlive).count());
    }

    @Test
    public void shouldReturnNoNeighboursIfCellIsOutOfRange() {
        // Given
        var pointOutOfRange = new Point(-1, -1);

        // When
        var neighbours = gameBoard.getNeighbours(pointOutOfRange);

        // Then
        assertEquals(0, neighbours.size());
    }

    @Test
    public void shouldKillCellInRange() {
        // Given
        var pointInRange = new Point(1, 1);

        // When
        var cellBeforeKill = gameBoard.getCell(pointInRange);
        var killSuccessful = gameBoard.killCell(pointInRange);
        var cellAfterKill = gameBoard.getCell(pointInRange);

        // Then
        assertTrue(killSuccessful);
        assertTrue(cellBeforeKill.isPresent());
        assertTrue(cellBeforeKill.get());
        assertTrue(cellAfterKill.isPresent());
        assertFalse(cellAfterKill.get());
    }

    @Test
    public void shouldFailToKillCellOutOfRange() {
        // Given
        var pointInRange = new Point(-1, -1);

        // When
        var killSuccessful = gameBoard.killCell(pointInRange);

        // Then
        assertFalse(killSuccessful);
    }

    @Test
    public void shouldReviveCellInRange() {
        // Given
        var pointInRange = new Point(0, 0);

        // When
        var cellBeforeRevival = gameBoard.getCell(pointInRange);
        var reviveSuccessful = gameBoard.reviveCell(pointInRange);
        var cellAfterRevival = gameBoard.getCell(pointInRange);

        // Then
        assertTrue(reviveSuccessful);
        assertTrue(cellBeforeRevival.isPresent());
        assertFalse(cellBeforeRevival.get());
        assertTrue(cellAfterRevival.isPresent());
        assertTrue(cellAfterRevival.get());
    }

    @Test
    public void shouldFailToReviveCellOutOfRange() {
        // Given
        var pointInRange = new Point(-1, -1);

        // When
        var reviveSuccessful = gameBoard.reviveCell(pointInRange);

        // Then
        assertFalse(reviveSuccessful);
    }
}