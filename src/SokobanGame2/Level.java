package SokobanGame2;

import javax.swing.*;
import java.awt.*;

public class Level extends JPanel {
    private static final int CELL_SIZE = 40; // Size of each cell in pixels
    static final int LEVEL_WIDTH = 20; // Width of the level in cells
    public static final int LEVEL_HEIGHT = 20; // Height of the level in cells

    private char[][] layout; // 2D array to store the level layout
    private Image wallImage; // Image for walls
    private Image floorImage; // Image for floors
    private Image playerImage; // Image for the player
    private Image goalImage; // Image for goals
    private Image boxOnGoalImage; // Image for boxes on goals
    private Image boxImage; // Image for boxes
    private int movesCount; // Count of moves made by the player
    private JLabel movesLabel; // Label to display the moves count
    private LevelManager levelManager; // Reference to the LevelManager instance
    private boolean nextLevelFrameDisplayed = false; // Flag to track if NextLevelFrame has been displayed

    public Level(String levelContent, LevelManager levelManager2) {
        this.levelManager = levelManager2; // Assign the LevelManager instance to the class variable
        loadImages(); // Load images for the level
        loadLevelFromString(levelContent); // Load the level layout from a string
        movesCount = 0; // Initialize moves count
        movesLabel = new JLabel("Moves: " + movesCount); // Initialize moves label
        movesLabel.setForeground(Color.BLUE); // Set the label color
        add(movesLabel); // Add moves label to the level panel
    }

    // Method to load images from files
    private void loadImages() {
        try {
            wallImage = new ImageIcon("images/Wall.png").getImage();
            floorImage = new ImageIcon("images/floor.png").getImage();
            playerImage = new ImageIcon("images/Player.png").getImage();
            goalImage = new ImageIcon("images/Goal.png").getImage();
            boxOnGoalImage = new ImageIcon("images/BoxOnGoal.png").getImage();
            boxImage = new ImageIcon("images/Box.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to load the level layout from a string
    private void loadLevelFromString(String levelContent) {
        layout = new char[LEVEL_HEIGHT][LEVEL_WIDTH];
        String[] lines = levelContent.split("\n");
        for (int y = 0; y < LEVEL_HEIGHT && y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < LEVEL_WIDTH && x < line.length(); x++) {
                layout[y][x] = line.charAt(x);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean allGoalsCovered = true; // Flag to track if all goals are covered

        // Iterate through each cell in the level and draw the corresponding image
        for (int y = 0; y < LEVEL_HEIGHT; y++) {
            for (int x = 0; x < LEVEL_WIDTH; x++) {
                int xPos = x * CELL_SIZE;
                int yPos = y * CELL_SIZE;
                char tile = layout[y][x];

                // Draw walls, floors, goals, and boxes
                switch (tile) {
                    case '#':
                        g.drawImage(wallImage, xPos, yPos, CELL_SIZE, CELL_SIZE, null);
                        break;
                    case ' ':
                        g.drawImage(floorImage, xPos, yPos, CELL_SIZE, CELL_SIZE, null);
                        break;
                    case '@':
                        g.drawImage(playerImage, xPos, yPos, CELL_SIZE, CELL_SIZE, null);
                        break;
                    case '*':
                        g.drawImage(boxOnGoalImage, xPos, yPos, CELL_SIZE, CELL_SIZE, null);
                        break;
                    case '$':
                        g.drawImage(boxImage, xPos, yPos, CELL_SIZE, CELL_SIZE, null);
                        break;
                    case '.':
                        g.drawImage(goalImage, xPos, yPos, CELL_SIZE, CELL_SIZE, null);
                        if (getLayout(x, y) != '*') {
                            allGoalsCovered = false; // Set flag to false if any goal is not covered
                        }
                        break;
                }
            }
        }

        // Check if all goals are covered with box-on-goal icons
        if (allGoalsCovered && !nextLevelFrameDisplayed) {
            displayNextLevelFrame();
            nextLevelFrameDisplayed = true;
        }
    }

    // Method to display the next level frame
    private void displayNextLevelFrame() {
        NextLevelFrame nextLevelFrame = new NextLevelFrame(levelManager); // Create a NextLevelFrame instance
        nextLevelFrame.setVisible(true); // Make the NextLevelFrame visible
    }

    // Method to get the content of a specific cell in the layout
    public char getLayout(int x, int y) {
        if (x < 0 || y < 0 || x >= LEVEL_WIDTH || y >= LEVEL_HEIGHT) {
            return ' '; // Return empty space if out of bounds
        }
        return layout[y][x];
    }

    // Method to set the content of a specific cell in the layout
    public void setLayout(int x, int y, char element) {
        if (x >= 0 && y >= 0 && x < LEVEL_WIDTH && y < LEVEL_HEIGHT) {
            layout[y][x] = element;
        }
    }

    // Method to increment moves count
    public void incrementMovesCount() {
        movesCount++;
        movesLabel.setText("Moves: " + movesCount); // Update moves label
    }

    // Method to update the layout when a box is moved onto a goal
    public void updateBoxOnGoal(int x, int y) {
        setLayout(x, y, '*'); // Replace the box ('$') with a box on a goal ('*')
    }

    // Method to check if the level is complete
    public boolean isLevelComplete() {
        // Iterate through each cell in the level
        for (int y = 0; y < LEVEL_HEIGHT; y++) {
            for (int x = 0; x < LEVEL_WIDTH; x++) {
                char tile = getLayout(x, y);

                // If the cell contains a box ('$') and is not on a goal ('.')
                if ((tile == '$' || tile == '*') && getLayout(x, y) != '.') {
                    return false; // Level is not complete
                }
            }
        }
        return true; // All boxes are on goal squares
    }
}
