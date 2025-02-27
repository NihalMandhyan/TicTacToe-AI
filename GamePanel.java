import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private char[][] board = new char[3][3];
    private boolean playerTurn = true; // True for player, false for AI

    public GamePanel() {
        initializeBoard();
        setBackground(new Color(30, 30, 30)); // Dark background
        setForeground(Color.WHITE); // White grid lines

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!playerTurn) return;

                int row = e.getY() / 133;
                int col = e.getX() / 133;

                if (board[row][col] == '-') {
                    board[row][col] = 'O';
                    repaint();
                    playerTurn = false;

                    if (checkWinner('O')) {
                        showWinMessage("You win!", Color.CYAN);
                        resetGame();
                        return;
                    }
                    if (isBoardFull(board)) {
                        showWinMessage("It's a draw!", Color.ORANGE);
                        resetGame();
                        return;
                    }

                    makeAIMove();
                }
            }
        });
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private void makeAIMove() {
        Move bestMove = TicTacToeAI.findBestMove(board);
        board[bestMove.row][bestMove.col] = 'X';
        repaint();

        if (checkWinner('X')) {
            showWinMessage("AI wins!", Color.RED);
            resetGame();
            return;
        }
        if (isBoardFull(board)) {
            showWinMessage("It's a draw!", Color.ORANGE);
            resetGame();
            return;
        }

        playerTurn = true;
    }

    private boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }

    private void resetGame() {
        initializeBoard();
        playerTurn = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the background
        g2d.setColor(new Color(30, 30, 30));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Glowing rounded corners
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = col * 133;
                int y = row * 133;
                drawCell(g2d, x, y);

                // Draw symbols ('X' and 'O') inside the cells
                if (board[row][col] == 'X') {
                    drawX(g2d, row, col);
                } else if (board[row][col] == 'O') {
                    drawO(g2d, row, col);
                }
            }
        }
    }

    private void drawCell(Graphics2D g2d, int x, int y) {
        // glowing border around each cell
        g2d.setColor(new Color(50, 50, 50)); // base color
        g2d.fillRoundRect(x + 5, y + 5, 123, 123, 20, 20); // Rounde rectangle

        // Add a subtle gradient effect
        GradientPaint gradient = new GradientPaint(
                x + 10, y + 10, new Color(40, 40, 40),
                x + 123, y + 123, new Color(60, 60, 60)
        );
        g2d.setPaint(gradient);
        g2d.fillRoundRect(x + 10, y + 10, 113, 113, 15, 15);
    }

    private void drawX(Graphics2D g2d, int row, int col) {
        g2d.setColor(Color.MAGENTA); // Neon magenta for X
        g2d.setStroke(new BasicStroke(5)); // Thicker stroke
        int x = col * 133 + 20;
        int y = row * 133 + 20;
        g2d.drawLine(x, y, x + 93, y + 93);
        g2d.drawLine(x + 93, y, x, y + 93);
    }

    private void drawO(Graphics2D g2d, int row, int col) {
        g2d.setColor(Color.CYAN); // Neon cyan for O
        g2d.setStroke(new BasicStroke(5)); // Thicker stroke
        int x = col * 133 + 66;
        int y = row * 133 + 66;
        g2d.drawOval(x - 40, y - 40, 80, 80);
    }

    private void showWinMessage(String message, Color color) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Game Over");
        dialog.setBackground(color);
        dialog.setVisible(true);
    }
}