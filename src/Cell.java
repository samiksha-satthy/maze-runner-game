import javax.swing.JLabel;

//template class to build Cell Object 
public class Cell extends JLabel {

	// fields
	private int row;
	private int column;

	// constructor method
	public Cell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	// constructor method
	public Cell() {

	}

	// getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	// prints out each instance variable
	@Override
	public String toString() {
		return "Cell [row=" + row + ", column=" + column + "]";
	}

}
