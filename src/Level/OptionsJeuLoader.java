package Level;

import gui.TextDisplayMode;
import gui.jeu.OptionsJeu;

import java.util.Locale;
import java.util.ResourceBundle;

public class OptionsJeuLoader {
	private static final String BUNDLE_NAME = "pref"; //$NON-NLS-1$

	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	public static final boolean saveOptions(OptionsJeu o){
		return true;
	}
	private static final void updateKeys(OptionsJeu o){
		o.setUp(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("up")));
		o.setDown(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("down")));
		o.setLeft(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("left")));
		o.setRight(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("right")));
		o.setInventory(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("inventory")));
		o.setEquipment(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("equipment")));
		o.setShop(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("shop")));
		o.setOptions(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("options")));
		o.setExit(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("exit")));
		o.setAction(Integer.valueOf("0"+RESOURCE_BUNDLE.getString("action")));
	}
	public static final OptionsJeu loadOptions(){
		OptionsJeu o = null;
		o = new OptionsJeu();
		String vSync = RESOURCE_BUNDLE.getString("vsync");
		if(vSync.equals("1"))
			o.setVsync(true);
		else
			o.setVsync(false);
		String fullscreen = RESOURCE_BUNDLE.getString("fullscreen");
		if(fullscreen.equals("0"))
			o.setWindowed(true);
		else
			o.setWindowed(false);
		String textspeed = RESOURCE_BUNDLE.getString("textspeed");
		if(textspeed.equals("0"))
			o.setTextDisplayMode(TextDisplayMode.Slowly);
		else if(textspeed.equals("1"))
			o.setTextDisplayMode(TextDisplayMode.Normal);
		else if(textspeed.equals("2"))
			o.setTextDisplayMode(TextDisplayMode.Fast);
		else if(textspeed.equals("3"))
			o.setTextDisplayMode(TextDisplayMode.Instantly);
		String width = RESOURCE_BUNDLE.getString("width");
			o.setScreenWidth(Integer.parseInt(width));
		String height = RESOURCE_BUNDLE.getString("height");
			o.setScreenHeight(Integer.parseInt(height));
		o.setLanguage(Locale.FRENCH);
		o.setLanguage(new Locale(RESOURCE_BUNDLE.getString("language")));
		String alwaysRend = RESOURCE_BUNDLE.getString("alwaysrend");
		if(alwaysRend.equals("1"))
			o.setAlwaysRend(true);
		else
			o.setAlwaysRend(false);
		updateKeys(o);
		return o;
	}
}
