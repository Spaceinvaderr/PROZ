package model;

import java.awt.Image;
import java.awt.Rectangle;
import commons.Direction;
import commons.Coordinates;

public class Bullet {
    /** where is he */
    private Coordinates coordinates;
    private final Direction direction;

    public Bullet(Coordinates coordinates,
                  Direction direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }


    public Direction getDirection() {
        return direction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Coordinates advance() {
        coordinates = Board.getNextCoordinates(coordinates, direction);
        return coordinates;
    }
}
