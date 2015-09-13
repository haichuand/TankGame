package Game2D.objects;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;

import Game2D.GameSounds;
import Game2D.GameWorld;
import Game2D.controllers.InputController;
import Game2D.weapons.SimpleWeapon;
/**
 * PlayerShips are game players with input controls.
 * @author Fudou
 *
 */
public class PlayerShip extends MoveableObject {
	
    protected int lives;
    protected int score;
    protected Point resetPoint;
    /**
     * respawnCounter is used to count down to respawn time
     */
    protected int respawnCounter;
    protected int lastFired=0;
    protected boolean isFiring=false; //firing flag
    public int left=0,right=0,up=0,down=0; // movement flags
    protected String name;
    /**
     * Input controller for the player
     */
    protected InputController controller;
	protected boolean isRespawning;
    /**
     * Constructs a player with default 3 lives, 100 health, 100 strength
     * @param location Location of player
     * @param speed Speed of player
     * @param strength Strength of player
     * @param health Hit points of player
     * @param img Image of player
     * @param controls Integer array to designate the left, up, right, down and firing keys
     * @param name Name of player
     */
    public PlayerShip (Point location, Point speed, int strength, int health, BufferedImage img, 
    	int[] controls, String name) {
        super(location, speed, strength, health, img);
        resetPoint = new Point(location);
        this.name = name;
    	this.gunLocation = new Point(0,0);
        weapon = new SimpleWeapon();
        lives = 3;
        health = 100;
        strength = 100;
        score = 0;
        respawnCounter=0;
        isRespawning = false;
    }
    /**
     * If the player isDead field is false, draws the player. If the player is respawning, draws the player based on respawn counter
     */
    public void draw(Graphics g, ImageObserver observer) {
    	if (isDead) return;
    	if(respawnCounter<=0)
    		g.drawImage(img, super.getLocation().x, super.getLocation().y, observer);
    	else if(respawnCounter<=80){
    		if (GameWorld.clock.getFrame()%4 == 0)
    			g.drawImage(img, super.getLocation().x, super.getLocation().y, observer);
    	}
    }
    
    /**
     * Default damage behavior is to reduce health by specified points. When health is less than or equals 0,
     * calls the die() method.* Override the method to let the player die.
     * @param damageDone Damage points done to the player.
     */
    public void damage(int damageDone){
    	if(respawnCounter<=0) {
    		health -= damageDone;
    		if (health <= 0)
    			this.die();
    	}
    }
        
    public void startFiring(){
    	isFiring=true;
    }
    
    public void stopFiring(){
    	isFiring=false;
    }
    /**
     * Fires the weapon
     */
    public void fire()
    {
    	if(respawnCounter<=0){
    		weapon.fireWeapon(this);
    		GameSounds.play("Resources/snd_explosion1.wav");
    	}
    }
    /**
     * Resets the location of player to resetPoint, as well as health points. Starts the respawn counter and
     * sets isRespawning true. Resets the weapon to SimpleWeapon.
     */
    public void reset(){    	
    	this.setLocation(resetPoint);
    	health=strength;
    	respawnCounter=160;
    	isRespawning = true;
    	weapon.dispose();
    	this.weapon = new SimpleWeapon();
    }
    /**
     * Creates a BigExplosion. If the player has lives left, reset() is called. Otherwise sets isDead true.
     */
    @Override
    public void die(){
    	this.show=false;
    	GameWorld.setSpeed(new Point(0,0));
    	Point explosionPoint = new Point(location.x,location.y);    	
    	new BigExplosion(explosionPoint);
    	lives--;
    	if(lives > 0) {
    		reset();
    	}
    	else{
    		this.isDead = true;
    	}
    }
    
    public int getLives(){
    	return this.lives;
    }
    
    public int getScore(){
    	return this.score;
    }
    
    public String getName(){
    	return this.name;
    }
    /**
     * Increase player score
     * @param increment The increased score amount
     */
    public void incrementScore(int increment){
    	score += increment;
    }
    /**
     * Sets the respawn counter
     * @param num New respawn counter value
     */
    public void setRespawnCounter(int num) {
    	respawnCounter = num;
    	isRespawning = true;
    }
    
	public boolean isMoving() {
    	return (up==1 || down==1);
    }
	

	/**
	 * update() is called by observable objects (e.g. by game clock every tick).
	 * If the player is not dead or respawning, moves the player one step as specified by motion. Fires the weapon if isFiring is true.
	 * If the player is dead, removes the player from players list in GameWorld.
	 * If the player is respawning, decrease the respawCounter by one if respawnCounter is greater than or equals 0.
	 */
	@Override
	public void update (Observable ob, Object arg) {
		if (isDead) {
			GameWorld.removePlayer(this);
			return;
		}
		if (!isRespawning) {
			motion.move(1);
			if (isFiring)
				fire();
		}
		else {
			respawnCounter--;
			if (respawnCounter <=0 )
				isRespawning = false;
		}
	}

	public void setLocation(Point loc) {
		super.setLocation(loc);
	}
	
	@Override
	public void addScore(int score) {
		this.score += score;
	}
	/**
	 * If the player is respawning
	 * @return True if the player is respawning, false otherwise
	 */
	public boolean isRespawning() {
		return isRespawning;
	}
}
