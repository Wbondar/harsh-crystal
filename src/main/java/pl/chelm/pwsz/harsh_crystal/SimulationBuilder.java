package pl.chelm.pwsz.harsh_crystal;

public abstract class SimulationBuilder extends Builder<Simulation>
{
    private Board board = null;

    SimulationBuilder()
    {
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }
}
