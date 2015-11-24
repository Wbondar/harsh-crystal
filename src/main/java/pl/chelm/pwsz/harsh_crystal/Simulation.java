package pl.chelm.pwsz.harsh_crystal;

public abstract class Simulation implements Runnable {
	private final Board board;
	
	protected Simulation() {
		this.board = Board.newInstance(1, 1);
	}

	protected Simulation(Board board) {
		this.board = board;
	}

	public final Board getBoard() {
		return board;
	}
}
