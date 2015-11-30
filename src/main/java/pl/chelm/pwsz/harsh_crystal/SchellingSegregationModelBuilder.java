package pl.chelm.pwsz.harsh_crystal;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

final class SchellingSegregationModelBuilder extends SimulationBuilder {
	private double tolerance = 1.0 / 3.0;
	private int quantityOfRaces = 2;
	private int quantityOfActors = 50;

	public final double getTolerance() {
		return tolerance;
	}

	public final SchellingSegregationModelBuilder setTolerance(final double tolerance) {
		if (tolerance > 0. && tolerance < 1.) {
			this.tolerance = tolerance;
		}
		return this;
	}

	public final int getQuantityOfRaces() {
		return quantityOfRaces;
	}

	public final SchellingSegregationModelBuilder setQuantityOfRaces(final int quantityOfRaces) {
		if (quantityOfRaces < 1) {
			this.quantityOfRaces = 1;
			return this;
		}
		this.quantityOfRaces = quantityOfRaces;
		return this;
	}

	public final int getQuantityOfActors() {
		return quantityOfActors;
	}

	public final SchellingSegregationModelBuilder setQuantityOfActors(final int quantityOfActors) {
		if (quantityOfActors < 1) {
			this.quantityOfActors = 1;
			return this;
		}
		this.quantityOfActors = quantityOfActors;
		return this;
	}

	@Override
	public Simulation build() {
		final Board board = newBoard();
		IntStream
		.range(0, quantityOfActors)
		/* TODO Make sure, that races are created and assigned properly. */
		.forEach(i -> board.getEmptyPositionsStream().findAny().ifPresent(p -> p.setOccupant(new Actor(ActorType.getInstance(i / quantityOfRaces)))));
		return (Simulation)SchellingSegregationModel.newInstance(board, tolerance);
	}
}
