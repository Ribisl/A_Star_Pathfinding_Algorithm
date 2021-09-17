import java.util.ArrayList;

import Felder.Field;
import Felder.Item;

public class main {

	public static void main(String[] args) {
		File_handling file_handling = new File_handling();
		file_handling.readMap();
		Board board = file_handling.initBoard();
		board.initBoard();
		board.printMap();
		
		ArrayList<Item> items = board.getItems();
		
		A_Star pathfinding = new A_Star(board.getFields(), items.get(0), items.get(1), board.getHeight_(), board.getWidth_());
		
		if(pathfinding.findPath()) {
			System.out.println();
			System.out.println("FOUND PATH");
		} else {
			System.out.println();
			System.out.println("NO PATH");
		}

	}

}
