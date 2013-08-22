package texts.utils.converters;

import java.awt.Color;

import texts.Balise;
import texts.converters.BaliseConverter;

public class ColorConverter implements BaliseConverter {

	@Override
	public Color convert(Balise balise) {
		float a = Float.valueOf(balise.getAttribute("a").getValue());
		float r = Float.valueOf(balise.getAttribute("r").getValue());
		float g = Float.valueOf(balise.getAttribute("g").getValue());
		float b = Float.valueOf(balise.getAttribute("b").getValue());
		
		Color c = new Color(a, r, g, b);
		
		return c;
	}

}
