package gui.editeurs;

import gui.Container;
import gui.Text;

public class EditeurCaracEquipment extends Container{

	public EditeurCaracEquipment(int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.addComponent(new Text("Carac", this));
	}

}
