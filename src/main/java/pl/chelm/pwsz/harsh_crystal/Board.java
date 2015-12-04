package pl.chelm.pwsz.harsh_crystal;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
/**
 * Board class represents a filed (or "magic circle", as it sometimes referred),
 * on which simulation takes place.
 * Board class does not hold any logic, responsible for simulation.
 * It is up to Simulation class derivatives to handle behaviour of actors on a board.
 * @author Vladyslav Bondarenko
 *
 */
public final class Board {
	private final int width;
	private final int height;
	private final Actor[][] grid;

	/**
	 * Position class represents a single cell on a board.
	 * Position is implemented as an inner class towards Board class,
	 * in order to avoid implementing Observer pattern on Board class,
	 * thus reducing complexity of the program.
	 * Position class is made abstract in order to 
	 * make possible implementation of immutable derivatives,
	 * benefiting parallel programming this way.
	 */
	public abstract class Position {
		private final int x;
		private final int y;
		private final Board board;
		
		
		private Position(final Board board, int x, int y) {
			this.board = board;
			this.x = x;
			this.y = y;
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
		/**
		 * Moore's neighborhood.
		 * @param assumedNeighbor
		 * @return `true` if assumedNeighbor is one point away from this position and is not equal to this position; `false` otherwise.
		 */
		public final boolean isNeighbor(final Position assumedNeighbor) {
			if (assumedNeighbor.getBoard() == board) {
				if (assumedNeighbor != this) {
					if (assumedNeighbor.getX() >= x - 1 && assumedNeighbor.getY() >= y - 1) {
						return true;
					}
				}
			}
			return false;
		}
		
		public final Stream<? extends Position> getNeighborhoodStream() {
			return board.getPositionsStream()
					.filter(p -> p.isNeighbor(this));
		}
		
		public final Stream<OccupiedPosition> getOccupiedNeighborhoodStream() {
			return getNeighborhoodStream()
					.filter(Position::isOccupied)
					.map(OccupiedPosition.class::cast);
		}
		
		public final Stream<EmptyPosition> getEmptyNeighborhoodStream() {
			return getNeighborhoodStream()
					.filter(Position::isEmpty)
					.map(EmptyPosition.class::cast);
		}
		
		public final Set<? extends Position> getNeighborhood() {
			return getNeighborhoodStream()
					.collect(Collectors.toSet());
		}
		
		/**
		 * Sets an occupant to the position.
		 * Removes the old one, if needed.
		 * Important: this method does not mutate it's object!
		 * Instead, it instantiates complete new object, 
		 * which class extends Position class,
		 * and replaces given position with newly created on the board.
		 * It is done so for the sake of immutablility, parallel programming 
		 * and performance in the long run.
		 * @param newOccupant
		 * @return
		 */
		
		public final Position setOccupant(final Actor newOccupant) {
			Position updatedPosition = null;
			if (newOccupant != null) {
				updatedPosition = new OccupiedPosition (board, x, y, newOccupant);
			} else {
				if (this.isEmpty()) {
					return this;
				}
				updatedPosition = new EmptyPosition(board, x, y);
			}
			board.grid[x][y] = newOccupant;
			return updatedPosition;
		}

		public final void empty() {
			setOccupant(null);
		}
		
		public final void swap(final Position positionToSwapActorsWith) {
			Actor actorToSwapWith = positionToSwapActorsWith.getOccupant();
			positionToSwapActorsWith.setOccupant(this.getOccupant());
			this.setOccupant(actorToSwapWith);
		}
		
		public abstract Actor getOccupant();
		
		public final boolean isOccupied() {
			return !isEmpty();
		}
		
		public final boolean isEmpty() {
			return getOccupant() == null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((board == null) ? 0 : board.hashCode());
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (board == null) {
				if (other.board != null)
					return false;
			} else if (!board.equals(other.board))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private Board getOuterType() {
			return Board.this;
		}
	}
	
	/**
	 * EmptyPosition class is immutable class that represents
	 * empty cell on a board.
	 */
	public final class EmptyPosition extends Position {
		private EmptyPosition(Board board, int x, int y) {
			super(board, x, y);
		}
		
		@Override
		public final Actor getOccupant() {
			return null;
		}
	}
	
	/**
	 * EmptyPosition class is immutable class that represents
	 * occupied cell on a board.
	 */
	public final class OccupiedPosition extends Position {
		private final Actor occupant;
		
		private OccupiedPosition(Board board, int x, int y, Actor occupant) {
			super(board, x, y);
			this.occupant = occupant;
		}
		
		@Override
		public final Actor getOccupant() {
			return occupant;
		}
	}
	
	private Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new Actor[width][height];
	}

	public static Board newInstance (int width, int height) {
		if (width > 0 && height > 0) {
			return new Board(width, height);
		}
		return null;
	}
	
	public final int getWidth() {
		return width;
	}
	
	public final int getHeight() {
		return height;
	}
	
	private final Position getPosition(int x, int y) {
		/* 
		 * NOTE: In case method will end up as public,
		 * ensure, that provided coordinates respect bounds. 
		 */
		final Actor occupant = grid[x][y];
		if (occupant == null) {
			return new EmptyPosition(this, x, y);
		}
		return new OccupiedPosition(this, x, y, occupant);
	}
	
	public final Stream<? extends Position> getPositionsStream() {
		return IntStream.range(0, width)
				.boxed()
				.flatMap(x -> IntStream.range(0, height).mapToObj(y -> getPosition(x, y)));
	}
	
	public final Set<? extends Position> getPositions() {
		return getPositionsStream()
				.collect(Collectors.toSet());
	}
	
	public final Stream<OccupiedPosition> getOccupiedPositionsStream() {
		return getPositionsStream()
				.filter(Position::isOccupied)
				.map(OccupiedPosition.class::cast);
	}
	
	public final Set<Position> getOccupiedPositions() {
		return getOccupiedPositionsStream()
				.collect(Collectors.toSet());
	}
	
	public final Stream<EmptyPosition> getEmptyPositionsStream() {
		return getPositionsStream()
				.filter(Position::isEmpty)
				.map(EmptyPosition.class::cast);
	}
	
	public final Set<Position> getEmptyPositions() {
		return getEmptyPositionsStream()
				.collect(Collectors.toSet());
	}
	
	public final Stream<Actor> getActorsStream() {
		return Arrays.stream(grid).flatMap(Arrays::stream).filter(a -> a != null);
	}
	
	public final Set<Actor> getActors() {
		return getActorsStream()
				.collect(Collectors.toSet());
	}
	
	public final Stream<ActorType> getActorTypesStream() {
		return getActorsStream()
				.map(Actor::getType)
				.distinct();
	}
	
	public final Set<ActorType> getActorTypes() {
		return getActorTypesStream()
				.collect(Collectors.toSet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(grid);
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(grid, other.grid))
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
}
