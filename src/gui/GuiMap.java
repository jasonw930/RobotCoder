package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import command.Command;
import command.Grid;
import command.GridCommand;
import main.Game;
import map.Map;
import map.MapProp;
import map.Prop;

// GUI for drawing the current map
public class GuiMap extends Gui {

	public Font sansSerif;
	public Color background;
	public Image exit;
	public Rectangle exitButton;
	public Image tips;
	public Rectangle tipsButton;
	
	public GuiMap() {
		super();

		x = 0;
		y = 0;
		width = Game.screenWidth;
		height = Game.screenHeight;
		
		sansSerif = new Font("SansSerif", Font.BOLD, 18);
		exitButton = new Rectangle(width - (64 + 32), 32, 64, 64);
		tipsButton = new Rectangle(width - (64 + 32) * 2, 32, 64, 64);
		background = new Color(0x202020);
		
		exit = Toolkit.getDefaultToolkit().createImage("textures/misc/exit_level.png");
		tips= Toolkit.getDefaultToolkit().createImage("textures/misc/tips.png");
	}
	
	@Override
	public void drawImage() {
		graphics.translate(x, y);
		AffineTransform at = graphics.getTransform();
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		Map curMap = Game.levelManager.currentMap;
		int mapx = 400 + (width - 400 - curMap.width * curMap.tileSize) / 2;
		int mapy = (height - curMap.height * curMap.tileSize) / 2;
		
		try {
			// Drawing all the tiles
			for (int r = 0; r < curMap.height; r++) {
				for (int c = 0; c < curMap.width; c++) {
					graphics.drawImage(curMap.tiles[r][c].texture, mapx+c*curMap.tileSize, mapy+r*curMap.tileSize, curMap.tileSize, curMap.tileSize, Game.frame);
				}
			}
			
			// Drawing all the props
			for (MapProp p : curMap.props) {
				if (!p.visible) continue;
				graphics.translate(mapx+p.col*curMap.tileSize + curMap.tileSize/2, mapy+p.row*curMap.tileSize + curMap.tileSize/2);
				graphics.rotate(Math.PI/2 * p.rot);
				// Custom player drawing
				if (p.prop == Prop.propPlayer && Game.levelManager.currentGrid.running) {
					Grid g = Game.levelManager.currentGrid;
					GridCommand curCmd = g.commands[g.curRow][g.curCol];
					int size = curMap.tileSize;
					if (curCmd.command == Command.commandMove && !curMap.tiles[curMap.player.row+curMap.player.dr][curMap.player.col+curMap.player.dc].solid) {
						graphics.translate(curMap.tileSize * g.commands[g.curRow][g.curCol].runStage / g.commands[g.curRow][g.curCol].command.runLength, 0);
					} else if (curCmd.command == Command.commandRotRight) {
						graphics.rotate(Math.PI / 2 * g.commands[g.curRow][g.curCol].runStage / g.commands[g.curRow][g.curCol].command.runLength);
					} else if (curCmd.command == Command.commandRotLeft) {
						graphics.rotate(-Math.PI / 2 * g.commands[g.curRow][g.curCol].runStage / g.commands[g.curRow][g.curCol].command.runLength);
					} else if (curCmd.command == Command.commandStairs && curMap.player.row == curMap.stairs.row && curMap.player.col == curMap.stairs.col && curMap.remainingKeys == 0) {
						size = curMap.tileSize * (curCmd.command.runLength - curCmd.runStage) / curCmd.command.runLength;
					}
					graphics.drawImage(p.prop.texture, -size/2, -size/2, size, size, Game.frame);
				} else {
					graphics.drawImage(p.prop.texture, -curMap.tileSize/2, -curMap.tileSize/2, curMap.tileSize, curMap.tileSize, Game.frame);
				}
				graphics.setTransform(at);
			}
		} catch (NullPointerException e) {
			// Map probably resetting
		}
		
		// Draw buttons
		graphics.drawImage(exit, exitButton.x, exitButton.y, exitButton.width, exitButton.height, Game.frame);
		graphics.drawImage(tips, tipsButton.x, tipsButton.y, tipsButton.width, tipsButton.height, Game.frame);
		
		graphics.translate(-x, -y);
	}
	
	public void mousePressed(int x, int y, int button) {
		// Handle button presses
		if (exitButton.contains(x, y)) {
			Game.levelManager.deselectLevel();
		} else if (tipsButton.contains(x, y)) {
			Game.guiTips.visible = !Game.guiTips.visible;
			Game.guiTips.curPage = Game.levelManager.currentMap.id;
		}
	}
	
}
