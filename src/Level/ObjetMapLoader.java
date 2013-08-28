package Level;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import ObjetMap.ObjetMap;



public class ObjetMapLoader {
	public static ArrayList<ObjetMap> loadFolder(String path){
		ArrayList<ObjetMap> liste = new ArrayList<ObjetMap>();
		File[] files = null;
		File directory = new File(path);
		files = directory.listFiles();
		Arrays.sort(files);
		if(files != null){
			for(int i = 0; i < files.length; i++){
				File file = files[i];
				if(file.isDirectory())
					loadFolder(file.getPath());
				else if(file.isFile() && file.getName().contains(".obj")){ //$NON-NLS-1$
					ObjetMap o = ObjetMapLoader.loadObject(file.getPath());
					liste.add(o);
				}
			}
		}
		return liste;
	}
	public static final boolean saveObject(ObjetMap o, String path){
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(
									new File(path))));
			
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static final ObjetMap loadObject(String path){
		ObjectInputStream ois;
		ObjetMap o = null;
		try {
			ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									new File(path))));
			
			try {
				o = (ObjetMap) ois.readObject();
				
				ois.close();
				return o;
			} catch (ClassNotFoundException e) {
				//Launch that only to delete not working objects.
				/*ois.close();
				File f = new File(path);
				f.deleteOnExit();*/
				e.printStackTrace();
			}
			ois.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return o;
	}
}
