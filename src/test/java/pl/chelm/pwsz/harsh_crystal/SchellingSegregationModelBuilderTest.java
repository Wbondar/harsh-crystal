package pl.chelm.pwsz.harsh_crystal;

import static org.junit.Assert.*;

import org.junit.Test;

public class SchellingSegregationModelBuilderTest {
	@Test
	public void testSetTolerance() throws Exception {
		SchellingSegregationModelBuilder builder = new SchellingSegregationModelBuilder();
		builder.setTolerance(0.5);
		assertEquals(0.5, builder.getTolerance(), Double.MIN_VALUE);
	}

}
