package pl.chelm.pwsz.harsh_crystal;

import java.beans.PropertyDescriptor;

public abstract class SimulationBuilder {
	private Board board = null;
	
	SimulationBuilder() {}
	
	public final <V> SimulationBuilder setProperty(String key, final V value) {
		try {
			(new PropertyDescriptor(key, this.getClass()))
			.getWriteMethod()
			.invoke(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public final <V> V getProperty(final String key) {
		try {
			return (V)(new PropertyDescriptor(key, this.getClass()))
			.getReadMethod()
			.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract Simulation build();

	public final SimulationBuilder setBoard(final Board board) {
		this.board = board;
		return this;
	}
	
	public final Board getBoard() {
		return board;
	}
}
