package pl.chelm.pwsz.harsh_crystal;

import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class BoardBuilder {
	private int currentWidth                = 1;
	private int currentHeight               = 1;
	private int currentQuantityOfActorTypes = 1;
	private int currentQuantityOfActors     = 0;

	BoardBuilder() {
	}

	public final BoardBuilder setWidth(final int newWidth) {
		if (newWidth < 1) {
			currentWidth = 1;
		} else {
			currentWidth = newWidth;
		}
		return this;
	}

	public final BoardBuilder setHeight(final int newHeight) {
		if (newHeight < 1) {
			currentHeight = 1;
		} else {
			currentHeight = newHeight;
		}
		return this;
	}

	public final int getWidth() {
		return currentWidth;
	}

	public final int getHeight() {
		return currentHeight;
	}

	public final int getQuantityOfActorTypes() {
		return currentQuantityOfActorTypes;
	}

	public final BoardBuilder setQuantityOfActorTypes(final int newQuantityOfActorTypes) {
		if (newQuantityOfActorTypes < 1) {
			this.currentQuantityOfActorTypes = 1;
			return this;
		}
		this.currentQuantityOfActorTypes = newQuantityOfActorTypes;
		return this;
	}

	public final int getQuantityOfActors() {
		return currentQuantityOfActors;
	}

	public final BoardBuilder setQuantityOfActors(final int newQuantityOfActors) {
		if (newQuantityOfActors < 0) {
			this.currentQuantityOfActors = 0;
			return this;
		}
		this.currentQuantityOfActors = newQuantityOfActors;
		return this;
	}

	public final Board build() {
		final Board board = Board.newInstance(currentWidth, currentHeight);
		Iterator<Integer> i = Stream.iterate(1, j -> {if (j >= currentQuantityOfActorTypes) {return 1;} return ++j;}).iterator();
		board.getEmptyPositionsStream().limit(currentQuantityOfActors)
		.forEach(p -> p.setOccupant(new Actor(ActorType.getInstance(i.next()))));
		return board;
	}
}
