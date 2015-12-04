package pl.chelm.pwsz.harsh_crystal;

import static org.junit.Assert.assertEquals;

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
		assertEquals(400L, board.getActorsStream().count());
		assertEquals(400, board.getActors().size());
		assertEquals(2L, board.getActorTypesStream().count());
		assertEquals(2, board.getActorTypes().size());
	}
}
