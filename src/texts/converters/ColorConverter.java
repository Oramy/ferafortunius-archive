package texts.converters;

import org.newdawn.slick.Color;

import texts.Balise;

public class ColorConverter implements BaliseConverter {

	@Override
	public Color convert(Balise balise) {
		Color c = new Color(0f,0f,0f, 1f);
		
		//Changing attributes
		c.a = Float.valueOf(balise.getAttribute("a").getValue());
		c.r = Float.valueOf(balise.getAttribute("r").getValue());
		c.g = Float.valueOf(balise.getAttribute("g").getValue());
		c.b = Float.valueOf(balise.getAttribute("b").getValue());
		
		return c;
	}

}
