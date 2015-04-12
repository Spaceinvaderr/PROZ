package view;

import java.awt.event.ActionEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import message.Message;
import message.NewGameMessage;


/**
 * menu bar
 * 
 * @author Margarita Chirillova
 * 
 */
public class MenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	private final BlockingQueue<Message> messageBuffer;

	private class NewGameAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		public NewGameAction(final String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(final ActionEvent event)
		{
			final Message mes = new NewGameMessage();
			try
			{
				messageBuffer.add(mes);
			}
			catch (final IllegalStateException e)
			{
				View.logger.severe("Blad wstawienia wiadomosci do kolejki");
			}
		}
	}
	
	private class ExitAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		public ExitAction(final String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(final ActionEvent event)
		{
			System.exit(0);
		}
	}

	public MenuBar(final BlockingQueue<Message> messageBufferOut)
	{
		messageBuffer = messageBufferOut;
		final JMenu game = new JMenu("Gra");
		game.add(new NewGameAction("Nowa gra"));
		game.addSeparator();
		game.add(new ExitAction("Zakończ"));

		add(game);
	}
}
