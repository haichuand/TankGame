package tank;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Observable;

import Game2D.GameWorld;
import Game2D.controllers.DirectionalMotion;
import Game2D.objects.Bullet;

public class TankBullet extends Bullet {
	private DirectionalMotion tankBulletMotion;
	private int degOffset;
	
	public TankBullet(Point tankLoc, int velocity, int strength, int offsetDeg, Tank owner){
		super(tankLoc, new Point(0,0), strength, GameWorld.sprites.get("bullet"), owner);
		this.degOffset = offsetDeg;
		int ownerDirection = owner.getMotion().getDirection();
		this.location.x = (int) (owner.getX() + 30 + 30*Math.cos(DirectionalMotion.DEG_TO_RAD*ownerDirection));
		this.location.y = (int) (owner.getY() + 30 - 30*Math.sin(DirectionalMotion.DEG_TO_RAD*ownerDirection));
		tankBulletMotion = new DirectionalMotion(this, velocity, ownerDirection+degOffset);
		tankBulletMotion.setPositionX(this.location.x);
		tankBulletMotion.setPositionY(this.location.y);
	}
	
	@Override
	public void update(Observable obj, Object arg) {
		if (isDead) {
			GameWorld.removeBullet(this);
			return;
		}
		tankBulletMotion.move(1);
	}
	
    public void draw(Graphics g, ImageObserver obs) {
    	if(show){
    		g.drawImage(img, location.x, location.y, null);
    	}
    }
}
