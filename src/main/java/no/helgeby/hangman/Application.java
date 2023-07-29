package no.helgeby.hangman;

import static java.awt.event.ActionEvent.ACTION_PERFORMED;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import no.helgeby.hangman.command.CommandHandler;
import no.helgeby.hangman.command.CommandListener;
import no.helgeby.hangman.command.DifficultyCommand;
import no.helgeby.hangman.command.ExitCommand;
import no.helgeby.hangman.command.KeyCommand;
import no.helgeby.hangman.command.NewGameCommand;
import no.helgeby.hangman.command.PaintCommand;
import no.helgeby.hangman.command.ShowSettingsCommand;
import no.helgeby.hangman.command.StageCommand;
import no.helgeby.hangman.gui.GallowsPanel;
import no.helgeby.hangman.gui.SettingsFrame;
import no.helgeby.hangman.gui.StatusPanel;
import no.helgeby.hangman.model.Config;
import no.helgeby.hangman.model.GameModel;

public class Application extends JFrame {

	private static final long serialVersionUID = 363692463735547307L;

	private static final Logger log = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		try {
			log.info("Applying system specific look and feel.");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			log.warn("Failed to set look and feel. Using Java's default.", e);
		}

		try {
			new Application();
		} catch (IOException e) {
			log.error("Could not load config file.", e);
		} catch (URISyntaxException e) {
			log.error("Could not make URI.", e);
		}
	}

	public Application() throws IOException, URISyntaxException {
		log.info("Starting application.");

		Settings settings = new Settings();

		// Note: The root path is relative to application resources.
		String fileName = "/application.properties";
		Path configFile = Paths.get(Application.class.getResource(fileName).toURI());
		Config config = new Config(configFile);
		settings.put("config", config);

		GameModel gameModel = new GameModel(config);
		settings.put("gameModel", gameModel);

		CommandListener commandListener = new CommandListener(settings);
		commandListener.addHandler(new ExitCommand());
		commandListener.addHandler(new DifficultyCommand(gameModel));
		commandListener.addHandler(new NewGameCommand(gameModel));
		commandListener.addHandler(new KeyCommand(gameModel));

		SwingUtilities.invokeLater(() -> {
			GuiBundle bundle = buildGui(commandListener, gameModel);

			// These commands depends on GUI elements and must be constructed on the event
			// dispatch thread.
			commandListener.addHandler(new ShowSettingsCommand(bundle.settingsFrame));
			commandListener.addHandler(new PaintCommand(bundle.gallows.getPainterManager()));
			commandListener.addHandler(new StageCommand(gameModel));
		});

		// This adds a key listener for guesses.
		addGlobalKeyListener(gameModel, commandListener);
	}

	/**
	 * Builds the GUI elements.
	 * 
	 * @param listener  Handles the actions that the user generates. It may delegate
	 *                  further to a {@link CommandHandler}.
	 * @param gameModel Views register their listener to act on model changes.
	 * 
	 * @return A wrapper object with references to various GUI elements that may be
	 *         useful.
	 */
	private GuiBundle buildGui(CommandListener listener, GameModel gameModel) {
		log.info("Building GUI.");

		GuiBundle bundle = new GuiBundle();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Hangman");
		setName("game");
		setLayout(new BorderLayout());

		bundle.gallows = new GallowsPanel(gameModel);
		add(bundle.gallows, BorderLayout.NORTH);

		bundle.statusPanel = new StatusPanel(listener, gameModel);
		add(bundle.statusPanel, BorderLayout.SOUTH);

		bundle.settingsFrame = new SettingsFrame(listener, gameModel);

		setResizable(false);
		setLocationByPlatform(true);
		pack();
		setVisible(true);

		return bundle;
	}

	/**
	 * Adds a global key listener. This is only for capturing keys when the game
	 * window is active.
	 */
	private void addGlobalKeyListener(GameModel gameModel, CommandListener listener) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent event) {
				if (event.getID() != KeyEvent.KEY_TYPED) {
					// Not the event type we are interested in.
					return false;
				}

				// Only process keys events when main window has focus.
				Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
				String name = activeWindow.getName();
				if (!name.equals("game")) {
					// Game window does not have focus. Let something else handle the key event.
					return false;
				}

				char keyChar = event.getKeyChar();

				if (!Character.isAlphabetic(keyChar)) {
					// Ignore keys we're not interested in.
					return false;
				}

				keyChar = Character.toLowerCase(keyChar);
				ActionEvent actionEvent = new ActionEvent(this, ACTION_PERFORMED, "key " + keyChar);
				listener.actionPerformed(actionEvent);
				return true;
			}
		});
	}
}
