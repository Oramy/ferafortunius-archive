package gui;


public class RadioBox extends CheckBox{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final PImage normal = new PImage("GUI/radioBox.png");
	protected static final PImage active = new PImage("GUI/radioBoxActive.png");
	public RadioBox(String text, Container parent) {
		super(text, parent);
		boutonAct = normal;
		
	}
	
	@Override
	public void normal(){
		if(!check)
			boutonAct = normal;
		if(check)
			boutonAct = active;
	}
	@Override
	public void hover(){
		
	}
	
	@Override
	public void clickReleased(){
		check = !check;
		if(check){
			boutonAct = active;
			if(parent instanceof RadioBoxManager){
				RadioBoxManager manager = (RadioBoxManager)parent;
				if(manager.getChoices().size() > 0){
					((RadioBox)manager.getChoices().get(0)).check = false;
					((RadioBox)manager.getChoices().get(0)).boutonAct = normal;
				}
				manager.getChoices().clear();
				manager.getChoices().add(this);
			}
		}
		else{
			boutonAct = normal;
			if(parent instanceof RadioBoxManager){
				CheckBoxManager manager = (CheckBoxManager)parent;
				if(manager.getChoices().contains(this))
					manager.getChoices().remove(this);
			}
		}
	}
	

}
