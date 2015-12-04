package pl.chelm.pwsz.harsh_crystal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public abstract class Simulation implements Runnable {
	/* TODO Ensure immutability and prevent injections of `board`. */
	private final Board board;

	protected Simulation(final Board board) {
		this.board = board;
	}

	public final Board getBoard() {
		return board;
	}
	
	public abstract boolean isFinished();
	
	public final boolean isOngoing() {
		return !isFinished();
	}
}
