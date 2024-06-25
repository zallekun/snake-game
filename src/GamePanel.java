import java.awt.*; // Mengimpor paket AWT untuk grafis dan GUI
import java.awt.event.*; // Mengimpor paket untuk event handling
import javax.swing.*; // Mengimpor paket Swing untuk komponen GUI
import java.util.Random; // Mengimpor kelas Random untuk menghasilkan angka acak

public class GamePanel extends JPanel implements ActionListener {
    // Deklarasi konstanta dan variabel
    private final int SCREEN_WIDTH = 600; // Lebar layar
    private final int SCREEN_HEIGHT = 600; // Tinggi layar
    private final int UNIT_SIZE = 25; // Ukuran unit (kotak) dalam game
    private final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; // Jumlah unit dalam game
    private final int DELAY = 75; // Delay dalam milidetik untuk timer
    private final int x[] = new int[GAME_UNITS]; // Array untuk posisi x snake player 1
    private final int y[] = new int[GAME_UNITS]; // Array untuk posisi y snake player 1
    private final int x2[] = new int[GAME_UNITS]; // Array untuk posisi x snake player 2
    private final int y2[] = new int[GAME_UNITS]; // Array untuk posisi y snake player 2
    private final int MAX_FOOD = 5; // Jumlah maksimal makanan tambahan
    private final int MAX_FOOD_TYPES = 3; // Jumlah jenis makanan tambahan
    private int foodX[] = new int[MAX_FOOD]; // Array untuk posisi x makanan tambahan
    private int foodY[] = new int[MAX_FOOD]; // Array untuk posisi y makanan tambahan
    private int foodType[] = new int[MAX_FOOD]; // Array untuk jenis makanan tambahan
    private int bodyParts = 6; // Jumlah awal bagian tubuh snake player 1
    private int bodyParts2 = 6; // Jumlah awal bagian tubuh snake player 2
    private int applesEaten; // Skor player 1
    private int applesEaten2; // Skor player 2
    private int appleX; // Posisi x apple utama
    private int appleY; // Posisi y apple utama
    private int highScore; // Skor tertinggi
    private char direction = 'R'; // Arah awal snake player 1
    private char direction2 = 'L'; // Arah awal snake player 2
    private boolean running = false; // Status game apakah sedang berjalan
    private boolean gameOver = false; // Status game apakah sudah selesai
    private Timer timer; // Timer untuk game loop
    private Random random; // Objek random untuk menghasilkan angka acak
    private boolean isMultiplayer; // Status mode multiplayer
    
    

    // Tombol untuk restart dan keluar ke menu
    private JButton restartButton;
    private JButton menuButton;
    
    // Konstruktor untuk GamePanel
    public GamePanel(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer; // Set status multiplayer
        random = new Random(); // Inisialisasi objek random
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Set ukuran panel
        this.setBackground(Color.black); // Set background panel menjadi hitam
        this.setFocusable(true); // Set panel dapat menerima input
        this.addKeyListener(new MyKeyAdapter()); // Tambahkan key listener
        startGame(); // Mulai game
    }

    // Metode untuk memulai game
    public void startGame() {
        bodyParts = 6; // Reset jumlah bagian tubuh player 1
        bodyParts2 = 6; // Reset jumlah bagian tubuh player 2
        applesEaten = 0; // Reset skor player 1
        applesEaten2 = 0; // Reset skor player 2
        direction = 'R'; // Reset arah player 1
        direction2 = 'L'; // Reset arah player 2
        newApple(); // Buat apple baru
        for (int i = 0; i < MAX_FOOD; i++) {
            newFood(i); // Buat makanan tambahan baru
        }
        running = true; // Set status game berjalan
        gameOver = false; // Set status game tidak selesai

        // Inisialisasi posisi awal player 1
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 100 - i * UNIT_SIZE;
            y[i] = 100;
        }

        // Inisialisasi posisi awal player 2 jika multiplayer
        if (isMultiplayer) {
            for (int i = 0; i < bodyParts2; i++) {
                x2[i] = 500 + i * UNIT_SIZE;
                y2[i] = 500;
            }
        }

        // Jika timer ada, hentikan dulu
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this); // Inisialisasi timer baru
        timer.start(); // Mulai timer

        // Hapus tombol jika ada saat memulai ulang game
        if (restartButton != null) {
            this.remove(restartButton);
            restartButton = null;
        }
        if (menuButton != null) {
            this.remove(menuButton);
            menuButton = null;
        }

        this.requestFocusInWindow(); // Fokuskan panel untuk menerima input
    }

    // Metode untuk melukis komponen
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Panggil metode superclass
        draw(g); // Gambar komponen
    }

    // Metode untuk menggambar komponen game
    public void draw(Graphics g) {
        if (running) { // Jika game sedang berjalan
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // Gambar apple utama

            // Gambar makanan tambahan
            for (int i = 0; i < MAX_FOOD; i++) {
                switch (foodType[i]) {
                    case 0:
                        g.setColor(Color.yellow);
                        break;
                    case 1:
                        g.setColor(Color.blue);
                        break;
                    case 2:
                        g.setColor(Color.green);
                        break;
                    // Tambahkan lebih banyak warna atau jenis makanan jika diperlukan
                }
                g.fillOval(foodX[i], foodY[i], UNIT_SIZE, UNIT_SIZE); // Gambar makanan tambahan sebagai lingkaran
            }

            // Gambar tubuh snake player 1
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // Gambar tubuh snake player 2 jika multiplayer
            if (isMultiplayer) {
                for (int i = 0; i < bodyParts2; i++) {
                    if (i == 0) {
                        g.setColor(Color.blue);
                        g.fillRect(x2[i], y2[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(0, 0, 255));
                        g.fillRect(x2[i], y2[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }

            Toolkit.getDefaultToolkit().sync(); // Sinkronkan grafis
        } else { // Jika game sudah selesai
            gameOver(g); // Panggil metode game over
        }
    }

    // Metode untuk membuat apple baru
    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE; // Posisi x apple acak
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE; // Posisi y apple acak
    }

    // Metode untuk membuat makanan tambahan baru
    public void newFood(int index) {
        foodX[index] = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE; // Posisi x makanan tambahan acak
        foodY[index] = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE; // Posisi y makanan tambahan acak
        foodType[index] = random.nextInt(MAX_FOOD_TYPES); // Jenis makanan tambahan acak
    }

    // Metode untuk menggerakkan snake
    public void move() {
        // Gerakkan tubuh snake player 1
        for (int i = bodyParts - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Gerakkan kepala snake player 1 sesuai arah
        switch (direction) {
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }

        // Membuat snake muncul di sisi berlawanan jika keluar layar
        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH - UNIT_SIZE;
        } else if (x[0] >= SCREEN_WIDTH) {
            x[0] = 0;
        }
        if (y[0] < 0) {
            y[0] = SCREEN_HEIGHT - UNIT_SIZE;
        } else if (y[0] >= SCREEN_HEIGHT) {
            y[0] = 0;
        }

        // Jika multiplayer, gerakkan snake player 2
        if (isMultiplayer) {
            for (int i = bodyParts2 - 1; i > 0; i--) {
                x2[i] = x2[i - 1];
                y2[i] = y2[i - 1];
            }

            // Gerakkan kepala snake player 2 sesuai arah
            switch (direction2) {
                case 'U':
                    y2[0] -= UNIT_SIZE;
                    break;
                case 'D':
                    y2[0] += UNIT_SIZE;
                    break;
                case 'L':
                    x2[0] -= UNIT_SIZE;
                    break;
                case 'R':
                    x2[0] += UNIT_SIZE;
                    break;
            }

            // Membuat snake player 2 muncul di sisi berlawanan jika keluar layar
            if (x2[0] < 0) {
                x2[0] = SCREEN_WIDTH - UNIT_SIZE;
            } else if (x2[0] >= SCREEN_WIDTH) {
                x2[0] = 0;
            }
            if (y2[0] < 0) {
                y2[0] = SCREEN_HEIGHT - UNIT_SIZE;
            } else if (y2[0] >= SCREEN_HEIGHT) {
                y2[0] = 0;
            }
        }
    }

    // Metode untuk mengecek apakah snake memakan apple atau makanan tambahan
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

        // Jika snake player 1 memakan makanan tambahan
        for (int i = 0; i < MAX_FOOD; i++) {
            if ((x[0] == foodX[i]) && (y[0] == foodY[i])) {
                bodyParts++;
                applesEaten++;
                newFood(i);
            }
        }

        // Jika snake player 2 memakan apple utama
        if (isMultiplayer && (x2[0] == appleX) && (y2[0] == appleY)) {
            bodyParts2++;
            applesEaten2++;
            newApple();
        }

        // Jika snake player 2 memakan makanan tambahan
        for (int i = 0; i < MAX_FOOD; i++) {
            if (isMultiplayer && (x2[0] == foodX[i]) && (y2[0] == foodY[i])) {
                bodyParts2++;
                applesEaten2++;
                newFood(i);
            }
        }
    }

    // Metode untuk mengecek apakah snake bertabrakan dengan dirinya sendiri atau player lain
    public void checkCollisions() {
        // Cek tabrakan snake player 1 dengan dirinya sendiri
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                gameOver = true;
            }
        }

        // Cek apakah snake player 1 keluar layar
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
            gameOver = true;
        }

        // Cek tabrakan snake player 2 dengan dirinya sendiri jika multiplayer
        if (isMultiplayer) {
            for (int i = bodyParts2; i > 0; i--) {
                if ((x2[0] == x2[i]) && (y2[0] == y2[i])) {
                    running = false;
                    gameOver = true;
                }
            }

            // Cek apakah snake player 2 keluar layar
            if (x2[0] < 0 || x2[0] >= SCREEN_WIDTH || y2[0] < 0 || y2[0] >= SCREEN_HEIGHT) {
                running = false;
                gameOver = true;
            }

            // Cek tabrakan antara snake player 1 dan snake player 2
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x2[i]) && (y[0] == y2[i])) {
                    running = false;
                    gameOver = true;
                }
            }

            for (int i = bodyParts2; i > 0; i--) {
                if ((x2[0] == x[i]) && (y2[0] == y[i])) {
                    running = false;
                    gameOver = true;
                }
            }
        }
        if (!running) {
            if (applesEaten > highScore) {
                highScore = applesEaten;
            }
            if (isMultiplayer && applesEaten2 > highScore) {
                highScore = applesEaten2;
            }
            timer.stop();
        }
        

        // Hentikan timer jika game selesai
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        String message = "Game Over";
        String player1Score = "Player 1 Score: " + applesEaten;
        String player2Score = "Player 2 Score: " + applesEaten2;
        Font messageFont = new Font("Ink Free", Font.BOLD, 75);
        Font scoreFont = new Font("Ink Free", Font.PLAIN, 40);
    
        // Tentukan warna ular untuk masing-masing pemain
        Color player1Color = Color.green; // Gantilah dengan warna ular player 1
        Color player2Color = Color.blue;  // Gantilah dengan warna ular player 2
    
        g.setColor(Color.red);
        g.setFont(messageFont);
        FontMetrics metrics = g.getFontMetrics(messageFont);
        int messageX = (SCREEN_WIDTH - metrics.stringWidth(message)) / 2;
        int messageY = SCREEN_HEIGHT / 4; // Menyesuaikan posisi agar lebih tinggi di layar
        g.drawString(message, messageX, messageY);
    
        g.setFont(scoreFont);
        FontMetrics scoreMetrics = g.getFontMetrics(scoreFont);
    
        // Setel warna teks sesuai ular player 1 dan tampilkan skor
        g.setColor(player1Color);
        int player1ScoreX = (SCREEN_WIDTH - scoreMetrics.stringWidth(player1Score)) / 2;
        int player1ScoreY = messageY + 100; // Jarak antara "Game Over" dan skor player 1
        g.drawString(player1Score, player1ScoreX, player1ScoreY);
    
        // Tampilkan high score di bawah skor player 1
        String highScoreText = "High Score: " + highScore;
        g.setColor(Color.white); // Setel warna teks menjadi putih
        int highScoreX = (SCREEN_WIDTH - scoreMetrics.stringWidth(highScoreText)) / 2;
        int highScoreY = player1ScoreY + 60; // Jarak antara skor player 1 dan high score
        g.drawString(highScoreText, highScoreX, highScoreY);
    
        // Jika multiplayer, tampilkan skor player 2 di bawah high score
        if (isMultiplayer) {
            g.setColor(player2Color);
            int player2ScoreX = (SCREEN_WIDTH - scoreMetrics.stringWidth(player2Score)) / 2;
            int player2ScoreY = highScoreY + 60; // Jarak antara high score dan skor player 2
            g.drawString(player2Score, player2ScoreX, player2ScoreY);
        }
    
        // Tambahkan tombol "Restart Game" dan "Keluar ke Menu" saat game over
        if (restartButton == null && menuButton == null) {
            restartButton = new JButton("Restart Game");
            menuButton = new JButton("Keluar");
    
            int buttonWidth = 200;
            int buttonHeight = 50;
            int buttonX = (SCREEN_WIDTH - buttonWidth) / 2;
            int buttonY = highScoreY + 120; // Jarak antara high score/skor player dan tombol
    
            restartButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
            menuButton.setBounds(buttonX, buttonY + 70, buttonWidth, buttonHeight);
    
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });
    
            menuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Implementasi keluar ke menu
                    System.exit(0); // Sementara keluar dari program
                }
            });
    
            this.setLayout(null);
            this.add(restartButton);
            this.add(menuButton);
    
            this.repaint();
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_A:
                    if (isMultiplayer && direction2 != 'R') {
                        direction2 = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (isMultiplayer && direction2 != 'L') {
                        direction2 = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (isMultiplayer && direction2 != 'D') {
                        direction2 = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (isMultiplayer && direction2 != 'U') {
                        direction2 = 'D';
                    }
                    break;
            }
        }
    }
}
