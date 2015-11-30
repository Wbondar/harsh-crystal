package pl.chelm.pwsz.harsh_crystal;

public final class SimulationBuilderInstantiationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6409427374180676134L;

	SimulationBuilderInstantiationException(Throwable cause) {
		super("Failed to instantiate simulation builder.", cause);
	}
}
