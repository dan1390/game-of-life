package org.daniel.gameoflife;

import org.daniel.gameoflife.board.FixedDimensionGameBoard;
import org.daniel.gameoflife.seed.oscillator.BlinkerSeed;
import org.daniel.gameoflife.statehandler.GameStateHandler;
import org.daniel.gameoflife.statehandler.CompleteIterationGameHandler;
import org.daniel.gameoflife.util.BoardPrinter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) {
        var gameBoard = new FixedDimensionGameBoard(new BlinkerSeed());
        var gameHandler = new CompleteIterationGameHandler(gameBoard);

        runGame(gameHandler);
    }

    private static void runGame(GameStateHandler gameStateHandler) {
        Runnable gameRunnable = () -> {
            BoardPrinter.drawBoard(gameStateHandler.getGameBoard());
            try {
                gameStateHandler.tick();
            } catch (IllegalStateException e) {
                System.out.println("Failed to perform tick! Reason: " + e.getMessage());
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(gameRunnable, 0, 1, TimeUnit.SECONDS);
    }
}
