package pl.chelm.pwsz.harsh_crystal;

final class SchellingSegregationModelBuilder extends SimulationBuilder {
	private double tolerance = 1.0 / 3.0;
	
	public SchellingSegregationModelBuilder() {
		super();
	}

	public final void setTolerance(final double tolerance) {
		if (tolerance > 0. && tolerance < 1.) {
			this.tolerance = tolerance;
		}
	}

	public final double getTolerance() {
		return tolerance;
	}

	@Override
	public Simulation build() {
		final Board board = getBoard();
		if (board == null) {
			throw new BoardIsMissingException();
		}
		return (Simulation)SchellingSegregationModel.newInstance(board, tolerance);
	}
}
