package pl.chelm.pwsz.harsh_crystal;

/**
 * Board class represents a filed (or "magic circle", as it sometimes referred),
 * on which simulation takes place.
 * 
 * Board class does not hold any logic responsible for simulation. It is up to
 * Simulation class derivatives to handle behaviour of actors on a board.
 * 
 * A board consists of cells, that are intersections of rows and columns of a
 * board. Each cell has a type, which is represented by integer number, that is
 * cell type identificator (id).
 * 
 * At any given type, a cell can be one and only one of the following: -
 * reachable and empty; - reachable and occupied; - unreachable and nor empty
 * nor occupied;
 * 
 * Empty cell is a reachable cell, on which is not occupied by any actor at the
 * moment.
 * 
 * Occupied cell is a reachable cell, on which some actor is placed at the
 * moment.
 * 
 * Unreachable cell is a cell which can not be occupied by an actor. Usually
 * this means that it is out of the bounds of the board.
 * 
 * Reachable cells can change their type to any, but unreachable, while
 * unreachable ones can not change their types at all.
 * 
 * An actor is a unit that simulation operates.
 * 
 * Ids of reachable cells are always equal or greater than zero. Ids of
 * unreachable cells are always less than zero. Ids of occupied cells are always
 * greater than zero. Ids of unoccupied (empty) cells are always equal to zero.
 * 
 * @author Vladyslav Bondarenko
 */
public class Board
{
    public static Board newInstance(int width, int height)
    {
        if (width > 0 && height > 0)
        {
            return new Board(width, height);
        }
        return null;
    }

    /**
     * Two-dimensional matrix to store types of cells. See description of Board
     * class for more details.
     */
    private final int[][] grid;

    /**
     * Quantity of cells on the board vertically (quantity of rows on the
     * board). Must be greater than zero.
     */
    private final int height;

    /**
     * Quantity of cells on the board horizontally (quantity of columns on the
     * board). Must be greater than zero.
     */
    private final int width;

    protected Board()
    {
        this(1, 1);
    }

    private Board(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
    }

    /**
     * If cell of coordinates `x` and `y` is reachable, set it's type to empty.
     * 
     * @param x
     *            in [0; `width` - 1]
     * @param y
     *            in [0; `height` - 1]
     */
    public void emptyCell(int x, int y)
    {
        if (isReachable(x, y))
        {
            grid[x][y] = 0;
        }
    }

    /**
     * Returns id of cell type, where: - positive if occupied (some actor is
     * placed on it); - zero if unoccupied (no actor is placed on it at the
     * moment), but reachable (respects the bounds of the board); - negative if
     * unreachable (out of the bounds of the board);
     * 
     * @param x
     *            in [0; `width` - 1]
     * @param y
     *            in [0; `height` - 1]
     * @return zero if empty; negative if unreachable; positive if occupied.
     */
    public int getCellTypeId(int x, int y)
    {
        if (isReachable(x, y))
        {
            return grid[x][y];
        }
        return -1;
    }

    /**
     * Quantity of rows on the board.
     * 
     * @return always positive
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Quantity of columns on the board.
     * 
     * @return always positive
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Checks if some actor is placed on a cell of provided coordinates at the
     * moment.
     * 
     * @param x
     *            in [0; `width` - 1]
     * @param y
     *            in [0; `height` - 1]
     * @return `true` if empty; `false` if occupied or unreachable;
     */
    public boolean isEmpty(int x, int y)
    {
        return getCellTypeId(x, y) == 0;
    }

    /**
     * Checks if some actor is placed on a cell of provided coordinates at the
     * moment.
     * 
     * @param x
     *            in [0; `width` - 1]
     * @param y
     *            in [0; `height` - 1]
     * @return `true` if some actor is currently occupies the cell; `false` if
     *         empty or unreachable;
     */
    public boolean isOccupied(int x, int y)
    {
        return getCellTypeId(x, y) > 0;
    }

    /**
     * Checks if it is possible to place an actor on a cell of given
     * coordinates.
     * 
     * @param x
     *            in [0; `width` - 1]
     * @param y
     *            in [0; `height` - 1]
     * @return `true` if reachable; `false` otherwise;
     */
    public boolean isReachable(int x, int y)
    {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isUnreachable(int x, int y)
    {
        return !isReachable(x, y);
    }

    /**
     * Sets type of cell of specified coordinates to provided. Can empty the
     * cell or place an actor on it, but cannot make the cell unreachable;
     * 
     * @param x
     *            in [0; `width` - 1]
     * @param y
     *            in [0; `height` - 1]
     * @param typeId
     *            must be greater or equal to zero;
     */
    public void setCellTypeId(int x, int y, int typeId)
    {
        if (isReachable(x, y) && typeId >= 0)
        {
            grid[x][y] = typeId;
        }
    }

    /**
     * Swaps types of cells of provided coordinates. Operates normally in case
     * one or both of the cells are empty. If either of the cells is
     * unreachable, does nothing.
     * 
     * @param x0
     *            in [0; `width` - 1]
     * @param y0
     *            in [0; `height` - 1]
     * @param x1
     *            in [0; `width` - 1]
     * @param y1
     *            in [0; `height` - 1]
     */
    public void swap(int x0, int y0, int x1, int y1)
    {
        if (isReachable(x0, y0) && isReachable(x1, y1))
        {
            int swap = getCellTypeId(x0, y0);
            setCellTypeId(x0, y0, getCellTypeId(x1, y1));
            setCellTypeId(x1, y1, swap);
        }
    }
}
