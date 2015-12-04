package pl.chelm.pwsz.harsh_crystal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SimulationBuilderTest {

	@Test
	public void testSetProperty() {
		SimulationBuilder builder = new SchellingSegregationModelBuilder();
		builder.setProperty("tolerance", 0.5);
		assertEquals(0.5, builder.getProperty("tolerance"), Double.MIN_VALUE);
	}

}
