package Game2D.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observable;

import Game2D.GameWorld;
import Game2D.controllers.Motion;

/**Bullets are fired by player and enemy weapons*/
public class Bullet extends MoveableObject {
	protected GameObject owner;
	protected boolean friendly;
	/**
	 * 
	 * @param location Location of the bullet to be created
	 * @param speed Speed of the bullet
	 * @param strength Strength (damage points) of the bullet
	 * @param img Image of the bullet
	 * @param owner The object that fired the bullet
	 */
	public Bullet(Point location, Point speed, int strength, BufferedImage img, GameObject owner){
		super(location, speed, strength, 1, img);
		if(owner instanceof PlayerShip){
			this.owner = owner;
			this.friendly=true;
			this.setImage(GameWorld.sprites.get("bullet"));
		}
		motion = new Motion(this);
	}
	
	/**
	 * 
	 * @return The owner of the bullet
	 */
	public GameObject getOwner(){
		return owner;
	}
	
	public boolean isFriendly(){
		if(friendly){
			return true;
		}
		return false;
	}
	
	public int getStrength() {
		return strength;
	}
	/**
	 * Moves the bullet after each game clock tick, removes the bullet if it moves out of window.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (isDead) {
			GameWorld.removeBullet(this);
			return;
		}
		motion.move(1);
		if (location.x<0 || location.x>GameWorld.sizeX || location.y<0 || location.y>GameWorld.sizeY) {
			this.die();
		}
	}

	@Override
	public void die() {
		this.isDead = true;
	}

	@Override
	public void damage(int damageDone) {
		// TODO Auto-generated method stub
		
	}
}
