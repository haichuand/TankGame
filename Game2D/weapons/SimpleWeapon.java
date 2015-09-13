package Game2D.weapons;

import java.awt.Image;
import java.awt.Point;
import java.util.Observer;

import Game2D.GameWorld;
import Game2D.objects.Bullet;
import Game2D.objects.MoveableObject;


public class SimpleWeapon extends Weapon {
	
	
	public SimpleWeapon(){
		this(5, 10);
	}
	
	public SimpleWeapon(int reload){
		this(5, reload);
	}
	
	public SimpleWeapon(int strength, int reload){
		super(strength, reload);
	}
	
	@Override
	public void fireWeapon(MoveableObject theShip) {
		
		Point location = theShip.getLocationPoint();
		Point offset = theShip.getGunLocation();
		location.x += offset.x;
		location.y += offset.y;
		Point speed = new Point(theShip.getSpeed().x+20, theShip.getSpeed().y+20);
		
		Bullet bullet = new Bullet(location, speed, strength, GameWorld.sprites.get("bullet"), theShip);
		GameWorld.addBullet(bullet);
	}
}
