import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Main frame
        JFrame frame = new JFrame("Tic-Tac-Toe AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Game panel 
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        // Display the frame
        frame.setVisible(true);
    }
}