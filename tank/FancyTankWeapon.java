package tank;

import java.awt.Point;
import Game2D.GameWorld;
import Game2D.objects.MoveableObject;
import Game2D.weapons.Weapon;

/**
 * More powerful tank weapon with two bullets fired simultaneously
 * @author Fudou
 *
 */
public class FancyTankWeapon extends Weapon {
	/**
	 * FancyTankWeapon has strength of 10
	 */
	public FancyTankWeapon(){
		super(10, 5);
	}
	
	@Override
	/**
	 * Fires the weapon
	 * @param theTank The tank that owns the weapon.
	 */
	public void fireWeapon(MoveableObject theTank) {
		
		Point location = theTank.getLocationPoint();
		int strength = 10;
		if (reloadCount >= reloadTime) {
			TankBullet bullets[] = new TankBullet[2];
			bullets[0] = new TankBullet(location, 15, strength, -5, (Tank) theTank);
			bullets[1] = new TankBullet(location, 15, strength, 5, (Tank) theTank);
			GameWorld.addBullet(bullets);
			reloadCount = 0;
		}
	}
}
