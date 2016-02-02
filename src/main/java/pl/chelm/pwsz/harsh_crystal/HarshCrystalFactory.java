package pl.chelm.pwsz.harsh_crystal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HarshCrystalFactory {
	public HarshCrystal newInstance(final Simulation simulation, final Board board) {
		BoardCanvas boardCanvas = new BoardCanvas(board);
		HarshCrystal harshCrystal = new HarshCrystal(simulation, boardCanvas);
		harshCrystal.setTitle(simulation.getClass().getSimpleName());
		harshCrystal.add(boardCanvas);
		harshCrystal.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  harshCrystal.setVisible(false);
		    	  harshCrystal.dispose();
		      }
		    });
		harshCrystal.pack();
		harshCrystal.setVisible(true);
		return harshCrystal;
	}
	
	public HarshCrystal newInstance(final Simulation simulation) {
		return newInstance(simulation, simulation.getBoard());
	}
}
