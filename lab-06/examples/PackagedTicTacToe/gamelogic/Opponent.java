package gamelogic;
/**
 * Defines how an opponent chooses a move
 * during a Tic-Tac-Toe game.
 *
 * This interface can be used by an AI opponent
 * to select a position on the game board.
 *
 * @author Jabari George
 * @version 1.0
 */
public interface Opponent {

    /**
     * Chooses a move for the opponent.
     * This method is called during the opponent's turn
     * while the game is still active.
     *
     * @param game the current Tic-Tac-Toe game
     * @param me the mark assigned to the opponent
     * @return the selected board position from 0 to 8
     */
    int chooseMove(TicTacToeGame game, TicTacToeGame.Mark me);
}
