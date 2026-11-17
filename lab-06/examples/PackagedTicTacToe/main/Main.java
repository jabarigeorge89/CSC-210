package main;

import gamelogic.*;
import userinterface.*;
/**
 * The Main class starts the Tic-Tac-Toe game.
 * It allows the user to play using the command line
 * or a graphical interface.
 *
 * The game can be played against another person
 * or against an AI opponent.
 *
 * @author Jabari George
 * @version 1.0
 */
public class Main {

    /**
     * Starts the Tic-Tac-Toe game.
     * Checks the command-line arguments to determine
     * the game mode and user interface.
     *
     * @param args command-line options for the game
     */
    public static void main(String[] args) {
        // Config (you can change these or make them flags)
        boolean useSwing = argsContain(args, "--swing");
        boolean useCLI   = argsContain(args, "--cli") || !useSwing;
        boolean vsAI     = argsContain(args, "--ai");
        boolean aiFirst  = argsContain(args, "--ai-first");

        TicTacToeGame game = new TicTacToeGame();
        Opponent ai = new MinimaxOpponent();
        TicTacToeGame.Mark aiAs = aiFirst ? TicTacToeGame.Mark.X : TicTacToeGame.Mark.O;

        if (useSwing) {
            TicTacToeUI ui = new SwingUI();
            ui.start(game, vsAI, ai, aiAs);
        } else if (useCLI) {
            TicTacToeUI ui = new CLIUI();
            ui.start(game, vsAI, ai, aiAs);
        }
    }

    /**
     * Checks whether a command-line argument contains
     * a specific option.
     *
     * @param args the command-line arguments
     * @param flag the option to search for
     * @return true if the option is found, otherwise false
     */
    private static boolean argsContain(String[] args, String flag) {
        if (args == null) return false;
        for (String a : args) if (flag.equalsIgnoreCase(a)) return true;
        return false;
    }
}
