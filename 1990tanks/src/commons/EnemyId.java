package commons;

import model.Board;
/**
 * 
 * @author Margarita Chirillova
 * enum of enemy ids
 *
 */


public class EnemyId
{

    protected Board board;
    /**
     * where is he
     */
    private Coordinates coordinates;
    /**
     * is he alive
     */
    private boolean alive;

    private Direction direction;

    public Coordinates getCoordinates() {
        return this.coordinates;
    }
}