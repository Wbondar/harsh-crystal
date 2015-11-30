package pl.chelm.pwsz.harsh_crystal;

import java.lang.reflect.Modifier;

public final class SimulationBuilders {
	private SimulationBuilders() {
		throw new AssertionError("This " + SimulationBuilders.class.getName() + " does not meant to be instantiated.");
	}

	public static <K extends SimulationBuilder> K getInstance (final Class<? extends Simulation> simulationType) 
			throws SimulationBuilderInstantiationException, UnsupportedSimulationTypeException {
		if (simulationType == null || Modifier.isAbstract(simulationType.getModifiers())) {
			throw new UnsupportedSimulationTypeException(simulationType);
		}
		/*
		 * TODO: Replace naming convention with something more efficient.
		 */
		Class<K> builerClass = null;
		try {
			builerClass = (Class<K>) Class.forName(simulationType.getName() + "Builder");
		} catch (ClassNotFoundException e) {
			builerClass = null;
		}
		if (builerClass != null && SimulationBuilder.class.isAssignableFrom(builerClass)) {
			try {
				return builerClass.newInstance( );
			} catch (Exception e) {
				throw new SimulationBuilderInstantiationException(e);
			}
		} else {
			throw new UnsupportedSimulationTypeException(simulationType);
		}
	}
}
