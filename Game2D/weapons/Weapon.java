package Game2D.weapons;

import java.util.Observable;
import java.util.Observer;

import Game2D.GameWorld;
import Game2D.objects.MoveableObject;

/*Weapons are fired by motion controllers on behalf of players or ships
 * They observe motions and are observed by the Game World
 */
public abstract class Weapon implements Observer {
	
	protected boolean friendly;
	protected int reloadTime;
	
	protected int strength;
	protected int reloadCount;
	
	public Weapon(int strength, int reloadTime){
		this.reloadTime = reloadTime;
		this.strength = strength;
		reloadCount = reloadTime;
		GameWorld.addClockObserver(this);
	}
	
	@Override
	public void update (Observable ob, Object arg) {
		if (reloadCount < reloadTime)
			reloadCount++;
	}
	
	public void dispose() {
		GameWorld.removeClockObserver(this);
	}
	
	public abstract void fireWeapon (MoveableObject theShip);
	
}
