package Game2D.controllers;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Game2D.objects.PlayerShip;

public class InputController implements KeyListener{
	//Field field; //similar to pointers in C++
	//Method action;
	//int moveState;
	int[] keys;
	PlayerShip player;
	
	public InputController(PlayerShip player, Component world){
		this(player, new int[] {KeyEvent.VK_LEFT,KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_SPACE}, world);
		//moveState = 0;
		
	}
	
	public InputController(PlayerShip player, int[] keys, Component world){
		this.player = player;
		//this.action = null;
		//this.field = null;
		this.keys = keys;
		world.addKeyListener(this);
	}
	/*
	private void setMove(String direction) {
		try{
			field = PlayerShip.class.getDeclaredField(direction);
			field.setAccessible(true);
			moveState=1;
			field.setInt(player, moveState);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private void unsetMove(String direction) {
		try{
			field = player.getClass().getDeclaredField(direction);
			field.setAccessible(true);
			moveState = 0;
			field.setInt(player, moveState);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private void setFire(){
		player.startFiring();
	}
	
	private void unsetFire(){
		player.stopFiring();
	}
	
	public void read(Object theObject) {
		PlayerShip player = (PlayerShip) theObject;
		
		try{
			field.setInt(player, moveState);
		} catch (Exception e) {
			//e.printStackTrace();
			try {
				action.invoke(player);
			} catch (Exception e2) {}
		}
	}
	*/
    public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		// left
		if(code==keys[0]) {
			player.left = 1;
		}
		// up
		else if(code==keys[1]) {
			player.up = 1;
		}
		// right
		else if(code==keys[2]) {
			player.right = 1;
		}
		// down
		else if(code==keys[3]) {
			player.down = 1;
		}
		// fire
		else if(code==keys[4]){
			player.startFiring();
		}
    }
    
    public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==keys[0]) {		//
			player.left = 0;
		}
		else if(code==keys[1]) {
			player.up = 0;
		}
		else if(code==keys[2]) {
			player.right = 0;
		}
		else if(code==keys[3]) {
			player.down = 0;
		}
		else if(code==keys[4]){
			player.stopFiring();
		}
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}