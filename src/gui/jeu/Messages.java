package gui.jeu;

import gui.GameMain;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "gui.jeu.messages"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME, GameMain.options.getLanguage());

	private Messages() {
	}

	public static String getString(String key) {
		 RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, GameMain.options.getLanguage());
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
