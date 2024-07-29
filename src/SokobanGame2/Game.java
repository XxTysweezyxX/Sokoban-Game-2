package SokobanGame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame {
    private Level currentLevel; // The current level being played
    public LevelManager levelManager; // Manager for handling level operations
    private int playerX; // Player's current X position
    private int playerY; // Player's current Y position

    public Game() {
        setTitle("Sokoban Game"); // Set the title of the game window
        setSize(694, 516); // Set the size of the game window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false); // Prevent resizing of the window

        levelManager = new LevelManager(this); // Initialize the level manager
        currentLevel = levelManager.getCurrentLevel(); // Get the current level from the level manager

        // Add key listener to handle player movement
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        movePlayer(0, -1); // Move up
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        movePlayer(-1, 0); // Move left
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        movePlayer(0, 1); // Move down
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        movePlayer(1, 0); // Move right
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        setFocusable(true); // Ensure that the window has focus to receive key events
    }

    // Method to set the current level and initialize player position
    public void setLevel(Level level) {
        currentLevel = level;
        getContentPane().removeAll();
        add(currentLevel);
        revalidate();
        repaint();
        // Find player's initial position in the level
        for (int y = 0; y < Level.LEVEL_HEIGHT; y++) {
            for (int x = 0; x < Level.LEVEL_WIDTH; x++) {
                if (currentLevel.getLayout(x, y) == '@') {
                    playerX = x;
                    playerY = y;
                    return;
                }
            }
        }
    }

    // Method to move the player
    public void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        // Check if the new position is valid (not a wall)
        if (isValidMove(newX, newY)) {
            char content = currentLevel.getLayout(newX, newY);

            if (content != '#') { // Check if the position is not a wall
                if (content == '.') {
                    currentLevel.setVisible(false); // Hide the level if the player steps on a goal
                } else if (content != '.' && !currentLevel.isVisible()) {
                    currentLevel.setVisible(true); // Show the level if the player steps off a goal
                }

                if (content == '$' || content == '*') { // Check if the position contains a box
                    int newBoxX = newX + dx;
                    int newBoxY = newY + dy;

                    // Check if the box is not on a goal square
                    if (content == '$' || (content == '*' && currentLevel.getLayout(newX, newY) != '*')) {
                        if (isValidMove(newBoxX, newBoxY)) {
                            char nextContent = currentLevel.getLayout(newBoxX, newBoxY);

                            if (nextContent == ' ' || nextContent == '.') { // Check if the next position is valid for the box
                                if (nextContent == '.') {
                                    currentLevel.setLayout(newBoxX, newBoxY, '*'); // Move box to the goal
                                    currentLevel.updateBoxOnGoal(newX, newY); // Update box image on goal
                                } else {
                                    currentLevel.setLayout(newBoxX, newBoxY, '$'); // Move box
                                }

                                currentLevel.setLayout(newX, newY, '@'); // Move player
                                currentLevel.setLayout(playerX, playerY, ' '); // Clear previous player position

                                playerX = newX;
                                playerY = newY;

                                currentLevel.incrementMovesCount(); // Increment move count

                                currentLevel.repaint(); // Repaint the level
                            }
                        }
                    }
                } else { // If the position does not contain a box
                    currentLevel.setLayout(playerX, playerY, ' '); // Clear previous player position
                    currentLevel.setLayout(newX, newY, '@'); // Move player

                    playerX = newX;
                    playerY = newY;

                    currentLevel.incrementMovesCount(); // Increment move count

                    currentLevel.repaint(); // Repaint the level
                    checkLevelCompletion(); // Check if the level is complete
                }
            }
        }

        // Check level completion after each move
        checkLevelCompletion();
    }

    // Method to check if a box is on a goal
    private boolean isBoxOnGoal(int x, int y) {
        return currentLevel.getLayout(x, y) == '*';
    }

    // Method to complete the current level
    private void completeCurrentLevel() {
        levelManager.completeCurrentLevel(); // Call LevelManager method to complete the current level
    }

    // Method to check if the current level is complete
    private void checkLevelCompletion() {
        boolean isLevelComplete = currentLevel.isLevelComplete();

        if (isLevelComplete) {
            loadNextLevel(); // Load the next level if the current level is complete
        }
    }

    // Method to check if a move is valid (not out of bounds or into a wall)
    private boolean isValidMove(int x, int y) {
        if (x < 0 || y < 0 || x >= Level.LEVEL_WIDTH || y >= Level.LEVEL_HEIGHT) {
            return false; // Out of bounds
        }
        return currentLevel.getLayout(x, y) != '#'; // Not a wall
    }

    // Method to load the next level
    public void loadNextLevel() {
        levelManager.loadNextLevel();
    }
}
