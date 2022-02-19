package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import main.Game;

// GUI for the main menu that the player sees when they start up the game
public class GuiMainMenu extends Gui {

	public Font sansSerifBig;
	public Font sansSerif;
	public Color background;
	public Rectangle buttonStartGame;
	
	public GuiMainMenu() {
		super();

		x = 0;
		y = 0;
		width = Game.screenWidth;
		height = Game.screenHeight;
		visible = true;
		
		sansSerifBig = new Font("SansSerif", Font.BOLD, 80);
		sansSerif = new Font("SansSerif", Font.BOLD, 28);
		background = new Color(0x202020);
		buttonStartGame = new Rectangle((width - 400) / 2, height - 210, 400, 60);
	}
	
	@Override
	public void drawImage() {
		graphics.translate(x, y);
		AffineTransform at = graphics.getTransform();
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		// Draw game name and play button
		graphics.setColor(Color.WHITE);
		graphics.setFont(sansSerifBig);
		graphics.drawString("ROBOT CODER", (width - graphics.getFontMetrics().stringWidth("ROBOT CODER")) / 2, height / 2 + graphics.getFontMetrics().getHeight() / 3);
		
		drawButton(buttonStartGame, sansSerif, Color.WHITE, Color.BLACK, "Play");
		
		graphics.setTransform(at);
		graphics.translate(-x, -y);
	}
	
	public void mousePressed(int x, int y, int button) {
		// Start the game
		if (buttonStartGame.contains(x, y)) {
			visible = false;
		}
	}
	
}
