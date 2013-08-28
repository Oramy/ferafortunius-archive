package gui.editeurs.items;

import gui.Container;
import gui.ContainerWithBords;
import Items.Item;

public class EditeurItemBasic extends ContainerWithBords {

	/**
	 * 
	 */
	protected Item editItem;
	
	private static final long serialVersionUID = 1L;

	public EditeurItemBasic(Item editItem,int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent); 
		this.editItem = editItem;
	}
	public Item getEditItem() {
		return editItem;
	}
	public void setEditItem(Item editItem) {
		this.editItem = editItem;
	}
}
