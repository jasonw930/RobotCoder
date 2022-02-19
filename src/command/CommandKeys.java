package command;

import java.util.LinkedHashMap;

import map.Map;
import map.MapProp;
import map.Prop;

// Command used to pick up keys
public class CommandKeys extends Command {

	public CommandKeys(String n, String key, int l) {
		super(n, key, l);
		args = new String[] {"next"};
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			if (map.remainingKeys > 0) {
				for (MapProp p : map.props) {
					// Pick up key if key is in current tile
					if (p.prop == Prop.propKey && map.player.row == p.row && map.player.col == p.col) {
						p.visible = false;
						map.remainingKeys--;
						break;
					}
				}
			}
			nextCommand(grid, "next");
		}
	}
	
}
