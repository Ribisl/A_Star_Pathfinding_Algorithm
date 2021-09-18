import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Felder.Field;
import Felder.Item;
import Graphics.Rectangle;

public class GUI extends JFrame implements Runnable {

	private volatile int[] sets = { 0, 0 };

	static int height_ = 1000;
	static int width_ = 1000;

	int maze_width_, maze_height_;

	JFrame mainFrame = new JFrame("A_Star Pathfinding");
	public JLabel open_set_label = new JLabel();
	JLabel closed_set_label = new JLabel();

	JPanel panel = new JPanel();

	JPanel maze = new JPanel();

	GridLayout gr;

	Board board_;

	ArrayList<ArrayList<JPanel>> panels = new ArrayList<ArrayList<JPanel>>();

	public GUI(Board board) {
		maze_width_ = board.getWidth_();
		maze_height_ = board.getHeight_();
		board_ = board;

		gr = new GridLayout(maze_width_, maze_height_);
	}

	public void openGUI() {

		mainFrame.getContentPane();
		mainFrame.setSize(width_, height_);
		mainFrame.setBackground(Color.gray);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addStatsPanel();
		addMazePanel();

	}

	private void addStatsPanel() {
		open_set_label.setText("Open Set: 10000000000");
		closed_set_label.setText("Closed Set: 0");

		Dimension size = open_set_label.getPreferredSize();
		open_set_label.setBounds(10, 10, size.width, size.height);
		closed_set_label.setBounds(10, 25, size.width, size.height);
		panel.setLayout(null);
		panel.setBackground(Color.red);
		panel.add(open_set_label);
		panel.add(closed_set_label);
		panel.setSize(width_, 50);
		mainFrame.add(panel);
	}

	private void addMazePanel() {
		maze.setLayout(gr);
		// Random random = new Random();
		ArrayList<Field> fields = board_.getFields();
		mainFrame.setTitle("ABCD");
		mainFrame.add(maze);
//		Thread t1 = new Thread(new Runnable() {
	//		@Override
		//	public void run() {

				double complete = maze_height_ * maze_width_;
				double counter = 0;
				for (int y = 0; y < maze_height_; y++) {
					panels.add(new ArrayList<>());
					double percent = (counter / complete) * 100.0;
					mainFrame.setTitle(String.valueOf(percent) + "%");
					for (int x = 0; x < maze_width_; x++) {
						counter++;
						JPanel panel = new JPanel();
						Field field = fields.get(y * maze_width_ + x);
						if (field != null) {
							if (field.isItem()) {
								panel.setBackground(Color.green);
							} else if (field.isBlocked()) {
								panel.setBackground(Color.black);
							} else {
								panel.setBackground(Color.white);
							}
						} else {
							panel.setBackground(Color.gray);
						}
						panels.get(y).add(panel);
						// panel.setBackground(new Color(random.nextFloat(), random.nextFloat(),
						// random.nextFloat()));
						maze.add(panel);

					}
				}
				mainFrame.setTitle("100%");
			//}
		//});
		//t1.start();



	}

	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		g.drawRect(30, 50, 420, 100);
	}

	@Override
	public void run() {
		open_set_label.setText(String.valueOf(sets[1]));
	}

	public ArrayList<ArrayList<JPanel>> getPanels() {
		return panels;
	}

	public void setPanels(ArrayList<ArrayList<JPanel>> panels) {
		this.panels = panels;
	}

}
