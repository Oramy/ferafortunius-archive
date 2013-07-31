package texts.converters;

import texts.Balise;

public interface BaliseConverter {
	/**Convert the balise to an object
	 * @param balise 
	 * @return new objects with the balise parameters.
	 */
	public Object convert(Balise balise);
	
}
