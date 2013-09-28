package gui.editeurs.animations;

import gui.Container;
import gui.layouts.BorderLayout;
import ObjetMap.ObjetMap;

public class EditeurAnimationKey extends Container{

	private PanneauAnimationKey pan;
	
	private ObjetMap workedObj;
	public EditeurAnimationKey(ObjetMap obj, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.actualLayout = new BorderLayout();
		this.setWorkedObj(obj);
		
		pan = new PanneauAnimationKey(obj, 0,0,sizeX,sizeY, this);
		this.addComponent(BorderLayout.CENTER, pan);
	}
	public ObjetMap getWorkedObj() {
		return workedObj;
	}
	public void setWorkedObj(ObjetMap workedObj) {
		this.workedObj = workedObj;
		if(pan != null)
		pan.setWorkOn(workedObj);
	}

}
