package gui.buttons;

import gui.Container;
import gui.PImage;


public class RadioBox extends CheckBox{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final PImage normal = new PImage("GUI/radioBox.png");
	protected static final PImage active = new PImage("GUI/radioBoxActive.png");
	public RadioBox(String text, Container parent) {
		super(text, parent);
		setBoutonAct(normal);
		
	}
	
	@Override
	public void normal(){
		if(!check)
			setBoutonAct(normal);
		if(check)
			setBoutonAct(active);
	}
	@Override
	public void hover(){
		
	}
	
	@Override
	public void clickReleased(){
		check = !check;
		if(check){
			setBoutonAct(active);
			if(parent instanceof RadioBoxManager){
				RadioBoxManager manager = (RadioBoxManager)parent;
				if(manager.getChoices().size() > 0){
					((RadioBox)manager.getChoices().get(0)).check = false;
					((RadioBox)manager.getChoices().get(0)).setBoutonAct(normal);
				}
				manager.getChoices().clear();
				manager.getChoices().add(this);
			}
		}
		else{
			setBoutonAct(normal);
			if(parent instanceof RadioBoxManager){
				CheckBoxManager manager = (CheckBoxManager)parent;
				if(manager.getChoices().contains(this))
					manager.getChoices().remove(this);
			}
		}
	}
	

}
