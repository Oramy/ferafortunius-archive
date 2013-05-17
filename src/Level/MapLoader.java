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
}
