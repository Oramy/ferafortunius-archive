package gui.editeurs;

import gui.Container;
import gui.ContainerWithBords;
import gui.layouts.BorderLayout;
import gui.widgets.WCollisionList;
import gui.widgets.WEntityCaracs;
import ObjetMap.BasicObjetMap;
import ObjetMap.ObjetMap;

public class EditeurEntity extends Container{

	private ObjetMap obj;
	
	private WEntityCaracs caracEdit;
	
	private static final long serialVersionUID = 1L;
	public EditeurEntity(ObjetMap obj, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.actualLayout = new BorderLayout();
		
		this.addComponent(BorderLayout.NORTH, new ContainerWithBords(0,0,1,1, this));
		this.addComponent(BorderLayout.SOUTH, new ContainerWithBords(0,0,1,1, this));
		this.addComponent(BorderLayout.CENTER, new WCollisionList(new BasicObjetMap(0,0,0,0,0,0), this));
		
		caracEdit = new WEntityCaracs(obj, this);
		this.addComponent(BorderLayout.WEST, caracEdit);
	}
	public ObjetMap getObj() {
		return obj;
	}
	public void setObj(ObjetMap obj) {
		this.obj = obj;
		caracEdit.setObj(obj);
	}
}
