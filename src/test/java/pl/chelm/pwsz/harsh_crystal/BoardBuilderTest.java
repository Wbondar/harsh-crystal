package pl.chelm.pwsz.harsh_crystal;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class BoardBuilderTest {
	@Test
	public void testSetQuantityOfActorTypes() throws Exception {
		BoardBuilder builder = new BoardBuilder();
		builder.setQuantityOfActorTypes(2);
		assertEquals(2, builder.getQuantityOfActorTypes());
		builder.setQuantityOfActorTypes(-13);
		/* 
		 * In order to avoid division by zero,
		 * quantity of types of actors has to be positive number.
		 */
		assertEquals(1, builder.getQuantityOfActorTypes());
	}

	@Test
	public void testSetQuantityOfActors() throws Exception {
		BoardBuilder builder = new BoardBuilder();
		builder.setQuantityOfActors(10);
		assertEquals(10, builder.getQuantityOfActors());
		builder.setQuantityOfActors(-13);
		assertEquals(0, builder.getQuantityOfActors());
	}

	@Test
	public void testBuild() throws Exception {
		final Board board = new BoardBuilder()
			.setWidth(1000)
			.setHeight(1000)
			.setQuantityOfActors(400)
			.setQuantityOfActorTypes(2)
			.build();
		assertEquals(1000, board.getWidth());
		assertEquals(1000, board.getHeight());
		Set<Integer> types = new HashSet<>();
		int count = 0;
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				if (board.isOccupied(x, y)) {
					count++;
					types.add(board.getCellTypeId(x, y));
				}
			}
		}
		assertEquals(400, count);
		assertEquals(2, types.size());
	}
}
