package Game2D.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;

import Game2D.GameWorld;
import Game2D.controllers.Motion;
import Game2D.weapons.Weapon;

/**
 * Moving objects in the game. The movement is controlled by Motion object.
 * @author Fudou
 *
 */
public abstract class MoveableObject extends GameObject {
	/**
	 * motion determines movement of the moveable object
	 */
	protected Motion motion;
	protected Point speed;
	protected int strength;
	protected int health;
	protected Weapon weapon;
    protected Point gunLocation;
    
    /**
     * 
     * @param location Location of the object
     * @param speed Speed of the object
     * @param strength Strength of the object
     * @param health Health points of the object
     * @param img Image of the object
     */
    public MoveableObject(Point location, Point speed, int strength, int health, BufferedImage img){
    	super(location,img);
    	this.speed=speed;
    	this.strength=strength;
    	this.health=health;
    	this.motion = new Motion(this);
    	GameWorld.clock.addObserver(this);
    }
    
    public abstract void damage(int damageDone);
    
    /**
     * Subclasses of MoveableObject must implement die method.
     */
    public abstract void die();
    
    public void setHealth(int health){
    	this.health = health;
    }
    
    public int getHealth(){
    	return health;
    }
    
    public Motion getMotion(){
    	return this.motion;
    }
    
    public void setMotion(Motion motion){
    	this.motion = motion;
    }
    
  	public Point getSpeed() {
		return speed;
	}
  	
  	public void setSpeed (Point speed) {
  		this.speed = speed;
  	}
  	

    public Weapon getWeapon(){
    	return this.weapon;
    }
    
    public void setWeapon(Weapon weapon) {
    	this.weapon = weapon;
    }
    
    public Point getGunLocation(){
    	return this.gunLocation;
    }

}