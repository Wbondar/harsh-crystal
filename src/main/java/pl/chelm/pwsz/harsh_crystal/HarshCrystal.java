package pl.chelm.pwsz.harsh_crystal;

import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

public final class HarshCrystal extends Frame
{
    private static final int DEFAULT_PERIOD = 1000;

    private static final HarshCrystalFactory FACTORY = new HarshCrystalFactory();
    private static final long serialVersionUID = -2726845902979637456L;

    /**
     * Expects paths to configuration files in XML. Format of those files you
     * can check among test resources.
     * 
     * @param arguments
     *            paths to configuration files
     * @throws Exception
     *             if anything goes wrong
     */
    public static void main(String arguments[]) throws Exception
    {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        /*
         * Set up XML parser.
         */

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        ConfigurationFileContentHandler contentHandler = new ConfigurationFileContentHandler();
        xmlReader.setContentHandler(contentHandler);

        /*
         * Maps, to be populated by the parser.
         */
        Map<String, Object> boardProperties = new HashMap<String, Object>();
        Map<String, Object> simulationProperties = new HashMap<String, Object>();
        Map<String, Object> timerProperties = new HashMap<String, Object>();

        for (String argument : arguments)
        {
            /*
             * TODO Parse argument as filesystem path.
             */
            String pathToConfigurationFile = argument;

            /*
             * Clear existing maps to avoid creation of unnecessary objects.
             */
            boardProperties.clear();
            simulationProperties.clear();
            timerProperties.clear();

            /*
             * Those maps will be populated by values, read from the
             * configuration file by the content handler.
             */
            contentHandler.setBoardProperties(boardProperties);
            contentHandler.setSimulationProperties(simulationProperties);
            contentHandler.setTimerProperties(timerProperties);

            xmlReader.parse(pathToConfigurationFile);

            /*
             * Build board entity using properties, read from configuration
             * file.
             */
            BoardBuilder boardBuilder = new BoardBuilder();
            for (String key : boardProperties.keySet())
            {
                boardBuilder.setProperty(key, boardProperties.get(key));
            }

            /*
             * Build simulation entity using properties, read from configuration
             * file, and board entity instantiated earlier.
             */
            Class<? extends SimulationBuilder> builderClass = (Class<? extends SimulationBuilder>) simulationProperties
                    .get(ConfigurationFileContentHandler.ExpectedElements.builderClass
                            .name());
            SimulationBuilder simulationBuilder;
            try
            {
                simulationBuilder = builderClass.newInstance();
            } catch (Exception e)
            {
                e.printStackTrace();
                throw new AssertionError(e);
            }
            simulationProperties
                    .remove(ConfigurationFileContentHandler.ExpectedElements.builderClass
                            .name());
            for (String key : simulationProperties.keySet())
            {
                simulationBuilder.setProperty(key,
                        simulationProperties.get(key));
            }

            /*
             * Executing instantiated simulation.
             */
            Board board = boardBuilder.build();
            simulationBuilder.setBoard(board);
            Simulation simulation = simulationBuilder.build();
            HarshCrystal harshCrystal = FACTORY.newInstance(simulation);

            /*
             * Run simulation.
             */

            harshCrystal
                    .setTimerPeriod((Integer) timerProperties.get("period"));

            /*
             * Board is to be repainted for specified period of time. Simulation
             * keeps going while canvas awaits repainting.
             */
            Timer timer = new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    harshCrystal.boardCanvas.repaint();
                }
            }, harshCrystal.period, harshCrystal.period);

            /*
             * Start new simulation in separate thread.
             */
            threadFactory.newThread(simulation).start();
        }

    }
    private final BoardCanvas boardCanvas;

    private int period = DEFAULT_PERIOD;

    private final Simulation simulation;

    HarshCrystal(Simulation simulation, BoardCanvas boardCanvas)
    {
        this.boardCanvas = boardCanvas;
        this.simulation = simulation;
    }

    public void setTimerPeriod(int period)
    {
        if (period > 0)
        {
            this.period = period;
            return;
        }
        this.period = 1;
    }
}
