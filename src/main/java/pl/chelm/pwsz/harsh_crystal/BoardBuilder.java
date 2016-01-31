package pl.chelm.pwsz.harsh_crystal;

import java.util.Random;
import java.util.stream.IntStream;

public class BoardBuilder extends Builder<Board>
{
    private final static Random RANDOM = new Random();

    private int height = 1;
    private float populationRate = 0;
    private int quantityOfActorTypes = 1;
    private int width = 1;

    @Override
    public Board build()
    {
        final Board board = Board.newInstance(width, height);
        IntStream.range(0, Math.round(width * height * populationRate)).forEach(
                i -> setCellTypeIdSafely(board, i % quantityOfActorTypes + 1));
        return board;
    }

    public int getHeight()
    {
        return height;
    }

    public float getPopulationRate()
    {
        return populationRate;
    }

    public int getQuantityOfActorTypes()
    {
        return quantityOfActorTypes;
    }

    public int getWidth()
    {
        return width;
    }

    /**
     * The method expects for at least on empty cell to exist on the `board`.
     * 
     * @param board
     * @param typeId
     */
    private void setCellTypeIdSafely(Board board, int typeId)
    {
        int x = RANDOM.nextInt(width);
        int y = RANDOM.nextInt(height);
        if (board.isEmpty(x, y))
        {
            board.setCellTypeId(x, y, typeId);
        } else
        {
            setCellTypeIdSafely(board, typeId);
        }

    }

    public void setHeight(final int newHeight)
    {
        if (newHeight < 1)
        {
            height = 1;
        } else
        {
            height = newHeight;
        }
    }

    /**
     * Sets quantity of actors on the board according to the size of the board.
     * 
     * @param newPopulationRate
     *            `rate` in (0; 1)
     */
    public void setPopulationRate(float newPopulationRate)
    {
        if (newPopulationRate >= 1)
        {
            populationRate = 1;
            return;
        }
        if (newPopulationRate <= 0)
        {
            populationRate = 0;
            return;
        }
        populationRate = newPopulationRate;
    }

    public void setQuantityOfActorTypes(final int newQuantityOfActorTypes)
    {
        if (newQuantityOfActorTypes < 1)
        {
            this.quantityOfActorTypes = 1;
            return;
        }
        this.quantityOfActorTypes = newQuantityOfActorTypes;
    }

    public void setWidth(final int newWidth)
    {
        if (newWidth < 1)
        {
            width = 1;
        } else
        {
            width = newWidth;
        }
    }
}
