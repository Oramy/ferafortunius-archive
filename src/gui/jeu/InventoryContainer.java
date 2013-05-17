package gui.jeu;

import gui.Container;
import gui.PImage;

import org.newdawn.slick.GameContainer;

import Items.CirkulomaVerre;
import Items.Inventory;


public class InventoryContainer extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4000459908656599844L;
	private Inventory inventaire;
	private int itemNumber;
	public InventoryContainer(int x, int y, int sizeX, int sizeY, Inventory inventaire,	Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.inventaire = inventaire;
		background = new PImage("alpha.png");
		itemNumber = 0;
		addInventory();
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(this.inventaire.getContents().size() != itemNumber){
			this.components.clear();
			addInventory();
		}
	}
	private void addInventory() {
		itemNumber = 0;
		for(int i = 0; i < this.inventaire.getContents().size(); i++){
			boolean added = false;
			for(int j = 0; j < this.getComponents().size(); j++){
				if(this.getComponents().get(j) instanceof ItemComponent){
					if(!(((ItemComponent)this.getComponents().get(j)).getCibles().get(0) instanceof CirkulomaVerre)){
					
						if(((ItemComponent)this.getComponents().get(j)).getCibles().get(0).getName().equals(this.inventaire.getContents().get(i).getName())){
							if(((ItemComponent)this.getComponents().get(j)).getCibles().size() < ((ItemComponent)this.getComponents().get(j)).getCibles().get(0).getMaxstackNumber())
							{
								((ItemComponent)this.getComponents().get(j)).getCibles().add(this.inventaire.getContents().get(i));
								added = true;
								itemNumber++;
							}
						}
					}
					else{
						added = true;
						itemNumber++;
					}
				}
			}
			if(!added){
				
				itemNumber++;
				if(!(this.inventaire.getContents().get(i) instanceof CirkulomaVerre)){
					
					ItemComponent item = new ItemComponent(this.inventaire.getContents().get(i), this);
					item.setX((int) (((this.getComponents().size()) % (getSizeX() / item.getSizeX()) ) * item.getSizeX()) + 1);
					item.setY((int) ((this.getComponents().size() / (getSizeX() / item.getSizeX())) * item.getSizeY()));
					this.addComponent(item);
					if(item.getY() > this.getSizeY())
					{
						this.setSizeY(item.getY() + item.getSizeY()  * 3);
					}
				
				}
			}
		}
	}
}
