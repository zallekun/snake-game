import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {
    private JButton singlePlayerButton;
    private JButton multiplayerButton;
    private JFrame parentFrame;

    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Snake Game");
        titleLabel.setFont(new Font("Ink Free", Font.BOLD, 75));
        titleLabel.setForeground(Color.green);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.setFont(new Font("Ink Free", Font.BOLD, 40));
        singlePlayerButton.setBackground(Color.black);
        singlePlayerButton.setForeground(Color.green);
        singlePlayerButton.addActionListener(this);
        gbc.gridy = 1;
        add(singlePlayerButton, gbc);

        multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setFont(new Font("Ink Free", Font.BOLD, 40));
        multiplayerButton.setBackground(Color.black);
        multiplayerButton.setForeground(Color.green);
        multiplayerButton.addActionListener(this);
        gbc.gridy = 2;
        add(multiplayerButton, gbc);

        setBackground(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayerButton) {
            startGame(false);
        } else if (e.getSource() == multiplayerButton) {
            startGame(true);
        }
    }

    private void startGame(boolean isMultiplayer) {
        parentFrame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(isMultiplayer);
        parentFrame.add(gamePanel);
        parentFrame.validate();
        gamePanel.requestFocusInWindow(); // to ensure key events are captured
    }
}
