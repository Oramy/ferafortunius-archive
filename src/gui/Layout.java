package gui;

public abstract class Layout {
	protected Container container;
	public void addComponent(Container container, FComponent c) {
		container.getComponents().add(c);
	}
	public abstract void updateLayout();
}
