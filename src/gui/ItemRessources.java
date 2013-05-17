package gui;

import java.io.File;
import java.util.ArrayList;

public abstract class ItemRessources {
	private static ArrayList<PImage> itemRessource;
	public static void loadFolder(String path){
		itemRessource = new ArrayList<PImage>();
		File[] files = null;
		File directory = new File(path);
		if(directory.isDirectory());
			files = directory.listFiles();
		for(int i = 0; i < files.length; i++){
			File file = files[i];
			if(file.isDirectory())
				loadFolder(file.getPath());
			else if(file.isFile() && file.getName().contains(".png")){
				itemRessource.add(new PImage(file.getPath().substring(7).replaceAll("\\\\", "/")));
			}
		}
	}
	public static ArrayList<PImage> getItemRessource(){
		if(itemRessource == null){
			loadFolder("Images/Items/");
		}
		return itemRessource;
	}
	public static PImage getOneItemRessource(String img){
		if(itemRessource == null){
			loadFolder("Images/Items/");
		}
		PImage ressource = null;
		for(int i = 0; i < itemRessource.size(); i++){
			if(itemRessource.get(i).getNom().equals(img)){
				ressource = itemRessource.get(i);
			}
		}
		return ressource;
	}
}
