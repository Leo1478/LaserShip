package lasership;

/* 
ship 1 extends Ship class
has same variables, except a few different things 

 */
public class ship1 extends Ship {
    
    SpeedShip speed = new SpeedShip();
    
    String typeOfShip ;

    ship1(int startx, int starty, int speed, String type) {

        //use same variables as Ship class 
        super(startx, starty);

        //new stats speed is differend 
        //speedUp = speed;
        typeOfShip = type;



    }


    @Override
    void resetSpeed(){
       speedUp = speed.speedUp;
       speedDown = speed.speedDown;
       speedLeft = speed.speedLeft;
       speedRight = speed.speedRight;
    }
    


    
    
    @Override
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
    
    public void setShip(String type){

        switch(type){
            
            case "SpeedShip":
                resetSpeed();
                break;
                
            case "MissileShip":
                //
                break;
        }
    }
    
    

    // special stats for ship1
}
