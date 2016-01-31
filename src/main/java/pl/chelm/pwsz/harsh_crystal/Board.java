package pl.chelm.pwsz.harsh_crystal;
/**
 * Board class represents a filed (or "magic circle", as it sometimes referred),
 * on which simulation takes place.
 * Board class does not hold any logic, responsible for simulation.
 * It is up to Simulation class derivatives to handle behaviour of actors on a board.
 * @author Vladyslav Bondarenko
 *
 */
public class Board {
	private final int     width;
	private final int     height;
	private final int[][] grid;
	
	protected Board() {
		this(1, 1);
	}
	
	private Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new int[width][height];
	}

	public static Board newInstance (int width, int height) {
		if (width > 0 && height > 0) {
			return new Board(width, height);
		}
		return null;
	}
	
	/**
	 * Quantity of columns on the board.
	 * @return always positive
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Quantity of rows on the board.
	 * @return always positive
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return zero if empty; negative if unreachable; positive if occupied.
	 */
	public int getCellTypeId(int x, int y) {
		if (isReachable(x, y)) {
			return grid[x][y];	
		}
		return -1;
	}
	
	public void setCellTypeId(int x, int y, int typeId) {
		if (isReachable(x, y) && typeId >= 0) {
			grid[x][y] = typeId;	
		}
	}
	
	public void emptyCell(int x, int y) {
		if (isReachable(x, y)) {
			grid[x][y] = 0;	
		}
	}
	
	public void swap(int x0, int y0, int x1, int y1) {
		if (isReachable(x0, y0) && isReachable(x1, y1)) {
			int swap = getCellTypeId(x0, y0);
			setCellTypeId(x0, y0, getCellTypeId(x1, y1));
			setCellTypeId(x1, y1, swap);
		}
	}

	public boolean isEmpty(int x, int y) {
		return getCellTypeId(x, y) == 0;
	}

	public boolean isOccupied(int x, int y) {
		return getCellTypeId(x, y) > 0;
	}
	
	public boolean isReachable(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public boolean isUnreachable(int x, int y) {
		return !isReachable(x, y);
	}
}
