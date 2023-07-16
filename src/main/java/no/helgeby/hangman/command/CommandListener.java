package no.helgeby.hangman.command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import no.helgeby.hangman.ExpressionEvaluator;

/**
 * Holds a map of command handlers.
 * <p>
 * As an ActionListener it delegates to the registered command handler based on
 * the based command given. The base command is the first word when splitting by
 * spaces. Assumes a command is set on the ActionEvent. Otherwise it does
 * nothing. You can use this as the ActionListener for a component.
 */
public class CommandListener implements ActionListener {

	private static final Logger log = LogManager.getLogger(CommandListener.class);

	private Map<String, CommandHandler> handlers;
	private ExpressionEvaluator evaluator;

	/**
	 * Constructs an empty command listener.
	 * 
	 * @param evaluator The evaluator to use for EL expressions.
	 */
	public CommandListener(ExpressionEvaluator evaluator) {
		this.evaluator = Objects.requireNonNull(evaluator, "evaluator");
		handlers = new HashMap<>();
	}

	/**
	 * Looks up the base command in the ActionEvent. If a handler is registered for
	 * this command it is delegated. Base commands that have no handler are ignored
	 * with a log warning.
	 * 
	 * @param event The event with the full command line to execute.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String commandLine = event.getActionCommand();
		if (StringUtils.isBlank(commandLine)) {
			log.info("Ignoring blank command.");
			return;
		}
		handleCommand(commandLine);
	}

	/**
	 * Handles a command line.
	 * 
	 * @param commandLine The full command line to handle.
	 * @return A result that tells if the command was successful or not.
	 */
	public CommandResult handleCommand(String commandLine) {
		log.info("Handling command line: '" + commandLine + "'");

		// Evaluate eventual expressions in the command line first.
		String expressionResult = evaluator.evaluate(commandLine).toString();

		StringTokenizer tokenizer = new StringTokenizer(expressionResult, " ");

		// The command is always the first token.
		String baseCommand = tokenizer.nextToken();

		CommandHandler handler = getHandler(baseCommand);
		if (handler == null) {
			return new CommandResult("Command not found: " + baseCommand);
		}

		// Execute the command.
		try {
			CommandResult result = handler.handleCommand(commandLine, baseCommand, tokenizer);
			log.info(result.getMessage());
			if (result.hasException()) {
				log.warn("Exception when handling command.", result.getError());
			}
			return result;
		} catch (Exception e) {
			String errorMessage = "Unhandled exception when handling command '" + baseCommand + "'.";
			log.error(errorMessage, e);
			return new CommandResult(errorMessage, e);
		}
	}

	/**
	 * Adds a command handler.
	 * 
	 * @param baseCommand The base command to handle. This is the first word of the
	 *                    command line when splitting by spaces. Must not be null or
	 *                    blank.
	 * @param handler     Hander that performs the command.
	 * @return Previous handler for this command, if any. Null if none.
	 * @throws IllegalArgumentException if baseCommand is null or blank.
	 */
	public CommandHandler addHandler(String baseCommand, CommandHandler handler) {
		if (StringUtils.isBlank(baseCommand)) {
			throw new IllegalArgumentException("Perameter baseCommand must not be null or blank.");
		}
		Objects.requireNonNull(handler, "handler");
		return handlers.put(baseCommand, handler);
	}

	/**
	 * Adds a command handler using its own defined base name.
	 * 
	 * @param handler Hander that performs the command.
	 * @return Previous handler for this command, if any. Null if none.
	 * @throws IllegalArgumentException if baseCommand is null or blank.
	 */
	public CommandHandler addHandler(CommandHandler handler) {
		Objects.requireNonNull(handler, "handler");
		return handlers.put(handler.getName(), handler);
	}

	/**
	 * Gets a registered command handler.
	 * 
	 * @param baseCommand The command to get handler for. This is the first word of
	 *                    the command when splitting by spaces. Must not be null or
	 *                    blank.
	 * @return The command handler or null if not found.
	 * @throws IllegalArgumentException if baseCommand is null or blank.
	 */
	public CommandHandler getHandler(String baseCommand) {
		if (StringUtils.isBlank(baseCommand)) {
			throw new IllegalArgumentException("Perameter baseCommand must not be null or blank.");
		}
		return handlers.get(baseCommand);
	}

	/**
	 * Removes a command handler if present.
	 *
	 * @param baseCommand Command to remove. Must not be null or blank.
	 * @return The removed command handler if present, or null if no matching
	 *         handler was found.
	 * @throws IllegalArgumentException if baseCommand is null or blank.
	 */
	public CommandHandler removeHandler(String baseCommand) {
		if (StringUtils.isBlank(baseCommand)) {
			throw new IllegalArgumentException("Perameter baseCommand must not be null or blank.");
		}
		return handlers.remove(baseCommand);
	}

}
