package message;

import commons.Direction;

/**
 * Created by Margarita Chirillova
 */
public class AimMessage implements Message {
    private final Direction direction;

    public AimMessage(Direction d)
    {
        direction = d;
    }

    public Direction getDirection()
    {
        return direction;
    }
}
