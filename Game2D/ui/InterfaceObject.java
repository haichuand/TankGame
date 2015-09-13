package Game2D.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

import Game2D.GameWorld;
/**
 * Objects related to user interfaces
 * @author Fudou
 *
 */
public class InterfaceObject {
	protected Point location;
	protected Image img;
	protected boolean show;
	/**
	 * @param loc Location of interface object
	 * @param img Image of interface object
	 */
	public InterfaceObject (Point loc, Image img) {
		this.location = loc;
		this.img = img;
		this.show = true;
		GameWorld.addUi(this);
	}
	
	public void setShow(boolean sh) {
		this.show = sh;
	}
	
	public boolean getShow() {
		return show;
	}
	
	/**
	 * Draws the object if the field show is true
	 * @param g
	 * @param ob
	 */
	public void draw(Graphics g, ImageObserver ob) {
		if (show)
			g.drawImage(img, location.x, location.y, ob);
	}
}
