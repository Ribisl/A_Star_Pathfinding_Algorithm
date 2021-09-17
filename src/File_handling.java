import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class File_handling {
	String file_data = new String(); 
	
	void readMap() {
		try {
			//File file = new File("C:\\Users\\Mathias\\eclipse-workspace\\A_Star_Pathfinding_Algorithm\\src\\config_01.txt");
			File file = new File ("C:\\Users\\Mathias\\Documents\\UNI\\OOP1\\mazeGenerator\\config_1000x1000.txt");
			//File file = new File("C:\\Users\\Mathias\\Documents\\UNI\\OOP1\\oop1ss21_a1_314\\config\\config_200x200.txt");
			Scanner myReader = new Scanner(file);
			while(myReader.hasNextLine()) {
				file_data += myReader.nextLine() + "\n";
			}
		} catch(FileNotFoundException e) {
			System.out.println("File "+ e.getMessage() + " not found!");
		}
	}
	
	Board initBoard() {
		String[] parts = file_data.split("\n",4);
		int height = Integer.parseInt(parts[0]);
		int width = Integer.parseInt(parts[1]);
		int number_of_blocks = Integer.parseInt(parts[2]);
		
		String map_data = parts[3];
		Board board = new Board(height,width,number_of_blocks, map_data);
		return board;
	}
}
