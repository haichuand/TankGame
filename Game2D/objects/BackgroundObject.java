package Game2D.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * Background objects are immovable objects of the game.
 * @author Fudou
 *
 */
public class BackgroundObject extends GameObject {
	protected int health;
	/**
	 * Constructs a background object with health set to Ingeter.MAX_VALUE
	 * @param loc Location of the background object
	 * @param img Image of the background object
	 */
	public BackgroundObject(Point loc, BufferedImage img) {
		super(loc, img);
		health = Integer.MAX_VALUE;
	}
	
	/**
	 * Background objects cannot be damaged. The method simply returns.
	 * @param damage
	 */
	public void damage (int damage) {
	}
/**
 * Empty method
 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
