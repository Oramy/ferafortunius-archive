package ObjetMap;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjetImageList implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<ObjetImage> list;
	protected String alias;
	
	public ObjetImageList clone(){
		ObjetImageList clone = null;
		try {
			clone = (ObjetImageList) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		clone.list = new ArrayList<ObjetImage>();
		for(ObjetImage i : list){
			clone.list.add(i.clone());
		}
		return clone;	
	}
	public ObjetImageList(String alias){
		this.alias = alias;
		list = new ArrayList<ObjetImage>();
	}
	public ArrayList<ObjetImage> getList(){
		return list;
	}
	public String getAlias(){
		return alias;
	}
}
