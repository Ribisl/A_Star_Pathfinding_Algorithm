package Felder;

public final class Item extends Field{
	char name_;
	
	public Item() {}
	public Item(char name, Coordinates pos) {
		name_ = name;
		coord_ = pos;
	}
	
	public Item(char name, int x, int y) {
		name_ = name;
		coord_.x_ = x;
		coord_.y_ = y;
	}
	
	char getName() {
		return name_;
	}
	
	public void whatAmI() {
		System.out.println("I am a Item");
	}
	
	@Override
	public
	boolean isBlocked() {
		return true;
	}
	
	@Override
	public 
	void print() {
		System.out.print(name_);
	}
}
