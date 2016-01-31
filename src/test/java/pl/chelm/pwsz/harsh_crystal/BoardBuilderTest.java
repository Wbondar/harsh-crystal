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
	public void testBuild() throws Exception {
		final Random random = new Random();
		final int width = random.nextInt(1000);
		final int height = random.nextInt(1000);
		final float populationRate = random.nextFloat();
		final int quantityOfActors = (int)((width * height) * populationRate);
		int quantityOfActorTypes = random.nextInt(10) + 1;
		if (quantityOfActorTypes > quantityOfActors) {
			quantityOfActorTypes = quantityOfActors;
		}
		
		final BoardBuilder builder = new BoardBuilder();
		builder.setWidth(width);
		builder.setHeight(height);
		builder.setPopulationRate(populationRate);
		builder.setQuantityOfActorTypes(quantityOfActorTypes);

		assertEquals(width, builder.getWidth());
		assertEquals(height, builder.getHeight());
		assertEquals(populationRate, builder.getPopulationRate(), 0.00001);
		assertEquals(quantityOfActorTypes, builder.getQuantityOfActorTypes());

		final Board board = builder.build();
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
		
		assertEquals(quantityOfActors, count, 1);
		assertEquals(quantityOfActorTypes, types.size());
		for (Integer type : types) {
			assertEquals(Integer.valueOf(quantityOfActors / quantityOfActorTypes), groupedCount.get(type), 2);
		}
	}
	
	@Test 
	public void testSetHeightWithPropertyWriter() throws Exception {
		Random random = new Random();
		Builder<Board> builder = new BoardBuilder();
		final int height = random.nextInt(100) + 1;
		builder.setProperty("height", height);
		assertEquals(height, builder.<Integer>getProperty("height").intValue());
	}
	
	@Test
	public void testSetPopulationRate() throws Exception {
		BoardBuilder builder = new BoardBuilder();
		
		builder.setWidth(11);
		builder.setHeight(11);
		
		builder.setPopulationRate((float)0.5);
		assertEquals(0.5, builder.getPopulationRate(), 0.00001);
		
		builder.setPopulationRate(1);
		assertEquals(1, builder.getPopulationRate(), 0.00001);
		
		builder.setPopulationRate((float)-1.0);
		assertEquals(0, builder.getPopulationRate(), 0.00001);
	}
	
	@Test
	public void testSetPopulationRateWithPropertyWriter() throws Exception {
		Builder<Board> builder = new BoardBuilder();
		
		builder.setProperty("width", 11).setProperty("height", 11);
		
		builder.setProperty("populationRate", (float)0.5);
		assertEquals((float)0.5, builder.<Float>getProperty("populationRate").floatValue(), (float)0.00001);
	}

	@Test
	public void testSetQuantityOfActorTypes() throws Exception {
		BoardBuilder builder = new BoardBuilder();
		builder.setWidth(10);
		builder.setHeight(10);
		builder.setPopulationRate((float)1.0);
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
	public void testSetWidthWithPropertyWriter() throws Exception {
		Random random = new Random();
		Builder<Board> builder = new BoardBuilder();
		final int width = random.nextInt(100) + 1;
		builder.setProperty("width", width);
		assertEquals(width, builder.<Integer>getProperty("width").intValue());
	}
}
