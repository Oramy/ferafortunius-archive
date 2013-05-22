package gui.editeurs;

import gui.Container;
import gui.Text;

import org.newdawn.slick.GameContainer;

import Items.Item;

public class EmptyEditeurItem extends EditeurItemBasic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Text test;
	public EmptyEditeurItem(Item editItem, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(editItem, x, y, sizeX, sizeY, parent);
		test = new Text(editItem.getName(), this);
		this.addComponent(test);
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(!editItem.getName().equals(test.getText()))
			test.setText(editItem.getName());
	}
}
