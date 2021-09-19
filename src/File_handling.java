import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class File_handling {
	String file_data = new String();

	void readMap() {
		try {
			int selection = -1;

			File folder = new File("./configs/");
			File[] listOfFiles = folder.listFiles();

			if (listOfFiles.length == 0) {
				System.out.println("No files found in config folder!");
				System.exit(0);
			}

			do {
				System.out.println("Select Config-File: ");
				for (int i = 0; i < listOfFiles.length; i++) {
					System.out.println("[" + i + "] " + listOfFiles[i].getName());
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				try {
					String input = reader.readLine();
					selection = Integer.parseInt(input);
				} catch (Exception ex) {
					System.err.println("Error on input: " + ex);
				}
			} while (selection < 0 || selection > listOfFiles.length - 1);

			File file = listOfFiles[selection];
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				file_data += myReader.nextLine() + "\n";
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + e.getMessage() + " not found!");
		}
	}

	Board initBoard() {
		String[] parts = file_data.split("\n", 4);
		int height = Integer.parseInt(parts[0]);
		int width = Integer.parseInt(parts[1]);
		int number_of_blocks = Integer.parseInt(parts[2]);

		String map_data = parts[3];
		Board board = new Board(height, width, number_of_blocks, map_data);
		return board;
	}
}
