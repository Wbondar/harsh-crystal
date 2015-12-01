package pl.chelm.pwsz.harsh_crystal;

import java.lang.reflect.Modifier;

public final class SimulationBuilders {
	private SimulationBuilders() {
		throw new AssertionError("Class of name " + SimulationBuilders.class.getName() + " does not meant to be instantiated.");
	}

	public static <K extends SimulationBuilder> K getInstance (final Class<? extends Simulation> simulationType, BoardBuilder boardBuilder) 
			throws SimulationBuilderInstantiationException, UnsupportedSimulationTypeException {
		if (simulationType == null || Modifier.isAbstract(simulationType.getModifiers())) {
			throw new UnsupportedSimulationTypeException(simulationType);
		}
		/*
		 * TODO: Replace naming convention with something more efficient.
		 */
		Class<K> simulationBuilderClass = null;
		try {
			simulationBuilderClass = (Class<K>) Class.forName(simulationType.getName() + "Builder");
		} catch (ClassNotFoundException e) {
			simulationBuilderClass = null;
		}
		if (simulationBuilderClass != null && SimulationBuilder.class.isAssignableFrom(simulationBuilderClass)) {
			try {
				if (boardBuilder != null) {
					return simulationBuilderClass.getConstructor(boardBuilder.getClass()).newInstance(boardBuilder);
				} else {
					return simulationBuilderClass.newInstance( );	
				}
			} catch (Exception e) {
				throw new SimulationBuilderInstantiationException(e);
			}
		} else {
			throw new UnsupportedSimulationTypeException(simulationType);
		}
	}
}
