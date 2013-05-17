package gui;

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
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	public void addComponent(Container container, FComponent c) {
		addComponent(CENTER, container, c);
	}
	public void addComponent(String name, Container container, FComponent c) {
		this.container = container;
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
		if(container.getComponents().contains(center))
			center.setBounds(0, 0, container.sizeX, container.sizeY);
		if(container.getComponents().contains(west)){
			
			west.setBounds(0, 0, container.sizeX / 10 * 2, container.sizeY);
			center.setBounds(container.sizeX / 10 * 2, 0, container.sizeX -  container.sizeX / 10 * 2, container.sizeY);
		
		}
		if(container.getComponents().contains(east)){
			east.setBounds(container.sizeX -  container.sizeX / 10 * 2, 0, container.sizeX / 10 * 2, container.sizeY);
			center.setSizeX(container.sizeX -  container.sizeX / 10 * 2 - center.x);
		}
		if(container.getComponents().contains(north)){
			north.setBounds(0, 0, container.sizeX, container.sizeY / 10 * 2);
			center.setY(container.sizeY / 10 * 2);
			center.setSizeY(container.sizeY -  container.sizeY / 10 * 2);
			west.setY(north.sizeY);
			east.setY(north.sizeY);
			west.setSizeY(container.sizeY - north.sizeY);
			east.setSizeY(container.sizeY  - north.sizeY);
		}
		
		if(container.getComponents().contains(south)){
			south.setBounds(0, container.sizeY -  container.sizeY / 10 * 2, container.sizeX, container.sizeY / 10 * 2);
			center.setSizeY(container.sizeY -  south.sizeY - center.y);
			west.setSizeY(container.sizeY -  south.sizeY - west.y);
			east.setSizeY(container.sizeY -  south.sizeY - east.y);
		}
	}
	
}
