package Felder;

import java.awt.Graphics2D;

public abstract class Field {
	private int field_id_ = 0;
	private static int counter = 0;

	public class Coordinates {
		public int x_;
		public int y_;
	}

	Coordinates coord_ = new Coordinates();

	private int f_ = 0;
	private int g_ = 0;
	private int h_ = 0;

	private Field neighbor_right;
	private Field neighbor_left;
	private Field neighbor_top;
	private Field neighbor_bottom;
	Field[] neighbors = { null, null, null, null };
	private Field previous;
	private boolean isFastest = false;
	private boolean isRendered = false;

	public Field() {
		counter++;
		field_id_ = counter;

	}

	public Field(int x, int y) {
		coord_.x_ = x;
		coord_.y_ = y;
		field_id_ = counter;
	}

	public int getFieldId() {
		return field_id_;
	}

	public Coordinates getPosition() {
		return coord_;
	}

	public void whatAmI() {
		System.out.println("I am a Field");
	}

	abstract public boolean isBlocked();

	abstract public void print();

	abstract public void render(Graphics2D g, int maxF, int minF);

	public int getF() {
		return f_;
	}

	public void setF(int f_) {
		if(f_ != this.f_) {
			this.setRendered(false);
		}
		this.f_ = f_;
	}

	public int getG() {
		return g_;
	}

	public void setG(int g_) {
		this.g_ = g_;
	}

	public int getH() {
		return h_;
	}

	public void setH(int h_) {
		this.h_ = h_;
	}

	public Field getNeighbor_right() {
		return neighbor_right;
	}

	public void setNeighbor_right(Field neighbor_right) {
		this.neighbor_right = neighbor_right;
		neighbors[1] = neighbor_right;
	}

	public Field getNeighbor_left() {
		return neighbor_left;
	}

	public void setNeighbor_left(Field neighbor_left) {
		this.neighbor_left = neighbor_left;
		neighbors[3] = neighbor_left;
	}

	public Field getNeighbor_top() {
		return neighbor_top;
	}

	public void setNeighbor_top(Field neighbor_top) {
		this.neighbor_top = neighbor_top;
		neighbors[0] = neighbor_top;
	}

	public Field getNeighbor_bottom() {
		return neighbor_bottom;
	}

	public void setNeighbor_bottom(Field neighbor_bottom) {
		this.neighbor_bottom = neighbor_bottom;
		neighbors[2] = neighbor_bottom;
	}

	public Field getNeighbor(int i) {
		return neighbors[i];
	}

	public Field getPrevious() {
		return previous;
	}

	public void setPrevious(Field previous) {
		this.previous = previous;
	}

	public boolean isFastest() {
		return isFastest;
	}

	public void setFastest(boolean isFastest) {
		this.isFastest = isFastest;
	}

	public boolean isRendered() {
		return isRendered;
	}

	public void setRendered(boolean isRendered) {
		this.isRendered = isRendered;
	}

}
