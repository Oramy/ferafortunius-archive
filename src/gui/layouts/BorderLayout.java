package gui.layouts;

import gui.Container;
import gui.FComponent;

public class BorderLayout extends Layout{
	public static final String EAST = "east",NORTH = "north",SOUTH = "south",WEST = "west",CENTER = "center";
	
	private FComponent east, north, west, south, center;
	
	public BorderLayout(){
		east = north = west = south = center = new FComponent(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void updateSize() {
				
			}
			
		};
	}
	@Override
	public void addComponent(Container container, FComponent c) {
		addComponent(CENTER, container, c);
	}
	/**
	 * Add a component in a specific zone
	 * @param name zone name
	 * @param container parent container
	 * @param c component to add
	 */
	public void addComponent(String name, Container container, FComponent c) {
		this.setContainer(container);
		if(name.equals(EAST))
				east = c;
		else if(name.equals(NORTH))
			north = c;
		
		else if(name.equals(SOUTH))
			south = c;
		
		else if(name.equals(WEST))
			west = c;
		else if(name.equals(CENTER))
			center = c;
			
		
		container.getComponents().add(c);
		updateLayout();
		
	}
	
	@Override
	public void updateLayout() {
		if(getContainer().getComponents().contains(center))
			center.setBounds(0, 0, getContainer().getSizeX(), getContainer().getSizeY());
		if(getContainer().getComponents().contains(west)){
			
			west.setBounds(0, 0, getContainer().getSizeX() / 10 * 2, getContainer().getSizeY());
			center.setBounds(getContainer().getSizeX() / 10 * 2, 0, getContainer().getSizeX() -  getContainer().getSizeX() / 10 * 2, getContainer().getSizeY());
		
		}
		if(getContainer().getComponents().contains(east)){
			east.setBounds(getContainer().getSizeX() -  getContainer().getSizeX() / 10 * 2, 0, getContainer().getSizeX() / 10 * 2, getContainer().getSizeY());
			center.setSizeX(getContainer().getSizeX() -  getContainer().getSizeX() / 10 * 2 - center.getX());
		}
		if(getContainer().getComponents().contains(north)){
			north.setBounds(0, 0, getContainer().getSizeX(), getContainer().getSizeY() / 10 * 2);
			center.setY(getContainer().getSizeY() / 10 * 2);
			center.setSizeY(getContainer().getSizeY() -  getContainer().getSizeY() / 10 * 2);
			west.setY(north.getSizeY());
			east.setY(north.getSizeY());
			west.setSizeY(getContainer().getSizeY() - north.getSizeY());
			east.setSizeY(getContainer().getSizeY()  - north.getSizeY());
		}
		
		if(getContainer().getComponents().contains(south)){
			south.setBounds(0, getContainer().getSizeY() -  getContainer().getSizeY() / 10 * 2, getContainer().getSizeX(), getContainer().getSizeY() / 10 * 2);
			center.setSizeY(getContainer().getSizeY() -  south.getSizeY() - center.getY());
			west.setSizeY(getContainer().getSizeY() -  south.getSizeY() - west.getY());
			east.setSizeY(getContainer().getSizeY() -  south.getSizeY() - east.getY());
		}
	}
	
}
