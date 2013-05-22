package Level;

import java.util.ArrayList;

public class ArrayIterator implements Iterator{
	  private ArrayList<? extends Object> array;
	  private int nge;
	  /** Constructeur avec un tableau de données à parcourir */
	  public ArrayIterator(ArrayList<? extends Object> array) {
	   this.array = array;
	   nge = 0;
	  }
	  /** Retourne chaque élément de la collection ou null */
	  public Object getNextElement() {
		  if(array == null) return null;
		  if (nge >= array.size()) return null;
		   return array.get(nge++);
	  }
}
