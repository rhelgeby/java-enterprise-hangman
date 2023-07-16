package no.helgeby.hangman;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

/**
 * Contains a map of settings. Expressions are evaluated against the map.
 */
public class Settings implements ExpressionEvaluator {

	private HashMap<String, Object> values;
	private PropertyChangeSupport propertyChangeSupport;

	public Settings() {
		values = new HashMap<>();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) values.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T put(String key, Object value) {
		Object oldValue = values.put(key, value);
		propertyChangeSupport.firePropertyChange(key, oldValue, value);
		return (T) oldValue;
	}

	public <T> T putIfNone(String key, T value) {
		if (hasProperty(key)) {
			return null;
		}
		put(key, value);
		return value;
	}

	public boolean hasProperty(String key) {
		return values.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T require(String key) {
		T value = (T) values.get(key);
		if (value == null) {
			throw new IllegalStateException("Key '" + key + "' is not set.");
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public <T> T evaluate(String expression) {
		// TODO: Evaluate EL-expressions against the map.
		return (T) expression;
	}

	public void addListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
