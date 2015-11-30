package pl.chelm.pwsz.harsh_crystal;

public final class UnsupportedSimulationTypeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3724977244600926466L;

	UnsupportedSimulationTypeException(Class<? extends Simulation> simulationType) {
		super("Failed to instantiate simulation builder due to unsupported simulation type " + simulationType.getName() + ".");
	}
}
