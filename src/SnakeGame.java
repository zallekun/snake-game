import javax.swing.*; // Mengimpor paket Swing untuk komponen GUI

public class SnakeGame { // Mendeklarasikan kelas utama SnakeGame
    public static void main(String[] args) { // Metode utama untuk menjalankan program
        JFrame frame = new JFrame(); // Membuat instance JFrame baru
        MenuPanel menuPanel = new MenuPanel(frame); // Membuat instance MenuPanel dengan JFrame sebagai parameter

        frame.add(menuPanel); // Menambahkan MenuPanel ke JFrame
        frame.setTitle("Snake Game"); // Mengatur judul JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur aksi default ketika aplikasi ditutup
        frame.setResizable(false); // Mengatur agar ukuran JFrame tidak bisa diubah
        frame.pack(); // Menyesuaikan ukuran JFrame berdasarkan komponen di dalamnya
        frame.setSize(600, 600); // Mengatur ukuran JFrame menjadi 600x600 piksel
        frame.setLocationRelativeTo(null); // Menempatkan JFrame di tengah layar
        frame.setVisible(true); // Menampilkan JFrame
    }
}
