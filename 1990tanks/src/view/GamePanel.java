package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import commons.*;
import model.Bullet;


/**
 * 
 * @author Margarita Chirillova board panel of view
 */
public class GamePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
    /** current game dummy */
	private GameDummy currentBoard;

	/** references to the images */
	private Map<FieldType, Image> fieldImageMap;
	private Map<Direction, Image> playerImageMap;
	private Map<EnemyId, Image> enemyImageMap;

	/**
	 * Reads images into program
	 * 
	 */
	public GamePanel()
	{
		try
		{
			fieldImageMap = new HashMap<FieldType, Image>();
			fieldImageMap.put(FieldType.CORRIDOR,
					ImageIO.read(new File(Config.corridorImagePath)));
			fieldImageMap.put(FieldType.WALL,
					ImageIO.read(new File(Config.wallImagePath)));
            fieldImageMap.put(FieldType.BREAKABLE,
                    ImageIO.read(new File(Config.breakableImagePath)));
            fieldImageMap.put(FieldType.BULLET,
                    ImageIO.read(new File(Config.bulletImagePath)));

            playerImageMap = new HashMap<Direction, Image>();
            playerImageMap.put(Direction.NORTH, makeTransparent(ImageIO.read(new File(Config.playerImagePath1))));
            playerImageMap.put(Direction.SOUTH, makeTransparent(ImageIO.read(new File(Config.playerImagePath2))));
            playerImageMap.put(Direction.EAST, makeTransparent(ImageIO.read(new File(Config.playerImagePath3))));
            playerImageMap.put(Direction.WEST, makeTransparent(ImageIO.read(new File(Config.playerImagePath4))));

			enemyImageMap = new HashMap<EnemyId, Image>();
			for(EnemyId e : EnemyId.values()) enemyImageMap.put
				(e, ImageIO.read(new File(Config.enemyImagePath1)));
		
		}
		catch (final IOException e)
		{
			//System.out.println("Brakuje obrazków do wczytania\n" + e.toString());
		}
		currentBoard = null;
	}

	private int getImageSize()
	{
		if (this.getBounds().getWidth() > this.getBounds().getHeight())
			return (int) this.getBounds().getHeight() / Config.sideLength;
		else
			return (int) this.getBounds().getWidth() / Config.sideLength;
	}

	/**
	 * makes the image background transparent - if white
	 * 
	 * @param image
	 * @return image
	 */
	private Image makeTransparent(final Image image)
	{
		final ImageFilter filter = new RGBImageFilter()
		{
			public int markerRGB = Color.WHITE.getRGB() | 0xFF000000;

			@Override
			public final int filterRGB(final int x, final int y, final int rgb)
			{
				if ((rgb | 0xFF000000) == markerRGB) return 0x00FFFFFF & rgb;
				else return rgb;
			}
		};

		final ImageProducer ip = new FilteredImageSource(image.getSource(),	filter);

		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	/**
	 * paints the board in graphics g
	 * 
	 * @param g
	 * @param imageSize
	 */
	private void paintBoard(final Graphics g, final int imageSize)
	{
		for (int i = 0; i < Config.sideLength; ++i)
		{
			for (int j = 0; j < Config.sideLength; ++j)
			{
				final int x = imageSize * i;
				final int y = imageSize * j;
				g.drawImage(fieldImageMap.get(currentBoard
						.getFieldType(new Coordinates(i, j))),
						x, y, imageSize, imageSize, null);
			}
		}
	}

	@Override
	public void paintComponent(final Graphics g)
	{
		if (currentBoard == null) return;

		final int imageSize = getImageSize();
		paintBoard(g, imageSize);
		paintPlayer(g, imageSize);
		paintEnemies(g, imageSize);
	}

	private void paintEnemies(final Graphics g, final int imageSize)
	{
		for (final EnemyId e : EnemyId.values())
		{
			final Coordinates coordinates = currentBoard.getEnemyCoordinates(e);
			if (coordinates != null)
			{
				g.drawImage(enemyImageMap.get(currentBoard.getEnemyAim()), coordinates.getX()
						* imageSize, coordinates.getY() * imageSize,
						imageSize, imageSize, null);
			}
		}
	}

	private void paintPlayer(final Graphics g, final int imageSize)
	{
		final Coordinates coordinates = currentBoard.getPlayerCoordinates();
		if (coordinates != null)
		{
			g.drawImage(playerImageMap.get(currentBoard.getAim()), coordinates.getX()
					* imageSize, coordinates.getY() * imageSize,
					imageSize,	imageSize, null);
		}
	}

	/**
	 * gets the new board into the class
	 * 
	 * @param newBoard
	 */
	public void update(final GameDummy newBoard)
	{
		currentBoard = newBoard;
	}
}
