import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final int UNIT_SIZE = 25;
    private final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private final int DELAY = 75;
    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];
    private final int x2[] = new int[GAME_UNITS];
    private final int y2[] = new int[GAME_UNITS];
    private final int MAX_FOOD = 5; // Jumlah maksimal makanan tambahan
    private final int MAX_FOOD_TYPES = 3; // Jumlah jenis makanan tambahan
    private int foodX[] = new int[MAX_FOOD];
    private int foodY[] = new int[MAX_FOOD];
    private int foodType[] = new int[MAX_FOOD]; // Menyimpan jenis makanan tambahan
    private int bodyParts = 6;
    private int bodyParts2 = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private char direction2 = 'L';
    private boolean running = false;
    private boolean gameOver = false;
    private Timer timer;
    private Random random;
    private boolean isMultiplayer;

    public GamePanel(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        bodyParts = 6;
        bodyParts2 = 6;
        applesEaten = 0;
        direction = 'R';
        direction2 = 'L';
        newApple();
        for (int i = 0; i < MAX_FOOD; i++) {
            newFood(i);
        }
        running = true;
        gameOver = false;

        // Initialize starting positions for player 1
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 100 - i * UNIT_SIZE;
            y[i] = 100;
        }

        // Initialize starting positions for player 2 if multiplayer
        if (isMultiplayer) {
            for (int i = 0; i < bodyParts2; i++) {
                x2[i] = 500 + i * UNIT_SIZE;
                y2[i] = 500;
            }
        }

        if(timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Draw additional food
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
                    // Add more colors or food types here if needed
                }
                g.fillOval(foodX[i], foodY[i], UNIT_SIZE, UNIT_SIZE); // Draw food as circles
            }

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

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

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void newFood(int index) {
        foodX[index] = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY[index] = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        foodType[index] = random.nextInt(MAX_FOOD_TYPES); // Generate random food type
    }

    public void move() {
        for (int i = bodyParts - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

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

        if (isMultiplayer) {
            for (int i = bodyParts2 - 1; i > 0; i--) {
                x2[i] = x2[i - 1];
                y2[i] = y2[i - 1];
            }

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

            if (x2[0] < 0) {
                x2[0            ] = SCREEN_WIDTH - UNIT_SIZE;
            }
            else if (x2[0] >= SCREEN_WIDTH) {
                x2[0] = 0;
            }
            if (y2[0] < 0) {
                y2[0] = SCREEN_HEIGHT - UNIT_SIZE;
            } else if (y2[0] >= SCREEN_HEIGHT) {
                y2[0] = 0;
            }
            }
            
            }
            
            public void checkApple() {
                if ((x[0] == appleX) && (y[0] == appleY)) {
                    bodyParts++;
                    applesEaten++;
                    newApple();
                }
            
                for (int i = 0; i < MAX_FOOD; i++) {
                    if ((x[0] == foodX[i]) && (y[0] == foodY[i])) {
                        bodyParts++;
                        applesEaten++;
                        newFood(i);
                    }
                }
            
                if (isMultiplayer && (x2[0] == appleX) && (y2[0] == appleY)) {
                    bodyParts2++;
                    applesEaten++;
                    newApple();
                }
            
                for (int i = 0; i < MAX_FOOD; i++) {
                    if (isMultiplayer && (x2[0] == foodX[i]) && (y2[0] == foodY[i])) {
                        bodyParts2++;
                        applesEaten++;
                        newFood(i);
                    }
                }
            }
            
            public void checkCollisions() {
                for (int i = bodyParts; i > 0; i--) {
                    if ((x[0] == x[i]) && (y[0] == y[i])) {
                        running = false;
                        gameOver = true;
                    }
                }
            
                if (isMultiplayer) {
                    for (int i = bodyParts2; i > 0; i--) {
                        if ((x2[0] == x2[i]) && (y2[0] == y2[i])) {
                            running = false;
                            gameOver = true;
                        }
                    }
            
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
                    timer.stop();
                }
            }
            
            public void gameOver(Graphics g) {
                g.setColor(Color.red);
                g.setFont(new Font("Ink Free", Font.BOLD, 75));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
            
                g.setFont(new Font("Ink Free", Font.BOLD, 40));
                g.setColor(Color.white);
                g.drawString("Player 1 Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Player 1 Score: " + applesEaten)) / 2, SCREEN_HEIGHT / 2 + 75);
                if (isMultiplayer) {
                    g.drawString("Player 2 Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Player 2 Score: " + applesEaten)) / 2, SCREEN_HEIGHT / 2 + 150);
                }
            
                g.setFont(new Font("Ink Free", Font.BOLD, 30));
                g.setColor(Color.white);
                g.drawString("Press Enter to Restart", (SCREEN_WIDTH - metrics.stringWidth("Press Enter to Restart")) / 2, SCREEN_HEIGHT / 2 + 200);
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
                    if (running) {
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
                    } else if (gameOver) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            startGame();
                            repaint();
                        }
                    }
                }
            }
         }
            