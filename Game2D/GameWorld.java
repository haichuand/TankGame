package Game2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Observer;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Game2D.objects.BackgroundObject;
import Game2D.objects.Bullet;
import Game2D.objects.Enemy;
import Game2D.objects.PlayerShip;
import Game2D.objects.PowerUp;
import Game2D.objects.SmallExplosion;
import Game2D.ui.InterfaceObject;

/**
 * GameWorld represents the gaming environment. It keeps track of game objects.
 * It has methods to add and remove game objects and iterate through them.
 * It also has methods to detect and resolve collision between objects, and draw game objects.
 * Extend GameWorld to create specific games using the Game2D engine.
 * @author Fudou
 */
public abstract class GameWorld extends JPanel implements Runnable, Observer {
	//game sprite image map
	public static HashMap<String,BufferedImage> sprites = new HashMap<String,BufferedImage>();
	protected boolean gameOver; //is game over
	protected static Point speed;
	private Random generator = new Random();
	//game sound and game clock are singletons
    public static final GameSounds sound = new GameSounds();
    public static final GameClock clock = new GameClock();
   
    public static int sizeX, sizeY; //size of window
    protected BufferedImage bimg; //image of game
    //ArrayLists to keep track of different types of game objects
    protected static ArrayList<BackgroundObject> backgrounds = new ArrayList<BackgroundObject>();
    protected static ArrayList<PlayerShip> players = new ArrayList<PlayerShip>();
    protected static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    protected static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    protected static ArrayList<BackgroundObject> explosions = new ArrayList<BackgroundObject>();
    protected static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    protected static ArrayList<InterfaceObject> uis = new ArrayList<InterfaceObject>();
    
	private static final long serialVersionUID = 1692137856541631175L;
	
	/**
	 * Load the sprite images.
	 */
    protected abstract void loadSprites();
    /**
     * Draws each frame of the game.
     * @param g2 The game graphics object
     */
    protected abstract void drawFrame(Graphics2D g2);
    
    /**
     * Add an observer to the game clock.
     * @param o The observer to be added
     */
    public static void addClockObserver (Observer o) {
    	clock.addObserver(o);
    }
    /**
     * Remove an observer from the game clock.
     * @param o The observer to be removed
     */
    public static void removeClockObserver (Observer o) {
    	clock.deleteObserver(o);
    }
    /**
     * Set GameWorld speed.
     * @param speed The new speed
     */
    public static void setSpeed(Point speed){
    	GameWorld.speed = speed;
    }
    /**
     * Get GameWorld speed
     * @return The GameWorld speed
     */
    public static Point getSpeed(){
    	return new Point(GameWorld.speed);
    }
    /**
     * Creates a Graphics2D object to use.
     * @param w Width
     * @param h Height
     * @return The Graphics2D object
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
     * Gets the sprite image.
     * @param filename The file name of the image
     * @return The BufferedImage object from the file
     * @throws IOException If the file cannot be read
     */
    public BufferedImage getSprite(String fileName) throws IOException {
    	return (ImageIO.read(this.getClass().getResource(fileName)));    	
    }
    /**
     * Add background object(s) to the BackgroundObject array list
     * @param newBackground Background object(s) to be added
     */
    public static void addBackground (BackgroundObject...newBackground) {
    	for (BackgroundObject obj : newBackground)
    		backgrounds.add(obj);
    }
    /**
     * Remove background object(s) from the BackgroundObject array list
     * @param objects Background objects to be removed
     */
    public static void removeBackground(BackgroundObject...objects) {
    	for (BackgroundObject obj : objects)
    		backgrounds.remove(obj);
	}
    /**
     * Add PlayerShip object(s) to the PlayerShip array list
     * @param newPlayer PlayerShip object(s) to be added
     */
    public static void addPlayer (PlayerShip...newPlayer) {
    	for (PlayerShip obj : newPlayer){
    		players.add(obj);    		
    	}
    }
    /**
     * Remove PlayerShip object(s) from the PlayerShip array list
     * @param thePlayer The PlayerShip object(s) to be removed
     */
	public static void removePlayer(PlayerShip...thePlayer) {
		for (PlayerShip obj : thePlayer){
			players.remove(obj);
			GameWorld.clock.deleteObserver(obj);
		}
	}

    public static void addEnemy (Enemy...newEnemy) {
    	for (Enemy obj : newEnemy){
    		enemies.add(obj);
    	}
    }
    
    public static void removeEnemy(Enemy...theEnemy) {
    	for (Enemy enemy : theEnemy) {
    		enemies.remove(enemy);
    		GameWorld.clock.deleteObserver(enemy);
    	}
	}
    
    public static void addBullet (Bullet...newBullet) {
    	for (Bullet obj : newBullet) {
    		bullets.add(obj);
    	}
    }
    
    public static void removeBullet(Bullet...theBullet) {
    	for (Bullet bullet : theBullet) {
    		bullets.remove(bullet);
    		GameWorld.clock.deleteObserver(bullet);
    	}
	}
    
    public static void addExplosion (BackgroundObject...newExplosion) {
    	for (BackgroundObject expl : newExplosion) {
    		explosions.add(expl);
    		GameWorld.clock.addObserver(expl);
    	}
    }
    
    public static void removeExplosion (BackgroundObject...theExplosion) {
    	for (BackgroundObject expl : theExplosion) {
    		explosions.remove(expl);
    		GameWorld.clock.deleteObserver(expl);
    	}
    }
    
	public static void addPowerUp(PowerUp...powerUp) {
		for (PowerUp pow : powerUp)	{
			powerUps.add(pow);
		}
	}
	
	public static void removePowerUp(PowerUp...thePow) {
		for (PowerUp pow : thePow) {
			powerUps.remove(pow);
			GameWorld.clock.deleteObserver(pow);
		}
	}
	
	public static void addUi(InterfaceObject...obj) {
		for (InterfaceObject ui : obj)	{
			uis.add(ui);
		}
	}
	
	public static void removeUi(InterfaceObject...obj) {
		for (InterfaceObject ui : obj) {
			uis.remove(ui);
		}
	}
	/**
	 * Gets the game clock frame number
	 * @return The frame number of the game clock
	 */
	public int getFrameNumber(){
    	return clock.getFrame();
    }
    
    public int getTime(){
    	return clock.getTime();
    }
    
    public ListIterator<BackgroundObject> getBackgroundObjects(){
    	return backgrounds.listIterator();
    }
    
    public ListIterator<Bullet> getBullets(){
    	return bullets.listIterator();
    }
    

    public ListIterator<BackgroundObject> getExplosions() {
		return explosions.listIterator();
	}
    
    
    public ListIterator<PlayerShip> getPlayers(){
    	return players.listIterator();
    }
    
    public ListIterator<Enemy> getEnemies(){
    	return enemies.listIterator();
    }
    
    public ListIterator<PowerUp> getPowerUps(){
    	return powerUps.listIterator();
    }
    
    public ListIterator<InterfaceObject> getUis(){
    	return uis.listIterator();
    }
    
    public void setDimensions(int w, int h){
    	sizeX = w;
    	sizeY = h;
    }
    
    /**
     * Detects collision between player and background objects. Once a collision is detected, it repositions the player.
     */
    public void playerBackgroundCollision() {
    	ListIterator <PlayerShip> iterator1 = getPlayers();
    	ListIterator <BackgroundObject> iterator2;
    	PlayerShip player;
    	BackgroundObject obj;
    	while(iterator1.hasNext()) { // iterate through background objects
        	player = iterator1.next();
        	iterator2 = getBackgroundObjects();
        	while(iterator2.hasNext()) { //detects and resolves player-background collision
        		obj = iterator2.next();
        		if (player.collision(obj))
        			player.getMotion().collisionReposition(obj);
        	}
    	}
    }
    
    /**
     * Detects collision between two players. Once a collision is detected, it repositions the player based on who is moving.
     *  
     */
    public void playerPlayerCollision() {
    	for (int i=0; i<players.size() && !players.get(i).isDead(); i++){ //detects and resolves player-player collision
        	for (int j=i+1; j<players.size() && !players.get(j).isDead(); j++) {
        		if (players.get(i).collision(players.get(j))) {
        			if (players.get(i).isMoving()) {
        				if (!players.get(j).isMoving()) {
        					players.get(i).getMotion().collisionReposition(players.get(j));
        				}
        				else {
        					int r = generator.nextInt();
        					if (r > 0)
        						players.get(i).getMotion().collisionReposition(players.get(j));
        					else
        						players.get(j).getMotion().collisionReposition(players.get(i));
        				}
        			}
        			else {
        				players.get(j).getMotion().collisionReposition(players.get(i));
        			}
        		}
        	}
        }
    }
    /**
     * Detects collision between player and enemy objects. Once a collision is detected, it simply repositions the player.
     */
    public void playerEnemyCollision() {
    	ListIterator <PlayerShip> iterator1 = getPlayers();
    	ListIterator <Enemy> iterator2;
    	PlayerShip player;
    	Enemy enemy;
    	while(iterator1.hasNext()) { // iterate through background objects
        	player = iterator1.next();
        	iterator2 = getEnemies();
        	while(iterator2.hasNext()) { //detects and resolves player-background collision
        		enemy = iterator2.next();
        		if (player.collision(enemy)){
        			player.getMotion().collisionReposition(enemy);
        		}        			
        	}
    	}
    }
    
    /**
     * Detects collision between player and PowerUp objects. Once a collision is detected,
     * the player gets the weapon from the PowerUp object, and the PowerUp object dissapears.
     */
    public void playerPowerUpCollision () {
    	ListIterator <PlayerShip> iterator1 = getPlayers();
    	ListIterator <PowerUp> iterator2;
    	PlayerShip player;
    	PowerUp powerUp;
    	while(iterator1.hasNext()) { // iterate through background objects
        	player = iterator1.next();
        	iterator2 = getPowerUps();
        	while(iterator2.hasNext()) { //detects and resolves player-background collision
        		powerUp = iterator2.next();
        		if (player.collision(powerUp)){
        			player.getWeapon().dispose();
        			player.setWeapon(powerUp.getWeapon());
        			powerUp.die();
        		}        			
        	}
    	}
    }
    
    /**
     * Detects collision between bullet and background objects. Generate explosion upon collision,
     * damage the ground object, and destroys the bullet
     */
    public void bulletBackgroundCollision() {
    	ListIterator<Bullet> iterator1= getBullets();
    	ListIterator<BackgroundObject> iterator2;
    	Bullet bullet;
    	BackgroundObject obj;
    	while (iterator1.hasNext()) {
    		bullet = iterator1.next();
    		iterator2 = getBackgroundObjects();
    		while (iterator2.hasNext()) {
    			obj = iterator2.next();
    			if (bullet.collision(obj) && !bullet.getOwner().equals(obj)) {
    				Point explosionPoint = bullet.getLocationPoint();
    				explosionPoint.translate(-16, -16);
    				GameWorld.addExplosion(new SmallExplosion(explosionPoint));
    				obj.damage(bullet.getStrength());
    				bullet.die();
    				break;
    			}
    		}
    	}
    }
    /**
     * Detects collision between bullet and players. Generate explosion upon collision, damage the player object only if 
     * (a) the palyer is not respawning; (b) the player is not the owner of the bullet.
     * Then destroys the bullet.
     */
    public void bulletPlayerCollision() {
    	ListIterator<Bullet> iterator1= getBullets();
    	ListIterator<PlayerShip> iterator2;
    	Bullet bullet;
    	PlayerShip player;
    	while (iterator1.hasNext()) {
    		bullet = iterator1.next();
    		iterator2 = getPlayers();
    		while (iterator2.hasNext()) {
    			player = iterator2.next();
    			if (!player.isRespawning() && bullet.collision(player) && !(bullet.getOwner().equals(player))) {
    				bullet.getOwner().addScore(bullet.getStrength());
    				GameWorld.addExplosion(new SmallExplosion(bullet.getLocationPoint()));
    				player.damage(bullet.getStrength());
    				bullet.die();
    				break;
    			}
    		}
    	}
    }
    
    public void drawBackgroundObjects(Graphics g, ImageObserver obs) {
    	ListIterator<BackgroundObject> iterator = getBackgroundObjects();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    
    public void drawPlayers(Graphics g, ImageObserver obs) {
    	ListIterator<PlayerShip> iterator = getPlayers();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    
    public void drawEnemies(Graphics g, ImageObserver obs) {
    	ListIterator<Enemy> iterator = getEnemies();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    
    public void drawBullets(Graphics g, ImageObserver obs) {
    	ListIterator<Bullet> iterator = getBullets();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    
    public void drawExplosions(Graphics g, ImageObserver obs) {
    	ListIterator<BackgroundObject> iterator = getExplosions();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    
	public void drawPowerUps(Graphics g, ImageObserver obs) {
    	ListIterator<PowerUp> iterator = getPowerUps();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
	
	public void drawUis(Graphics g, ImageObserver obs) {
    	ListIterator<InterfaceObject> iterator = getUis();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
}
