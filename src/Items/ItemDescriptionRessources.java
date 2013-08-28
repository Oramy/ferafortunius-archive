package Items;

import gui.GameMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class ItemDescriptionRessources {
	private static final String TEXT_PATH = "Texts/Items/";
	private static final String TEXT_EXT = ".desc";
	
	public static String loadName(String file){
		String contenu = "";
		File f = null;
		f = new File(TEXT_PATH + GameMain.options.getLanguage().getLanguage() + "/"+ file + TEXT_EXT);
		if(!f.exists())
		f = new File(TEXT_PATH + "fr/"+ file + TEXT_EXT);
		if(f.exists()){
			FileInputStream fis;
				try {
					fis = new FileInputStream(f);
				
				char carac;
				carac = (char) fis.read();
				while(carac != '\n'){
					contenu += carac;
					carac = (char) fis.read();
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
		}
		return contenu;
	}
	public static void createDesc(String nom, String desc, String file){
		String contenu = nom + "\n\n" + desc;
		File f = null;
		f = new File(TEXT_PATH + GameMain.options.getLanguage().getLanguage() + "/"+ file + TEXT_EXT);
		try {
			if(f.exists())
				f.delete();
			f.createNewFile();
			if(f.exists()){
				FileOutputStream fos;
					try {
						fos = new FileOutputStream(f);
					for(byte b : contenu.getBytes()){
						fos.write(b);
					}			
				} catch (IOException e) {
					
					e.printStackTrace();
				}	
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	public static String loadDescription(String file){
		String contenu = "";
		File f = null;
		f = new File(TEXT_PATH + GameMain.options.getLanguage().getLanguage() + "/"+ file + TEXT_EXT);
		if(!f.exists())
		f = new File(TEXT_PATH + "fr/"+ file + TEXT_EXT);
		if(f.exists()){
			FileInputStream fis;
				try {
					fis = new FileInputStream(f);
				
				char carac = 'a';
				
				fis.skip(loadName(file).length());
				
				carac =  (char) fis.read();
				while(carac == (char) 10 || carac == (char) 13 )
					carac = (char) fis.read();
				while(carac != (char)-1){
					contenu += carac;
					carac = (char) fis.read();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return contenu;
	}

}
