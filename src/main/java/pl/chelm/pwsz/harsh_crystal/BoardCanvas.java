package pl.chelm.pwsz.harsh_crystal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BoardCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7786397736961190376L;
	private static final int DEFAULT_CELL_WIDTH = 10;
	private static final int DEFAULT_CELL_HEIGHT = 10;
	private static final Color DEFAULT_COLOR = Color.WHITE;
	
	private final Board board;
	private final List<Color> colors = new ArrayList<Color>();
	
	public BoardCanvas(final Board board) {
		this.setSize(board.getWidth() * DEFAULT_CELL_WIDTH, board.getHeight() * DEFAULT_CELL_HEIGHT);
		this.board = board;
	}
	
	private Color getColor(int cellTypeId) {
		if (cellTypeId > 0) {
			if (cellTypeId % 2 == 0) {
				return new Color(255 / cellTypeId, 0, 0);	
			} else {
				return new Color(0, 0, 255 / cellTypeId);	
			}
		}
		return DEFAULT_COLOR;
	}

	@Override
	public void paint(Graphics graphics) {
		final int width = board.getWidth();
		final int height = board.getHeight();
		final int canvasWidth = this.getWidth() >= width ? this.getWidth() : width;
		final int canvasHeight = this.getHeight() >= height ? this.getHeight() : height;
		final int cellWidth = canvasWidth / width > 0 ? canvasWidth / width : 1;
		final int cellHeight = canvasHeight / height > 0 ? canvasHeight / height : 1;
		for (int i = 0; i < width; i++) {
			int x = i * cellWidth;
			//graphics.drawLine(x, 0, x, canvasHeight);
			for (int j = 0; j < height; j++) {
				int y =  j * cellHeight;
				//graphics.drawLine(0, y, canvasWidth, y);
				graphics.setColor(this.getColor(board.getCellTypeId(i, j)));
				graphics.fillRect(x, y, cellWidth, cellHeight);
			}
		}
	}
}
