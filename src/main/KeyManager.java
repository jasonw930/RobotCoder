package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Listens to and handles all of the key events
public class KeyManager implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

//	Description: Key pressed event, only manages the cheat system and user entering save name
//	Parameters: KeyEvent
//	Return: Void
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			Game.cheatManager.cheat();
		} else if (key >= 'A' && key <= 'Z' || key >= '0' && key <= '9' || key == '-') {
			Game.cheatManager.addChar(e.getKeyChar());
			if (Game.guiSaves.saving && Game.guiSaves.saving && Game.guiSaves.saveName.length() < 20) {
				Game.guiSaves.saveName += Character.toLowerCase(e.getKeyChar());
			}
		} else if (key == KeyEvent.VK_BACK_SPACE) {
			if (Game.guiSaves.saving && Game.guiSaves.saveName.length() > 0) {
				Game.guiSaves.saveName = Game.guiSaves.saveName.substring(0, Game.guiSaves.saveName.length() - 1);
			}
		}
		System.out.println(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
