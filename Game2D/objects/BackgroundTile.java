package Game2D.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;

/**
 * BackgroundTile are not added to BackgroundObject arraylist in GameWorld.
 * They are not included in collision detection.
 * @author Fudou
 */
public class BackgroundTile extends GameObject {
		
	int w, h;
	/**
	 * Construct background tiles within the specified window size by filling the window with the tiles.
	 * @param w Width of the window
	 * @param h Height of the window
	 * @param img Image of the tile
	 */
	public BackgroundTile(int w, int h, BufferedImage img) {
		super(new Point(0,0), img);
		this.w = w;
		this.h = h;
	}
	
	/**
	 * Draws as many background tiles as possible to fill the window.
	 */
    public void draw(Graphics g, ImageObserver obs) {
        int TileWidth = img.getWidth(obs);
        int TileHeight = img.getHeight(obs);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);
        
        for (int i = 0; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g.drawImage(img, j * TileWidth, i * TileHeight, TileWidth, TileHeight, obs);
            }
        }
    }
	
	/**
	 * Empty method
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
