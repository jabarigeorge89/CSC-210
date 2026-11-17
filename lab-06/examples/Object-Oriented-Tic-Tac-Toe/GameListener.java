
/**
 * Defines the events that listeners can receive
 * during a Tic-Tac-Toe game.
 *
 * @author Jabari George
 * @version 1.0
 */
public interface GameListener {

    /**
     * Called after a player makes a successful move.
     *
     * @param index the board position where the move occurred
     * @param who the player who made the move
     */
    void onMove(int index, TicTacToeGame.Mark who);

    /**
     * Called when the game ends with a winner or a draw.
     *
     * @param winner the winning player, or EMPTY for a draw
     */
    void onGameOver(TicTacToeGame.Mark winner);

    /**
     * Called when the game board is reset.
     *
     * @param starting the player who starts the new game
     */
    void onReset(TicTacToeGame.Mark starting);
}
