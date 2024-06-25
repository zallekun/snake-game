import javax.swing.JFrame; // Mengimpor kelas JFrame dari paket Swing

public class GameFrame extends JFrame { // Mendeklarasikan kelas GameFrame yang merupakan turunan dari JFrame
    GamePanel panel; // Mendeklarasikan variabel GamePanel

    public GameFrame(boolean isMultiplayer) { // Konstruktor untuk GameFrame dengan parameter boolean isMultiplayer
        panel = new GamePanel(isMultiplayer); // Membuat instance GamePanel baru dengan parameter isMultiplayer
        this.add(panel); // Menambahkan GamePanel ke GameFrame
        this.setTitle("Snake"); // Mengatur judul JFrame menjadi "Snake"
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur aksi default ketika aplikasi ditutup
        this.setResizable(false); // Mengatur agar ukuran JFrame tidak bisa diubah
        this.pack(); // Menyesuaikan ukuran JFrame berdasarkan komponen di dalamnya
        this.setVisible(true); // Menampilkan JFrame
        this.setLocationRelativeTo(null); // Menempatkan JFrame di tengah layar
    }
}
