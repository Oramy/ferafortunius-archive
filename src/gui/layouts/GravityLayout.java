package gui.layouts;

import gui.Container;
import gui.FComponent;

public class GravityLayout extends Layout{
	@Override
	public void addComponent(Container container, FComponent c) {
		container.getComponents().add(c);
		updateLayout();
	}
	@Override
	public void updateLayout() {
		int y = 0;
		for(FComponent c : getContainer().getComponents()){
			c.setY(y);
			y += c.getSizeY();
		}
	}

}
