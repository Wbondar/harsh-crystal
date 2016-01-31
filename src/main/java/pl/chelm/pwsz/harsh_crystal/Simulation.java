package pl.chelm.pwsz.harsh_crystal;

/**
 * Simulation class is to be extended for each type of simulation that is
 * required by domain. Simulation is always has a single board on which to
 * operate.
 * 
 * It is possible (in theory) for different simulations to share a board
 * simultaneously, but this will surely brake the flow of the program. Thus,
 * such a situation is to be avoided.
 * 
 * @author Vladyslav Bondarenko
 */
public abstract class Simulation implements Runnable
{
    /* TODO Ensure immutability and prevent injections of `board`. */
    private final Board board;

    protected Simulation(final Board board)
    {
        this.board = board;
    }

    /**
     * Returns board on which given simulation instance currently operates. Is
     * final to ensure, that another instance of Board class can not be
     * injected.
     * 
     * @return instance of board on which this simulation operates
     */
    public final Board getBoard()
    {
        return board;
    }

    /**
     * Indicates if simulation is finished all of it's possible actions. This
     * method is to be overriden by descendants.
     * 
     * @return `true` if no actions can be made by the simulation; `false` if
     *         some actions are to be made.
     */
    public abstract boolean isFinished();

    /**
     * Indicates if simulation is still on-going. Is to be used in `run()`
     * method of simulation. Final in order to ensure compatibility with
     * `isFinished()` method.
     * 
     * @return `true` if some actions are yet to be made by the simulation;
     *         `false` otherwise;
     */
    public final boolean isOngoing()
    {
        return !isFinished();
    }

    /**
     * This method is where actual procedural code of the simulation is to be
     * stored. If make implementation thread-safe or not is up to programmer
     * that implements logic of the simulation. Yet, it is advised to ensure
     * compatibility with multi-threaded (parallel) programming. Is to be
     * overriden by the class descendants.
     */
    public abstract void run();
}
