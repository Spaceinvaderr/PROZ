package controller;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.util.HashMap; 
import java.util.Map; 
import java.util.concurrent.BlockingQueue; 
import java.util.concurrent.LinkedBlockingQueue; 
import java.util.logging.Logger; 
  
import javax.swing.Timer; 
  
import commons.Config; 
import commons.GameDummy; 
  
import view.View; 
  
import message.*; 
import model.*; 
  
  
/** 
 * Controls the whole application 
 *  
 * @author Margarita Chirillova
 */
  
public class Controller implements Runnable { 

  
    static Logger logger = Logger.getLogger("Controller"); 
  
    private View view;
    private Model model;
      
    private BlockingQueue<Message> messageBuffer;
    private Map<Class<?>, MessageHandler> handlerMap;
  
    private boolean playing = false; 
  
    private Timer timer; 
  
    public Controller() 
    {
        this.messageBuffer = new LinkedBlockingQueue<Message>();
        this.view = new View(messageBuffer);
        this.model = new Model();

        handlerMap = new HashMap<Class<?>, MessageHandler>();
        createHandlerMap();
    } 
  
    /** 
     * Creates handler map which projects messages to handlers which process 
     * them 
     *  
     */
    private void createHandlerMap() { 
        handlerMap.put(NewGameMessage.class, new NewGameHandler()); 
        handlerMap.put(MoveMessage.class, new MoveHandler());
        handlerMap.put(TimerMessage.class, new TimerHandler());
        handlerMap.put(AimMessage.class, new AimHandler());
        handlerMap.put(BulletMessage.class, new BulletHandler());
    }

    public static void main(String args[]) {
        new Thread(new Controller()).start();
    }

    @Override
    public void run() 
    {
        while (true) {
            try {
                final Message newEvent = messageBuffer.take();
                final GameDummy dummy = handlerMap.get(newEvent.getClass()).process(newEvent);
                view.repaintGameView(dummy);
            } catch (final NullPointerException e) {
                //e.printStackTrace();
                logger.severe("Nieoprogramowany message\n" + e);
            } catch (final InterruptedException e) {
                //e.printStackTrace();
                logger.warning("Wyjatek: przerwanie zewnetrzne\n" + e);
            }
        }
    } 
  
    public void startGame() 
    { 
        playing = true; 
        startTimer(); 
        view.setGameView(); 
    } 
  
    public void loseGame() 
    { 
        playing = false; 
        timer.stop(); 
        view.setEndView(); 
    } 
      
    public void winGame() 
    { 
        playing = false; 
        timer.stop(); 
        view.setEndView(); 
    } 
      
    /** 
     * Starts timer and sets ActionListener, which puts a TimerMessage each tic 
     *  
     */
    private void startTimer() { 
        final ActionListener taskPerformer = new ActionListener() { 
            @Override
            public void actionPerformed(final ActionEvent evt) { 
                messageBuffer.add(new TimerMessage()); 
            } 
        }; 
        timer = new Timer(Config.timerDelay, taskPerformer); 
        timer.start(); 
    }
  
    /** 
     * Interface for all MessageHandlers 
     */
    interface MessageHandler { 
        GameDummy process(Message message); 
  
    } 
  
    private class MoveHandler implements MessageHandler { 
  
        @Override
        public GameDummy process(final Message message) { 
            if (playing) { 
                logger.entering("MoveHandler", "process"); 
                final MoveMessage moveMessage = (MoveMessage) message; 
                return model.movePlayer(moveMessage.getDirection()); 
            } 
            return null; 
        } 
  
    }


    private class AimHandler implements MessageHandler {

        @Override
        public GameDummy process(final Message message) {
            if (playing) {
                logger.entering("AimHandler", "process");
                final AimMessage aimMessage = (AimMessage) message;
                return model.aim(aimMessage.getDirection());
            }
            return null;
        }

    }


    private class NewGameHandler implements MessageHandler { 
  
        @Override
        public GameDummy process(final Message message) { 
            logger.entering("NewGameHandler", "process"); 
            startGame(); 
            return model.newGame(); 
        } 
    } 
  
    private class TimerHandler implements MessageHandler { 
        @Override
        public GameDummy process(final Message message) { 
            logger.entering("TimeHandler", "process"); 
            if (playing) { 
                final GameDummy dummy = model.advance(); 
  
                if (dummy.isGameEnd()) 
                { 
                    if(dummy.isWinner()) winGame(); 
                    else loseGame(); 
                } 
                return dummy; 
            } 
            return null; 
  
        } 
    }

    private class BulletHandler implements MessageHandler {
        @Override
        public GameDummy process(final Message message) {
            if (playing) {
                logger.entering("BulletHandler", "process");
                return model.shoot();
            }
            return null;
        }

    }
}
