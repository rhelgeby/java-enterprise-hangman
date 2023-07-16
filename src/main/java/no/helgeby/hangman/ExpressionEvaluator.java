package no.helgeby.hangman;

/**
 * Indicates that the implementation is able to evaluate an expression.
 */
public interface ExpressionEvaluator {

	/**
	 * Evaluates expressions in a string. If the whole string is an expression, the
	 * resulting object is returned.
	 * <p>
	 * The evaluation context depends on the class implementing this interface.
	 * 
	 * @param <T>     Cast the resulting content to this type. This depends on if
	 *                the input content is a pure expression. Otherwise the type
	 *                will be a String.
	 * @param content Content to evaluate expressions on.
	 * @return A string if the content contains text before or after the expression,
	 *         or the resulting object if content contains one expression.
	 * @throws ClassCastException if the resulting object cannot be casted to the
	 *                            expected type.
	 */
	<T> T evaluate(String content);

}
