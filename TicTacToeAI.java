public class TicTacToeAI {

    // Current state of the board
    public static int evaluateBoard(char[][] board) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == 'X') return 10;
                if (board[i][0] == 'O') return -10;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == 'X') return 10;
                if (board[0][i] == 'O') return -10;
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'X') return 10;
            if (board[0][0] == 'O') return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'X') return 10;
            if (board[0][2] == 'O') return -10;
        }

        return 0; // No winner yet
    }

    // Check if the board is full or not
    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }

    // MiniMax Algorithm with Alpha-Beta Pruning
    public static int minimax(char[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
        int score = evaluateBoard(board);

        // Terminal states
        if (score == 10) return score - depth; // AI wins
        if (score == -10) return score + depth; // Opponent wins
        if (isBoardFull(board)) return 0; // Draw

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X'; // AI makes a move
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false, alpha, beta));
                        board[i][j] = '-'; // Undo the move
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) break; // Alpha-Beta pruning
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O'; // Opponent makes a move
                        bestScore = Math.min(bestScore, minimax(board, depth + 1, true, alpha, beta));
                        board[i][j] = '-'; // Undo the move
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) break; // Alpha-Beta pruning
                    }
                }
            }
            return bestScore;
        }
    }

    // Find the best move for AI
    public static Move findBestMove(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'X'; // AI makes a move
                    int moveScore = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = '-'; // Undo the move

                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        bestMove.row = i;
                        bestMove.col = j;
                    }
                }
            }
        }
        return bestMove;
    }
}