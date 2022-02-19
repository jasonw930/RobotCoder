package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import command.Command;
import command.Grid;
import gui.Gui;
import gui.GuiEditCommand;
import gui.GuiEditGrid;
import gui.GuiGrid;
import gui.GuiLevelOver;
import gui.GuiLevelSelect;
import gui.GuiMainMenu;
import gui.GuiMap;
import gui.GuiSaves;
import gui.GuiTips;
import map.Map;
import map.Prop;
import map.Tile;

// Main driver class that stores the java swing objects used to display the game, also manages the game loop
public class Game {

	public static String name;
	public static int screenWidth;
	public static int screenHeight;
	public static int FPS;
	public static int frameCount;

	public static int mouseX;
	public static int mouseY;
	
	public static JFrame frame;
	public static Panel panel;
	public static ArrayList<Gui> guiList;
	public static GuiMap guiMap;
	public static GuiGrid guiGrid;
	public static GuiEditGrid guiEditGrid;
	public static GuiEditCommand guiEditCommand;
	public static GuiLevelSelect guiLevelSelect;
	public static GuiLevelOver guiLevelOver;
	public static GuiSaves guiSaves;
	public static GuiTips guiTips;
	public static GuiMainMenu guiMainMenu;
	
	public static LevelManager levelManager;
	public static CheatManager cheatManager;
	
	public static void main(String[] args) {
		// JFrame and panel code
		name = "Robot Game";
		screenWidth = 1280;
		screenHeight = 720;
		FPS = 60;
		frameCount = 0;
		
		frame = new JFrame("Game");
		frame.setPreferredSize(new Dimension(screenWidth, screenHeight + 22));
		frame.setVisible(true);
		frame.addKeyListener(new KeyManager());
		
		panel = new Panel();
		panel.addMouseListener(new MouseManager());
		panel.addMouseMotionListener(new MouseManager());
		panel.setDoubleBuffered(true);
		frame.add(panel);
		frame.pack();
		
		initialize();
		initializeGraphics();
		
		// Game loop
		Timer timer = new Timer(1000 / FPS ,new ActionListener() {
			long last = System.currentTimeMillis();
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frameCount % (FPS * 10) == 0) System.out.println("FPS: " + (1000 / (System.currentTimeMillis() - last)));
				last = System.currentTimeMillis();
				frameCount++;
				loop();
			}
		});
        timer.setRepeats(true);
        timer.start();
	}
	
//	Description: Initializes graphics variables
//	Parameters: None
//	Return: Void
	public static void initializeGraphics() {
		guiList = new ArrayList<Gui>();
		guiMap = new GuiMap();
		guiGrid = new GuiGrid();
		guiEditGrid = new GuiEditGrid();
		guiEditCommand = new GuiEditCommand();
		guiLevelSelect = new GuiLevelSelect();
		guiLevelOver = new GuiLevelOver();
		guiSaves = new GuiSaves();
		guiTips = new GuiTips();
		guiMainMenu = new GuiMainMenu();
		
		guiList.add(guiMap);
		guiList.add(guiGrid);
		guiList.add(guiEditGrid);
		guiList.add(guiEditCommand);
		guiList.add(guiLevelSelect);
		guiList.add(guiLevelOver);
		guiList.add(guiSaves);
		guiList.add(guiTips);
		guiList.add(guiMainMenu);
	}
	
//	Description: Initializes game variables
//	Parameters: None
//	Return: Void
	public static void initialize() {
		Tile.initialize();
		Prop.initialize();
		Map.initialize();
		
		Command.initialize();
		Grid.initialize();
		
		levelManager = new LevelManager();
		levelManager.currentMap = Map.maps[0];
		levelManager.currentGrid = Grid.grids[0];
		
		cheatManager = new CheatManager();
	}
	
//	Description: Game loop, called FPS times a second
//	Parameters: None
//	Return: Void
	public static void loop() {
		levelManager.currentGrid.update();
		
		for (Gui g : guiList) {
			if (g.visible) {
				g.drawImage();
			}
		}
		panel.repaint();
	}
	
}
