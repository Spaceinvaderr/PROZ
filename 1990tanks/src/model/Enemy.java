package model;

import java.util.List;

import commons.Config;
import commons.Coordinates;
import commons.Direction;


/**
 * 
 * @author Margarita Chirillova enemy - moves around and kills the player
 */
public class Enemy {
	/** his current coordinates */
	private Coordinates coordinates;
	/** is he alive */
	private boolean alive;
	/** previous coordinates - used to randomize the movement */
	private Coordinates previousCoordinates;
	/** reference to the board */
	private Board motherBoard;
    private int currentBulletsLimit;

	public Enemy(final Coordinates coordinates, final Board board)
	{
		//this.coordinates = coordinates;
		this.previousCoordinates = Board.getNextCoordinates(coordinates, Direction.NORTH); /** doesn't matter which */
		this.alive = true;
        this.coordinates = new Coordinates(500, 500);
		this.motherBoard = board;
        currentBulletsLimit = Config.playerBulletsLimit;
	}

	/**
	 * moves to one (random) of possible new coordinates
	 */
	void move()
	{
		List<Coordinates> possibleMoves = motherBoard.getPossibleMoves(this.coordinates);
		if (possibleMoves.size()==0) return;

		Coordinates coordinates;
		if(possibleMoves.size()>1)
		{
			possibleMoves.remove(this.previousCoordinates);
			
			double rand = Math.random();
			if (rand<0.33) coordinates = possibleMoves.get(0);
			else if (rand>0.66) coordinates = possibleMoves.get(1);
			else coordinates = possibleMoves.get(2);

		}
		else coordinates=this.previousCoordinates;	/** gets back if no other options */
		
		this.previousCoordinates=this.coordinates;
		this.coordinates=coordinates;
	}

	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public boolean isAlive() {
		return this.alive;
	}
	
	void kill() {
		this.alive = false;
	}
}
