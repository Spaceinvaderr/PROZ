package commons;

/**
 * 
 * @author Margarita Chirillova
 * enum of directions
 */

public enum Direction
{

	SOUTH, NORTH, EAST, WEST;

	/**
	 * gets us next coordinates in the specified direction
	 * no matter if they're on the board or not
	 * @param oldCoordinates
	 * @return next Coordinates
	 */
	public final Coordinates getNextCoordinates(Coordinates oldCoordinates)
	{
		int x = oldCoordinates.getX();
		int y = oldCoordinates.getY();
		if (this == Direction.NORTH)
		{
			--y;
		} else if (this == Direction.SOUTH)
		{
			++y;
		} else if (this == Direction.EAST)
		{
			++x;
		} else
		{
			--x;
		}
		return new Coordinates(x, y);
	}
}
