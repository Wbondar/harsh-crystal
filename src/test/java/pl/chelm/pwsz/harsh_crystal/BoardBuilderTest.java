package pl.chelm.pwsz.harsh_crystal;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
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
		builder.setWidth(11).setHeight(11);
		builder.setQuantityOfActors(10);
		assertEquals(10, builder.getQuantityOfActors());
		builder.setQuantityOfActors(-13);
		assertEquals(0, builder.getQuantityOfActors());
	}

	@Test
	public void testBuild() throws Exception {
		final Random random = new Random();
		final int width = random.nextInt(1000);
		final int height = random.nextInt(1000);
		final int quantityOfActors = random.nextInt(width * height);
		int quantityOfActorTypes = random.nextInt(10) + 1;
		if (quantityOfActorTypes > quantityOfActors) {
			quantityOfActorTypes = quantityOfActors;
		}
		final Board board = new BoardBuilder()
			.setWidth(width)
			.setHeight(height)
			.setQuantityOfActors(quantityOfActors)
			.setQuantityOfActorTypes(quantityOfActorTypes)
			.build();
		assertEquals(width, board.getWidth());
		assertEquals(height, board.getHeight());
		Set<Integer> types = new HashSet<>();
		int count = 0;
		Map<Integer, Integer> groupedCount = new HashMap<Integer, Integer>();
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				if (board.isOccupied(x, y)) {
					count++;
					final int type = board.getCellTypeId(x, y);
					types.add(type);
					groupedCount.put(type,  groupedCount.get(type) != null ? groupedCount.get(type) + 1 : 1);
				}
			}
		}
		assertEquals(quantityOfActors, count);
		assertEquals(quantityOfActorTypes, types.size());
		for (Integer type : types) {
			assertEquals(Integer.valueOf(quantityOfActors / quantityOfActorTypes), groupedCount.get(type), 2);
		}
	}
}
