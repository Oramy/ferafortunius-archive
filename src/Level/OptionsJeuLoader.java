package Level;

import gui.TextDisplayMode;
import gui.jeu.OptionsJeu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class OptionsJeuLoader {
	private static final String BUNDLE_NAME = "pref"; //$NON-NLS-1$

	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	public static final boolean saveOptions(OptionsJeu o){
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File("src/pref.properties")));
			p.setProperty("language", o.getLanguage().getLanguage());
			p.store(new FileOutputStream(new File("src/pref.properties")), "Wouh");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public static boolean loadBoolean(String key){
		String value = RESOURCE_BUNDLE.getString(key);
		if(value.equals("1"))
			return true;
		return false;
	}
	
	
	public static final OptionsJeu loadOptions(){
		//Création d'un nouvel objet option
		OptionsJeu o = null;
		o = new OptionsJeu();
		
		//Données pour la vSync
		o.setVsync(loadBoolean("vsync"));
		
		//Plein écran
		o.setWindowed(!loadBoolean("fullscreen"));
		
		o.setScreenshotGUI(loadBoolean("screenshotGUI"));
		
		//Text Speed
		String textspeed = RESOURCE_BUNDLE.getString("textspeed");
		
		if(textspeed.equals("0"))
			o.setTextDisplayMode(TextDisplayMode.Slowly);
		else if(textspeed.equals("1"))
			o.setTextDisplayMode(TextDisplayMode.Normal);
		else if(textspeed.equals("2"))
			o.setTextDisplayMode(TextDisplayMode.Fast);
		else if(textspeed.equals("3"))
			o.setTextDisplayMode(TextDisplayMode.Instantly);
		
		//Width
		String width = RESOURCE_BUNDLE.getString("width");
			o.setScreenWidth(Integer.parseInt(width));
			
		//Height
		String height = RESOURCE_BUNDLE.getString("height");
			o.setScreenHeight(Integer.parseInt(height));
			
		//Language
		o.setLanguage(Locale.FRENCH);
		
		o.setLanguage(new Locale(RESOURCE_BUNDLE.getString("language")));
		
		//Always rend
		o.setAlwaysRend(loadBoolean("alwaysrend"));
		
		o.setGameSpeedPrint(loadBoolean("gamespeedprint"));
		
		updateKeys(o);
		return o;
	}
}
