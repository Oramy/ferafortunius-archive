package gui.editeurs;

import ObjetMap.Terre;
import gui.BorderLayout;
import gui.Container;
import gui.ContainerWithBords;
import gui.widgets.WCollisionList;

public class EditeurEntity extends Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EditeurEntity(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.actualLayout = new BorderLayout();
		this.addComponent(BorderLayout.NORTH, new ContainerWithBords(0,0,1,1, this));
		this.addComponent(BorderLayout.SOUTH, new ContainerWithBords(0,0,1,1, this));
		this.addComponent(BorderLayout.WEST, new WCollisionList(new Terre(0,0,0,0,0,0), this));
		this.addComponent(BorderLayout.CENTER, new ContainerWithBords(0,0,1,1, this));
	}
}