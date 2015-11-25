package pl.chelm.pwsz.harsh_crystal;

import java.util.Optional;
import java.util.stream.Stream;

public final class SchellingSegregationModel extends Simulation {
	/**
	 * `tolerance` must be greater than zero.
	 */
	private final double tolerance;
	
	private SchellingSegregationModel(final Board board, final double tolerance) {
		super(board);
		this.tolerance = tolerance;
	}
	
	private boolean isPositionTolerableForKin(final Board.Position positionToCheck, final ActorType kinToCheck) {
		long quantityOfAllies = positionToCheck
				.getOccupiedNeighborhoodStream()
				.filter(p -> p.getOccupant().getType().equals(kinToCheck))
				.count();
		long quantityOfEnemies = positionToCheck.getNeighborhoodStream()
				.count() - quantityOfAllies;
		if (quantityOfEnemies <= 0) {
			return true;
		}
		double coefficientOfAllies = quantityOfAllies / quantityOfEnemies;
		return coefficientOfAllies < tolerance;
	}
	
	private boolean isOccupantUnsatisfied(final Board.OccupiedPosition occupiedPosition) {
		return isPositionTolerableForKin(occupiedPosition, occupiedPosition.getOccupant().getType());
	}
	
	private final Stream<Board.OccupiedPosition> getUnsatisfiedOccupiedPositionsStream() {
		return getBoard()
				.getOccupiedPositionsStream()
				.filter(p -> isOccupantUnsatisfied(p));
	}
	
	private final Stream<Board.EmptyPosition> getTolerableNeighborhoodStream(final Board.OccupiedPosition positionOfActorToRelocate) {
		final ActorType kin = positionOfActorToRelocate.getOccupant().getType();
		return positionOfActorToRelocate
				.getEmptyNeighborhoodStream()
				.filter(p -> isPositionTolerableForKin(p, kin));
	}
	
	private final void relocateOrKillOccupant(Board.OccupiedPosition positionOfActorToRelocate) {
		Optional<Board.EmptyPosition> aim = getTolerableNeighborhoodStream(positionOfActorToRelocate).findFirst();
		if (aim.isPresent()) {
			aim.get().swap(positionOfActorToRelocate);
		} else {
			positionOfActorToRelocate.empty();
		}
	}
	
	@Override
	public final boolean isFinished() {
		return !getUnsatisfiedOccupiedPositionsStream()
				.findAny().isPresent();
	}
	
	@Override
	public void run() {
		if (isOngoing()) {
			getUnsatisfiedOccupiedPositionsStream().forEach(p -> this.relocateOrKillOccupant(p));	
		}
	}

}
