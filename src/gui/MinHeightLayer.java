package gui;

public class MinHeightLayer extends GravityLayer {
	@Override
	public void updateLayout() {
		int y = 0;
		for(FComponent c : container.getComponents()){
			c.setY(y);
			y += c.getSizeY();
		}
		container.setSizeY(y);
	}
}
