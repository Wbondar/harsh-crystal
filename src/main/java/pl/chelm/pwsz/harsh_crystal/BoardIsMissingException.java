package pl.chelm.pwsz.harsh_crystal;

public final class BoardIsMissingException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = -8637445888337772494L;

    BoardIsMissingException()
    {
        super("No board instance was provided to a simulation builder.");
    }
}
