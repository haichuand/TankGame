package Game2D.controllers;

import java.awt.Point;
import java.awt.Rectangle;

import Game2D.objects.GameObject;
import Game2D.objects.MoveableObject;

/**
 * DirectionalMotion is vectorial motion. It is specified by two parameters: direction(0~360 degrees) and velocity in int units.
 * It calculates the coordinate positions x and y in double precision numbers and synchronizes with the object location.
 * @author Fudou
 */
public class DirectionalMotion extends Motion {
	public static final double DEG_TO_RAD = 0.0174532925;
	private double positionX, positionY; //precision location
	private int velocity;
	private int direction;
	/**
	 * Constructs a DirectionalMotion object with reference to the subject
	 * @param subject The subject of DirectionalMotion
	 * @param velocity Absolute movement speed
	 * @param direction Heading (0~360 degrees) of the subject. 0 degree is 3 o'clock position; 90 degree is 12 o'clock position, etc.
	 */
	public DirectionalMotion (MoveableObject subject, int velocity, int direction) {
		super (subject);
		setPositionX(subject.getLocation().x);
		setPositionY(subject.getLocation().y);
		this.velocity = velocity;
		this.setDirection(direction);
	}
	/**
	 * Sync the DirectionalMotion object with location of the subject.
	 * This is called when an event other than move changes the location of the subject, e.g. after reset.
	 * @param obj The subject of the DirectionalMotion object.
	 */
	public void synchronize (MoveableObject obj) {
		setPositionX(obj.getLocation().x);
		setPositionY(obj.getLocation().y);
	}
	/**
	 * Moves subject the number of steps. Each step is one translocation of speed.
	 * @param steps The number of steps to move the subject. Negative number means move opposite to the direction.
	 */
	@Override
	public void move (int steps) {
		double dx = velocity*Math.cos(DEG_TO_RAD*getDirection());
		double dy = -velocity*Math.sin(DEG_TO_RAD*getDirection());
		setPositionX(getPositionX() + steps*dx);
		subject.getLocation().x = (int) getPositionX();
        setPositionY(getPositionY() + steps*dy);
        subject.getLocation().y = (int) getPositionY();
	}
	/**
	 * Turns the subject the number of degrees.
	 * @param degree The number of degrees to turn the subject. Positive numbers are clockwise; negative numbers are counterclockwise.
	 */
	public void turn (int degree) {
		int newDegree = getDirection() + degree;
		if (newDegree < 0)
			newDegree += 360;
		else if (newDegree >= 360)
			newDegree -= 360;
		setDirection(newDegree);
	}
	
	/**
	 * Reposition the MoveableObject relative to the other object after a collision is detected.
	 * It translocates the subject on the x or y axis based on which movement is smaller. Also synchronizes positionX and positionY.
	 * @param otherObj The object to position against (not moved).
	 */
	@Override
	public void collisionReposition(GameObject otherObj) {
    	Rectangle myLoc = subject.getLocation(), otherLoc = otherObj.getLocation();
    	Point myCenter = new Point((myLoc.x + myLoc.width/2), (myLoc.y + myLoc.height/2));
    	Point otherCenter = new Point((otherLoc.x + otherLoc.width/2), (otherLoc.y + otherLoc.height/2));
    	
    	if (Math.abs(Math.abs(myCenter.x-otherCenter.x)-(myLoc.width+otherLoc.width)/2) <= 
    			Math.abs(Math.abs(myCenter.y-otherCenter.y)-(myLoc.height+otherLoc.height)/2)) { //reposition myLoc.x
    		if (myCenter.x < otherCenter.x) {
    			subject.setLocation(new Point(otherLoc.x-myLoc.width, myLoc.y));
    			this.synchronize(subject);
    		}
    		else {
    			subject.setLocation(new Point(otherLoc.x+otherLoc.width, myLoc.y));
    			this.synchronize(subject);
    		}
    	}
    	else { //reposition myLoc.y
    		if (myCenter.y < otherCenter.y) {
    			subject.setLocation(new Point(myLoc.x, otherLoc.y-myLoc.height));
    			this.synchronize(subject);
    		}
    		else {
    			subject.setLocation(new Point(myLoc.x, otherLoc.y+otherLoc.height));
    			this.synchronize(subject);
    		}
    	}
    }
	/**
	 * @return the positionX
	 */
	public double getPositionX() {
		return positionX;
	}
	/**
	 * @param positionX the positionX to set
	 */
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	/**
	 * @return the positionY
	 */
	public double getPositionY() {
		return positionY;
	}
	/**
	 * @param positionY the positionY to set
	 */
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}
	/**
	 * @param direction The direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getVelocity() {
		return velocity;
	}

}
