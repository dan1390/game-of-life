package org.daniel.gameoflife.statehandler;

import org.daniel.gameoflife.board.GameBoard;

import static org.daniel.gameoflife.statehandler.GameStateHandler.ActiveBoard.BOARD_A;
import static org.daniel.gameoflife.statehandler.GameStateHandler.ActiveBoard.BOARD_B;

public abstract class GameStateHandler {

    protected enum ActiveBoard {
        BOARD_A, BOARD_B
    }

    private final GameBoard boardA;
    private final GameBoard boardB;
    private ActiveBoard activeBoard;

    public GameStateHandler(GameBoard gameBoard) {
        this.boardA = gameBoard;
        this.boardB = gameBoard.clone();
        this.activeBoard = BOARD_A;
    }

    abstract public void tick();

    public GameBoard getGameBoard() {
        return getActiveBoard();
    }

    protected GameBoard getActiveBoard() {
        return (activeBoard == BOARD_A) ? boardA : boardB;
    }

    protected GameBoard getInactiveBoard() {
        return (activeBoard == BOARD_A) ? boardB : boardA;
    }

    protected void switchActiveBoard() {
        activeBoard = (activeBoard == BOARD_A) ? BOARD_B : BOARD_A;
    }
}
