package Level;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Items.Item;


public class ItemLoader {
	public static final boolean saveObject(Item o, String path){
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
	public static final Item loadObject(String path){
		ObjectInputStream ois;
		Item o = null;
		try {
			ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									new File(path))));
			
			try {
				o = (Item) ois.readObject();
				
				ois.close();
				return o;
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			ois.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return o;
	}
}
