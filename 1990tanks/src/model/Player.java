package model;

import commons.Config;
import commons.Coordinates;
import commons.Direction;

/**
 * 
 * @author Margarta Chirillova player part of model
 */
public class Player {
    protected Board board;
	/** where is he */
	private Coordinates coordinates;
	/** is he alive */
	private boolean alive;

    private Direction direction;

    private int currentBulletsLimit;

	public Player(Board board) {
        this.board = board;
		this.alive = true;
		this.coordinates = new Coordinates(1, 1);
        this.direction = Direction.EAST;
        currentBulletsLimit = Config.playerBulletsLimit;
	}

	public boolean isAlive() {
		return this.alive;
	}

	void kill() {
		this.alive = false;
}

	void move(Direction direction)
	{
        Coordinates newCoordinates = Board.getNextCoordinates(this.coordinates, direction);
        if (board.getPossibleMoves(coordinates).contains(newCoordinates)) {
            this.coordinates = newCoordinates;
        }
	}

    void aim(Direction direction)
    {
      this.direction = direction;
    }
	
	public Coordinates getCoordinates()
	{
		return this.coordinates;
	}

    public boolean canShoot (){
        return currentBulletsLimit > 0;

    }
    public void shoot() {
        currentBulletsLimit--;
    }

    public Direction getDirection() {
        return direction;
    }
}
