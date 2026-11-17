package userinterface;

import gamelogic.*;

/**
 * Defines the user interface for the Tic-Tac-Toe game.
 * This interface extends GameListener so that the
 * interface can respond to game events.
 *
 * @author Jabari George
 * @version 1.0
 */
public interface TicTacToeUI extends GameListener {

    /**
     * Starts the user interface for a Tic-Tac-Toe game.
     * Implementations should register themselves as
     * listeners to receive game updates.
     *
     * @param game the Tic-Tac-Toe game to start
     * @param vsAI true if playing against the computer
     * @param ai the computer opponent
     * @param aiPlaysAs the mark assigned to the computer
     */
    void start(TicTacToeGame game, boolean vsAI,
               Opponent ai, TicTacToeGame.Mark aiPlaysAs);
}
