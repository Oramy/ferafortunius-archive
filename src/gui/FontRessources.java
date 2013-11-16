package gui;

import java.awt.Color;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.font.effects.OutlineWobbleEffect;

public class FontRessources {
	private static FontRessources singleton;
	public UnicodeFont bufftext;
	public UnicodeFont text;
	public UnicodeFont titres;
	public UnicodeFont gametext;
	public UnicodeFont gametitles;

	@SuppressWarnings("unchecked")
	private FontRessources() {
		// Texte normal
		try {
			text = new UnicodeFont("Polices/TravelingTypewriter.ttf", 15,
					false, false);
		} catch (SlickException e) {

			e.printStackTrace();
		}
		text.addAsciiGlyphs();
		text.addGlyphs(400, 600);
		text.getEffects().add(new GradientEffect(Color.gray, Color.white, 1));
		try {
			text.loadGlyphs();
		} catch (SlickException e) {

			e.printStackTrace();
		}
		// Buff bar text
		try {
			bufftext = new UnicodeFont("Polices/TravelingTypewriter.ttf", 15,
					false, false);
		} catch (SlickException e) {

			e.printStackTrace();
		}
		bufftext.addAsciiGlyphs();
		bufftext.addGlyphs(400, 600);
		bufftext.getEffects().add(
				new GradientEffect(Color.gray, Color.white, 1));

		try {
			bufftext.loadGlyphs();
		} catch (SlickException e) {

			e.printStackTrace();
		}
		// Gros texte
		try {
			gametext = new UnicodeFont("Polices/evanescent.ttf", 130,
					false, false);
		} catch (SlickException e) {

			e.printStackTrace();
		}
		gametext.addAsciiGlyphs();
		gametext.addGlyphs(400, 600);
		gametext.getEffects().add(
				new GradientEffect(Color.gray, Color.white, 1));

		try {
			gametext.loadGlyphs();
		} catch (SlickException e) {

			e.printStackTrace();
		}

		// Titres
		try {
			titres = new UnicodeFont("Polices/Pieces of Eight.ttf", 60, false,
					false);
		} catch (SlickException e) {

			e.printStackTrace();
		}
		titres.addAsciiGlyphs();
		titres.addGlyphs(400, 600);
		titres.getEffects().add(new OutlineWobbleEffect(5, Color.white));
		titres.getEffects().add(new ColorEffect(Color.black));

		try {
			titres.loadGlyphs();
		} catch (SlickException e) {

			e.printStackTrace();
		}
		
		// Game Titles
		try {
			gametitles = new UnicodeFont("Polices/evanescent.ttf", 120, false,
					false);
		} catch (SlickException e) {

			e.printStackTrace();
		}
		gametitles.addAsciiGlyphs();
		gametitles.addGlyphs(400, 600);
		gametitles.getEffects().add(new ColorEffect(Color.white));

		try {
			gametitles.loadGlyphs();
		} catch (SlickException e) {

			e.printStackTrace();
		}
	}

	public static FontRessources getFonts() {
		if(singleton == null)
			singleton = new FontRessources();
		return singleton;
	}
}
