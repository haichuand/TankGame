package Game2D.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observable;

import Game2D.GameSounds;
import Game2D.GameWorld;

/** BigExplosion plays when player dies*/
public class BigExplosion extends BackgroundObject {
	int timer;
	int frame;
	//array of explosion image sequence
	static BufferedImage animation[] = new BufferedImage[] {GameWorld.sprites.get("explosion2_1"),
			GameWorld.sprites.get("explosion2_2"),
			GameWorld.sprites.get("explosion2_3"),
			GameWorld.sprites.get("explosion2_4"),
			GameWorld.sprites.get("explosion2_5"),
			GameWorld.sprites.get("explosion2_6"),
			GameWorld.sprites.get("explosion2_7")};
	/**
	 * Construct a big explosion at the location.
	 * @param location The location of the explosion
	 */
	public BigExplosion(Point location) {
		super(location, animation[0]);
		timer = 0;
		frame=0;
		GameSounds.play("Resources/snd_explosion2.wav");
		GameWorld.addExplosion(this);
		GameWorld.addClockObserver(this);
	}
	/**
	 * Sets the explosion image sequence. Removes the explosion after it is over.
	 */
	@Override
	public void update(Observable ob, Object arg){
		if (isDead) {
			GameWorld.removeExplosion(this);
			return;
		}
    	timer++;
    	if(timer%3==0){
    		frame++;
    		if(frame<7)
    			this.img = animation[frame];
    		else{
    			this.isDead = true;
    		}
    	}
	}
}
