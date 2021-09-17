import java.util.ArrayList;

import javax.swing.SwingUtilities;

import Felder.Field;
import Felder.Item;

public class A_Star {
	ArrayList<ArrayList<Field>> board_ = new ArrayList<ArrayList<Field>>();
	ArrayList<Field> open_set_ = new ArrayList<Field>();
	ArrayList<Field> closed_set_ = new ArrayList<>();

	Item start_;
	Item goal_;
	int height_;
	int width_;
	
	GUI gui_;

	public A_Star(GUI gui, ArrayList<Field> fields, Item start_, Item goal_, int height_, int width_) {
		super();
		for (int y = 0; y < height_; y++) {
			board_.add(new ArrayList<Field>());
			for (int x = 0; x < width_; x++) {
				board_.get(y).add(fields.get(y * width_ + x));
			}
		}
		this.start_ = start_;
		this.goal_ = goal_;
		this.height_ = height_;
		this.width_ = width_;
		gui_ = gui;

		open_set_.add(start_);
	}

	Field getLowestFScore() {
		Field smallest_score = open_set_.get(0);
		for (int i = open_set_.size() - 1; i >= 0; i--) {
			if (open_set_.get(i).getF() < smallest_score.getF()) {
				smallest_score = open_set_.get(i);
			}
		}
		return smallest_score;
	}

	int getIndexFromCurrentLowF(Field current) {
		for (int i = 0; i < open_set_.size(); i++) {
			if (open_set_.get(i).getFieldId() == current.getFieldId()) {
				return i;
			}
		}
		return -1;
	}

	void addNeighbors(Field current) {
		int x = current.getPosition().x_;
		int y = current.getPosition().y_;

		if (x < width_ - 1) {
			current.setNeighbor_right(board_.get(y).get(x + 1));
		}

		if (x > 0) {
			current.setNeighbor_left(board_.get(y).get(x - 1));
		}

		if (y < height_ - 1) {
			current.setNeighbor_bottom(board_.get(y + 1).get(x));
		}
		if (y > 0) {
			current.setNeighbor_top(board_.get(y - 1).get(x));
		}

	}

	double calculateHeursitic(Field a, Field b) {
		int x_start = a.getPosition().x_;
		int y_start = a.getPosition().y_;

		int x_end = b.getPosition().x_;
		int y_end = b.getPosition().y_;

		return Math.sqrt(Math.pow(x_end - x_start, 2) + Math.pow(y_end - y_start, 2));
	}

	int getArrayIndexFromCurrentLowF(Field current) {
		for (int i = 0; i < open_set_.size(); i++) {
			if (open_set_.get(i).getFieldId() == current.getFieldId()) {
				return i;
			}
		}
		return -1;
	}

	public boolean findPath() {
		start_.setF((int) calculateHeursitic(start_, goal_));
		start_.setG(0);
		while(open_set_.size() != 0) {
			Field current = getLowestFScore();
			if(current.getFieldId() == goal_.getFieldId()) {
				return true;
			}
			
			updateGUI();
			
			//int index_current = getArrayIndexFromCurrentLowF(current);
			open_set_.remove(current);
			closed_set_.add(current);
			addNeighbors(current);
			
			for(int i = 0; i< 4; i++) {
				Field neighbor = current.getNeighbor(i);
				if(neighbor != null) {
					if(neighbor.getFieldId() == goal_.getFieldId()) {
						return true;
					}
					if(!neighbor.isBlocked() && !closed_set_.contains(neighbor)) {
						int temp_g = current.getG() +1;
						if(open_set_.contains(neighbor)) {
							if(temp_g < neighbor.getG()) {
								neighbor.setG(temp_g);
							}
						} else {
							neighbor.setG(temp_g);
							open_set_.add(neighbor);
						}
						neighbor.setH((int) calculateHeursitic(neighbor, goal_));
						neighbor.setF(neighbor.getH() + neighbor.getG());
					}
				}
			}
			
		}

		return false;
	}
	
	public void updateGUI() {
		   gui_.closed_set_label.setText("Closed Set: " + String.valueOf(closed_set_.size()));
		   gui_.open_set_label.setText("Open Set: " + String.valueOf(open_set_.size()));
	}


}
