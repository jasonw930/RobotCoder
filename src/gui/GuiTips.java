package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import command.Command;
import main.Game;

// GUI for displaying tips for the game
public class GuiTips extends Gui {

	public Font[] sansSerif;
	public Color background;
	public Image changeVar;
	
	public int curPage;
	public String[][] tips;
	public Command[][] commands;
	
	public String[] wrapSplit(String s, int width, Font f) {
		FontMetrics metrics = graphics.getFontMetrics(f);
		String[] words = s.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		String cur = "";
		for (int i = 0; i < words.length; i++) {
			if (metrics.stringWidth(cur + words[i]) > width && cur.length() > 0) {
				lines.add(cur);
				cur = "";
			}
			cur += words[i] + " ";
		}
		if (cur.length() > 0) lines.add(cur);
		return lines.toArray(new String[0]);
	}
	
	public GuiTips() {
		super();
		
		x = Game.screenWidth - 32 - 240;;
		y = 128;
		width = 240;
		height = Game.screenHeight - y - 30;
		visible = false;
		
		sansSerif = new Font[100];
		for (int i = 0; i < sansSerif.length; i++) {
			sansSerif[i] = new Font("SansSerif", Font.BOLD, i);
		}
		background = new Color(0x101010);
		changeVar = Toolkit.getDefaultToolkit().createImage("textures/misc/change_var.png");
		
		// Reading tips from file and spliting them into separate lines so they all fit in the textbox
		curPage = 0;
		try {
			Scanner file = new Scanner(new File("tips.txt"));
			tips = new String[Integer.parseInt(file.nextLine())][];
			file.nextLine();
			String tip = null;
			int i = 0;
			while (file.hasNextLine()) {
				String line = file.nextLine();
				if (line.equals("")) {
					tips[i++] = wrapSplit(tip, width - 32, sansSerif[12]);
					tip = null;
					continue;
				}
				if (tip == null) tip = line;
				else tip += "\n" + line;
			}
			if (tip != null) tips[i] = wrapSplit(tip, width - 32, sansSerif[12]);
			
			file.close();
		} catch (FileNotFoundException e) {
			
		}
		
		// Initializing which commands should be drawn for each tip
		commands = new Command[Game.levelManager.unlocked.length][];
		commands[0] = new Command[] {Command.commandStart, Command.commandStairs};
		commands[1] = new Command[] {Command.commandMove};
		commands[2] = new Command[] {Command.commandRotRight};
		commands[3] = new Command[] {Command.commandRotRight, Command.commandRotLeft};
		commands[4] = new Command[] {};
		commands[5] = new Command[] {};
		commands[6] = new Command[] {Command.commandLoop};
		commands[7] = new Command[] {Command.commandLoop};
		commands[8] = new Command[] {Command.commandLoop};
		commands[9] = new Command[] {Command.commandLoop, Command.commandCopy};
		commands[10] = new Command[] {Command.commandConnect};
		commands[11] = new Command[] {Command.commandKeys};
		commands[12] = new Command[] {};
		commands[13] = new Command[] {};
		commands[14] = new Command[] {};
		commands[15] = new Command[] {};
		commands[16] = new Command[] {};
	}
	
	public void drawImage() {
		graphics.translate(x, y);
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(sansSerif[32]);
		graphics.drawString("Tip " + (curPage+1), (width - graphics.getFontMetrics().stringWidth("Tip " + (curPage+1))) / 2, 40);
		
		// Drawing tips
		graphics.setFont(sansSerif[12]);
		for (int i = 0; i < tips[curPage].length; i++) {
			graphics.drawString(tips[curPage][i], 16, 70 + i*24);
		}
		// Drawing commands beneath the tips
		for (int i = 0; i < commands[curPage].length; i++) {
			graphics.drawImage(commands[curPage][i].texture, (width-commands[curPage].length*96+32)/2 + i*96, height-80*2, 64, 64, Game.frame);
		}
		graphics.drawImage(changeVar, (width - 96) / 2, height-80, 96, 64, Game.frame);
		
		graphics.translate(-x, -y);
	}
	
	public void mousePressed(int x, int y, int mouse) {
		int size = 64;
		// Handle next tip and previous tip button presses
		if (x >= (width - size*3/2) / 2 && x < (width + size*3/2) / 2 && y >= height - size*5/4 && y < height - size/4) {
			int dx = x - width / 2;
			int dy = y - (height - size*3/4);
			if (dx < -size / 4 && Math.abs(dy) + Math.abs(dx + size / 4) < size / 2) {
				curPage = Math.max(0, curPage-1);
			} else if (dx > size / 4 && Math.abs(dy) + Math.abs(dx - size / 4) < size / 2) {
				if (curPage < Game.levelManager.unlocked.length - 1 && Game.levelManager.unlocked[curPage+1]) curPage++;
			}
		}
	}
	
}
