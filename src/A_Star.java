import java.util.ArrayList;

import Felder.Field;
import Felder.Item;

public class A_Star {
	public ArrayList<ArrayList<Field>> fields__ = new ArrayList<ArrayList<Field>>();
	public ArrayList<Field> open_set_ = new ArrayList<Field>();

	Board board_;
	Item start_;
	Item goal_;
	int height_;
	int width_;

	private Renderer renderer;
	
	int frameskip = 1; // set to high number when bigger board (1000x1000) for faster rendering

	public A_Star(Board board, Item start_, Item goal_, int height_, int width_, Renderer renderer) {
		super();
		board_ = board;
		for (int y = 0; y < height_; y++) {
			fields__.add(new ArrayList<Field>());
			for (int x = 0; x < width_; x++) {
				fields__.get(y).add(board.getFields().get(y * width_ + x));
			}
		}
		this.start_ = start_;
		this.goal_ = goal_;
		this.height_ = height_;
		this.width_ = width_;

		this.renderer = renderer;

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
			current.setNeighbor_right(fields__.get(y).get(x + 1));
		}

		if (x > 0) {
			current.setNeighbor_left(fields__.get(y).get(x - 1));
		}

		if (y < height_ - 1) {
			current.setNeighbor_bottom(fields__.get(y + 1).get(x));
		}
		if (y > 0) {
			current.setNeighbor_top(fields__.get(y - 1).get(x));
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
		ArrayList<Field> closed_set_ = new ArrayList<>();
		start_.setF((int) calculateHeursitic(start_, goal_));
		start_.setG(0);

		boolean needsRerender = true;
		long framecounter = 0;

		while (open_set_.size() != 0) {
			Field current = getLowestFScore();
			if (needsRerender) {
				framecounter++;
				if(framecounter%frameskip == 0) {
					renderer.render(board_, false);
					needsRerender = false;
				}
			}
			if (current.getFieldId() == goal_.getFieldId()) {
				return true;
			}

			open_set_.remove(current);
			closed_set_.add(current);
			addNeighbors(current);

			for (int i = 0; i < 4; i++) {
				Field neighbor = current.getNeighbor(i);
				if (neighbor != null) {
					if (neighbor.getFieldId() == goal_.getFieldId()) {
						reconstructPath(current);
						return true;
					}
					if (!neighbor.isBlocked() && !closed_set_.contains(neighbor)) {
						int temp_g = current.getG() + 1;
						if (open_set_.contains(neighbor)) {
							if (temp_g < neighbor.getG()) {
								neighbor.setG(temp_g);
							}
						} else {
							neighbor.setG(temp_g);
							open_set_.add(neighbor);
						}
						neighbor.setH((int) calculateHeursitic(neighbor, goal_));
						int prevF = neighbor.getF();
						neighbor.setF(neighbor.getH() + neighbor.getG());
						neighbor.setPrevious(current);
						if(prevF != neighbor.getF()) {
							needsRerender = true;
						}
					}
				}
			}

		}

		return false;
	}

	public void reconstructPath(Field current) {
		Field temp = current;
		while (temp.getPrevious() != null) {
			temp.setFastest(true);
			temp = temp.getPrevious();
		}
		System.out.println("\nReconstructed Path");
	}
}
