package texts.converters;

import texts.Balise;

public interface BaliseReactor {
	/**
	 * Called when we want to react to a balise.
	 * @param o Object maked by the balise.
	 * @param b Balise maked by the text.
	 */
	public void react(Object o, Balise b);
}
