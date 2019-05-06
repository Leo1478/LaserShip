package lasership;

/*
everything that will be the same between ship1 and ship2 will go here
things that are different goes to ship1 and ship2 

*/


public class Ship {
    
    Ship(int startx, int starty){
        
        x = startx;
        y = starty;
        
    }
    
    int frame = 0;
    
    //x and y positions 
    int x = 100;
    int y = 100;
    
    boolean shipAlive = true;
    
    int speedUp = -5;
    int speedDown = 5;
    int speedLeft = -5;
    int speedRight = 5;

    //laser position
    int lasX;
    int lasY;
    int laserX[] = new int[100];
    int laserY[] = new int[100];

    //laserspeed
    int laserSpeedX = 8;
    int laserSpeedY = 8;

    //move direction 
    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;

    //amount of lasers 
    int amountLaser = 0;
    int totalLaser = 0;
    int maxLaser = 100;
    
    
    void resetSpeed() {
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
        
        

        if (x < 0) {
            speedLeft = 0;
        }
        if (x > 1300) {
            speedRight = 0;
        }
        if (y < 0) {
            speedUp = 0;
        }
        if (y > 800) {
            speedDown = 0;
        }
    }

    public void moveShip() {
        if (up) {
            y = y + speedUp;
        }
        if (down) {
            y = y + speedDown;
        }
        if (left) {
            x = x + speedLeft;
        }
        if (right) {
            x = x + speedRight;
        }
    }

    public void setLaser() {

        lasX = x;
        lasY = y;

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

        for (int numLaser = 0; numLaser < 100; numLaser++) {
            laserX[numLaser] = laserX[numLaser] + laserSpeedX;
        }

    }
    
}
