package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import command.Command;
import main.Game;

// GUI used to edit which commands are in the grid
public class GuiEditGrid extends Gui {

	public Font sansSerif;
	public Color background;
	public Image highlight;
	public Image changeVar;
	
	public List<Command> commands;
	public int offset;
	public int windowSize;
	public int cur;
	
	public int size;
	
	public GuiEditGrid() {
		super();
		visible = false;
		
		x = 400;
		y = 30;
		width = 120;
		height = Game.screenHeight - 60;
		
		sansSerif = new Font("SansSerif", Font.BOLD, 18);
		background = new Color(0x101010);
		highlight = Toolkit.getDefaultToolkit().createImage("textures/misc/grid_highlight.png");
		changeVar = Toolkit.getDefaultToolkit().createImage("textures/misc/change_var.png");
		
		commands = new ArrayList<Command>(Command.commands.values());
		
		offset = 1;
		windowSize = 7;
		cur = 1;
		size = 64;
	}
	
	public void drawImage() {
		graphics.translate(x, y);
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		// Drawing all of the command options
		for (int i = offset; i < Math.min(commands.size(), offset + windowSize); i++) {
			graphics.drawImage(commands.get(i).texture, (width - size) / 2, (width - size) / 2 + (size + size / 4) * (i-offset), size, size, Game.frame);
		}
		if (cur-offset >= 0 && cur-offset < windowSize) {
			graphics.drawImage(highlight, (width - size) / 2, (width - size) / 2 + (size + size / 4) * (cur-offset), size, size, Game.frame);
		}
		graphics.drawImage(changeVar, (width - size*3/2) / 2, (width - size) / 2 + (size + size / 4) * (windowSize), size*3/2, size, Game.frame);
		
		graphics.translate(-x, -y);
	}
	
	@Override
	public void mousePressed(int x, int y, int button) {
		// Changing the offset to scroll through the possible commands
		if (x >= (width - size*3/2) / 2 && x < (width + size*3/2) / 2 && y >= (width - size) / 2 + (size + size / 4) * (windowSize) && y < (width - size) / 2 + (size + size / 4) * (windowSize) + size) {
			int dx = x - width / 2;
			int dy = y - ((width - size) / 2 + (size + size / 4) * (windowSize) + size / 2);
			if (dx < -size / 4 && Math.abs(dy) + Math.abs(dx + size / 4) < size / 2) {
				offset = Math.max(1, offset-1);
			} else if (dx > size / 4 && Math.abs(dy) + Math.abs(dx - size / 4) < size / 2) {
				offset = Math.min(commands.size()-windowSize, offset+1);
			}
		} else if (x >= (width - size) / 2 && x < (width + size) / 2 && (y - (width - size) / 2) % (size + size / 4) < size) {
			// Selecting a command to place
			int i = (y - (width - size) / 2) / (size + size / 4);
			if (i >= 0 && i < windowSize) {
				cur = i+offset;
			}
		}
	}
	
}
