package gamelogic;
import java.util.List;

/**
 * Represents an AI opponent that uses the minimax algorithm
 * to choose moves in a Tic-Tac-Toe game.
 * Alpha-beta pruning helps reduce unnecessary calculations.
 *
 * @author Jabari George
 * @version 1.0
 */
public class MinimaxOpponent implements Opponent {

    /**
     * Chooses the best available move for the AI.
     *
     * @param game the current Tic-Tac-Toe game
     * @param me the mark assigned to the AI
     * @return the selected board position from 0 to 8
     */
    @Override
    public int chooseMove(TicTacToeGame game, TicTacToeGame.Mark me) {
        TicTacToeGame.Mark opp = (me == TicTacToeGame.Mark.X ? TicTacToeGame.Mark.O : TicTacToeGame.Mark.X);

        // Opening shortcut: take the center when available.
        List<Integer> moves = game.legalMoves();
        if (moves.size() >= 8 && moves.contains(4)) return 4;
        return bestByMinimax(game, me, opp);
    }

    /**
     * Evaluates legal moves and selects the highest-scoring move.
     *
     * @param game the current game
     * @param me the AI player's mark
     * @param opp the opposing player's mark
     * @return the best move index
     */
    private int bestByMinimax(TicTacToeGame game, TicTacToeGame.Mark me, TicTacToeGame.Mark opp) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int move : game.legalMoves()) {
            Sim s = new Sim(game);
            s.play(move);
            int score = minimax(s, false, me, opp, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (score > bestScore) { bestScore = score; bestMove = move; }
        }
        return bestMove;
    }

    /**
     * Recursively evaluates possible game outcomes using minimax.
     *
     * @param s the simulated game state
     * @param maximizing true when maximizing the score
     * @param me the AI player's mark
     * @param opp the opposing player's mark
     * @param alpha the best score for the maximizing player
     * @param beta the best score for the minimizing player
     * @return the evaluated score of the game state
     */
    private int minimax(Sim s, boolean maximizing, TicTacToeGame.Mark me, TicTacToeGame.Mark opp, int alpha, int beta) {
        if (s.isOver()) {
            TicTacToeGame.Mark w = s.winner();
            if (w == me) return +10;
            if (w == opp) return -10;
            return 0;
        }

        if (maximizing) {
            int best = Integer.MIN_VALUE;
            for (int m : s.legalMoves()) {
                Sim nxt = s.copy(); nxt.play(m);
                int val = minimax(nxt, false, me, opp, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, val);
                if (beta <= alpha) break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int m : s.legalMoves()) {
                Sim nxt = s.copy(); nxt.play(m);
                int val = minimax(nxt, true, me, opp, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, val);
                if (beta <= alpha) break;
            }
            return best;
        }
    }

    /**
     * Simulates Tic-Tac-Toe game states for the minimax search.
     */
    private static class Sim {
        private final TicTacToeGame.Mark[] b = new TicTacToeGame.Mark[9];
        private TicTacToeGame.Mark turn;
        private boolean over = false;
        private TicTacToeGame.Mark win = TicTacToeGame.Mark.EMPTY;

        /**
         * Creates a simulation from an existing game.
         *
         * @param g the game to copy
         */
        Sim(TicTacToeGame g) {
            TicTacToeGame.Mark[] src = g.getBoard();
            System.arraycopy(src, 0, b, 0, 9);
            turn = g.getCurrent();
            over = g.isGameOver();
            win  = g.getWinner();
        }

        /**
         * Creates a simulation from an existing board state.
         *
         * @param b the board marks
         * @param turn the current player
         * @param over whether the game has ended
         * @param win the winning mark
         */
        Sim(TicTacToeGame.Mark[] b, TicTacToeGame.Mark turn, boolean over, TicTacToeGame.Mark win) {
            System.arraycopy(b, 0, this.b, 0, 9);
            this.turn = turn; this.over = over; this.win = win;
        }

        /**
         * Creates a copy of the simulation.
         *
         * @return a new simulation with the same state
         */
        Sim copy() {
            return new Sim(b, turn, over, win);
        }

        /**
         * Checks whether the simulated game is finished.
         *
         * @return true if the game is over
         */
        boolean isOver() {
            return over;
        }

        /**
         * Gets the winner of the simulated game.
         *
         * @return the winning mark, or EMPTY if there is no winner
         */
        TicTacToeGame.Mark winner() {
            return win;
        }

        /**
         * Finds the available moves in the simulated game.
         *
         * @return a list of available board positions
         */
        List<Integer> legalMoves() {
            java.util.ArrayList<Integer> ms = new java.util.ArrayList<>();
            if (over) return ms;
            for (int i = 0; i < 9; i++) if (b[i] == TicTacToeGame.Mark.EMPTY) ms.add(i);
            return ms;
        }

        /**
         * Attempts to make a move in the simulation.
         *
         * @param idx the board position from 0 to 8
         * @return true if the move succeeds, otherwise false
         */
        boolean play(int idx) {
            if (over || idx < 0 || idx > 8 || b[idx] != TicTacToeGame.Mark.EMPTY) return false;
            b[idx] = turn;

            int[][] L = {
                    {0,1,2},{3,4,5},{6,7,8},
                    {0,3,6},{1,4,7},{2,5,8},
                    {0,4,8},{2,4,6}
            };

            for (int[] line : L) {
                TicTacToeGame.Mark a = b[line[0]];
                TicTacToeGame.Mark c = b[line[1]];
                TicTacToeGame.Mark d = b[line[2]];

                if (a != TicTacToeGame.Mark.EMPTY && a == c && c == d) {
                    win = a; over = true; return true;
                }
            }

            boolean full = true;
            for (TicTacToeGame.Mark m : b) if (m == TicTacToeGame.Mark.EMPTY) { full = false; break; }
            if (full) { over = true; win = TicTacToeGame.Mark.EMPTY; return true; }

            turn = (turn == TicTacToeGame.Mark.X ? TicTacToeGame.Mark.O : TicTacToeGame.Mark.X);
            return true;
        }
    }
}
