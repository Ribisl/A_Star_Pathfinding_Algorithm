import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import java.awt.event.*;

import javax.swing.JFrame;

import Felder.Field;
import Felder.Road;

public class Renderer extends Canvas {

	private JFrame frame;
	private int fps = 0;
	private final String title = "Path Finding";
	private long currSecond = 0;

	public static int width = 1000, height = 1000;

	private int maxF, minF;

	public Point p = new Point(0, 0);

	Board board_;

	public Renderer(Board board) {
		createFrame(title);
		board_ = board;
		render(null,false);
	}

	public void render(Board fields2d_, boolean renderAll) {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(1);
			return;
		}
		board_ = fields2d_;
		ArrayList<ArrayList<Field>> board = fields2d_.getFields2d();
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

		if (renderAll) {
			findMaxMinF(board);
		}
		for (ArrayList<Field> x : board) {
			for (Field f : x) {
				if (f != null && (!f.isRendered() || renderAll)) {
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
		frame.setUndecorated(false);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen testing
		frame.setSize(width, height);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});

		frame.setVisible(true);
		width = frame.getWidth();
		height = frame.getHeight();
		frame.requestFocus();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("repaint");
		if (board_ != null) {
			board_.setField(new Road(false, p.x, p.y), p.x, p.y);
		}
		// g.drawRect(point_x, point_y, 1, 1);
		// g.setColor(Color.RED);
	}

	public JFrame getJFrame() {
		return frame;
	}

	public Canvas getCanvas() {
		return this;
	}

}
