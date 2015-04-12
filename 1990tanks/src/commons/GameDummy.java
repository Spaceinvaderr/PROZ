package commons;

import java.util.HashMap;
import java.util.Map;

import model.*;



/**
 * 
 * @author Margarita Chirillova dummy of the game state carrier between view, model
 *         and controller
 * 
 */

public class GameDummy {
	private final Map<Coordinates, FieldType> board;
	private final boolean endGameFlag;
	private final boolean winning;

	private Coordinates playerCoordinates;
	private Map<EnemyId, Coordinates> enemyMap;
    private Direction playerDirection;
    private Direction enemyDirection;

    public GameDummy(final Board currentBoard, final Player player,
			final Map<EnemyId, Enemy> enemies, final boolean endGame,
			final boolean winner) {
		board = new HashMap<Coordinates, FieldType>();
		board.putAll(currentBoard.getFieldMap());
		endGameFlag = endGame;
		winning = winner;

		loadPlayerInfo(player);
		loadEnemyInfo(enemies);
	}

	private void loadEnemyInfo(final Map<EnemyId, Enemy> enemies)
	{
		enemyMap = new HashMap<EnemyId, Coordinates>();

		for (final EnemyId e : EnemyId.values())
		{
			final Enemy enemy = enemies.get(e);
			if (enemy != null)
			{
				if(enemy.isAlive()) enemyMap.put(e, enemy.getCoordinates());
			}
		}
	}

	private void loadPlayerInfo(final Player player)
	{
        playerCoordinates = player.getCoordinates();
        playerDirection = player.getDirection();
	}
	
	public Coordinates getEnemyCoordinates(final EnemyId e) {
		return enemyMap.get(e);
	}

	public FieldType getFieldType(final Coordinates field) {
		return board.get(field);
	}

	public Coordinates getPlayerCoordinates() {
		return playerCoordinates;
	}

	public boolean isGameEnd() {
		return endGameFlag;
	}

	public boolean isWinner() {
		return winning;
	}

    public Direction getAim() { return playerDirection; }
    
    public Direction getEnemyAim() { return enemyDirection; }
    }
