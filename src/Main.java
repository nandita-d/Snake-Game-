// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        // Create the game panel (width and height are set inside SnakeGame)
        SnakeGame game = new SnakeGame();

        // Create the frame
        JFrame frame = new JFrame("Snake Game");
        frame.add(game);                     // Add the game panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();                        // Fit frame to the preferred size of SnakeGame
        frame.setLocationRelativeTo(null);   // Center the window
        frame.setVisible(true);
    }
}