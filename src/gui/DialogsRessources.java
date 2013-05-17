package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class DialogsRessources {
	private static final String TEXT_PATH = "Texts/Dialogs/";
	private static final String TEXT_EXT = ".dialog";
	
	public static String loadText(String file){
		String contenu = "";
		File f = null;
		//Chercher le fichier de dialogue dans la langue actuelle.
			f = new File(TEXT_PATH + GameMain.options.getLanguage().getLanguage() + "/"+ file + TEXT_EXT);
		//Sinon prendre le fichier par défaut.
		if(!f.exists())
			f = new File(TEXT_PATH + "fr/"+ file + TEXT_EXT);
		
		FileInputStream fis;
		
		try {
			fis = new FileInputStream(f);
			
			// Lecture du fichier
			char carac;
			carac = (char) fis.read();
			
			while(carac != (char)-1){
				contenu += carac;
				carac = (char) fis.read();
			}
			
			fis.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return contenu;
	}
}
