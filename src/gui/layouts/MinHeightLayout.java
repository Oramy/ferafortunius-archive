package gui.layouts;

import gui.FComponent;

public class MinHeightLayout extends GravityLayout {
	@Override
	public void updateLayout() {
		int y = 0;
		for(FComponent c : getContainer().getComponents()){
			c.setY(y);
			y += c.getSizeY();
		}
		getContainer().setSizeY(y);
	}
}
