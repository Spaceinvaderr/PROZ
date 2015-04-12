package message;

import commons.*;
import commons.EnemyId;

/**
 * 
 * @author Margarita Chirillova
 * event: enemy moves
 *
 */
public class EneMoveMessage implements Message
{

	private final EnemyId enemy;
	private final Direction direction;

	public EneMoveMessage(EnemyId e, Direction d)
	{
		enemy = e;
		direction = d;
	}

	public EnemyId getEnemy()
	{
		return enemy;
	}

	public Direction getDirection()
	{
		return direction;
	}

}
