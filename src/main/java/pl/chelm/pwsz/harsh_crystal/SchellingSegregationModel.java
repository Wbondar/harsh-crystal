package pl.chelm.pwsz.harsh_crystal;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Implementation of very specific simulation algorithm.
 * 
 * @author Vladyslav Bondarenko
 *
 */
public final class SchellingSegregationModel extends Simulation
{
    private final static Random RANDOM = new Random();
    public static SchellingSegregationModel newInstance(final Board board,
            final double tolerance)
    {
        if (board == null)
        {
            return null;
        }
        return new SchellingSegregationModel(board, tolerance);
    }
    private boolean finished = false;
    private boolean relocationOccurred;

    /**
     * `tolerance` must be greater than zero.
     */
    private final double tolerance;

    private SchellingSegregationModel(final Board board, final double tolerance)
    {
        super(board);
        this.tolerance = tolerance;
    }

    @Override
    public boolean isFinished()
    {
        return finished;
    }

    /**
     * 
     * @param x0
     *            position x of actor to check satisfaction
     * @param y0
     *            position y of actor to check satisfaction
     * @return `true` if percent of non-kin of occupant is greater than occupant
     *         can tolerate and `false` otherwise; `false` if cell of
     *         coordinates (`x0`, `y0`) is inoccupied.
     */
    private boolean isUnsatisfied(int x0, int y0)
    {
        final Board board = getBoard();
        if (board.isEmpty(x0, y0) || board.isUnreachable(x0, y0))
        {
            return false;
        }
        final int targetCellTypeId = board.getCellTypeId(x0, y0);
        /*
         * `double` type is used to avoid type casting.
         */
        double quantityOfEnemies = 0d, quantityOfCells = 0d;
        for (int x = x0 - 1; x < x0 + 1; x++)
        {
            for (int y = y0 - 1; y < y0 + 1; y++)
            {
                /*
                 * Occupied cell means that the cell is reachable (an actor can
                 * be placed there) and non-empty.
                 */
                if (board.isOccupied(x, y))
                {
                    if (board.getCellTypeId(x, y) != targetCellTypeId)
                    {
                        quantityOfEnemies++;
                    }
                    quantityOfCells++;
                }
            }
        }
        final double hostility = quantityOfEnemies / quantityOfCells;
        System.out.println(String.format("Hostility %f.", hostility));
        return hostility > tolerance;
    }

    private void relocateIfNecessary(int x, int y)
    {
        System.out.println(String.format("Check (%d, %d).", x, y));
        if (isUnsatisfied(x, y))
        {
            System.out.println(
                    String.format("Dweller of (%d, %d) is unsatisfied.", x, y));
            relocateToRandomEmptyCell(x, y);
            relocationOccurred = true;
        } else
        {
            /*
             * If relocation already occured, preserve the information; don't
             * override it.
             */
            relocationOccurred = relocationOccurred || false;
        }
    }

    /**
     * The method assumes, that at least one empty cell exists on the board.
     * 
     * @param x0
     *            position x of actor to relocate
     * @param y0
     *            position y of actor to relocate
     */
    private final void relocateToRandomEmptyCell(final int x0, final int y0)
    {
        final Board board = getBoard();
        while (true)
        {
            int x = RANDOM.nextInt(board.getWidth());
            int y = RANDOM.nextInt(board.getHeight());
            if (board.isEmpty(x, y))
            {
                System.out.println(
                        String.format("Move %d from (%d, %d) to (%d, %d).",
                                board.getCellTypeId(x0, y0), x0, y0, x, y));
                final int cellTypeId = board.getCellTypeId(x0, y0);
                board.swap(x0, y0, x, y);
                assert board.isEmpty(x0, y0) == true;
                assert board.getCellTypeId(x, y) == cellTypeId;
                return;
            }
        }
    }

    @Override
    public void run()
    {
        while (isOngoing())
        {
            final Board board = getBoard();
            final int width = board.getWidth();
            final int height = board.getHeight();
            relocationOccurred = false;
            /*
             * Not obvious method of iterating over a board is used here, for
             * native functions interfere with the flow of the simulation. TODO
             * Prevent collisions and missed spots.
             */
            IntStream.rangeClosed(1, width * height).forEach(i -> {
                relocateIfNecessary(RANDOM.nextInt(width),
                        RANDOM.nextInt(height));
            });

            /*
             * End execution of the algorithm if no relocations occurred.
             */
            if (!relocationOccurred)
            {
                finished = true;
            }
        }
    }

}
