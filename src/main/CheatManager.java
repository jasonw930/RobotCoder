package main;

import java.util.LinkedList;
import java.util.ListIterator;

// Manages and executes cheats used for debugging purposes
public class CheatManager {

	public LinkedList<Character> chars;
	public int length;
	
	public CheatManager() {
		chars = new LinkedList<Character>();
		length = 32;
		for (int i = 0; i < length; i++) {
			chars.add('-');
		}
	}
	
//	Description: Registers a keystroke
//	Parameters: The character being pressed
//	Return: Void
	public void addChar(char c) {
		chars.addLast(c);
		chars.removeFirst();
	}
	
//	Description: Looks at the last few keystrokes and see if a valid cheat is present, if so execute it
//	Parameters: None
//	Return: Void
	public void cheat() {
		char[] c = new char[32];
		ListIterator<Character> iter = chars.listIterator();
		while (iter.hasNext()) {
			c[iter.nextIndex()] = iter.next();
		}
		String str = new String(c).toLowerCase();
		if (str.endsWith("unlock")) {
			for (int i = 0; i < Game.levelManager.unlocked.length; i++) {
				Game.levelManager.unlocked[i] = true;
			}
		} else if (str.endsWith("complete")) {
			for (int i = 0; i < Game.levelManager.unlocked.length; i++) {
				Game.levelManager.unlocked[i] = true;
				Game.levelManager.completed[i] = true;
			}
		}
	}
	
}
