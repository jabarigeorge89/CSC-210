
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Provides a graphical interface for the Tic-Tac-Toe game.
 * Uses Java Swing to display the board, handle player moves,
 * and show game results.
 *
 * @author Jabari George
 * @version 1.0
 */
public class SwingUI implements TicTacToeUI {

    private TicTacToeGame game;
    private boolean vsAI;
    private Opponent ai;
    private TicTacToeGame.Mark aiAs;

    private final JButton[] cells = new JButton[9];
    private final JLabel status = new JLabel(" ");
    private final JFrame frame = new JFrame("Tic-Tac-Toe");

    /**
     * Starts the graphical Tic-Tac-Toe interface.
     * Initializes the game window and handles AI settings.
     *
     * @param game the Tic-Tac-Toe game
     * @param vsAI true if playing against the computer
     * @param ai the computer opponent
     * @param aiPlaysAs the mark assigned to the computer
     */
    @Override
    public void start(TicTacToeGame game, boolean vsAI, Opponent ai, TicTacToeGame.Mark aiPlaysAs) {
        this.game = game;
        this.vsAI = vsAI;
        this.ai = ai;
        this.aiAs = aiPlaysAs;

        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Initializes and displays the game window.
             */
            @Override
            public void run() {
                initUi();
                game.addListener(SwingUI.this);
                updateFromBoard();
                frame.setVisible(true);
                maybeAIMove();
            }
        });
    }

    /**
     * Creates the game window, board buttons,
     * status label, and reset button.
     */
    private void initUi() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8,8));

        JPanel grid = new JPanel(new GridLayout(3,3,6,6));
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 42);

        for (int i = 0; i < 9; i++) {
            final int idx = i;
            JButton btn = new JButton(" ");
            btn.setFont(f);
            btn.setFocusPainted(false);
            btn.addActionListener(new ActionListener() {
                /**
                 * Handles a click on a board cell.
                 *
                 * @param e the button click event
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    onCell(idx);
                }
            });
            cells[i] = btn;
            grid.add(btn);
        }

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status.setFont(status.getFont().deriveFont(Font.BOLD));
        top.add(status);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton reset = new JButton(new AbstractAction("Reset") {
            /**
             * Resets the game when the reset button is clicked.
             *
             * @param e the button click event
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                game.reset();
                maybeAIMove();
            }
        });
        bottom.add(reset);

        frame.add(top, BorderLayout.NORTH);
        frame.add(grid, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setSize(360, 420);
        frame.setLocationByPlatform(true);
    }

    /**
     * Handles a player's selection of a board cell.
     *
     * @param idx the selected board position from 0 to 8
     */
    private void onCell(int idx) {
        if (game.isGameOver()) return;
        if (vsAI && game.getCurrent() == aiAs) return;
        if (game.play(idx)) {
            maybeAIMove();
        }
    }

    /**
     * Makes the computer's move when it is the AI's turn.
     * Uses a short delay before placing the AI's mark.
     */
    private void maybeAIMove() {
        if (!vsAI || game.isGameOver()) return;
        if (game.getCurrent() != aiAs) return;

        Timer t = new Timer(200, new ActionListener() {
            /**
             * Executes the AI's selected move.
             *
             * @param e the timer event
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int move = ai.chooseMove(game, aiAs);
                game.play(move);
            }
        });
        t.setRepeats(false);
        t.start();
    }

    /**
     * Updates the board buttons and status message
     * to reflect the current game state.
     */
    private void updateFromBoard() {
        TicTacToeGame.Mark[] b = game.getBoard();

        for (int i = 0; i < 9; i++) {
            cells[i].setText(sym(b[i]));
            cells[i].setEnabled(!game.isGameOver() && b[i] == TicTacToeGame.Mark.EMPTY);
        }

        if (game.isGameOver()) {
            if (game.getWinner() == TicTacToeGame.Mark.EMPTY) {
                status.setText("Draw. Click Reset.");
            } else {
                status.setText("Winner: " + game.getWinner() + ". Click Reset.");
            }
        } else {
            status.setText("Turn: " + game.getCurrent() + (vsAI ? (", AI as " + aiAs) : ""));
        }
    }

    /**
     * Converts a game mark into a display character.
     *
     * @param m the board mark
     * @return X, O, or a blank space
     */
    private String sym(TicTacToeGame.Mark m) {
        if (m == TicTacToeGame.Mark.X) return "X";
        if (m == TicTacToeGame.Mark.O) return "O";
        return " ";
    }

    /**
     * Updates the graphical board after a move.
     *
     * @param index the position of the move
     * @param who the player who made the move
     */
    @Override
    public void onMove(int index, TicTacToeGame.Mark who) {
        updateFromBoard();
    }

    /**
     * Updates the graphical interface when the game ends.
     *
     * @param winner the winning player, or EMPTY for a draw
     */
    @Override
    public void onGameOver(TicTacToeGame.Mark winner) {
        updateFromBoard();
    }

    /**
     * Updates the graphical interface after the game resets.
     *
     * @param starting the player who starts the new game
     */
    @Override
    public void onReset(TicTacToeGame.Mark starting) {
        updateFromBoard();
    }
}
