package gui;

public class GravityLayer extends Layout{
	public void addComponent(Container container, FComponent c) {
		container.getComponents().add(c);
		updateLayout();
	}
	@Override
	public void updateLayout() {
		int y = 0;
		for(FComponent c : container.getComponents()){
			c.setY(y);
			y += c.getSizeY();
		}
	}

}
