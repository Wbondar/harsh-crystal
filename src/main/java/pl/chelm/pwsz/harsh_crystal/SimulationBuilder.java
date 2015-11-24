package pl.chelm.pwsz.harsh_crystal;

import java.lang.reflect.Modifier;

public final class SimulationBuilder {
	private Class<? extends Simulation> currentSimulationType = null;
	private int                         currentBoardWidth     = 1;
	private int                         currentBoardHeight    = 1;
	
	public SimulationBuilder() {}

	public final SimulationBuilder setType (final Class<? extends Simulation> newSimulationType) {
		if (!Modifier.isAbstract(newSimulationType.getModifiers())) {
			currentSimulationType = newSimulationType;
		} else {
			currentSimulationType = null;
		}
		return this;
	}
	
	public final SimulationBuilder setBoardWidth(final int newBoardWidth) {
		if (newBoardWidth < 1) {
			currentBoardWidth = 1;
		} else {
			currentBoardWidth = newBoardWidth;
		}
		return this;
	}
	
	public final SimulationBuilder setBoardHeight(final int newBoardHeight) {
		if (newBoardHeight < 1) {
			currentBoardHeight = 1;
		} else {
			currentBoardHeight = newBoardHeight;
		}
		return this;
	}
	
	public final Simulation build() {
		try {
			return currentSimulationType
					.getConstructor(Board.class)
					.newInstance(Board.newInstance(currentBoardWidth, currentBoardHeight));
		} catch (Exception e) {
			throw new AssertionError("Failed to build a simulation.", e);
		}
	}
}
