import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame implements Runnable{
	
	private volatile int[] sets = {0,0};
	
	static int height_ = 500;
	static int width_ = 500;
	
	JFrame mainFrame = new JFrame("A_Star Pathfinding");
	public JLabel open_set_label = new JLabel();
	JLabel closed_set_label = new JLabel();
	
	JPanel panel = new JPanel();
	
	public GUI() {}
	
	public void openGUI() {
		
		mainFrame.setSize(width_, height_);
		mainFrame.getContentPane();
		
		open_set_label.setText("Open Set: 0");
		closed_set_label.setText("Closed Set: 0");
		
		Dimension size = open_set_label.getPreferredSize();
	    open_set_label.setBounds(10, 10, size.width+100, size.height);
		closed_set_label.setBounds(10, 25, size.width+100, size.height);
		
		panel.setLayout(null);
		
		panel.add(open_set_label);
		panel.add(closed_set_label);
		mainFrame.add(panel);
		mainFrame.setVisible(true);
	}

	@Override
	public void run() {
		open_set_label.setText(String.valueOf(sets[1]));
	}
}
