package bonus;

import gui.PImage;

import java.io.File;
import java.util.ArrayList;

public abstract class BuffRessources {
	private static final String IMAGE_PATH = "Images/GUI/Buff/";
	private static ArrayList<PImage> buffRessources;
	
	public static void loadFolder(String path){
		buffRessources = new ArrayList<PImage>();
		File[] files = null;
		File directory = new File(path);
		if(directory.isDirectory());
			files = directory.listFiles();
		for(int i = 0; i < files.length; i++){
			File file = files[i];
			if(file.isDirectory())
				loadFolder(file.getPath());
			else if(file.isFile() && (file.getName().contains(".png"))){
				buffRessources.add(new PImage(file.getPath().substring(7).replaceAll("\\\\", "/")));
			}
		}
	}
	public static ArrayList<PImage> getBuffRessource(){
		if(buffRessources == null){
			loadFolder(IMAGE_PATH);
		}
		return buffRessources;
	}
	public static PImage getOneBuffRessource(String img){
		if(buffRessources == null){
			loadFolder(IMAGE_PATH);
		}
		PImage ressource = null;
		for(int i = 0; i < buffRessources.size(); i++){
			if(buffRessources.get(i).getNom().equals(img)){
				ressource = buffRessources.get(i);
			}
		}
		return ressource;
	}
	
}
