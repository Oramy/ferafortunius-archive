package gui.jeu;

import java.io.Serializable;

import ObjetMap.ObjetMap;

public class ObjMessage implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObjetMap object;
	private char action;
	public ObjMessage(ObjetMap o, char action) {
		super();
		this.setObject(o);
		this.setAction(action);
	}
	public ObjetMap getObject() {
		return object;
	}
	public void setObject(ObjetMap o) {
		this.object = o;
	}
	public char getAction() {
		return action;
	}
	public void setAction(char action) {
		this.action = action;
	}
}
