import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GamePanel panel;
    
    public GameFrame(boolean isMultiplayer) {
        panel = new GamePanel(isMultiplayer);
        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}