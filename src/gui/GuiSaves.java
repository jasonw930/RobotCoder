package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import command.Grid;
import command.GridNameComparator;
import main.Game;

// GUI for managing grid saves
public class GuiSaves extends Gui {

	public Font[] sansSerif;
	public Image highlight;
	public Image[] arrow;

	public Rectangle exitButton;
	public Rectangle saveButton;
	public Rectangle savesRegion;
	public int saveHeight;
	public Rectangle previewRegion;
	public Rectangle loadButton;
	public Rectangle deleteButton;
	
	public Color background;
	public Color darkBackground;
	public Image exit;
	
	public int selected;
	public Grid previewGrid;
	
	public Rectangle saveDialog;
	public Rectangle confirmButton;
	public Rectangle cancelButton;
	public boolean saving;
	public String saveName;
	
	public GuiSaves() {
		super();

		x = 0;
		y = 0;
		width = Game.screenWidth;
		height = Game.screenHeight;
		visible = false;
		
		// Initializing all variables
		sansSerif = new Font[100];
		for (int i = 0; i < sansSerif.length; i++) {
			sansSerif[i] = new Font("SansSerif", Font.BOLD, i);
		}
		highlight = Toolkit.getDefaultToolkit().createImage("textures/misc/grid_highlight.png");
		arrow = new Image[3];
		for (int i = 0; i < 3; i++)
			arrow[i] = Toolkit.getDefaultToolkit().createImage("textures/misc/grid_arrow_" + i + ".png");
		
		exitButton = new Rectangle(width - 64 - 32, 32, 64, 64);
		saveButton = new Rectangle(200, 100, 400, 30);
		savesRegion = new Rectangle(200, 140, 400, height - 240);
		saveHeight = 30;
		previewRegion = new Rectangle(width - 600, 100, 400, height - 280);
		loadButton = new Rectangle(width - 600, height - 170, 400, 30);
		deleteButton = new Rectangle(width - 600, height - 130, 400, 30);
		
		background = new Color(0x202020);
		darkBackground = new Color(0x101010);
		exit = Toolkit.getDefaultToolkit().createImage("textures/misc/exit_level.png");
		
		selected = -1;
		previewGrid = null;
		
		saveDialog = new Rectangle((width - 400) / 2, (height - 160) / 2, 400, 160);
		confirmButton = new Rectangle(saveDialog.x + 210, saveDialog.y + saveDialog.height - 40, 170, 30);
		cancelButton = new Rectangle(saveDialog.x + 20, saveDialog.y + saveDialog.height - 40, 170, 30);
		saving = false;
		saveName = "";
	}
	
	@Override
	public void drawImage() {
		graphics.translate(x, y);
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		// Drawing different regions and buttons
		graphics.drawImage(exit, exitButton.x, exitButton.y, exitButton.width, exitButton.height, Game.frame);
		drawButton(saveButton, sansSerif[18], Color.WHITE, Color.BLACK, "Save Current Grid");
		graphics.setColor(darkBackground);
		graphics.fillRect(savesRegion.x, savesRegion.y, savesRegion.width, savesRegion.height);
		graphics.fillRect(previewRegion.x, previewRegion.y, previewRegion.width, previewRegion.height);
		drawButton(loadButton, sansSerif[18], Color.WHITE, Color.BLACK, "Load Save");
		drawButton(deleteButton, sansSerif[18], Color.WHITE, Color.BLACK, "Delete Save");
		
		// Drawing highlight
		if (selected > -1) {
			graphics.setColor(Color.DARK_GRAY);
			graphics.fillRect(savesRegion.x, savesRegion.y + saveHeight * selected, savesRegion.width, saveHeight);
		}
		graphics.setFont(sansSerif[18]);
		graphics.setColor(Color.WHITE);
		ArrayList<String> saves = Grid.gridSaves[Game.levelManager.currentGrid.id];
		for (int i = 0; i < saves.size(); i++) {
			graphics.drawString(saves.get(i), savesRegion.x + 5, savesRegion.y + saveHeight * i + saveHeight / 2 + graphics.getFontMetrics().getHeight() / 3);
		}
		// Displaying preview if a level is selected
		if (previewGrid != null) {
			GuiGrid.drawGrid(graphics, highlight, arrow, sansSerif, previewGrid, previewGrid.tileSize, previewRegion);
		}
		
		// Show the dialog box if the player is saving their grid
		if (saving) {
			graphics.setColor(Color.DARK_GRAY);
			graphics.fillRect(saveDialog.x, saveDialog.y, saveDialog.width, saveDialog.height);
			graphics.setColor(Color.WHITE);
			graphics.setFont(sansSerif[18]);
			graphics.drawString("Enter Save Name", saveDialog.x + (saveDialog.width - graphics.getFontMetrics().stringWidth("Enter Save Name")) / 2, saveDialog.y + graphics.getFontMetrics().getHeight() + 10);
			drawButton(confirmButton, sansSerif[18], Color.WHITE, Color.BLACK, "Save");
			drawButton(cancelButton, sansSerif[18], Color.WHITE, Color.BLACK, "Cancel");
			graphics.setColor(background);
			graphics.fillRect(saveDialog.x + (saveDialog.width - 360) / 2, saveDialog.y + (saveDialog.height - 30) / 2, 360, 30);
			graphics.setColor(Color.WHITE);
			graphics.drawString(saveName, saveDialog.x + (saveDialog.width - 360) / 2 + 5, saveDialog.y + saveDialog.height / 2 + graphics.getFontMetrics().getHeight() / 3);
		}
		
		graphics.translate(-x, -y);
	}
	
	public void mousePressed(int x, int y, int button) {
		ArrayList<String> saves = Grid.gridSaves[Game.levelManager.currentGrid.id];
		// Handle presses when user selects a save
		if (saving) {
			if (cancelButton.contains(x, y)) {
				saving = false;
			} else if (confirmButton.contains(x, y) && saveName.length() > 0) {
				Game.levelManager.currentGrid.save(String.format("grids/grid_%d_saves/%s.txt", Game.levelManager.currentGrid.id, saveName));
				if (!saves.contains(saveName)) saves.add(-Collections.binarySearch(saves, saveName, new GridNameComparator()) - 1, saveName);
				saving = false;
				selected = -1;
				previewGrid = null;
			}
			return;
		}
		// Handle button presses
		if (exitButton.contains(x, y)) {
			visible = false;
		} else if (saveButton.contains(x, y)) {
			saving = true;
			saveName = "";
		} else if (savesRegion.contains(x, y) && (y - savesRegion.y) / saveHeight < saves.size()) {
			selected = (y - savesRegion.y) / saveHeight;
			previewGrid = new Grid(String.format("grid_%d_saves/%s", Game.levelManager.currentGrid.id, saves.get(selected)), -1, Game.levelManager.currentGrid.tileSize);
		} else if (loadButton.contains(x, y) && selected > -1) {
			Game.levelManager.currentGrid.load(String.format("grids/grid_%d_saves/%s.txt", Game.levelManager.currentGrid.id, saves.get(selected)));
			visible = false;
		} else if (deleteButton.contains(x, y) && selected > -1) {
			new File(String.format("grids/grid_%d_saves/%s.txt", Game.levelManager.currentGrid.id, saves.get(selected))).delete();
			saves.remove(selected);
			selected = -1;
			previewGrid = null;
		}
	}
	
}
