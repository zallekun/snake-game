import javax.swing.*; // Mengimpor paket Swing untuk komponen GUI
import java.awt.*; // Mengimpor paket AWT untuk grafis dan tata letak
import java.awt.event.ActionEvent; // Mengimpor kelas untuk aksi event
import java.awt.event.ActionListener; // Mengimpor antarmuka untuk pendengar aksi

public class MenuPanel extends JPanel implements ActionListener {
    // Deklarasi tombol single player dan multiplayer
    private JButton singlePlayerButton;
    private JButton multiplayerButton;
    private JFrame parentFrame; // Referensi ke JFrame induk

    // Konstruktor MenuPanel
    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame; // Set JFrame induk
        setLayout(new GridBagLayout()); // Set tata letak GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Buat objek GridBagConstraints
        gbc.insets = new Insets(10, 10, 10, 10); // Set margin insets

        // Membuat label judul
        JLabel titleLabel = new JLabel("Snake Game");
        titleLabel.setFont(new Font("Ink Free", Font.BOLD, 75)); // Set font label
        titleLabel.setForeground(Color.green); // Set warna font label
        gbc.gridx = 0; // Set posisi grid x
        gbc.gridy = 0; // Set posisi grid y
        add(titleLabel, gbc); // Tambahkan label ke panel dengan constraints

        // Membuat tombol single player
        singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.setFont(new Font("Ink Free", Font.BOLD, 40)); // Set font tombol
        singlePlayerButton.setBackground(Color.black); // Set background tombol
        singlePlayerButton.setForeground(Color.green); // Set warna font tombol
        singlePlayerButton.addActionListener(this); // Tambahkan action listener ke tombol
        gbc.gridy = 1; // Set posisi grid y
        add(singlePlayerButton, gbc); // Tambahkan tombol ke panel dengan constraints

        // Membuat tombol multiplayer
        multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setFont(new Font("Ink Free", Font.BOLD, 40)); // Set font tombol
        multiplayerButton.setBackground(Color.black); // Set background tombol
        multiplayerButton.setForeground(Color.green); // Set warna font tombol
        multiplayerButton.addActionListener(this); // Tambahkan action listener ke tombol
        gbc.gridy = 2; // Set posisi grid y
        add(multiplayerButton, gbc); // Tambahkan tombol ke panel dengan constraints

        setBackground(Color.black); // Set background panel
    }

    // Metode yang akan dipanggil ketika aksi terjadi
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayerButton) { // Jika tombol single player ditekan
            startGame(false); // Mulai game single player
        } else if (e.getSource() == multiplayerButton) { // Jika tombol multiplayer ditekan
            startGame(true); // Mulai game multiplayer
        }
    }

    // Metode untuk memulai game
    private void startGame(boolean isMultiplayer) {
        parentFrame.getContentPane().removeAll(); // Hapus semua konten dari JFrame
        GamePanel gamePanel = new GamePanel(isMultiplayer); // Buat instance GamePanel
        parentFrame.add(gamePanel); // Tambahkan gamePanel ke JFrame
        parentFrame.validate(); // Validasi JFrame untuk merefresh tampilan
        gamePanel.requestFocusInWindow(); // Fokuskan gamePanel untuk menangkap event key
    }
}
