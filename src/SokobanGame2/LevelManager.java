package SokobanGame2;

public class LevelManager {                                     // @ = player
																// . = Goal
																// $ = Box
    // Define hardcoded level layouts
    private static final String[] HARD_CODED_LEVELS = {
            // Level 1
            "#################\n" +
            "#               #\n" +
            "#  $  #######   #\n" +
            "#       ####    #\n" +
            "###     ####    #\n" +
            "#       ####    #\n" +
            "#    #######    #\n" +
            "#       ####    #\n" +
            "###     ####    #\n" +
            "#    #######  @ #\n" +
            "#      .####    #\n" +
            "#################\n",

            // Level 2
            "#################\n" +
            "######          #\n" +
            "#           # @ #\n" +
            "#       $   #####\n" +
            "#               #\n" +
            "#####   #       #\n" +
            "########.       #\n" +
            "#               #\n" +
            "#  $    ###     #\n" +
            "#       #   .   #\n" +
            "#################\n" +
            "#################\n",

            // Level 3
            "#################\n" +
            "####  @ #########\n" +
            "#     #######   #\n" +
            "####      #     #\n" +
            "#      ## #  .  #\n" +
            "# #   ###$####  #\n" +
            "#         #     #\n" +
            "#.####          #\n" +
            "##     ####   $ #\n" +
            "#  $    #####   #\n" +
            "##   #####.     #\n" +
            "#################\n",

            // Level 4
            "#################\n" +
            "##  #          .#\n" +
            "#          ######\n" +
            "##. ###         #\n" +
            "#####  ##  # $  #\n" +
            "##      #       #\n" +
            "#     $    ######\n" +
            "#   #           #\n" +
            "##  #    #### $ #\n" +
            "#   ##### @ ##  #\n" +
            "#      .#       #\n" +
            "#################\n",

            // Level 5
            "#################\n" +
            "#.      #      @#\n" +
            "#   #      #  ###\n" +
            "##### $ #    $  #\n" +
            "#         #     #\n" +
            "#  ## $  ########\n" +
            "#   #           #\n" +
            "#   $  ### ### ##\n" +
            "#####           #\n" +
            "#.     #    #   #\n" +
            "##     #.#####.##\n" +
            "#################\n",
    };

    private final int NUM_LEVELS = HARD_CODED_LEVELS.length; // Total number of hardcoded levels
    private int currentLevelIndex; // Index of the current level
    private Level currentLevel; // The current level object
    private Game game; // Reference to the Game object
    private GoalManager goalManager; // Reference to the GoalManager object
    private static LevelManager instance; // Singleton instance of LevelManager

    public LevelManager(Game game) {
        this.game = game;
        currentLevelIndex = 0; // Start with the first level
        loadLevel(currentLevelIndex); // Load the first level
        goalManager = new GoalManager(); // Initialize GoalManager
        instance = this; // Set the singleton instance
    }

    // Method to load a level based on its index
    void loadLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < HARD_CODED_LEVELS.length) {
            currentLevel = new Level(HARD_CODED_LEVELS[levelIndex], this);
            game.setLevel(currentLevel); // Set the current level in the game
        } else {
            System.err.println("Invalid level index: " + levelIndex); // Print an error if the index is invalid
        }
    }

    // Method to complete the current level and load the next one
    public void completeCurrentLevel() {
        currentLevelIndex++;
        if (currentLevelIndex < NUM_LEVELS) {
            loadLevel(currentLevelIndex); // Load the next level
        } else {
            System.out.println("No more levels available."); // Handle case when no more levels are available
            // Handle game completion or display a message indicating no more levels
        }
    }

    // Method to check if all goals are covered by boxes
    public void checkLevelCompletion() {
        int goalsCount = 0;
        int boxesOnGoalsCount = 0;

        // Count the number of goals and boxes on goals
        for (int y = 0; y < Level.LEVEL_HEIGHT; y++) {
            for (int x = 0; x < Level.LEVEL_WIDTH; x++) {
                char tile = currentLevel.getLayout(x, y);
                if (tile == '*' || tile == '.') {
                    goalsCount++;
                }
                if (tile == '@' || tile == '+') {
                    boxesOnGoalsCount++;
                }
            }
        }

        // Check if all boxes are on goal squares
        if (boxesOnGoalsCount == goalsCount) {
            completeCurrentLevel();
        }
    }

    // Method to check if the level is complete
    public boolean isLevelComplete() {
        // Check each goal square
        for (int y = 0; y < Level.LEVEL_HEIGHT; y++) {
            for (int x = 0; x < Level.LEVEL_WIDTH; x++) {
                if (currentLevel.getLayout(x, y) == '.' && currentLevel.getLayout(x, y) != '*') {
                    // Check if the goal square is covered with a box
                    if (currentLevel.getLayout(x, y) != '*') {
                        return false; // Goal square is not covered
                    }
                }
            }
        }
        return true; // All goal squares are covered
    }

    // Method to get the current level
    public Level getCurrentLevel() {
        return currentLevel;
    }

    // Static method to load the next level
    public static void loadNextLevel() {
        if (instance != null) {
            instance.completeCurrentLevel(); // Call the completeCurrentLevel() method on the instance
        }
    }
}
