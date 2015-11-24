package pl.chelm.pwsz.harsh_crystal;

import java.util.function.Predicate;

public final class NeighborhoodPredicate implements Predicate<Board.Position>{
	private final Board.Position positionToFindNeighborsFor;
	
	NeighborhoodPredicate (Board.Position positionToFindNeighborsFor) {
		this.positionToFindNeighborsFor = positionToFindNeighborsFor;
	}
	
	public boolean test(Board.Position assumedNeighbor) {
		return assumedNeighbor.getX() >= positionToFindNeighborsFor.getX() - 1
				&& assumedNeighbor.getY() >= positionToFindNeighborsFor.getY() - 1;
	}

}
