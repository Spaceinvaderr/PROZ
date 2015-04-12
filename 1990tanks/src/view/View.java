package view;

import commons.GameDummy;
import message.Message;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class View extends JFrame {

    public static final int DEFAULT_WIDTH = 550;
    public static final int DEFAULT_HEIGHT = 500;
    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger("view");
    private MenuBar menu;
    private GamePanel gamePanel;
    private KeyListener keyListener;

    public View(final BlockingQueue<Message> messageBuffer) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("Tanks");
                setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

                menu = new MenuBar(messageBuffer);
                setJMenuBar(menu);

                setLayout(new GridLayout(1, 2));

                gamePanel = new GamePanel();
                add(gamePanel);

                keyListener = new KeyListener(messageBuffer);
                addKeyListener(keyListener);

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                setFocusable(true);
            }
        });
    }


    /**
     * repaints the view after an event
     *
     * @param currentGame
     */
    public void repaintGameView(final GameDummy currentGame) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gamePanel.update(currentGame);
                repaint();
            }
        });
    }

    /**
     * sets the game view
     */
    public void setGameView() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gamePanel.setVisible(true);
            }
        });
    }

    /**
     * sets the main view
     */
    public void setEndView() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // tu mozna dorzucic menu glowne
            }
        });
    }
}
