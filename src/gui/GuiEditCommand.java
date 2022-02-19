package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.GridCommand;
import main.Game;

// Displays the GUI used to edit a command
public class GuiEditCommand extends Gui {

	public Font sansSerif;
	public Color background;
	public Image selectDir;
	public Image[] dir;
	public Image changeVar;
	
	public List<Command> commands;
	public int curR;
	public int curC;
	public int size;
	
	public GuiEditCommand() {
		super();
		visible = false;
		
		x = 400;
		y = 30;
		width = 120;
		height = Game.screenHeight - 60;
		
		sansSerif = new Font("SansSerif", Font.BOLD, 18);
		background = new Color(0x101010);
		selectDir = Toolkit.getDefaultToolkit().createImage("textures/misc/select_dir.png");
		dir = new Image[3];
		for (int i = 0; i < 3; i++)
			dir[i] = Toolkit.getDefaultToolkit().createImage("textures/misc/dir_" + i + ".png");
		changeVar = Toolkit.getDefaultToolkit().createImage("textures/misc/change_var.png");
		
		commands = new ArrayList<Command>(Command.commands.values());
		
		size = 64;
	}
	
	public void drawImage() {
		graphics.translate(x, y);
		AffineTransform at = graphics.getTransform();
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		// Drawing the direction editing part
		GridCommand curGC = Game.levelManager.currentGrid.commands[curR][curC];
		graphics.setColor(Color.WHITE);
		graphics.setFont(sansSerif);
		if (curGC.command.args.length == 0 && curGC.command.vars.length == 0) {
			graphics.drawString("no args", (width - graphics.getFontMetrics().stringWidth("no args")) / 2, (width - size - 12));
		}
		for (int i = 0; i < curGC.command.args.length; i++) {			
			graphics.drawString(curGC.command.args[i], (width - graphics.getFontMetrics().stringWidth(curGC.command.args[i])) / 2, (width - size - 12) + size * 2 * i);
			graphics.translate(width / 2, (width - size) + (size) * 2 * i + size / 2);
			graphics.drawImage(selectDir, -size / 2, -size / 2, size, size, Game.frame);
			if (curGC.args.get(curGC.command.args[i]) > -1) {
				graphics.rotate(Math.PI / 2 * curGC.args.get(curGC.command.args[i]));
				graphics.drawImage(dir[i], -size / 2, -size / 2, size, size, Game.frame);
			}
			graphics.setTransform(at);
		}
		// Drawing the number editing part
		int varY = (width - size) + (size) * 2 * curGC.command.args.length;
		for (int i = 0; i < curGC.command.vars.length; i++) {
			String s = Integer.toString(curGC.iniVars.get(curGC.command.vars[i]));
			graphics.drawString(curGC.command.vars[i], (width - graphics.getFontMetrics().stringWidth(curGC.command.vars[i])) / 2, varY - 12 + size * 2 * i);
			graphics.drawImage(changeVar, (width - size * 3 / 2) / 2, varY + size * 2 * i, size * 3 / 2, size, Game.frame);
			graphics.drawString(s, (width - graphics.getFontMetrics().stringWidth(s)) / 2, varY + size * 2 * i + size / 2 + graphics.getFontMetrics().getHeight() / 3);
		}
		
		graphics.translate(-x, -y);
	}
	
	@Override
	public void mousePressed(int x, int y, int button) {
		GridCommand curGC = Game.levelManager.currentGrid.commands[curR][curC];
		// Editing direction
		if (x >= (width - size) / 2 && x < (width + size) / 2 && (y - (width - size)) % (size * 2) < size) {
			int i = (y - (width - size)) / (size * 2);
			int dx = x - width / 2;
			int dy = y - ((width - size) + (size) * 2 * i + size / 2);
			if (i >= 0 && i < curGC.args.size()) {
				int newDir = -1;
				if (dx >= 0 && Math.abs(dy) < Math.abs(dx)) {
					newDir = 0;
				} else if (dx < 0 && Math.abs(dy) < Math.abs(dx)) {
					newDir = 2;
				} else if (dy >= 0 && Math.abs(dx) < Math.abs(dy)) {
					newDir = 1;
				} else if (dy < 0 && Math.abs(dx) < Math.abs(dy)) {
					newDir = 3;
				}
				if (newDir == -1) return;
				if (newDir == curGC.args.get(curGC.command.args[i])) newDir = -1;
				else if (curGC.args.containsValue(newDir)) newDir = curGC.args.get(curGC.command.args[i]);
				curGC.args.put(curGC.command.args[i], newDir);
				return;
			}
		}
		// Editing numbers
		int varY = (width - size) + (size) * 2 * curGC.command.args.length;
		if (x >= (width - size * 3 / 2) / 2 && x < (width + size * 3 / 2) / 2 && (y - (width - size)) % (size * 2) < size) {
			int i = (y - varY) / (size * 2);
			int dx = x - width / 2;
			int dy = y - (varY + size * 2 * i + size / 2);
			if (dx < -size / 4 && Math.abs(dy) + Math.abs(dx + size / 4) < size / 2) {
				curGC.iniVars.put(curGC.command.vars[i], Math.max(0, curGC.iniVars.get(curGC.command.vars[i]) - 1));
			} else if (dx > size / 4 && Math.abs(dy) + Math.abs(dx - size / 4) < size / 2) {
				curGC.iniVars.put(curGC.command.vars[i], curGC.iniVars.get(curGC.command.vars[i]) + 1);
			}
			System.out.println("" + (dx - size / 4) + " " + dy);
		}
	}
	
}
