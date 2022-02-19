package command;

import java.util.Comparator;

// Comparator for grid names
public class GridNameComparator implements Comparator<String> {

//	Description: Grids named "default" have highest priority since they are loaded in when a level is selected, otherwise use normal string compare
//	Parameters: Two grid names to compare
//	Return: Comparison result
	public int compare(String s1, String s2) {
		if (s1.equals("default")) {
			return -65536;
		}
		if (s2.equals("default")) {
			return 65536;
		}
		return s1.compareTo(s2);
	}
	
}
