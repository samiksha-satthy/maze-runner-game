
//imports 
import javax.swing.ImageIcon;

//extends existing class 
//template class to build Character Object
public class Character extends Cell {

	// Constructor method
	public Character(ImageIcon image) {

		setIcon(image);
	}

	// Constructor method
	public void move(int dRow, int dColumn) {

		setRow(getRow() + dRow);
		setColumn(getColumn() + dColumn);

	}

}
