package model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import commons.Config;
import commons.Coordinates;
import commons.Direction;
import commons.FieldType;


/**
 * board part of model
 * @author Margarita Chirillova
 */

public class Board {
	/** map of field types */
	private Map<Coordinates, FieldType> fieldMap;
	/** list of bombs on map */
	private List<Bullet> bullets;
	/** list of used bullets - in this tic */
	//private List<Bullet> explodedBombs;
	/** list of all fields hurting the actives */
	private List<Coordinates> hurtingFields;

	public Board() {
		this.fieldMap = new HashMap<Coordinates, FieldType>();
		this.bullets = new ArrayList<Bullet>();
		generateBoard();
	}

	/**
	 * generates new board model
	 */
	private void generateBoard()
	{
		Coordinates coordinates;
		int i, j, max=Config.sideLength;
		
		/** the inner corridors */
		for (i = 1; i < max - 1; i++) {
			for (j = 1; j < max - 1; j++)
			{
				fieldMap.put(new Coordinates(i,j), FieldType.CORRIDOR);
			}
		}
		
		/** the outer walls */
		for (i = 0; i < max; i++)
		{
			coordinates = new Coordinates(0, i);
			fieldMap.put(coordinates, FieldType.WALL);

			coordinates = new Coordinates(i, 0);
			fieldMap.put(coordinates, FieldType.WALL);

			coordinates = new Coordinates(max - 1, i);
			fieldMap.put(coordinates, FieldType.WALL);

			coordinates = new Coordinates(i, max - 1);
			fieldMap.put(coordinates, FieldType.WALL);
		}
		
		/** the inside pillars */
		for (i = 2; i < max - 2; i += 2) {
			for (j = 2; j < max - 2; j += 2)
			{
				coordinates = new Coordinates(i,j);
				fieldMap.put(coordinates, FieldType.WALL);
			}
		}

		/** randomly generated breakables */
		double fieldsNumber = Math.pow((double) Config.sideLength - 1, 2.0);
		
		int a, b;
		final int howManyToCover = (int) Math.round(fieldsNumber/4);
		for (i = 0; i < howManyToCover; i++)
		{
			do
			{
				a = (int) Math.round(Math.random() * (max - 1));
				b = (int) Math.round(Math.random() * (max - 1));
				coordinates = new Coordinates(a,b);
			} while (fieldMap.get(coordinates)!=FieldType.CORRIDOR);

			fieldMap.put(coordinates, FieldType.BREAKABLE);
		}

		coordinates = new Coordinates(1,1);
		fieldMap.put(coordinates, FieldType.CORRIDOR);
	}

    /**
     * advances the board (explodes the bullets)
     */
    void advance() {
        List<Bullet> bullets = new ArrayList<Bullet>(this.bullets);
        for (Bullet bullet : bullets) {
            bullet.advance();
            if (isBulletExplode(bullet))//TO DO: naprawić
            {
                this.bullets.remove(bullet);
            }
        }
    }
	
	/**
	 * stuns the dire
	 * @param bullet
	 */
	private boolean isBulletExplode(Bullet bullet) {

        Coordinates i = bullet.getCoordinates();

        if (fieldMap.get(i) == FieldType.WALL) {
            return true;
        }

        if (fieldMap.get(i) == FieldType.BREAKABLE) {
            fieldMap.put(i, FieldType.CORRIDOR);
            return true;
        }
        return false;
    }

    /**
	 * copies the dummy and returns clone
	 * 
	 * @return Map of field types
	 */
	public Map<Coordinates, FieldType> getFieldMap()
	{
		final Map<Coordinates, FieldType> dummy = new HashMap<Coordinates, FieldType>();
		dummy.putAll(fieldMap);
        for (Bullet bullet: bullets){
            dummy.put(bullet.getCoordinates(), FieldType.BULLET);
        }
		return dummy;
	}

	
	/**
	 * gets us all possible move destination coordinates
	 * @param coordinates
	 * @return list of coordinates
	 */
	
	List<Coordinates> getPossibleMoves(Coordinates coordinates)
	{
		List<Coordinates> coordinatesList = new ArrayList<Coordinates>();
		Coordinates c;
		for (final Direction d : Direction.values())
		{
			c=getNextCoordinates(coordinates, d);
			if (fieldMap.get(c)==FieldType.CORRIDOR)
			{
				coordinatesList.add(c);
			}
		}
		return coordinatesList;
	}
	
	/**
	 * gets us next coordinates in a provided direction
	 * @param coordinates
	 * @param direction
	 * @return coordinates
	 */

	public static Coordinates getNextCoordinates(Coordinates coordinates, Direction direction)
	{
		int x=coordinates.getX();
		int y=coordinates.getY();
		if (direction == Direction.NORTH) y--;
		else if (direction == Direction.SOUTH) y++;
		else if (direction == Direction.EAST) x++;
		else x--;
		return new Coordinates(x,y);
	}


    public List<Bullet> getBullets() {
        return bullets;
    }

    public void shootIfPossible(Player player) {
        if (player.canShoot()){
            player.shoot();
            Bullet bullet = new Bullet(player.getCoordinates(), player.getDirection());
            bullets.add(bullet);
        }
    }
}
