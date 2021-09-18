package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Rectangle extends JPanel {

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(50, 50, 300, 300);
		g.setColor(Color.GREEN);
		g.fillRect(50, 50, 300, 300);
	}
}
