package gui.editeurs.objetmaps;

import gui.Container;
import gui.editeurs.PanneauApercu;
import gui.layouts.BorderLayout;
import gui.widgets.WCollisionList;
import ObjetMap.ObjetMap;

public class EditeurCollObjMap extends Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WCollisionList collisionList;
	private PanneauApercu panneauAp;
	public EditeurCollObjMap(ObjetMap workedObj, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.actualLayout = new BorderLayout();
		collisionList = new WCollisionList(workedObj, this);
		this.addComponent(BorderLayout.WEST, collisionList);
		panneauAp = new PanneauApercu(workedObj, 0,0,1,1, this);
		this.addComponent(BorderLayout.CENTER, panneauAp);
	}
	public void setObj(ObjetMap obj){
		collisionList.setCible(obj);
		panneauAp.setEditChoice(obj);
	}

}
