package tank;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Observable;

import Game2D.GameWorld;
import Game2D.objects.BackgroundObject;

/**
 * Breakable walls have health points and can be damaged by tank bullets and die.
 * They automatically recover health points in the absence of bullet hits.
 * @author Fudou
 *
 */
public class BreakableWall extends BackgroundObject {
	private boolean isRecovering;
	/**
	 * Constructs a breakable wall object at the location Point(x*32, y*32)
	 * @param x X coordinate value
	 * @param y Y coordinate value
	 */
	public BreakableWall(int x, int y){
		super(new Point(x*32, y*32), GameWorld.sprites.get("wall2"));
		health = 10;
		isRecovering = false;
		GameWorld.addClockObserver(this);
	}
 	
	@Override
	/**
	 * Reduce health points of the breakable wall. When health<=0, calls die().
	 * @param damage The damage done or health points deducted
	 */
	public void damage(int damage) {
		health -= damage;
		if (health <= 0)
			die();
	}
    
	@Override
	/**
	 * Draws the wall only if the health>0
	 */
	public void draw(Graphics g2, ImageObserver ob) {
		if (health > 0)
			g2.drawImage(img, getX(), getY(), ob);
	}
	
	@Override
	/**
	 * update the breakable wall based on its health, isDead and isRecovering fields.
	 */
	public void update(Observable obj, Object arg) {
		if (isDead) {
			GameWorld.removeBackground(this);
			isDead = false;
			isRecovering = true;
		}
		if (health < 10) {
			if (GameWorld.clock.getFrame()%20 == 0)
				health++;
		}
		else if (isRecovering) {
			GameWorld.addBackground(this);
			isRecovering = false;
		}
	}
	
	public void die() {
		this.isDead = true;
	}
}
