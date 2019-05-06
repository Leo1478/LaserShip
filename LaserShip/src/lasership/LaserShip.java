//Leo Zeng
//2019/05/04
package lasership;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LaserShip {

    // create object for jpanel
    Output output;
    JFrame j;

    //objects for key listener 
    KeyListener listener;

    //object for random 
    Random rand = new Random();

    //game mode 
    public static boolean singlePlayer() {
        return true;
    }

    public static boolean multiPlayer() {
        return true;
    }

    //variables 
    boolean gameRunning = true;
    boolean shipAlive = true;
    int frame = 0;

    //ship x and y
    int shipX = 100;
    int shipY = 100;

    //enemy x and y
    int eneX;
    int eneY;
    int enemyX[] = new int[100];
    int enemyY[] = new int[100];

    //speed of ship 
    int speedUp = -5;
    int speedDown = 5;
    int speedLeft = -5;
    int speedRight = 5;

    // laser pos
    int lasX;
    int lasY;
    int laserX[] = new int[100];
    int laserY[] = new int[100];

    //laserspeed
    int laserSpeedX = 8;
    int laserSpeedY = 8;

    //speed of enemy 
    //move direction 
    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;

    //amount of lasers 
    int amountLaser = 0;
    int totalLaser = 0;
    int maxLaser = 100;

    //amount of enemies 
    int amountEnemy = 0;
    int totalEnemy = 0;
    int maxEnemy = 100;

    public void resetSpeed() {
        speedUp = -5;
        speedDown = 5;
        speedLeft = -5;
        speedRight = 5;
    }

    public void resetKeys() {
        up = false;
        down = false;
        left = false;
        right = false;

    }

    public void shipInBound() {

        if (shipX < 0) {
            speedLeft = 0;
        }
        if (shipX > 1450) {
            speedRight = 0;
        }
        if (shipY < 50) {
            speedUp = 0;
        }
        if (shipY > 900) {
            speedDown = 0;
        }
    }

    public void moveShip() {
        if (up) {
            shipY = shipY + speedUp;
        }
        if (down) {
            shipY = shipY + speedDown;
        }
        if (left) {
            shipX = shipX + speedLeft;
        }
        if (right) {
            shipX = shipX + speedRight;
        }
    }

    public void setLaser() {

        lasX = shipX;
        lasY = shipY;

        laserX[amountLaser] = lasX;
        laserY[amountLaser] = lasY;

        amountLaser++;
        totalLaser++;

        if (amountLaser == maxLaser - 1) {
            amountLaser = 0;
        }
        if (totalLaser >= maxLaser) {
            totalLaser = maxLaser;

        }

    }

    //every 10 frames, return true --->spawn laser 
    public boolean laserSpawnRate(int rate) {

        if (frame % rate == 0 & amountLaser <= maxLaser) {

            return true;
        }
        return false;
    }

    public void moveLaser() {

        for (int x = 0; x < totalLaser; x++) {
            laserX[x] = laserX[x] + laserSpeedX;
        }

    }

    public boolean enemySpawnRate(int rate) {
        if (frame % rate == 0 & amountEnemy <= maxEnemy) {
            return true;
        }
        return false;
    }

    public void setEnemy() {

        eneX = rand.nextInt(1500) + 300;
        eneY = rand.nextInt(800) + 100;

        enemyX[amountEnemy] = eneX;
        enemyY[amountEnemy] = eneY;

        amountEnemy++;
        totalEnemy++;

        if (amountEnemy == maxEnemy) {
            amountEnemy = 0;
        }
        if (totalEnemy >= maxEnemy) {
            totalEnemy = maxEnemy;
        }
    }

    public int laserHitEnemy() {

        for (int numLaser = 0; numLaser < totalLaser; numLaser++) {

            for (int numEnemy = 0; numEnemy < totalEnemy; numEnemy++) {

                //System.out.println(numLaser + "laser");
                //System.out.println(numEnemy + "enemy");
                if (laserX[numLaser] > enemyX[numEnemy]
                        & laserX[numLaser] < enemyX[numEnemy] + 50
                        & laserY[numLaser] > enemyY[numEnemy]
                        & laserY[numLaser] < enemyY[numEnemy] + 50) {

                    return numEnemy;
                }
            }
        }
        return 101;
    }

    public void removeEnemy(int num) {

        if (num < 100) {
            enemyX[num] = -100;
            enemyY[num] = -100;
        }
    }

    public void moveEnemies() {

        for (int numEnemy = 0; numEnemy < totalEnemy; numEnemy++) {

            enemyX[numEnemy]--;
        }
    }



    public boolean shipHitEnemy() {

        for (int numEnemy = 0; numEnemy < maxEnemy; numEnemy++) {

            if (shipX < enemyX[numEnemy] + 49
                    & shipX + 150 > enemyX[numEnemy]
                    & shipY < enemyY[numEnemy] + 30
                    & shipY > enemyY[numEnemy] - 20) {

                return true;
            }
        }
        return false;
    }

    public void removeShip() {

        shipAlive = false;
    }

    // JPanel output settings 
    public void settings() {

        // settings for output 
        output = new Output();

        j = new JFrame();
        j.getContentPane().add(BorderLayout.CENTER, output);
        j.setTitle("Animation");
        j.setSize(1500, 1000);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Output o = new Output();
        j.add(o);

        gameLoop();

    }

    public void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception exc) {

        }
    }
    
    //main
    public static void main(String[] args) {

        //if single player 
        // create settings
        if (singlePlayer()) {
            //new LaserShip().settings();
        }

        if (multiPlayer()) {
            new LaserShipMulti().settings();
        }

    }

    public void gameLoop() {

        while (gameRunning) {

            frame++;

            resetSpeed();

            shipInBound();

            moveShip();

            if (shipAlive & laserSpawnRate(10)) {
                setLaser();
            }

            moveLaser();

            if (shipAlive & enemySpawnRate(50)) {
                setEnemy();
            }

            removeEnemy(laserHitEnemy());

            moveEnemies();

            if (shipHitEnemy()) {
                removeShip();
            }

            j.repaint();
            delay(8);

        }

    }

    //class for displaying graphics 
    class Output extends JPanel {

        private BufferedImage backGround;

        public Output() {

            KeyListener listener = new keyInput();
            addKeyListener(listener);
            setFocusable(true);

            try {
                backGround = ImageIO.read(new File("F:\\java stuff\\LaserShip\\build\\images\\space_background.jpg"));
            } catch (IOException e) {
            }

        }
        
        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            backGround(g);
            ship(g);
            drawLaser(g);
            drawEnemy(g);

        }

        public void backGround(Graphics g) {
            g.drawImage(backGround, 0, 0, null);
        }

        public void ship(Graphics g) {

            if (shipAlive) {

                g.setColor(Color.red);
                g.drawRect(shipX, shipY - 5, 150, 10);

                int xValues[] = {shipX, shipX, shipX + 100};
                int yValues[] = {shipY + 50, shipY - 50, shipY};
                Polygon triangle = new Polygon(xValues, yValues, 3);
                g.fillPolygon(triangle);
            }
        }

        public void drawLaser(Graphics g) {

            g.setColor(Color.ORANGE);
            for (int numLas = 0; numLas < totalLaser; numLas++) {
                g.fillRect(laserX[numLas], laserY[numLas] - 2, 30, 4);
            }
        }

        void drawEnemy(Graphics g) {

            g.setColor(Color.GREEN);
            for (int numEn = 0; numEn < totalEnemy; numEn++) {
                g.fillRect(enemyX[numEn], enemyY[numEn], 50, 50);
            }
        }

    }

// class for user input keyboard 
    public class keyInput implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

            //resetKeys();
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                up = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                down = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = true;
            }

        }
        
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = false;
            }
        }

    }

}
