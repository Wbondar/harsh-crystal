package pl.chelm.pwsz.harsh_crystal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 
 * Factory that is to be used to create instances of the application. It's main
 * purpose is to separate logic.
 * 
 * @author Vladyslav Bondarenko
 *
 */
public class HarshCrystalFactory
{
    public HarshCrystal newInstance(final Simulation simulation)
    {
        return newInstance(simulation, simulation.getBoard());
    }

    /**
     * Creates instance of the application and shows it on screen.
     * 
     * @param simulation simulation to perform
     * @param board board on which simulation to perform on
     * @return instance of application
     */
    public HarshCrystal newInstance(final Simulation simulation, final Board board)
    {
        BoardCanvas boardCanvas = BoardCanvas.newInstance(board);
        HarshCrystal harshCrystal = new HarshCrystal(simulation, boardCanvas);
        harshCrystal.setTitle(simulation.getClass().getSimpleName());
        harshCrystal.add(boardCanvas);
        harshCrystal.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                harshCrystal.setVisible(false);
                harshCrystal.dispose();
            }
        });
        harshCrystal.pack();
        harshCrystal.setVisible(true);
        return harshCrystal;
    }
}
