import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Felder.Field;
import Felder.Item;

public class main {

	public static void main(String[] args) {
		File_handling file_handling = new File_handling();
		file_handling.readMap();
		
		Board board = file_handling.initBoard();
		board.initBoard();
		
		GUI gui = new GUI(board);
		gui.openGUI();
		board.setGUI(gui);
		board.printMap();
		
    	ArrayList<Item> items = board.getItems();
		
		A_Star pathfinding = new A_Star(gui, board.getFields(), items.get(0), items.get(4), board.getHeight_(), board.getWidth_());
		
		System.out.println();
		System.out.println("Started looking for path");
		
		if(pathfinding.findPath()) {
			System.out.println();
			System.out.println("FOUND PATH");
		} else {
			System.out.println();
			System.out.println("NO PATH");
		}

	}

}
