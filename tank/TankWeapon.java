package tank;

import java.awt.Point;

import Game2D.GameWorld;
import Game2D.objects.MoveableObject;
import Game2D.weapons.Weapon;

public class TankWeapon extends Weapon {
	public TankWeapon(){
		super(10, 15);
	}
	
	@Override
	public void fireWeapon(MoveableObject theTank) {
		Point location = theTank.getLocationPoint();
		int strength = 10;
		if (reloadCount >= reloadTime) {
			TankBullet bullet = new TankBullet(location, 10, strength, 0, (Tank) theTank);
			GameWorld.addBullet(bullet);
			reloadCount = 0;
		}
	}
}