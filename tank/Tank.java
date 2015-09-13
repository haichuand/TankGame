package tank;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;

import Game2D.GameSounds;
import Game2D.controllers.DirectionalMotion;
import Game2D.controllers.InputController;
import Game2D.objects.PlayerShip;
/**
 * Tank players in the game. Tanks have DirectionalMotion
 * @author Fudou
 *
 */
public class Tank extends PlayerShip {
	DirectionalMotion tankMotion; //motion that controls movement of tank
	int resetDirection;
	
	/**
	 * Constructs a tank object
	 * @param location Location of tank
	 * @param direction Initial heading of tank
	 * @param img Image of tank
	 * @param controls Array specifying the key control of the tank (left, up, right, down and fire)
	 * @param name Name of tank
	 */
    public Tank(Point location, int direction, BufferedImage img, int[] controls, String name) {
        super(location, new Point(0,0), 100, 100, img, controls, name);
        this.motion = null;
        this.location.width = this.location.height = 64;
        tankMotion = new DirectionalMotion(this, 4, direction);
        resetPoint = new Point(location);
        resetDirection = direction;
        weapon = new TankWeapon();
        controller = new InputController(this, controls, TankWorld.getInstance());
        lives = 3;
        score = 0;
        respawnCounter=0;
    }
    
    @Override
    public DirectionalMotion getMotion() {
    	return tankMotion;
    }
    
    @Override
    /**
     * After each clock tick, update() is called to update the heading and movement of the tank.
     * Also checks the tank status. If isDead is true, then sets gameOver as true in TankWorld.
     * If tank isRespawning, decrement respawnCounter.
     */
    public void update(Observable o, Object arg) {
    		if (isDead) {
    			TankWorld.getInstance().setGameOver(true);
    		}
    		if (!isRespawning) {
    			if (this.left == 1)
            		tankMotion.turn (3);
        		if (this.right == 1)
            		tankMotion.turn (-3);
        		if (this.up == 1) { //move up
            		tankMotion.move(1);
            	}
        		if (this.down == 1) { //move down
            		tankMotion.move(-1);
            	}
        		if (isFiring)
        			fire();
    		}
    		else {
    			respawnCounter--;
    			if (respawnCounter <=0 )
    				isRespawning = false;
    		}
    		
    }
    
    @Override
    /**
     * Fires the tank weapon
     */
    public void fire()
    {
    	if(respawnCounter<=0){
    		weapon.fireWeapon(this);
    		GameSounds.play("Resources/turret.wav");
    	}
    }
    
    @Override
    /**
     * Reset the tank location to resetPoint, tank health. Dispose of old tank weapon and gets new weapon
     * Also starts respawnCounter
     */
    public void reset(){
    	this.setLocation(resetPoint);
    	this.tankMotion.setDirection(resetDirection);
    	health=strength;
    	respawnCounter=160;
    	isRespawning = true;
    	weapon.dispose();
    	this.weapon = new TankWeapon();
    }
    
    @Override
    public void setLocation(Point loc) {
    	super.setLocation(loc);
    	tankMotion.synchronize(this);
    }
    
    @Override
    /**
     * Sets the tank image from the array of 60 images according to the direction of the tank, then draws the tank.
     */
    public void draw(Graphics g, ImageObserver obs) {
    	if (this.name == "player1")
    		this.setImage(TankWorld.getInstance().player1Image[tankMotion.getDirection()/6]);
    	else if (this.name == "player2")
    		this.setImage(TankWorld.getInstance().player2Image[tankMotion.getDirection()/6]);
    	super.draw(g, obs);
    }
}
