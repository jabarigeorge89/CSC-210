
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Manages the rules and gameplay of Tic-Tac-Toe.
 * Keeps track of the board, player turns, legal moves,
 * winners, and game events.
 *
 * @author Jabari George
 * @version 1.0
 */
public class TicTacToeGame {

    /**
     * Represents the possible marks on the game board.
     */
    public enum Mark {
        /** Player X. */
        X,
        /** Player O. */
        O,
        /** An empty position. */
        EMPTY
    }

    private final Mark[] board = new Mark[9];
    private Mark current = Mark.X;
    private boolean gameOver = false;
    private Mark winner = Mark.EMPTY;
    private final List<GameListener> listeners = new ArrayList<>();

    /**
     * Creates a new Tic-Tac-Toe game and initializes the board.
     */
    public TicTacToeGame() {
        reset();
    }

    /**
     * Resets the board and starts a new game with player X.
     */
    public void reset() {
        Arrays.fill(board, Mark.EMPTY);
        current = Mark.X;
        winner = Mark.EMPTY;
        gameOver = false;
        fireReset(current);
    }

    /**
     * Returns a copy of the current game board.
     *
     * @return an array containing the nine board positions
     */
    public Mark[] getBoard() {
        return board.clone();
    }

    /**
     * Gets the player whose turn it is.
     *
     * @return the current player's mark
     */
    public Mark getCurrent() {
        return current;
    }

    /**
     * Checks whether the game has ended.
     *
     * @return true if the game is over, otherwise false
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the winner of the game.
     *
     * @return X or O if there is a winner, or EMPTY otherwise
     */
    public Mark getWinner() {
        return winner;
    }

    /**
     * Attempts to place the current player's mark on the board.
     * Updates the game status and checks for a winner or draw.
     *
     * @param index the board position from 0 to 8
     * @return true if the move is accepted, otherwise false
     */
    public boolean play(int index) {
        if (gameOver || index < 0 || index > 8 || board[index] != Mark.EMPTY) return false;

        board[index] = current;
        fireMove(index, current);

        Mark w = computeWinner();
        if (w != Mark.EMPTY) {
            winner = w;
            gameOver = true;
            fireGameOver(winner);
        } else if (isFull()) {
            gameOver = true;
            winner = Mark.EMPTY;
            fireGameOver(Mark.EMPTY);
        } else {
            current = (current == Mark.X ? Mark.O : Mark.X);
        }
        return true;
    }

    /**
     * Finds all available positions on the board.
     *
     * @return a list of legal move indexes
     */
    public List<Integer> legalMoves() {
        List<Integer> moves = new ArrayList<>();
        if (gameOver) return moves;
        for (int i = 0; i < 9; i++) if (board[i] == Mark.EMPTY) moves.add(i);
        return moves;
    }

    /**
     * Registers a listener to receive game events.
     *
     * @param l the listener to add
     * @throws NullPointerException if the listener is null
     */
    public void addListener(GameListener l) {
        listeners.add(Objects.requireNonNull(l));
    }

    /**
     * Removes a registered game listener.
     *
     * @param l the listener to remove
     */
    public void removeListener(GameListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies listeners when a player makes a move.
     *
     * @param idx the position of the move
     * @param who the player who made the move
     */
    private void fireMove(int idx, Mark who) {
        for (GameListener l : listeners) l.onMove(idx, who);
    }

    /**
     * Notifies listeners when the game ends.
     *
     * @param winner the winning mark, or EMPTY for a draw
     */
    private void fireGameOver(Mark winner) {
        for (GameListener l : listeners) l.onGameOver(winner);
    }

    /**
     * Notifies listeners when the game is reset.
     *
     * @param starting the player who starts the new game
     */
    private void fireReset(Mark starting) {
        for (GameListener l : listeners) l.onReset(starting);
    }

    /**
     * Checks whether every board position is occupied.
     *
     * @return true if the board is full, otherwise false
     */
    private boolean isFull() {
        for (Mark m : board) if (m == Mark.EMPTY) return false;
        return true;
    }

    private static final int[][] LINES = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    /**
     * Checks the board for three matching marks in a row.
     *
     * @return the winning mark, or EMPTY if no winner exists
     */
    private Mark computeWinner() {
        for (int[] line : LINES) {
            Mark a = board[line[0]], b = board[line[1]], c = board[line[2]];
            if (a != Mark.EMPTY && a == b && b == c) return a;
        }
        return Mark.EMPTY;
    }
}
