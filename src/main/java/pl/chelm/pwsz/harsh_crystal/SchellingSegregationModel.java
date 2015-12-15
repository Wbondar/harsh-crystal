package pl.chelm.pwsz.harsh_crystal;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.control.Label;

public final class SchellingSegregationModel extends Simulation {
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
		if (tolerance > 0. && tolerance < 1.) {
			return new SchellingSegregationModel(board, tolerance);
		}
		return new SchellingSegregationModel(board, 0.3);
	}
	
	private boolean relocateIfNecessary(int x, int y) {
		System.out.println(String.format("Check (%d, %d).", x, y));
		if (isUnsatisfied(x, y)) {
			System.out.println(String.format("Dweller of (%d, %d) is unsatisfied.", x, y));
			relocateToRandomEmptyCellOrDie(x, y);
			return true;
		}
		return false;
	}
	/**
	 * The method assumes, that at least one empty cell exists on the board.
	 * @param x0 position x of actor to relocate
	 * @param y0 position y of actor to relocate
	 */
	private final void relocateToRandomEmptyCellOrDie(final int x0, final int y0) {
		final Board board = getBoard();
		Random random = new Random();
		int x = random.nextInt(board.getWidth());
		int y = random.nextInt(board.getHeight());
		if (board.isEmpty(x, y)) {
			System.out.println(String.format("Move %d from (%d, %d) to (%d, %d).", board.getCellTypeId(x0, y0), x0, y0, x, y));
			final int cellTypeId = board.getCellTypeId(x0, y0);
			board.swap(x0, y0, x, y);
			assert board.isEmpty(x0, y0) == true;
			assert board.getCellTypeId(x, y) == cellTypeId;
			return;
		} else {
			relocateToRandomEmptyCellOrDie(x0, y0);
		}
		System.out.println(String.format("Kill %d at (%d, %d).", board.getCellTypeId(x0, y0), x0, y0));
		board.emptyCell(x0, y0);
		assert board.isEmpty(x0, y0) == true;
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
		int quantityOfAllies = 1;
		int quantityOfEnemies = 0;
		for (int x = x0 - 1; x >= 0 && x < board.getWidth(); x++) {
			for (int y = y0 - 1; y >= 0 && y < board.getHeight(); y++) {
				if (x != x0 && y != y0) {
					if (board.isOccupied(x, y)) {
						if (board.getCellTypeId(x, y) == targetCellTypeId) {
							quantityOfAllies++;
						} else {
							quantityOfEnemies++;
						}
					}
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
