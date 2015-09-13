package Game2D.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observable;

import Game2D.GameSounds;
import Game2D.GameWorld;

/**
 * Small explosions happen on bullet collisions with objects
 * @author Fudou
 */
public class SmallExplosion extends BackgroundObject {
	
	int frame;
	//array of explosion animation images
	static BufferedImage animation[] = new BufferedImage[] {GameWorld.sprites.get("explosion1_1"),
		GameWorld.sprites.get("explosion1_2"),
		GameWorld.sprites.get("explosion1_3"),
		GameWorld.sprites.get("explosion1_4"),
		GameWorld.sprites.get("explosion1_5"),
		GameWorld.sprites.get("explosion1_6"),
		GameWorld.sprites.get("explosion1_7")};
	/**
	 * Construct a small explosion at the location.
	 * @param location The location of the explosion
	 */
	public SmallExplosion(Point location) {
		super(location, animation[0]);
		frame=0;
		GameSounds.play("Resources/snd_explosion2.wav");
	}
	/**
	 * Sets the explosion image sequence. Removes the explosion after it is over.
	 */
	@Override
	public void update(Observable o, Object arg){
		if (isDead) {
			GameWorld.removeExplosion(this);
			return;
		}
    	if(GameWorld.clock.getFrame()%3==0){
    		frame++;
    		if(frame<6)
    			this.img = animation[frame];
    		else {
    			isDead = true;
    		}
    	}

	}
}
