import java.util.ArrayList;

import javax.swing.JFrame;

import Felder.Field;
import Felder.Item;
import Felder.Road;

public class Board {
	int height_;
	int width_;
	int number_items_;

	String map_data_;

	ArrayList<Field> fields_ = new ArrayList<Field>();
	ArrayList<Item> items_ = new ArrayList<Item>();

	public JFrame frame;

	public Board(int height, int width, int number_items, String map_data) {
		this.height_ = height;
		this.width_ = width;
		this.number_items_ = number_items;
		this.map_data_ = map_data;
		
		initBoard();
	}
	
	public Board() {
		height_ = 50;
		width_ = 50;
		number_items_ = 2;
		int counter = 0;
		for(int y = 0; y < height_; y++) {
			for(int x = 0; x < width_;x++) {
				Field field;
				if(counter == 0) {
					field = new Item('A', x,y);
					items_.add((Item) field);
				} else if(counter == height_*width_-1) {
					field = new Item('B',x,y);
					items_.add((Item) field);
				} else {
					field = new Road(false,x,y);
				}
				fields_.add(field);
				counter++;
			}
		}
	}

	private void initBoard() {
		map_data_ = map_data_.replace("\n", "");
		for (int y = 0; y < height_; y++) {
			for (int x = 0; x < width_; x++) {
				int index = y * width_ + x;
				Field temp_field;

				if (Character.isLetter(map_data_.charAt(index))) {
					temp_field = new Item(map_data_.charAt(index), x, y);
					items_.add((Item) temp_field);
				} else if (map_data_.charAt(index) == '#') {
					temp_field = new Road(false, x, y);
				} else if (map_data_.charAt(index) == '%') {
					temp_field = new Road(true, x, y);
				} else {
					temp_field = null;
				}

				fields_.add(temp_field);
			}
		}
	}
	
	public void setField(Field f,int x, int y) {
		fields_.set(y*width_+x, f);
	}

	void printMap() {
		int width_counter = 0;
		for (Field field : fields_) {
			if (width_counter == width_) {
				width_counter = 0;
				System.out.println();
			}
			if (field != null) {
				field.print();
			} else {
				System.out.print(" ");
			}
			width_counter++;
		}
	}



	void printMapstring() {
		System.out.println(map_data_);
	}

	public int getHeight_() {
		return height_;
	}

	public void setHeight_(int height_) {
		this.height_ = height_;
	}

	public int getWidth_() {
		return width_;
	}

	public void setWidth_(int width_) {
		this.width_ = width_;
	}

	public int getNumber_items_() {
		return number_items_;
	}

	public void setNumber_items_(int number_items_) {
		this.number_items_ = number_items_;
	}

	public String getMap_data_() {
		return map_data_;
	}

	public void setMap_data_(String map_data_) {
		this.map_data_ = map_data_;
	}

	public ArrayList<Field> getFields() {
		return fields_;
	}

	public ArrayList<Item> getItems() {
		return items_;
	}

	public ArrayList<ArrayList<Field>> getFields2d() {
		ArrayList<ArrayList<Field>> temp = new ArrayList<ArrayList<Field>>();
		
		for(int y = 0; y < height_; y++) {
			temp.add(new ArrayList<>());
			for(int x = 0; x < width_; x++) {
				temp.get(y).add(fields_.get(y*width_+x));
			}
		}
		return temp;
	}

	
	

}
