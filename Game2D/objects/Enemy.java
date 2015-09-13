package Game2D.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * Enemy objects in the game
 * @author Fudou
 *
 */
public class Enemy extends MoveableObject {

	public Enemy(Point location, Point speed, int strength, int health,
			BufferedImage img) {
		super(location, speed, strength, health, img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(int damageDone) {
		// TODO Auto-generated method stub
		
	}

}
