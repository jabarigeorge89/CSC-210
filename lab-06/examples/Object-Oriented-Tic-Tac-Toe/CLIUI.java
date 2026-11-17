
import java.util.Scanner;

/**
 * Provides the command-line interface for the Tic-Tac-Toe game.
 * Displays the board, accepts player moves, and shows game results.
 *
 * @author Jabari George
 * @version 1.0
 */
public class CLIUI implements TicTacToeUI {

    private TicTacToeGame game;
    private boolean vsAI;
    private Opponent ai;
    private TicTacToeGame.Mark aiAs;
    private final Scanner sc = new Scanner(System.in);

    /**
     * Starts the command-line Tic-Tac-Toe game.
     * Handles turns for human players and AI opponents.
     *
     * @param game the Tic-Tac-Toe game
     * @param vsAI true if playing against the computer
     * @param ai the computer opponent
     * @param aiPlaysAs the mark assigned to the computer
     */
    @Override
    public void start(TicTacToeGame game, boolean vsAI, Opponent ai,
                      TicTacToeGame.Mark aiPlaysAs) {
        this.game = game;
        this.vsAI = vsAI;
        this.ai = ai;
        this.aiAs = aiPlaysAs;

        game.addListener(this);
        draw(game.getBoard());

        while (!game.isGameOver()) {
            if (vsAI && game.getCurrent() == aiAs) {
                int move = ai.chooseMove(game, aiAs);
                game.play(move);
            } else {
                int move = promptMove();
                if (!game.play(move)) {
                    System.out.println("Illegal move. Try again.");
                }
            }
        }
        // Game-over message handled by onGameOver.
    }

    /**
     * Asks the player to enter a valid board position.
     *
     * @return the selected position as an index from 0 to 8
     */
    private int promptMove() {
        while (true) {
            System.out.print("Player " + game.getCurrent() + " move (1-9): ");
            String s = sc.nextLine().trim();

            try {
                int v = Integer.parseInt(s) - 1;
                if (v >= 0 && v <= 8) return v;
            } catch (NumberFormatException ignored) {}

            System.out.println("Enter a number 1..9.");
        }
    }

    /**
     * Displays the current Tic-Tac-Toe board.
     *
     * @param b the array containing the board marks
     */
    private void draw(TicTacToeGame.Mark[] b) {
        System.out.println();

        for (int r = 0; r < 3; r++) {
            int i = r * 3;
            System.out.printf(" %s | %s | %s %n",
                    sym(b[i]), sym(b[i+1]), sym(b[i+2]));

            if (r < 2) System.out.println("---+---+---");
        }

        System.out.println();
    }

    /**
     * Converts a board mark into a display character.
     *
     * @param m the mark to convert
     * @return X, O, or a blank space
     */
    private String sym(TicTacToeGame.Mark m) {
        if (m == TicTacToeGame.Mark.X) return "X";
        if (m == TicTacToeGame.Mark.O) return "O";
        return " ";
    }

    /**
     * Updates the displayed board after a move.
     *
     * @param index the position where the move occurred
     * @param who the player who made the move
     */
    @Override
    public void onMove(int index, TicTacToeGame.Mark who) {
        draw(game.getBoard());
    }

    /**
     * Displays the result when the game ends.
     *
     * @param winner the winning player, or EMPTY for a draw
     */
    @Override
    public void onGameOver(TicTacToeGame.Mark winner) {
        if (winner == TicTacToeGame.Mark.EMPTY) {
            System.out.println("Draw!");
        } else {
            System.out.println("Winner: " + winner);
        }
    }

    /**
     * Displays a message when the game is reset.
     *
     * @param starting the player who starts the new game
     */
    @Override
    public void onReset(TicTacToeGame.Mark starting) {
        System.out.println("=== New Game. " + starting + " starts. ===");
    }
}
