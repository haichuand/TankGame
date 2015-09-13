package tank;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import Game2D.GameWorld;
import Game2D.objects.PowerUp;


/**
 * TankLevel reads a text file to construct the play arena and tank player staring sites.
 * The text file contains line of characters of 6 types:
 * empty space corresponds to no object added, just background tile
 * '1' corresponds to wall
 * '2' corresponds to breakable wall
 * '3' corresponds to player1
 * '4' corresponds to player2
 * '5' corresponds to powerup
 * Each wall or breakable wall is 32X32 pixels, so total game arena is 32*wX32*h pixels
 * @author Fudou
 *
 */
public class TankLevel implements Observer {
	int start;
	Integer position;
	String filename;
	BufferedReader level;
	int w, h;	
	/**
	 * @param filename Name to give for the TankLevel
	 */
	public TankLevel(String filename){
		super();
		this.filename = filename;
		String line;
		try {
			level = new BufferedReader(new InputStreamReader(TankWorld.class.getResource(filename).openStream()));
			line = level.readLine();
			w = line.length();
			h=0;
			while(line!=null){
				h++;
				line = level.readLine();
			}
			level.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	/**
	 * Load the text file and constructs the game objects according to characters in the file
	 */
	public void load(){
		
		try {
			level = new BufferedReader(new InputStreamReader(TankWorld.class.getResource(filename).openStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		String line;
		try {
			line = level.readLine();
			w = line.length();
			h=0;
			while(line!=null){
				for(int i = 0, n = line.length() ; i < n ; i++) { 
				    char c = line.charAt(i); 
				    
				    if(c=='1'){
				    	Wall wall = new Wall(i,h);
				    	GameWorld.addBackground(wall);
				    }
				    
				    if(c=='2'){
				    	BreakableWall wall = new BreakableWall(i,h);
				    	GameWorld.addBackground(wall);
				    }
				    
				    if(c=='3'){
						int[] controls = {KeyEvent.VK_A,KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_SPACE};
						TankWorld.player1 = new Tank(new Point(i*32, h*32),0,GameWorld.sprites.get("player1"), controls, "player1");
						GameWorld.addPlayer(TankWorld.player1);
				    }
				    
				    if(c=='4'){
				    	int[] controls = new int[] {KeyEvent.VK_LEFT,KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER};
				    	TankWorld.player2 = new Tank(new Point(i*32, h*32),180,GameWorld.sprites.get("player2"), controls, "player2");
						GameWorld.addPlayer(TankWorld.player2);
				    }
				    
				    if(c=='5'){
				    	GameWorld.addPowerUp(new PowerUp(new Point(i*32, h*32), new Point(0,0), new FancyTankWeapon()));
				    }
				}
				h++;
				line = level.readLine();
			}
			level.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Empty method
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
