package gui;

import gui.layouts.Layout;

public class OngletManagerLayout extends Layout {
	public void addComponent(Container container, FComponent c) {
		container.getComponents().add(c);
		int x = 0;
		for(int i = 0; i < container.getComponents().size(); i++){
			FComponent e = container.getComponents().get(i);
			e.setY(10);
			e.setX(x);
			x += e.getSizeX();
		}
	}

	@Override
	public void updateLayout() {
		// TODO Auto-generated method stub
		
	}
}
