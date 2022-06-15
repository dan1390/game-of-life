package org.daniel.gameoflife.statehandler;

import org.daniel.gameoflife.board.FixedDimensionGameBoard;
import org.daniel.gameoflife.board.GameBoard;
import org.daniel.gameoflife.seed.Seed;
import org.daniel.gameoflife.seed.oscillator.BlinkerSeed;
import org.daniel.gameoflife.seed.oscillator.ToadSeed;
import org.daniel.gameoflife.seed.still.BlockSeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Point;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompleteIterationGameHandlerTest {

    @Test
    public void shouldNotChangeStateForStillSeedBoard() {
        // Given
        var board = new FixedDimensionGameBoard(new BlockSeed());
        var handler = new CompleteIterationGameHandler(board);

        // When
        var originalState = handler.getGameBoard();
        handler.tick();
        var updatedState = handler.getGameBoard();

        // Then
        assertTrue(isBoardStateSame(originalState, updatedState));
    }

    @ParameterizedTest
    @MethodSource("provideOscillatorSeeds")
    public void shouldAlternateStateForOscillatorSeedBoard(Seed seed) {
        // Given
        var board = new FixedDimensionGameBoard(seed);
        var handler = new CompleteIterationGameHandler(board);

        // When
        var originalState = handler.getGameBoard();
        handler.tick();
        var firstUpdate = handler.getGameBoard();
        handler.tick();
        var secondUpdate = handler.getGameBoard();
        handler.tick();
        var thirdUpdate = handler.getGameBoard();

        // Then
        assertTrue(isBoardStateSame(originalState, secondUpdate));
        assertTrue(isBoardStateSame(firstUpdate, thirdUpdate));
    }

    @Test
    public void shouldThrowExceptionIfInvalidState(@Mock GameBoard invalidBoard) {
        // Given
        var handler = new CompleteIterationGameHandler(invalidBoard);
        when(invalidBoard.getYDimension()).thenReturn(1);
        when(invalidBoard.getXDimension()).thenReturn(1);
        when(invalidBoard.getCell(any())).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalStateException.class, handler::tick);
    }

    public static Stream<Arguments> provideOscillatorSeeds() {
        return Stream.of(Arguments.of(new BlinkerSeed()),
                         Arguments.of(new ToadSeed()));
    }

    private static boolean isBoardStateSame(GameBoard firstBoard, GameBoard secondBoard) {
        var currCoordinate = new Point();
        for (int y=0; y< firstBoard.getYDimension(); y++) {
            for (int x=0; x<firstBoard.getXDimension(); x++) {
                currCoordinate.setLocation(x, y);

                if (firstBoard.getCell(currCoordinate).get() != secondBoard.getCell(currCoordinate).get()) {
                    return false;
                }
            }
        }

        return true;
    }

}