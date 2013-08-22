package texts.converters;

import texts.utils.converters.ColorConverter;

public class BaliseConvertersManager {
	private static boolean init;
	
	/**
	 * A method who links all created converters with a balise.
	 */
	private static void linkBaliseConverters(){
		BaliseConvertersList.linkConverters(new ColorConverter(), "color");
	}
	/** Links the balise converters if they are not linked now.
	 * @see linkBaliseConverters();
	 */
	public static void initBaliseConverters(){
		if(!init){
			linkBaliseConverters();
			init = true;
		}
	}

}
