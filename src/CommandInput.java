import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Felder.Field;
import Felder.Item;

public class CommandInput {
	Map<String, COMMANDS> commands = new HashMap<String, COMMANDS>();

	enum COMMANDS {
		HELP, INFO, FILE, DRAW, SOLVE, SAVE, LOAD, EXIT, CLEAR, RENDER , FRAMESKIP;
	}

	private static final String HELP_TEXT = "Das ist der HILFE Text";
	private static final String INFO_TEXT = "Dieses Programm visualisiert den A* Algorithmus!\n Kommandos:\n HELP\nINFO\nFILE\nDRAW\nSOLVE\nSAVE\nLOAD\nEXIT\n";
	private static final String NO_PARAMS_TAKEN = "Es sind keine Parameter vorhanden!";
	private static final String INPUT_ERROR = "Fehlerhafte Eingabe";
	private boolean file_loaded_ = false;
	File_handling file_handling_;
	Board board_;
	A_Star pathfinding;
	Renderer r;

	CommandInput() {
		commands.put("help", COMMANDS.HELP);
		commands.put("info", COMMANDS.INFO);
		commands.put("file", COMMANDS.FILE);
		commands.put("draw", COMMANDS.DRAW);
		commands.put("solve", COMMANDS.SOLVE);
		commands.put("save", COMMANDS.SAVE);
		commands.put("load", COMMANDS.LOAD);
		commands.put("exit", COMMANDS.EXIT);
		commands.put("clear", COMMANDS.CLEAR);
		commands.put("render", COMMANDS.RENDER);
		commands.put("frameskip", COMMANDS.FRAMESKIP);

		System.out.println(INFO_TEXT);
		board_ = new Board();
		r = new Renderer(board_);
	}

	public void readUserInput() {
		while (true) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			String input = "";
			try {
				input = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] input_splitted = input.split(" ");
			String command = input_splitted[0];
			if (command.length() == 0) {
				System.out.println(INPUT_ERROR);
				continue;
			}

			int param_count = input_splitted.length - 1;
			try {
				
				switch (commands.get(command)) {
				case HELP:
					System.out.println(HELP_TEXT);
					break;
				case INFO:
					System.out.println(INFO_TEXT);
					break;
				case FILE:
					printFileInfo();
					break;
				case DRAW:
					drawMaze();
					break;
				case SOLVE:
					solveMaze();
					break;
				case LOAD:
					loadFile();
					break;
				case EXIT:
					System.exit(0);
					break;
				case CLEAR:
					clearBoard();
					break;
				case RENDER:
					r.render(board_, true);
					break;
				case FRAMESKIP:
					pathfinding.frameskip = Integer.parseInt(input_splitted[1]);
					break;
				case SAVE:
					saveFile(input_splitted[1]);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				System.out.println("UPS ERROR");
			}
		}
	}

	private void saveFile(String file_name) {
		File f = new File(file_name);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void clearBoard() {
		board_.clear();
		renderBoard(r, pathfinding);
	}

	private void drawMaze() {
		Canvas frame = r.getCanvas();
		frame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				r.render(board_, true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					r.left_click = true;
				} else {
					r.left_click = false;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					r.left_click = true;
				} else {
					r.left_click = false;
				}

				r.mouse_pos = e.getPoint();

				System.out.println("repaint");
				frame.repaint();
				r.render(board_, true);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		frame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// r.pointEnd = e.getPoint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				r.mouse_pos = e.getPoint();

				System.out.println("repaint");
				frame.repaint();
			}
		});
	}

	private void loadFile() {
		file_handling_ = new File_handling();
		file_handling_.readMap();
		board_ = file_handling_.initBoard();

		file_loaded_ = true;

		ArrayList<Item> items = board_.getItems();

		pathfinding = new A_Star(board_, items.get(0), items.get(1), board_.getHeight_(), board_.getWidth_(), r);

		renderBoard(r, pathfinding);

	}

	private void solveMaze() {
		//TODO clear old path 
		clearPath();
		board_ = r.getBoard();
		ArrayList<Item> items = board_.getItems();

		pathfinding = new A_Star(board_, items.get(0), items.get(1), board_.getHeight_(), board_.getWidth_(), r);

		long startTime = System.currentTimeMillis();
		System.out.println("Started finding path...");
		if (pathfinding.findPath()) {
			System.out.println();
			System.out.println("FOUND PATH AFTER " + (System.currentTimeMillis() - startTime) + "ms");
		} else {
			System.out.println();
			System.out.println("NO PATH AFTER " + (System.currentTimeMillis() - startTime) + "ms");
		}


		r.render(pathfinding.board_, true);

	}

	private void clearPath() {
		board_.clearPath();
	}

	private void renderBoard(Renderer r, A_Star pathfinding) {
		r.render(pathfinding.board_, false);
	}

	private void printFileInfo() {
		// TODO Auto-generated method stub

	}
}
