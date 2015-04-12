package model;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import commons.Coordinates;
import commons.Direction;
import commons.EnemyId;
import commons.GameDummy;

/**
 * 
 * @author Margarita Chirillova model!
 * 
 */
public class Model {
	Logger logger = Logger.getLogger("Model");

	private Board board;
	private Player player;
	private final Map<EnemyId, Enemy> enemies;

	public Model() {
		board = new Board();
		player = new Player(board);
		enemies = new HashMap<EnemyId, Enemy>();
	}
	
	/**
	 * resets the game
	 * @return game dummy
	 */
	public GameDummy newGame() 
	{
		board = new Board();
		player = new Player(board);
		return new GameDummy(board, player, enemies, false, false);
	}

	/**
	 * advances the game state
	 * @return new Game Dummy
	 */
	public GameDummy advance() {
        // receiveMessages();
        Coordinates playerCoordinates = player.getCoordinates();

        board.advance();

        /** checking collision between player and bullet */
        int i;
        for (i = 0; i < board.getBullets().size(); i++) {
            Bullet bullet = board.getBullets().get(i);
            if (playerCoordinates == bullet.getCoordinates()) {
                player.kill();
                return new GameDummy(board, player, enemies, true, false);
            }
        }

        return new GameDummy(board, player, enemies, false, false);
    }


	/**
	 * moves the player
	 * @param direction
	 * @return new game dummy
	 */
	public GameDummy movePlayer(final Direction direction)
	{
		player.move(direction);
		return new GameDummy(board, player, enemies, false, false);

	}

    public GameDummy aim(final Direction direction)
    {
        player.aim(direction);
        return new GameDummy(board, player, enemies, false, false);
    }



	/**
	 * puts bomb and returns new game dummy
	 * @return GameDummy
	 */
	public GameDummy shoot()
	{
        board.shootIfPossible(player);
		return new GameDummy(board, player, enemies, false, false);
	}
}
