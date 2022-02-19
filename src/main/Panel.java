package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

// Provides graphics object that all of the graphical user interfaces draw onto
@SuppressWarnings("serial")
public class Panel extends JPanel {

	public BufferedImage image;
	public Graphics2D graphics;
	public Color background;
	
	public Panel() {
		image = new BufferedImage(Game.screenWidth, Game.screenHeight, BufferedImage.TYPE_INT_ARGB);
		graphics = (Graphics2D) image.getGraphics();
		background = new Color(0x20, 0x20, 0x20);
		setBounds(0, 0, Game.screenWidth, Game.screenHeight);
	}
	
//	Description: Draws game
//	Parameters: Graphics object
//	Return: Void
	public void paintComponent(Graphics g) {
		// Draw the image
		g.drawImage(image, 0, 0, Game.screenWidth, Game.screenHeight, Game.frame);
		// Reset the buffer
		graphics.setColor(background);
		graphics.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
	}
	
}
