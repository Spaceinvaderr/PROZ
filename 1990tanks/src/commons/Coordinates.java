package commons;

/**
 * 
 * @author Margarita Chirillova
 * carrier for pair of coordinates
 *
 */

public final class Coordinates implements Comparable<Coordinates>
{
	private int x;
	private int y;

	public Coordinates()
	{
		x = 0;
		y = 0;
	}
	
	/** 
	 * przy przekroczeniu - error
	 */
	public Coordinates(final int x, int y)
	{
		this.x = x;
		this.y = y;

		if(x >= Config.sideLength || y >= Config.sideLength || x < 0 || y < 0) throw new IllegalStateException();}
	
	public int hashCode()
	{
		return x + 37*y;
	}
	
	public boolean equals (Object o)
	{
		if(o==this) return true;
		if(o==null || !(o instanceof Coordinates)) return false;
		return ((Coordinates) o).getX() == x && ((Coordinates) o).getY() == y;
	}
	
	public int compareTo(final Coordinates cp)
    {
		if(cp==this) return 0;
		if ((this.x == cp.x)&&(this.y == cp.y)) return 0;
		if (this.x > cp.x) return 1;
		else return -1;
    }
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
