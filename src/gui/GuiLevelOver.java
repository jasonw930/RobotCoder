package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import main.Game;

// GUI that is displayed when a level is beat
public class GuiLevelOver extends Gui {

	public Font sansSerifBig;
	public Font sansSerif;
	public Color background;
	public Rectangle buttonReturn;
	public Rectangle buttonMainMenu;
	
	public GuiLevelOver() {
		super();

		x = 0;
		y = 0;
		width = Game.screenWidth;
		height = Game.screenHeight;
		visible = false;
		
		sansSerifBig = new Font("SansSerif", Font.BOLD, 80);
		sansSerif = new Font("SansSerif", Font.BOLD, 28);
		background = new Color(0x202020);
		buttonReturn = new Rectangle((width - 400) / 2, height - 210, 400, 60);
		buttonMainMenu = new Rectangle((width - 400) / 2, height - 120, 400, 60);
	}
	
	@Override
	public void drawImage() {
		graphics.translate(x, y);
		AffineTransform at = graphics.getTransform();
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		// Draw level complete text and buttons
		graphics.setColor(Color.WHITE);
		graphics.setFont(sansSerifBig);
		graphics.drawString("LEVEL COMPLETE", (width - graphics.getFontMetrics().stringWidth("LEVEL COMPLETE")) / 2, height / 2 + graphics.getFontMetrics().getHeight() / 3);
		
		drawButton(buttonReturn, sansSerif, Color.WHITE, Color.BLACK, "Return To Level");
		drawButton(buttonMainMenu, sansSerif, Color.WHITE, Color.BLACK, "Main Menu");
		
		graphics.setTransform(at);
		graphics.translate(-x, -y);
	}
	
	public void mousePressed(int x, int y, int button) {
		// Handle button presses, either returning to level or returning to level select menu
		if (buttonReturn.contains(x, y)) {
			Game.levelManager.currentMap.reset();
			visible = false;
		} else if (buttonMainMenu.contains(x, y)) {
			Game.levelManager.deselectLevel();
		}
	}
	
}
