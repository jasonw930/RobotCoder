package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import main.Game;
import map.Map;

// GUI for the user to pick which level to play
public class GuiLevelSelect extends Gui {

	public Font sansSerif;
	public Color background;
	public Image complete;
	public Image level;
	
	public int size;
	public int space;
	public int rowSize;
	public Rectangle levelRegion;
	
	public GuiLevelSelect() {
		super();

		x = 0;
		y = 0;
		width = Game.screenWidth;
		height = Game.screenHeight;
		
		sansSerif = new Font("SansSerif", Font.BOLD, 40);
		background = new Color(0x202020);
		complete = Toolkit.getDefaultToolkit().createImage("textures/misc/level_complete.png");
		level = Toolkit.getDefaultToolkit().createImage("textures/misc/level_select.png");
		size = 128;
		space = 40;
		rowSize = 5;
		int lw = (size + space) * Math.min(Map.maps.length, rowSize) - space;
		int lh = (size + space) * ((Map.maps.length - 1) / rowSize + 1) - space;
		levelRegion = new Rectangle((width - lw) / 2, (height - lh) / 2, lw, lh);
	}
	
	@Override
	public void drawImage() {
		graphics.translate(x, y);
		AffineTransform at = graphics.getTransform();
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		
		graphics.translate(levelRegion.x, levelRegion.y);
		// Drawing each level button based on whether they are unlocked and whether they are completed
		for (int i = 0; i < Map.maps.length; i++) {
			if (Game.levelManager.completed[i]) {				
				graphics.drawImage(complete, i % rowSize * (size + space), i / rowSize * (size + space), size, size, Game.frame);
				graphics.setColor(Color.WHITE);
			} else if (Game.levelManager.unlocked[i]) {
				graphics.drawImage(level, i % rowSize * (size + space), i / rowSize * (size + space), size, size, Game.frame);
				graphics.setColor(Color.WHITE);
			} else {
				graphics.drawImage(level, i % rowSize * (size + space), i / rowSize * (size + space), size, size, Game.frame);
				graphics.setColor(Color.DARK_GRAY);
			}
			// Draw level number
			graphics.setFont(sansSerif);
			int textX = (size - graphics.getFontMetrics().stringWidth(Integer.toString(i+1))) / 2;
			int textY = size / 2 + graphics.getFontMetrics().getHeight() / 3;
			graphics.drawString(Integer.toString(i+1), i % rowSize * (size + space) + textX, i / rowSize * (size + space) + textY);
		}
		
		graphics.setTransform(at);
		graphics.translate(-x, -y);
	}
	
	public void mousePressed(int x, int y, int button) {
		// Select a level if a level was pressed
		if (levelRegion.contains(x, y) && (x - levelRegion.x) % (size + space) < size && (y - levelRegion.y) % (size + space) < size) {
			int i = (y - levelRegion.y) / (size + space) * rowSize + (x - levelRegion.x) / (size + space);
			if (i < Game.levelManager.unlocked.length && Game.levelManager.unlocked[i]) {
				Game.levelManager.selectLevel((y - levelRegion.y) / (size + space) * rowSize + (x - levelRegion.x) / (size + space));
			}
		}
	}
	
}
