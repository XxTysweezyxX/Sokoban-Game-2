package SokobanGame2;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextLevelFrame extends JFrame {
    private JLabel messageLabel;
    private JButton nextButton;
    private LevelManager levelManager; // Add a reference to the LevelManager class
    private ImageIcon emojiImage;

    public NextLevelFrame(LevelManager levelManager) {
        this.levelManager = levelManager; // Assign the LevelManager instance to the class variable

        setTitle("Level Up!");
        setSize(694, 516);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background image
        emojiImage = new ImageIcon("images/thumbs-up-emoji.png");
        Image img = emojiImage.getImage();
        Image resizedImg = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        emojiImage = new ImageIcon(resizedImg);
        setContentPane(new JLabel(emojiImage));

        messageLabel = new JLabel("Congratulations! Level complete!");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 40));
        messageLabel.setForeground(Color.GREEN); 
        

        nextButton = new JButton("Next Level");
        nextButton.setFont(new Font("Arial", Font.BOLD, 40));
        nextButton.setForeground(Color.GREEN); 
        nextButton.setPreferredSize(new Dimension(50, 50)); // Set preferred size
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trigger loading of the next level
                loadNextLevel();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false); // Make the panel transparent
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panel);
    }

    private void loadNextLevel() {
        if (levelManager != null) {
            levelManager.loadNextLevel(); // Call the loadNextLevel() method on the LevelManager instance
        } else {
            System.err.println("LevelManager instance is null.");
        }
        dispose(); // Close the NextLevelFrame
    }
}




