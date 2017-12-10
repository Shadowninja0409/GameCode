package TileGame.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys, justPress, cantPress;
	public boolean up, down, left, right, w, s, a, d, t, space, enter, home;
	
	public KeyManager(){
		keys = new boolean[256];
		justPress = new boolean[keys.length];
		cantPress = new boolean[keys.length];

	}
	
	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode > keys.length)
			return false;
		return justPress[keyCode];
		}
	
	public void tick(){
		for(int i = 0; i < keys.length; i++){
			if(cantPress[i] && !keys[i]){
				cantPress[i] = false;
			}else if(justPress[i]){
				cantPress[i] = true;
				justPress[i] = false;
			}
			if(!cantPress[i] && keys[i]){
				justPress[i] = true;
			}
		}
		
		
		//movement keys
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		
		w = keys[KeyEvent.VK_W];
		s = keys[KeyEvent.VK_S];
		a = keys[KeyEvent.VK_A];
		d = keys[KeyEvent.VK_D];
		
		//attack
		space = keys[KeyEvent.VK_SPACE];
		
		//equip
		enter = keys[KeyEvent.VK_ENTER];
		
		// talk
		t = keys[KeyEvent.VK_T];
		home = keys[KeyEvent.VK_HOME];
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
