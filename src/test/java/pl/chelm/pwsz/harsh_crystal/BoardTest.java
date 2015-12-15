package pl.chelm.pwsz.harsh_crystal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testGetPositionsStream() throws Exception {
		final Board board = Board.newInstance(10, 10);
		assertEquals(10 * 10, board.getWidth() * board.getHeight());
	}

}
