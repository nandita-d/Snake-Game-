import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;

public class SnakeGame extends JPanel implements ActionListener {

    private final int width = 600;
    private final int height = 400;
    private final int cellSize = 20;
    private int speed = 120;

    private Timer timer;
    private LinkedList<Point> snake = new LinkedList<>();
    private Direction direction = Direction.RIGHT;

    private int score = 0;
    private boolean gameOver = false;

    private Random random = new Random();
    private Point food;
    private Point bonus;

    private boolean bonusActive = false;
    private int bonusDuration = 0; // number of moves bonus lasts

    public SnakeGame() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        initGame();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
                    initGame();
                }
            }
        });
    }

    private void initGame() {
        snake.clear();
        snake.add(new Point(width / 2, height / 2));
        score = 0;
        direction = Direction.RIGHT;
        gameOver = false;

        spawnFood();
        spawnBonus();

        bonusActive = false;
        bonusDuration = 0;

        timer = new Timer(speed, this);
        timer.start();
    }

    private void spawnFood() {
        int x = random.nextInt(width / cellSize) * cellSize;
        int y = random.nextInt(height / cellSize) * cellSize;
        food = new Point(x, y);
    }

    private void spawnBonus() {
        int x = random.nextInt(width / cellSize) * cellSize;
        int y = random.nextInt(height / cellSize) * cellSize;
        bonus = new Point(x, y);
    }

    private void move() {
        // Random zigzag change (25% chance)
        if (random.nextInt(4) == 0) {
            Direction[] directions = Direction.values();
            Direction newDir;
            do {
                newDir = directions[random.nextInt(directions.length)];
            } while (newDir == opposite(direction));
            direction = newDir;
        }

        Point head = snake.getFirst();
        Point newHead = new Point(head);

        switch (direction) {
            case UP -> newHead.y -= cellSize;
            case DOWN -> newHead.y += cellSize;
            case LEFT -> newHead.x -= cellSize;
            case RIGHT -> newHead.x += cellSize;
        }

        // Wrap edges
        newHead.x = (newHead.x + width) % width;
        newHead.y = (newHead.y + height) % height;

        // Self-collision
        if (snake.contains(newHead)) {
            gameOver = true;
            timer.stop();
            SoundPlayer.play("gameover.wav");
            return;
        }

        snake.addFirst(newHead);

        // Check food collision
        if (newHead.equals(food)) {
            score += random.nextInt(5) + 5; // 5-9 points
            SoundPlayer.play("eat.wav");
            spawnFood();
        }

        // Check bonus collision
        if (newHead.equals(bonus)) {
            score += random.nextInt(10) + 10; // 10-19 points
            SoundPlayer.play("bonus.wav");
            bonusActive = true;
            bonusDuration = 20; // auto-growth lasts 20 moves
            spawnBonus();
        }

        // Bonus auto-growth
        if (bonusActive) {
            bonusDuration--;
            if (bonusDuration <= 0) {
                bonusActive = false;
            }
            // tail not removed → snake grows automatically
        } else {
            snake.removeLast(); // normal movement
        }
    }

    private Direction opposite(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw snake
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            g.setColor(i == 0 ? Color.YELLOW : Color.GREEN);
            g.fillRect(p.x, p.y, cellSize, cellSize);
        }

        // Draw food (red)
        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, cellSize, cellSize);

        // Draw bonus (magenta)
        g.setColor(Color.MAGENTA);
        g.fillRect(bonus.x, bonus.y, cellSize, cellSize);

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 20);

        // Game over message
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", width / 2 - 120, height / 2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press R to Restart", width / 2 - 100, height / 2 + 40);
        }
    }

    private enum Direction { UP, DOWN, LEFT, RIGHT }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game - Zigzag with Food, Bonus & Auto Growth");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

// SoundPlayer class
