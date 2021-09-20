package Felder;

import java.awt.Color;
import java.awt.Graphics2D;

public final class Road extends Field {
	private boolean blocked_;

	Road(boolean blocked, Coordinates pos) {
		blocked_ = blocked;
		coord_ = pos;
	}

	public Road(boolean blocked, int x, int y) {
		blocked_ = blocked;
		coord_.x_ = x;
		coord_.y_ = y;
	}

	@Override
	public boolean isBlocked() {
		return blocked_;
	}

	@Override
	public void print() {
		if (blocked_) {
			System.out.print('%');
			return;
		}
		System.out.print('#');
	}

	@Override
	public void render(Graphics2D g, int maxF, int minF) {
		double colorDiff;
		if (maxF != minF && getF() != 0) {
			colorDiff = (Double.valueOf(getF() - minF) / Double.valueOf(maxF - minF)) * 255;
		} else {
			colorDiff = 0;
		}

		Color c;
		if (isBlocked()) {
			c = new Color(0, 0, 0);
		} else if (isFastest()) {
			c = new Color(255, 0, 0);
		} else {
			c = new Color(255 - (int) Math.floor(colorDiff), 255, 255 - (int) Math.floor(colorDiff));
		}
		g.setColor(c);
		g.fillRect(getPosition().x_, getPosition().y_, 1, 1);
		setRendered(true);
	}
	
	public void setBlocked(boolean blocked) {
		blocked_ = blocked;
	}

	@Override
	public boolean isItem() {
		return false;
	}

}
