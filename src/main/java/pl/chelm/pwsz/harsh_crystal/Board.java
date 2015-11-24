package pl.chelm.pwsz.harsh_crystal;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Board {
	private final int width;
	private final int height;
	private final Set<Position> positions;
	
	public final class Position {
		private final int x;
		private final int y;
		private final Board board;
		private final Actor occupant;
		
		private Position(Board board, int x, int y, Actor occupant) {
			this.board = board;
			this.x = x;
			this.y = y;
			this.occupant = occupant;
		}
		
		public final Board getBoard() {
			return board;
		}
		
		public final int getX() {
			return x;
		}
		
		public final int getY() {
			return y;
		}
		
		public final Position setOccupant(Actor actorToPlaceOnThePosition) {
			Position updatedPosition = new Position (board, x, y, actorToPlaceOnThePosition);
			board.positions.remove(this);
			board.positions.add(updatedPosition);
			return updatedPosition;
		}
		
		public final Actor getOccupant() {
			return occupant;
		}
		
		public final Stream<Position> getNeighborhood() {
			return board.positions.parallelStream()
					.filter((Predicate<Position>)new NeighborhoodPredicate(this));
		}
		
		public final boolean isEmpty() {
			return occupant == null;
		}
		
		public final boolean isOccupied() {
			return occupant != null;
		}
	}
	
	private Board (int width, int height, Set<Position> positions) {
		this.width = width;
		this.height = height;
		this.positions = positions;
	}
	
	public static Board newInstance (int width, int height) {
		if (width > 0 && height > 0) {
			return new Board(width, height, new HashSet<Position>());
		}
		return null;
	}
	
	public final int getWidth() {
		return width;
	}
	
	public final int getHeight() {
		return height;
	}
	
	public final Stream<Position> getPositionsStream() {
		return positions.parallelStream();
	}
	
	public final Set<Position> getPositions() {
		return getPositionsStream().collect(Collectors.toSet());
	}
	
	public final Stream<Actor> getActorsStream() {
		return positions.parallelStream()
				.filter(Board.Position::isOccupied)
				.map(p -> p.getOccupant());
	}
	
	public final Set<Actor> getActors() {
		return getActorsStream().collect(Collectors.toSet());
	}
}
