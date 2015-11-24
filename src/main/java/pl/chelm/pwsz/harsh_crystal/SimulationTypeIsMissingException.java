package pl.chelm.pwsz.harsh_crystal;

public final class SimulationTypeIsMissingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1058317256203873730L;

	SimulationTypeIsMissingException() {
		super("No simulation type was provided to the builder.");
	}
}
