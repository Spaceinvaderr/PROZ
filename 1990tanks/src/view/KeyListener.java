package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import commons.Config;
import commons.Direction;

import message.AimMessage;
import message.BulletMessage;
import message.Message;
import message.MoveMessage;


/**
 * the key listener
 * 
 * @author Margarita Chirillova
 * 
 */
public class KeyListener extends KeyAdapter {
	/**
	 * Queue connecting view with controller
	 * 
	 */
	private final BlockingQueue<Message> messageBuffer;
	/**
	 * Stores all input keys.
	 * 
	 */
	private final Map<Integer, Message> keyMap;
	Logger logger = Logger.getLogger("KeyListener");

	public KeyListener(final BlockingQueue<Message> messageBufferOut) {
		this.messageBuffer = messageBufferOut;
		this.keyMap = defineKeyMap();
	}

	@Override
	/*public void keyPressed(final KeyEvent event) {
		final int key = event.getKeyCode();
		final Message message = keyMap.get(key);
		try {
			Thread.sleep(10);
		} catch (final InterruptedException e1) {
			e1.printStackTrace();
		}
		if (message != null) {
			try {
				messageBuffer.add(message);
			} catch (final IllegalStateException e) {
				logger.severe("Blad dodania widomosci do kolejki");
			}
		} else {
			logger.fine("Brak obslugi tego klawisza " + key);
		}
	}
*/

    public void keyPressed(final KeyEvent event) {
        final int key = event.getKeyCode();
        final Message message = keyMap.get(key);
        try {
            Thread.sleep(10);
        } catch (final InterruptedException e1) {
            e1.printStackTrace();
        }
        if (message != null) {
            try {
                switch (key){
                    case KeyEvent.VK_UP : {
                        messageBuffer.add(keyMap.get(KeyEvent.VK_W));
                        Thread.sleep(10);
                        messageBuffer.add(message);
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        messageBuffer.add(keyMap.get(KeyEvent.VK_S));
                        Thread.sleep(10);
                        messageBuffer.add(message);
                        break;
                    }
                    case KeyEvent.VK_LEFT : {
                        messageBuffer.add(keyMap.get(KeyEvent.VK_A));
                        Thread.sleep(10);
                        messageBuffer.add(message);
                        break;
                    }
                    case KeyEvent.VK_RIGHT : {
                        messageBuffer.add(keyMap.get(KeyEvent.VK_D));
                        Thread.sleep(10);
                        messageBuffer.add(message);
                        break;
                    }
                    default:{
                        messageBuffer.add(message);
                    }
                }
            } catch (final IllegalStateException e) {
                logger.severe("Blad dodania widomo ci do kolejki");
            } catch (final InterruptedException e1) {
                e1.printStackTrace();
            }
        } else {
            logger.fine("Brak obs ugi tego klawisza " + key);
        }
    }

	/**
	 * Applies changes set in Config
	 * 
	 */
	private Map<Integer, Message> defineKeyMap()
	{
        Map<Integer, Message> keyMap = new HashMap<Integer, Message>();
		keyMap.put(Config.PLAYER1_NORTH, new MoveMessage(Direction.NORTH));
		keyMap.put(Config.PLAYER1_SOUTH, new MoveMessage(Direction.SOUTH));
		keyMap.put(Config.PLAYER1_EAST, new MoveMessage(Direction.EAST));
		keyMap.put(Config.PLAYER1_WEST, new MoveMessage(Direction.WEST));
		keyMap.put(Config.PLAYER1_BULLET, new BulletMessage());

        keyMap.put(Config.AIM_NORTH, new AimMessage(Direction.NORTH));
        keyMap.put(Config.AIM_SOUTH, new AimMessage(Direction.SOUTH));
        keyMap.put(Config.AIM_EAST, new AimMessage(Direction.EAST));
        keyMap.put(Config.AIM_WEST, new AimMessage(Direction.WEST));
		return Collections.unmodifiableMap(keyMap);
	}

}
