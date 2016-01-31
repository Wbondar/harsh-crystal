package pl.chelm.pwsz.harsh_crystal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * Wrapper class which is used to graphically represent changes of a given
 * board. Simulation that is performed on the board is of no concern for this
 * class.
 * 
 * @author Vladyslav Bondarenko
 *
 */
class BoardCanvas extends Canvas
{
    private static final Color COLOUR_CELL_UNREACHABLE = Color.BLACK;

    private static final Color COLOUR_EMPTY_CELL = Color.WHITE;
    private static final int DEFAULT_CELL_HEIGHT = 10;

    private static final int DEFAULT_CELL_WIDTH = 10;
    private static final float LUMINANCE = 0.9f;

    private static final float SATURATION = 0.9f;
    private static final long serialVersionUID = 7786397736961190376L;

    /**
     * Instantiates BoardCanvas class, while ensuring that provided board
     * actually exists.
     * 
     * @param board instance of board to render on screen
     * @return non-null; instance of BoardCanvas class with provided board if
     *         one was not empty, or with default board.
     */
    public static BoardCanvas newInstance(final Board board)
    {
        if (board != null)
        {
            BoardCanvas boardCanvas = new BoardCanvas(board);
            boardCanvas.setSize(board.getWidth() * DEFAULT_CELL_WIDTH,
                    board.getHeight() * DEFAULT_CELL_HEIGHT);
            return boardCanvas;
        }
        return new BoardCanvas();
    }

    private final Board board;

    private BoardCanvas()
    {
        this(new Board());
    }

    private BoardCanvas(final Board board)
    {
        this.board = board;
    }

    /**
     * Get colour that is associated with given cell type. The colour is
     * consistent for empty and unreachable cells.
     * 
     * @param cellTypeId
     *            see description of Board class for details
     * @return non-null
     */
    private Color getColor(int cellTypeId)
    {
        if (cellTypeId > 0)
        {
            final float hue = 0.3f * cellTypeId;
            return Color.getHSBColor(hue, SATURATION, LUMINANCE);
        }

        if (cellTypeId == 0)
        {
            return COLOUR_EMPTY_CELL;
        }

        return COLOUR_CELL_UNREACHABLE;
    }

    @Override
    public void paint(Graphics graphics)
    {
        final int width = board.getWidth();
        final int height = board.getHeight();
        final int canvasWidth = this.getWidth() >= width ? this.getWidth()
                : width;
        final int canvasHeight = this.getHeight() >= height ? this.getHeight()
                : height;
        final int cellWidth = canvasWidth / width > 0 ? canvasWidth / width : 1;
        final int cellHeight = canvasHeight / height > 0 ? canvasHeight / height
                : 1;
        for (int i = 0; i < width; i++)
        {
            int x = i * cellWidth;
            // graphics.drawLine(x, 0, x, canvasHeight);
            for (int j = 0; j < height; j++)
            {
                int y = j * cellHeight;
                // graphics.drawLine(0, y, canvasWidth, y);
                graphics.setColor(this.getColor(board.getCellTypeId(i, j)));
                graphics.fillRect(x, y, cellWidth, cellHeight);
            }
        }
    }
}
