package gui.buttons;

import gui.Container;
import gui.layouts.GravityLayout;

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
		this.setActualLayout(new GravityLayout());
	}
	/**
	 * 
	 * @return all checkbox added.
	 */
	public ArrayList<CheckBox> getChoices(){
		return choices;
	}
	/**
	 * 
	 * @return a list of checked checkbox.
	 */
	public ArrayList<String> getChecked(){
		ArrayList<String> checked = new ArrayList<String>();
		for(CheckBox cb : choices){
			if(cb.isCheck())
				checked.add(cb.getName());
		}
		return checked;
	}
}
