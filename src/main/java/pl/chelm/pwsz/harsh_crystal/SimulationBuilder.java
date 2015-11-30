package pl.chelm.pwsz.harsh_crystal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class SimulationBuilder {
	private int currentBoardWidth  = 1;
	private int currentBoardHeight = 1;
	
	SimulationBuilder() {}
	
	public final SimulationBuilder setParameter(String key, final Object value) {
		key = key.trim();
		try {
			this.getClass()
			.getMethod("set" + Character.toTitleCase(key.charAt(0)) + key.substring(1), value.getClass())
			.invoke(this, value);
		} catch (Exception e) {
			/* Do nothing. */
		}
		return this;
	}
	
	protected final <V> V getParameter(final String key) {
		try {
			return (V)this.getClass().getDeclaredField(key.trim()).get(this);
		} catch (Exception e) {
			return null;
		}
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
	
	public final int getBoardWidth() {
		return currentBoardWidth;
	}
	
	public final int getBoardHeight() {
		return currentBoardHeight;
	}
	
	public abstract Simulation build();

	public final Board newBoard() {
		return Board.newInstance(currentBoardWidth, currentBoardHeight);
	}
}
