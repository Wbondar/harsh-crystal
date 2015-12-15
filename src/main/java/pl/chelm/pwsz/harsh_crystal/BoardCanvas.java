package pl.chelm.pwsz.harsh_crystal;

import java.awt.Canvas;
import java.awt.Graphics;

public class BoardCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7786397736961190376L;
	
	private final Board board;
	
	public BoardCanvas(final Board board) {
		this.setSize(board.getWidth() * 10, board.getHeight() * 10);
		this.board = board;
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
			graphics.drawLine(x, 0, x, canvasHeight);
		}
		for (int i = 0; i < height; i++) {
			int y =  i * cellHeight;
			graphics.drawLine(0, y, canvasWidth, y);
		}
	}
}
