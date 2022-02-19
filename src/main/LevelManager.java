package main;

import command.Grid;
import map.Map;

// Stores information about the current level and the levels in general, also provides methods to switch between levels
public class LevelManager {

	public boolean[] unlocked;
	public boolean[] completed;
	public Map currentMap;
	public Grid currentGrid;
	
	public LevelManager() {
		unlocked = new boolean[Map.maps.length];
		completed = new boolean[Map.maps.length];
		for (int i = 0; i < Map.maps.length; i++) {
			unlocked[i] = false;
			completed[i] = false;
		}
		unlocked[0] = true;
	}
	
//	Description: Selects the i'th level, 0 indexed
//	Parameters: The level number of the level being selected
//	Return: Void
	public void selectLevel(int i) {
		if (i < 0 || i >= Map.maps.length) return;
		currentMap = Map.maps[i];
		currentMap.reset();
		currentGrid = Grid.grids[i];
		currentGrid.reset();
		if (Grid.gridSaves[currentGrid.id].contains("default")) {
			currentGrid.load(String.format("grids/grid_%d_saves/default.txt", currentGrid.id));
		}
		currentGrid.resetVars();
		
		// Brings up and resets the correct GUI's
		Game.guiLevelSelect.visible = false;
		Game.guiLevelOver.visible = false;
		
		Game.guiEditGrid.cur = 1;
		Game.guiEditGrid.visible = false;
		Game.guiEditCommand.curR = -1;
		Game.guiEditCommand.curC = -1;
		Game.guiEditCommand.visible = false;
		Game.guiTips.curPage = 0;
		Game.guiTips.visible = false;
	}
	
//	Description: Deselects the current level and returns to level select menu
//	Parameters: None
//	Return: Void
	public void deselectLevel() {
		Game.guiLevelSelect.visible = true;
		Game.guiLevelOver.visible = false;
		Game.guiEditCommand.visible = false;
		Game.guiEditGrid.visible = false;
		Game.guiTips.visible = false;
	}
	
}
