package tank;

import java.awt.Point;

import Game2D.objects.BackgroundObject;

/**
 * Walls are unbreakable by tank bullets
 * @author Fudou
 *
 */
public class Wall extends BackgroundObject {
	/**
	 * Construct a wall at location Point(x*32, y*32)
	 * @param x The x axis of location
	 * @param y The y axis of location
	 */
	public Wall(int x, int y){
		super(new Point(x*32, y*32), TankWorld.sprites.get("wall"));
	}
	
}
