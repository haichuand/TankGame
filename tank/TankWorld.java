package tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.util.Observable;
import java.util.Random;

import javax.swing.*;

import Game2D.*;
import Game2D.objects.BackgroundTile;
import Game2D.ui.InterfaceObject;
/**
 * TankWorld is where the tank game takes place. It has two tank players
 * @author Fudou
 *
 */
public class TankWorld extends GameWorld {

	private static final long serialVersionUID = 1L;

	static Tank player1, player2;

	private Thread thread;
	private static final TankWorld game = new TankWorld();
    public TankLevel level;
    //bimg has map of whole game. player1view and player2view are viewing areas for each player
    private BufferedImage bimg, player1view, player2view;
    BufferedImage player1Image[] = new BufferedImage[60], //image arrays for tanks with different directions
    		player2Image[] = new BufferedImage[60];
    Random generator = new Random();
    TankInfoBar bar1, bar2; //information bar for player1 and player2
    InterfaceObject gameoverInfo; //Game over image
    Point mapSize; //size of map
    ImageObserver observer;
    BackgroundTile tile; //background tile is separate from BackgroundObject list; not included in collision detection
        
    /**
     * construct TankWorld as a singleton
     */
    private TankWorld() {
    	super();
        this.setFocusable(true);
    }
    
    /* This returns a reference to the currently running game*/
    public static TankWorld getInstance(){
    	return game;
    }

    /*Game Initialization*/
    public void init() {
        setBackground(Color.white);
        loadSprites();
        gameOver = false;
        observer = this;
        
        GameWorld.setSpeed(new Point(0,0));
        level = new TankLevel("Resources/level.txt");
        mapSize = new Point(level.w*32,level.h*32);
        level.load();
        tile = new BackgroundTile (mapSize.x, mapSize.y, sprites.get("background"));
        //creates tank image array for different directions
        for (int i=0; i<60; i++) {
        	player1Image[i] = sprites.get("player1").getSubimage(i*64, 0, 64, 64);
        }
        for (int i=0; i<60; i++) {
        	player2Image[i] = sprites.get("player2").getSubimage(i*64, 0, 64, 64);
        }
        //creates inforbars for the two players
        bar1 = new TankInfoBar(player1, "1", new Point(60, 545));
        bar2 = new TankInfoBar(player2, "2", new Point(560, 545));
        //game over ui
        gameoverInfo = new InterfaceObject(new Point(300, 200), sprites.get("gameover"));
        gameoverInfo.setShow(false);
    }
    
    /*Functions for loading image resources*/
    @Override
    protected void loadSprites() {
    	try {
    		
    		sprites.put("background", getSprite("Resources/Background.png"));
    	    
    	    sprites.put("wall", getSprite("Resources/Blue_wall1.png"));
    	    sprites.put("wall2", getSprite("Resources/Blue_wall2.png"));
    	    
    	    sprites.put("bullet", getSprite("Resources/bullet.png"));
    	    sprites.put("powerup", getSprite("Resources/powerup.png"));
    	    
    	    sprites.put("explosion1_1", getSprite("Resources/explosion1_1.png"));
    		sprites.put("explosion1_2", getSprite("Resources/explosion1_2.png"));
    		sprites.put("explosion1_3", getSprite("Resources/explosion1_3.png"));
    		sprites.put("explosion1_4", getSprite("Resources/explosion1_4.png"));
    		sprites.put("explosion1_5", getSprite("Resources/explosion1_5.png"));
    		sprites.put("explosion1_6", getSprite("Resources/explosion1_6.png"));
    	    sprites.put("explosion2_1", getSprite("Resources/explosion2_1.png"));
    		sprites.put("explosion2_2", getSprite("Resources/explosion2_2.png"));
    		sprites.put("explosion2_3", getSprite("Resources/explosion2_3.png"));
    		sprites.put("explosion2_4", getSprite("Resources/explosion2_4.png"));
    		sprites.put("explosion2_5", getSprite("Resources/explosion2_5.png"));
    		sprites.put("explosion2_6", getSprite("Resources/explosion2_6.png"));
    		sprites.put("explosion2_7", getSprite("Resources/explosion2_7.png"));
    		sprites.put("player1", getSprite("Resources/Tank_blue_basic_strip60.png"));
    		sprites.put("player2", getSprite("Resources/Tank_red_basic_strip60.png"));
    		sprites.put("gameover", getSprite("Resources/gameover.png"));
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
    
    /**
     * Draws each frame after the game clock ticks. It draws the complete game image, which is subimaged to get the two player views.
     * It first draws the background tile, then calls functions in GameWorld to handle collisions between different types of object.
     * Then it draws background, players, bullets, powerups and explosions in that order.
     */
    public void drawFrame(Graphics2D g2) {
        tile.draw(g2, this);
        
        playerBackgroundCollision();
        playerPlayerCollision();
        playerPowerUpCollision();
        bulletBackgroundCollision();
        bulletPlayerCollision();        	
        
        drawBackgroundObjects(g2, this);
        drawPlayers(g2, this);
        drawBullets(g2, this);
        drawPowerUps(g2, this);
        drawExplosions(g2, this);
    }
    /**
     * Creates a Graphics2D object for drawing game frames on.
     */
    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
            bimg = (BufferedImage) createImage(w, h);
        }
        g2 = bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    /**
     * Paint each frame. 
     */
    public void paint(Graphics g) {
        if(players.size()!=0)
        	clock.tick();
    	Dimension windowSize = getSize();
        Graphics2D g2 = createGraphics2D(mapSize.x, mapSize.y);
        drawFrame(g2);
        g2.dispose();
        
        
        int p1x = players.get(0).getX() - windowSize.width/4 > 0 ? players.get(0).getX() - windowSize.width/4 : 0;
        int p1y = players.get(0).getY() - windowSize.height/2 > 0 ? players.get(0).getY() - windowSize.height/2 : 0;
        
        if(p1x > mapSize.x-windowSize.width/2){
        	p1x = mapSize.x-windowSize.width/2;
        }
        if(p1y > mapSize.y-windowSize.height){
        	p1y = mapSize.y-windowSize.height;
        }
        
        int p2x = players.get(1).getX() - windowSize.width/4 > 0 ? players.get(1).getX() - windowSize.width/4 : 0;
        int p2y = players.get(1).getY() - windowSize.height/2 > 0 ? players.get(1).getY() - windowSize.height/2 : 0;
        
        if(p2x > mapSize.x-windowSize.width/2){
        	p2x = mapSize.x-windowSize.width/2;
        }
        if(p2y > mapSize.y-windowSize.height){
        	p2y = mapSize.y-windowSize.height;
        }
        
        player1view = bimg.getSubimage(p1x, p1y, windowSize.width/2, windowSize.height);
        player2view = bimg.getSubimage(p2x, p2y, windowSize.width/2, windowSize.height);
        g.drawImage(player1view, 0, 0, this);
        g.drawImage(player2view, windowSize.width/2, 0, this);
        g.drawRect(windowSize.width/2-1, 0, 1, windowSize.height);//split player1 and player2 views
        g.drawImage(bimg, windowSize.width/2-80, windowSize.height-160, 160, 160, this); //draws the mini map
        
        drawUis(g, this); //Infobar is drawn the last
    }

    /**
     * Start the game thread
     */
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    /**
     * Runs the game
     */
    public void run() {
    	
        Thread me = Thread.currentThread();
        while (thread==me) {
        	if (gameOver) {
        		endGame();
        		gameOver = false;
        	}
        	this.requestFocusInWindow();
            repaint();
          
          try {
                Thread.sleep(25); // pause a little to slow things down
            } catch (InterruptedException e) {
                break;
            }
            
        }
    }
    
    /**
     * Sets the gameOver flag
     * @param ov New state of gameOver flag
     */
    public void setGameOver(boolean ov){
    	this.gameOver = ov;
    }
    /**
     * Check if the game is over
     * @return the gameOver flag
     */
    public boolean isGameOver(){
    	return gameOver;
    }
    
    /**
     * Shows gameoverInfo, sets the dead player's respawnCounter to Integer.MAX_VALUE
     */
    public void endGame(){
    	gameoverInfo.setShow(true);
    	for (int i=0; i<players.size(); i++){
    		if (players.get(i).isDead()){
    			players.get(i).setRespawnCounter (Integer.MAX_VALUE);
    		}
    	}
    }
    
	public static void main(String argv[]) {
		final TankWorld game = TankWorld.getInstance();
	    JFrame f = new JFrame("Tank Game");
	    f.addWindowListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        game.requestFocusInWindow();
		    }
	    });
	    f.getContentPane().add("Center", game);
	    f.pack();
	    f.setSize(new Dimension(900, 600));
	    game.setDimensions(800, 600);
	    game.init();
	    f.setVisible(true);
	    f.setResizable(false);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameSounds.play("Resources/castlemusic.wav");
	    game.start();
	}

	@Override
	/**
	 * Empty method
	 */
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}