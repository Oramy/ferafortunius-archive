package gui.layouts;

import gui.Container;
import gui.FComponent;

public abstract class Layout {
	private Container container;
	public void addComponent(Container container, FComponent c) {
		container.getComponents().add(c);
	}
	public abstract void updateLayout();
	public Container getContainer() {
		return container;
	}
	public void setContainer(Container container) {
		this.container = container;
	}
}
