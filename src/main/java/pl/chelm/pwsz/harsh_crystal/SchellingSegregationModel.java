package pl.chelm.pwsz.harsh_crystal;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.control.Label;

public final class SchellingSegregationModel extends Simulation {
	private final static Random RANDOM = new Random();
	/**
	 * `tolerance` must be greater than zero.
	 */
	private final double tolerance;
	private boolean finished = false;
	
	private SchellingSegregationModel(final Board board, final double tolerance) {
		super(board);
		this.tolerance = tolerance;
	}
	
	public static SchellingSegregationModel newInstance(final Board board, final double tolerance) {
		if (board == null) {
			return null;
		}
		return new SchellingSegregationModel(board, tolerance);
	}
	
	private boolean relocateIfNecessary(int x, int y) {
		System.out.println(String.format("Check (%d, %d).", x, y));
		if (isUnsatisfied(x, y)) {
			System.out.println(String.format("Dweller of (%d, %d) is unsatisfied.", x, y));
			relocateToRandomEmptyCell(x, y);
			return true;
		}
		return false;
	}
	/**
	 * The method assumes, that at least one empty cell exists on the board.
	 * @param x0 position x of actor to relocate
	 * @param y0 position y of actor to relocate
	 */
	private final void relocateToRandomEmptyCell(final int x0, final int y0) {
		final Board board = getBoard();
		while (true) {
			int x = RANDOM.nextInt(board.getWidth());
			int y = RANDOM.nextInt(board.getHeight());
			if (board.isEmpty(x, y)) {
				System.out.println(String.format("Move %d from (%d, %d) to (%d, %d).", board.getCellTypeId(x0, y0), x0, y0, x, y));
				final int cellTypeId = board.getCellTypeId(x0, y0);
				board.swap(x0, y0, x, y);
				assert board.isEmpty(x0, y0) == true;
				assert board.getCellTypeId(x, y) == cellTypeId;
				return;
			}
		}
	}
	/**
	 * 
	 * @param x0 position x of actor to check satisfaction
	 * @param y0 position y of actor to check satisfaction
	 * @return `true` if percent of non-kin of occupant is greater than occupant can tolerate and `false` otherwise; `false` if cell of coordinates (`x0`, `y0`) is inoccupied.
	 */
	private boolean isUnsatisfied(int x0, int y0) {
		final Board board = getBoard();
		if (board.isEmpty(x0, y0) || board.isUnreachable(x0, y0)) {
			return false;
		}
		final int targetCellTypeId = board.getCellTypeId(x0, y0);
		int quantityOfAllies = 0;
		int quantityOfEnemies = 0;
		int quantityOfEmpty = 0;
		for (int x = x0 - 1; x < x0 + 1; x++) {
			for (int y = y0 - 1; y < y0 + 1; y++) {
				if (board.getCellTypeId(x, y) == targetCellTypeId) {
					quantityOfAllies++;
				} else {
					quantityOfEnemies++;
				}
			}
		}
		return quantityOfEnemies / quantityOfAllies > tolerance;
	}

	@Override
	public void run() {
		if (isOngoing()) {
			final Board board = getBoard();
			final int width = board.getWidth();
			final int height = board.getHeight();
			boolean relocationOccured = false;
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					relocationOccured = relocateIfNecessary(x, y) || relocationOccured;
				}
			}
			if (!relocationOccured) {
				System.out.println("End.");
				finished  = true;
			}
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
