import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import Felder.Field;

public class Renderer extends Canvas {

	private JFrame frame;
	private int fps = 0;
	private final String title = "Path Finding";
	private long currSecond = 0;

	public static final int width = 1000, height = 1000;

	private int maxF, minF;

	public Renderer() {
		createFrame(title);
	}

	public void render(ArrayList<ArrayList<Field>> board) {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(1);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		long timestamp = System.currentTimeMillis() / 1000;
		if (currSecond != timestamp) {
			currSecond = timestamp;
			frame.setTitle(title + " FPS: " + fps);
			fps = 0;
		}

		fps++;

		g.scale(width / Double.valueOf(board.get(0).size()), height / Double.valueOf(board.size()));

		maxF = 0;
		minF = Integer.MAX_VALUE;

		findMaxMinF(board);

		for (ArrayList<Field> x : board) {
			for (Field f : x) {
				if (f != null) {
					f.render(g, maxF, minF);
				}
			}
		}

		g.dispose();
		bs.show();
	}

	private void findMaxMinF(ArrayList<ArrayList<Field>> board) {
		for (ArrayList<Field> x : board) {
			for (Field f : x) {
				if (f != null) {
					if (f.getF() > maxF) {
						maxF = f.getF();
					}
					if (f.getF() < minF && f.getF() != 0) {
						minF = f.getF();
					}
				}
			}
		}
	}

	private void createFrame(String titel) {
		frame = new JFrame(titel);
		frame.setUndecorated(true);
		frame.setSize(width, height);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setVisible(true);
	}

}
