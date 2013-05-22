package gui;

import java.util.ArrayList;

public class CheckBoxManager extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<CheckBox> choices; 
	public CheckBoxManager(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		choices = new ArrayList<CheckBox>();
		
	}
	public ArrayList<CheckBox> getChoices(){
		return choices;
	}
}
