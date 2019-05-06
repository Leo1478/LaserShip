package lasership;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;

import lasership.ship1;
import lasership.ship2;

public class LaserShipMulti {

    //type of ship
    String typeOfShip = "SpeedShip";

    //create 2 objects for ship   
    ship1 s1 = new ship1(600, 500, -20, typeOfShip);
    ship2 s2 = new ship2(300, 300);

    // create object for jpanel
    Output output;
    JFrame j;

    //objects for key listener 
    KeyListener listener;

    static int frame = 0;

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

    public void gameLoop() {

        //depending on which ship selected, change speed
        s1.setShip(typeOfShip);

        while (true) {

            s1.frame++;

            s1.resetSpeed();
            s2.resetSpeed();

            s1.shipInBound();
            s2.shipInBound();

            s1.moveShip();
            s2.moveShip();

            if (s1.shipAlive & s1.laserSpawnRate(10)) {
                s1.setLaser();
            }
            
            //System.out.println(s1.frame);

            s1.moveLaser();

            j.repaint();
            delay(10);

        }

    }

    //class for displaying graphics 
    class Output extends JPanel {

        public Output() {

            KeyListener listener = new keyInput();
            addKeyListener(listener);
            setFocusable(true);

        }

        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            draws1(g);
            draws2(g);
            s1drawLaser(g);

        }

        public void draws1(Graphics g) {

            g.setColor(Color.red);
            g.fillRect(s1.x, s1.y, 200, 200);

        }

        public void draws2(Graphics g) {

            g.setColor(Color.red);
            g.fillRect(s2.x, s2.y, 200, 200);

        }

        public void s1drawLaser(Graphics g) {

            g.setColor(Color.ORANGE);
            for(int numLas = 0; numLas < s1.totalLaser; numLas ++){
                System.out.println(numLas);
                g.fillRect(s1.laserX[numLas], s1.laserY[numLas] - 2, 30, 4);
            }
                
                
                
                
            
        }

    }

    // class for user input keyboard 
    public class keyInput implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

            //resetKeys();
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                s1.up = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                s1.down = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                s1.left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                s1.right = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_W) {
                s2.up = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                s2.down = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                s2.left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                s2.right = true;
            }

        }

        public void keyTyped(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                s1.up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                s1.down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                s1.left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                s1.right = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_W) {
                s2.up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                s2.down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                s2.left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                s2.right = false;
            }
        }

    }

}
