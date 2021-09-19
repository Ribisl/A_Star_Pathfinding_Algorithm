import java.util.ArrayList;

import Felder.Item;

public class Main {

	public static void main(String[] args) {
		File_handling file_handling = new File_handling();
		file_handling.readMap();
		Board board = file_handling.initBoard();
		board.initBoard();
		// board.printMap();

		Renderer r = new Renderer();

		ArrayList<Item> items = board.getItems();

		A_Star pathfinding = new A_Star(board.getFields(), items.get(0), items.get(1), board.getHeight_(),
				board.getWidth_(), r);

		long startTime = System.currentTimeMillis();
		System.out.println("Started finding path...");
		if (pathfinding.findPath()) {
			System.out.println();
			System.out.println("FOUND PATH AFTER " + (System.currentTimeMillis() - startTime) + "ms");
		} else {
			System.out.println();
			System.out.println("NO PATH AFTER " + (System.currentTimeMillis() - startTime) + "ms");
		}

		while (true) {
			r.render(pathfinding.board_, true);
		}

	}

}
