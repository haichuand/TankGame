package Game2D;

import java.util.Observable;

/**
 * Game clock ticks on every frame and notifies observers to update.
 * All MoveableObject (sub)class instances should observer game clock.
 */
public class GameClock extends Observable {
	private int startTime;
	private int frame;
	
	public GameClock(){
		startTime = (int) System.currentTimeMillis();
		frame = 0;
	}
	/**
	 * Increases frame and notify observers
	 */
	public void tick(){
		frame++;
		setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Gets the frame count of game clock.
	 * @return The frame count of the clock
	 */
	public int getFrame(){
		return this.frame;
	}
	/**
	 * Gets the time of the game clock.
	 * @return The time since the clock started, in milliseconds.
	 */
	public int getTime(){
		return (int)System.currentTimeMillis()-startTime;
	}
}
