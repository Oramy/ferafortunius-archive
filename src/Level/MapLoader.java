package Level;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MapLoader {
	public static final boolean saveMap(ChunkMap c , String path){
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(
									new File(path))));
			
			oos.writeObject(c);
			oos.close();
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static final ChunkMap loadMap(String path){
		ObjectInputStream ois;
		ChunkMap map = new ChunkMap(0, 0, 0, 0);
		try {
			ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									new File(path))));
			
			try {
				map = (ChunkMap) ois.readObject();
				
				ois.close();
				return map;
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			ois.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return map;
	}
	public static final boolean isExisting(String path){
		File f = new File(path);
		return f.exists();
	}
	public static final void clearAutoSaves(String path){
		for(int i = 0; i < 9; i++){
			path = path.substring(0, path.length() - 3)+"$"+i+".dat";
			File f =  new File(path);
			if(f.exists()){
				f.delete();
			}
		}
	}
	public static final void clearAllAutoSaves(){
		for(int i = 0; i < 9; i++){
			File f =  new File("data/Maps/");
		}
	}
	public static final boolean hasAutoSave(String path){
		int autoSave =  1;
		path = path.substring(0, path.length() - 3)+"$"+autoSave+".dat";
		File f =  new File(path);
		while(!f.exists() && autoSave <= 9){
			path = path.substring(0, path.length() - 3)+"$"+autoSave+".dat";
			f =  new File(path);
			autoSave++;
		}
		return f.exists();
	}
	public static final File getAutoSaveFile(String path){
		int autoSave =  1;
		path = path.substring(0, path.length() - 3)+"$"+autoSave+".dat";
		File f =  new File(path);
		while(!f.exists() && autoSave <= 9){
			path = path.substring(0, path.length() - 3)+"$"+autoSave+".dat";
			f =  new File(path);
			autoSave++;
		}
		return f;
	}
	public static final ChunkMap loadAutoSavedMap(String path){
		ObjectInputStream ois;
		
		File f = getAutoSaveFile(path);
		
		ChunkMap map = new ChunkMap(0, 0, 0, 0);
		
		if(f.exists()){
			try {
				ois = new ObjectInputStream(
						new BufferedInputStream(
								new FileInputStream(f)));
				
				try {
					map = (ChunkMap) ois.readObject();
					
					ois.close();
					return map;
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
				ois.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return map;
	}
}
