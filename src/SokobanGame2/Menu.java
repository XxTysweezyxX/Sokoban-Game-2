package SokobanGame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton startButton;
    private JLabel titleLabel;
    private ImageIcon backgroundImage;

    public Menu() {
        setTitle("Sokoban Game Menu");
        setSize(694, 516);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

     // Set background image
        backgroundImage = new ImageIcon("images/Menu Background.png");
        // Resize the background image to match the size of the JFrame
        Image img = backgroundImage.getImage();
        Image resizedImg = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        backgroundImage = new ImageIcon(resizedImg);
        setContentPane(new JLabel(backgroundImage));


        // Create start button
        startButton = new JButton("Start Game");

        // Set button font and style
        Font buttonFont = new Font("Arial", Font.PLAIN, 16); // Smaller font size
        startButton.setFont(buttonFont);
        startButton.setBackground(Color.WHITE); // Set button background color
        startButton.setPreferredSize(new Dimension(150, 50)); // Set button size

        // Set layout
        setLayout(new BorderLayout()); // Use BorderLayout for better control of component positioning

        // Create title label
        titleLabel = new JLabel("Sokoban Game");
        titleLabel.setFont(new Font("Impact", Font.BOLD, 60)); // Different font and larger size
        titleLabel.setForeground(Color.BLUE); // Set text color
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
     // Add an empty border to the top of the title label to bring it lower
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); // Adjust the top padding as needed

        // Add title label to the top half of the page
        add(titleLabel, BorderLayout.NORTH);

        // Create panel for the bottom half of the page
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false); // Make the panel transparent

        // Set layout for the bottom panel
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the button horizontally
        
     // Add an empty border to the bottom of the bottom panel to bring the button up
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0)); // Adjust the bottom padding as needed

        // Add start button to the bottom panel
        bottomPanel.add(startButton);

        // Add bottom panel to the bottom half of the page
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listener to the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                dispose(); // Close the menu window
                Game game = new Game();
                game.setVisible(true);
            }
        });

        setVisible(true);
    }

    
}
