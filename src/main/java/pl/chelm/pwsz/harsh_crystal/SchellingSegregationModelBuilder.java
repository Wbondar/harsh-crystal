package pl.chelm.pwsz.harsh_crystal;

final class SchellingSegregationModelBuilder extends SimulationBuilder
{
    private double tolerance = 0.99;

    @Override
    public Simulation build()
    {
        final Board board = getBoard();
        if (board == null)
        {
            throw new BoardIsMissingException();
        }
        return (Simulation) SchellingSegregationModel.newInstance(board,
                tolerance);
    }

    public final double getTolerance()
    {
        return tolerance;
    }

    public final void setTolerance(final double tolerance)
    {
        if (tolerance > 0 && tolerance < 1)
        {
            this.tolerance = tolerance;
        }
    }
}
