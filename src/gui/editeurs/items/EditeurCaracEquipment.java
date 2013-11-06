package gui.editeurs.objetmaps;

import gui.Container;
import gui.Text;

public class EditeurCaracEquipment extends Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditeurCaracEquipment(int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.addComponent(new Text("Carac", this));
	}

}
