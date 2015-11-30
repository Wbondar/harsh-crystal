package pl.chelm.pwsz.harsh_crystal;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class HarshCrystal {
	public static void main (String arguments[]) throws Exception {
		Simulation simulation = SimulationBuilders.getInstance(SchellingSegregationModel.class)
			.setBoardWidth(10)
			.setBoardHeight(10)
			.setParameter("tolerance", 1.0 / 3.0)
			.setParameter("quantityOfActors", 6)
			.setParameter("quantityOfRaces", 2)
			.build();
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(simulation);
	}
}
