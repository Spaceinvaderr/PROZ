package commons;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;
/**
 * 
 * @author Margarita Chirillova
 * 
 * Basic preferences
 */


public class Config
{
	static Logger logger = Logger.getLogger("Common");

	/** current side length - must be odd, at least 5, reasonable to 25 */
	public static int sideLength = 11;

	/** The number of bombs which player can put at once - at least 1 */
	public static int playerBulletsLimit = 100;

	/** How far bomb explosion injures players */
	//public static int flameLength = 5;

	/** Values connected with timer events */
	/** How many timer tics after putting a bomb it explodes */
	public static int bulletTicks = 1;
	/** How many timer tics after bomb explosion flames are visible */
	//public static int flameTicks = 3;

	/** controller info */
	/** How often timer generates a tic */
	public static int timerDelay = 200;

	/** view info */
	public static int PLAYER1_NORTH = KeyEvent.VK_UP;
	public static int PLAYER1_SOUTH = KeyEvent.VK_DOWN;
	public static int PLAYER1_WEST = KeyEvent.VK_LEFT;
	public static int PLAYER1_EAST = KeyEvent.VK_RIGHT;
	public static int PLAYER1_BULLET = KeyEvent.VK_SPACE;

    public static int AIM_NORTH = KeyEvent.VK_W;
    public static int AIM_SOUTH = KeyEvent.VK_S;
    public static int AIM_WEST = KeyEvent.VK_A;
    public static int AIM_EAST = KeyEvent.VK_D;

	public static String wallImagePath = "pic/wall.jpg";
	public static String corridorImagePath = "pic/field.jpg";
	public static String bulletImagePath = "pic/bomb.jpg";
	//public static String flameImagePath = "pic/flame.jpg";
	public static String breakableImagePath = "pic/breakable.jpg";

	public static String playerImagePath1 = "pic/player_w.bmp";
    public static String playerImagePath2 = "pic/player_s.bmp";
    public static String playerImagePath3 = "pic/player_d.bmp";
    public static String playerImagePath4 = "pic/player_a.bmp";

	public static String enemyImagePath1 = "pic/enemy_w.bmp";
    public static String enemyImagePath2 = "pic/enemy_s.bmp";
    public static String enemyImagePath3 = "pic/enemy_d.bmp";
    public static String enemyImagePath4 = "pic/enemy_a.bmp";

}
