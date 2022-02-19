package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Game;

// A generic graphical user interface class that is used to draw parts of the game to the screen
public class Gui {

	public Graphics2D graphics;
	public int x, y, width, height;
	public boolean visible;

	public Gui() {
		graphics = Game.panel.graphics;
		visible = true;
	}
	
//	Description: Called once every frame, draws the gui
//	Parameters: None
//	Return: Void
	public void drawImage() {
		
	}
	
//	Description: Called when a mouse is pressed inside a Gui
//	Parameters: x and y position of mouse position relative to top left as int, as well as the mouse button pressed as int
//	Return: Void
	public void mousePressed(int x, int y, int button) {
		
	}
	
//	Description: Checks to see if (x, y) is within the boundaries of a Gui object
//	Parameters: x and y pixel position as integers
//	Return: Boolean, whether or not (x, y) is within bounds
	public boolean withinBounds(int x, int y) {
		return visible && x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
	}
	
	public void drawButton(Rectangle rect, Font f, Color backCol, Color textCol, String text) {
		graphics.setColor(backCol);
		graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
		graphics.setColor(textCol);
		graphics.setFont(f);
		graphics.drawString(text, (int) (rect.getCenterX() - graphics.getFontMetrics().stringWidth(text)/2), (int) rect.getCenterY() + graphics.getFontMetrics().getHeight() / 3);
	}
	
}
