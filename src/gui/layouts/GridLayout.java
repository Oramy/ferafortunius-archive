package gui.layouts;

import gui.Button;
import gui.Chooser;
import gui.ComponentState;
import gui.Container;
import gui.FComponent;


public class GridLayout extends Layout {
	private int sizeX, sizeY;
	private int hgap;
	private int vgap;
	
	private int choice;
	
	/**
	 * Builder
	 * @param width the width of the grid
	 * @param height the height of the grid
	 */
	public GridLayout(int width, int height){
		sizeX = width;
		sizeY = height;
		choice = -1;
		hgap = 0;
		vgap = 0;
	}
	public void addComponent(Container container, FComponent c) {
		this.setContainer(container);
		container.getComponents().add(c);
		updateLayout();
	}
	
	
	@Override
	public void updateLayout() {
	
		int tempPosX = 0;
		int tempPosY = 0;
		if(getContainer() != null){
			for(FComponent c : getContainer().getComponents()){
				if(tempPosX != sizeX && tempPosY != sizeY){
					if(c instanceof Chooser)
					{
						if(!((Chooser)c).isShowed()){
							c.setBounds(tempPosX * (getContainer().getWidth() / sizeX) + hgap / 2, 
									tempPosY * (getContainer().getHeight() / sizeY) + vgap / 2, 
									getContainer().getWidth() / sizeX - hgap, 
									getContainer().getHeight() / sizeY - vgap);
							tempPosX++;
							if(tempPosX == sizeX){
								tempPosX = 0;
								tempPosY++;
							}
						}
					}
					else{
						c.setSize(getContainer().getWidth() / sizeX - hgap, 
								getContainer().getHeight() / sizeY - vgap);
						if(sizeX > 1){
							c.setX(tempPosX * (getContainer().getWidth() / sizeX) + hgap / 2);
							c.setSizeX(getContainer().getWidth() / sizeX - hgap);
						}
						if(sizeY > 1){
							c.setY(tempPosY * (getContainer().getHeight() / sizeY) + vgap / 2) ;
							c.setSizeY(getContainer().getHeight() / sizeY - vgap);
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
	
	/**
	 * Update the controller's choice
	 */
	public void updateChoice(){
		if(choice > -1){
			if(getContainer().getComponents().get(choice) instanceof Button){
				Button b = (Button) getContainer().getComponents().get(choice);
				b.hover();
			}
		}
	}
	/**
	 * Launch the controller's choice action.
	 */
	public void actionChoice(){
		if(choice > -1){
			if(getContainer().getComponents().get(choice) instanceof Button){
				Button b = (Button) getContainer().getComponents().get(choice);
				b.clickReleased();
				b.setState(ComponentState.Normal);
				updateChoice();
			}
		}
	}
	
	/**
	 * 
	 * @return the controller's choice component
	 */
	public FComponent getObjectChoice(){
		 return getContainer().getComponents().get(choice);
	}
	/**
	 * 
	 * @return id of the controller's choice component
	 */
	public int getChoice() {
		return choice;
	}
	
	/**
	 * Increase the choice, and setting it to the next enabled component.
	 */
	public void increaseChoice() {
		if(this.choice > -1){
			boolean disabled = true;
			int choice = this.choice;
			while(disabled){
				choice = choice + 1;
				if(choice > getContainer().getComponents().size() - 1)
					choice = getContainer().getComponents().size() - 1;
				if(getContainer().getComponents().get(choice) instanceof Button){
					Button b = (Button) getContainer().getComponents().get(choice);
					if(b.isEnable())
						disabled = false;
				}
			}
			setChoice(choice);
		}
	}
	/**
	 * Decrease the choice, and setting it to the previous enabled component.
	 */
	public void decreaseChoice() {
		if(this.choice > -1){
			boolean disabled = true;
			int choice = this.choice;
			while(disabled){
				choice = choice - 1;
				if(choice < 0)
					choice = 0;
				if(getContainer().getComponents().get(choice) instanceof Button){
					Button b = (Button) getContainer().getComponents().get(choice);
					if(b.isEnable())
						disabled = false;
				}
			}
			setChoice(choice);
		}
	}
	/**
	 * Changes the choice and update it.
	 * @param choice the new choice id
	 */
	public void setChoice(int choice) {
		if(this.choice > -1){
			if(getContainer().getComponents().get(this.choice) instanceof Button){
				Button b = (Button) getContainer().getComponents().get(this.choice);
				b.normal();
				b.setState(ComponentState.Normal);
			}
		}
		this.choice = choice;
		if(this.choice < 0)
			this.choice = 0;
		if(getContainer() != null){
			if(this.choice == getContainer().getComponents().size())
				this.choice--;
		}
		updateChoice();
	}
	/**
	 * Reset the controller's choice.
	 */
	public void resetChoice() {
		setChoice(-1);
		((Button)getObjectChoice()).normal();
		choice = -1;
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
}
