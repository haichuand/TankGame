package tank;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;

import Game2D.objects.PlayerShip;
import Game2D.ui.InterfaceObject;
import Game2D.GameWorld;

/**
 * Information bar with health, life and score for tank players.
 * @author Fudou
 *
 */
public class TankInfoBar extends InterfaceObject {
	PlayerShip player;
	String name;
	
	public TankInfoBar(PlayerShip player, String name, Point loc){
		super(loc, null);
		this.player = player;
		this.name = name;
		GameWorld.addUi(this);
		this.location = loc;
	}
	
	@Override
	/**
	 * Draws the Infobar
	 */
	public void draw(Graphics g2, ImageObserver ob){
        g2.setFont(new Font("Calibri", Font.PLAIN, 24));
        if(player.getHealth()>40){
        	g2.setColor(Color.GREEN);
        }
        else if(player.getHealth()>20){
        	g2.setColor(Color.YELLOW);
        }
        else{
        	g2.setColor(Color.RED);
        }
        g2.fillRect(location.x, location.y, (int) Math.round(player.getHealth()*1.1), 20);
        
        for(int i=0;i<player.getLives();i++){
        		g2.setColor(Color.ORANGE);
        		g2.fillOval(location.x+130 +i*30, location.y, 15, 15);
        }
        g2.setColor(Color.WHITE);
        g2.drawString(Integer.toString(player.getScore()), location.x+250, location.y+15);
	}

}
