package pl.chelm.pwsz.harsh_crystal;

import java.beans.PropertyDescriptor;

public abstract class SimulationBuilder {
	private final BoardBuilder boardBuilder;
	
	SimulationBuilder() {
		this(new BoardBuilder());
	}
	
	SimulationBuilder(BoardBuilder boardBuilder) {
		assert boardBuilder != null;
		this.boardBuilder = boardBuilder;
	}
	
	public final BoardBuilder getBoardBuilder() {
		return boardBuilder;
	}
	
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
}
