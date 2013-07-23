package gui;


public class GridLayout extends Layout {
	private int sizeX, sizeY;
	private int hgap;
	private int vgap;
	
	private int choice;
	public GridLayout(int width, int height){
		sizeX = width;
		sizeY = height;
		choice = -1;
		hgap = 0;
		vgap = 0;
	}
	public void addComponent(Container container, FComponent c) {
		this.container = container;
		container.getComponents().add(c);
		updateLayout();
	}
	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * @param sizeX the sizeX to set
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	/**
	 * @return the hgap
	 */
	public int getHgap() {
		return hgap;
	}
	/**
	 * @param hgap the hgap to set
	 */
	public void setHgap(int hgap) {
		this.hgap = hgap;
	}
	/**
	 * @return the vgap
	 */
	public int getVgap() {
		return vgap;
	}
	/**
	 * @param vgap the vgap to set
	 */
	public void setVgap(int vgap) {
		this.vgap = vgap;
	}
	@Override
	public void updateLayout() {
	
		int tempPosX = 0;
		int tempPosY = 0;
		if(container != null){
			for(FComponent c : container.getComponents()){
				if(tempPosX != sizeX && tempPosY != sizeY){
					if(c instanceof Chooser)
					{
						if(!((Chooser)c).isShowed()){
							c.setBounds(tempPosX * (container.getWidth() / sizeX) + hgap / 2, 
									tempPosY * (container.getHeight() / sizeY) + vgap / 2, 
									container.getWidth() / sizeX - hgap, 
									container.getHeight() / sizeY - vgap);
							tempPosX++;
							if(tempPosX == sizeX){
								tempPosX = 0;
								tempPosY++;
							}
						}
					}
					else{
						c.setSize(container.getWidth() / sizeX - hgap, 
								container.getHeight() / sizeY - vgap);
						if(sizeX > 1){
							c.setX(tempPosX * (container.getWidth() / sizeX) + hgap / 2);
							c.setSizeX(container.getWidth() / sizeX - hgap);
						}
						if(sizeY > 1){
							c.setY(tempPosY * (container.getHeight() / sizeY) + vgap / 2) ;
							c.setSizeY(container.getHeight() / sizeY - vgap);
						}
						tempPosX++;
						if(tempPosX == sizeX){
							tempPosX = 0;
							tempPosY++;
						}
					}
				}
			}
		}
	}
	public void updateChoice(){
		if(choice > -1){
			if(container.getComponents().get(choice) instanceof Button){
				Button b = (Button) container.getComponents().get(choice);
				b.hover();
			}
		}
	}
	public void actionChoice(){
		if(choice > -1){
			if(container.getComponents().get(choice) instanceof Button){
				Button b = (Button) container.getComponents().get(choice);
				b.clickPressed();
				b.clickReleased();
			}
		}
	}
	public FComponent getObjectChoice(){
		 return container.getComponents().get(choice);
	}
	public int getChoice() {
		return choice;
	}
	public void increaseChoice() {
		if(this.choice > -1){
			boolean disabled = true;
			while(disabled){
				setChoice(choice + 1);
				if(container.getComponents().get(choice) instanceof Button){
					Button b = (Button) container.getComponents().get(choice);
					if(b.isEnable())
						disabled = false;
				}
			}
		}
	}
	public void decreaseChoice() {
		if(this.choice > -1){
			boolean disabled = true;
			while(disabled){
				setChoice(choice - 1);
				if(container.getComponents().get(choice) instanceof Button){
					Button b = (Button) container.getComponents().get(choice);
					if(b.isEnable())
						disabled = false;
				}
			}
		}
	}
	public void setChoice(int choice) {
		if(this.choice > -1){
			if(container.getComponents().get(this.choice) instanceof Button){
				Button b = (Button) container.getComponents().get(this.choice);
				b.normal();
				b.state = ComponentState.Normal;
			}
		}
		this.choice = choice;
		if(this.choice < 0)
			this.choice = 0;
		if(container != null){
			if(this.choice == container.getComponents().size())
				this.choice--;
		}
	}
}
