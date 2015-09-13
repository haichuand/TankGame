package Game2D.objects;

import java.awt.Point;
import java.util.Observable;

import Game2D.GameWorld;
import Game2D.weapons.Weapon;

/**
 * PowerUp holds an (upgraded) weapon to give to player.
 * @author Fudou
 *
 */
public class PowerUp extends MoveableObject {
	protected Weapon weapon;
	/**
	 * 
	 * @param location Location of PowerUp
	 * @param speed Speed of PowerUp
	 * @param weapon Weapon held by the PowerUp
	 */
	public PowerUp(Point location, Point speed, Weapon weapon){
		super(location, speed, 0, 0, GameWorld.sprites.get("powerup"));
		this.weapon = weapon;
	}
	
	public Weapon getWeapon() {
		return this.weapon;
	}
	/**
	 * If PowerUp is dead, remove it from PowerUp array list in GameWorld. Otherwise moves PowerUp by one step.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (isDead) {
			GameWorld.removePowerUp(this);
			return;
		}
		motion.move(1);
	}
	
	public void die(){
    	this.isDead = true;
	}

	@Override
	public void damage(int damageDone) {
		// TODO Auto-generated method stub
		
	}

}
