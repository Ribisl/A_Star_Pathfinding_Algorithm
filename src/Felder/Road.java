package Felder;

public final class Road extends Field {
	private boolean blocked_;
	
	Road(boolean blocked, Coordinates pos){
		blocked_ = blocked;
		coord_ = pos;
	}
	
	public Road(boolean blocked,int x, int y) {
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
		if(blocked_) {
			System.out.print('%');
			return;
		}
		System.out.print('#');
	}

}
