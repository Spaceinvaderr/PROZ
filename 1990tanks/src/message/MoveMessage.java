package message;

import commons.*;

/**
 * 
 * @author Margarita Chirillova
 * event: player move
 *
 */

public class MoveMessage implements Message
{

	private final Direction direction;

	public MoveMessage(Direction d)
	{
		direction = d;
	}

	public Direction getDirection()
	{
		return direction;
	}

}
