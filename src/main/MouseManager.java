package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gui.Gui;

// Handles all of the mouse information, including movement information and click information
public class MouseManager implements MouseListener, MouseMotionListener {
	
//	Description: Different mouse events, mostly just two satisfy interface
//	Parameters: MouseEvent
//	Return: Void
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

//	Description: Mouse pressed event, responsible for passing mouse presses to specific GUI's
//	Parameters: MousEvent
//	Return: Void
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse Pressed At (" + e.getX() + ", " + e.getY() + ")");
		for (int i = Game.guiList.size() - 1; i >= 0; i--) {
			Gui gui = Game.guiList.get(i);
			if (gui.withinBounds(e.getX(), e.getY())) {
				gui.mousePressed(e.getX() - gui.x , e.getY() - gui.y, e.getButton());
				return;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Game.mouseX = e.getX();
		Game.mouseY = e.getY();
	}

}
