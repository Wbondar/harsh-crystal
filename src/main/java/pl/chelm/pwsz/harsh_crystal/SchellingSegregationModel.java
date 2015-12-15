package pl.chelm.pwsz.harsh_crystal;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.control.Label;

public final class SchellingSegregationModel extends Simulation {
	/**
	 * `tolerance` must be greater than zero.
	 */
	private final double tolerance;
	private boolean relocationOccured;
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
	
	private void relocateIfNecessary(int x, int y) {
		if (isUnsatisfied(x, y)) {
			relocationOccured = true || relocationOccured ;
			relocateToNearestEmptyCellOrDie(x, y);
		}
	}
	
	private final void relocateToNearestEmptyCellOrDie(final int x0, final int y0) {
		final Board board = getBoard();
		for (int x = x0 - 1; x >= 0 && x < board.getWidth(); x++) {
			for (int y = y0 - 1; y >= 0 && y < board.getHeight(); y++) {
				if (board.isEmpty(x, y)) {
					board.swap(x0, y0, x, y);
					return;
				}
			}
		}
		board.emptyCell(x0, y0);
	}

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
			relocationOccured = false;
			IntStream.iterate(0, i -> i++).limit(width)
			.forEach(x -> IntStream.iterate(0, i -> i++).limit(height).forEach(y -> relocateIfNecessary(x, y)));
			if (relocationOccured) {
				finished  = true;
			}
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
