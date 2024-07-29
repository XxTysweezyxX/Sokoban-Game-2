package SokobanGame2;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class GoalManager {
    private Set<Point> goalPositions; // Set to store positions of goals
    private int currentLevelIndex; // Index of the current level

    public GoalManager() {
        goalPositions = new HashSet<>(); // Initialize the set of goal positions
        currentLevelIndex = 1; // Initialize the current level index
    }

    // Method to add a goal position
    public void addGoalPosition(int x, int y) {
        goalPositions.add(new Point(x, y)); // Add a new goal position to the set
    }

    // Method to remove a goal position
    public void removeGoalPosition(int x, int y) {
        goalPositions.remove(new Point(x, y)); // Remove a goal position from the set
    }

    // Method to check if a position is a goal
    public boolean isGoal(int x, int y) {
        return goalPositions.contains(new Point(x, y)); // Check if the given position is a goal
    }

    // Method to get the current level index
    public int getCurrentLevelIndex() {
        return currentLevelIndex; // Return the current level index
    }

    // Method to set the current level index
    public void setCurrentLevelIndex(int index) {
        currentLevelIndex = index; // Set the current level index
    }
}
