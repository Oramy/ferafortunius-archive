package gui.jeu;

import gui.Container;
import gui.ControllersManager;
import gui.FComponent;
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
	
	private int x, y;
	public InventoryContainer(int x, int y, int sizeX, int sizeY, Inventory inventaire,	Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.inventaire = inventaire;
		background = new PImage("alpha.png");
		itemNumber = 0;
		addInventory();
		this.x = -1;
		this.y = -1;
		
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(this.inventaire.getContents().size() != itemNumber){
			reinitSelection();
			this.components.clear();
			addInventory();
			hoverSelection();
		}
	}
	public void reinitSelection(){
		if(inventaire.getContents().size() > 0){
			if(((ItemComponent)getComponent(this.x * getComponents().get(0).getSizeX() + 30, this.y * getComponents().get(0).getSizeY() + 30)) != null)
				((ItemComponent)getComponent(this.x * getComponents().get(0).getSizeX() + 30, this.y * getComponents().get(0).getSizeY() + 30)).normal();
		}
	}
	public void hoverSelection(){
		if(inventaire.getContents().size() > 0){
			if(((ItemComponent)getComponent(this.x * getComponents().get(0).getSizeX() + 30, this.y * getComponents().get(0).getSizeY() + 30)) != null)
				((ItemComponent)getComponent(this.x * getComponents().get(0).getSizeX() + 30, this.y * getComponents().get(0).getSizeY() + 30)).hover();
		}
	}
	public void setX(int x){
		if(getComponents() != null){
			if(this.x != x)
				reinitSelection();
			
			this.x = x;
			
			if(this.x < 0)
				this.x++;
			if(this.x > 3)
				this.x--;
			while(this.y * 4 + this.x + 1> getComponents().size())
				this.x--;
			
			hoverSelection();
			
		}

	}
	public void setY(int y){
		if(getComponents() != null){
			if(this.y != y)
				reinitSelection();
			this.y = y;
			
			if(this.y < 0)
				this.y = 0;
			if(this.y > (getComponents().size()) / 4)
				this.y = getComponents().size() / 4;
			
			while(this.y * 4 + x + 1> getComponents().size())
				this.x--;
			hoverSelection();
		}
	}
	public void updateController(GameContainer gc){
		if(inventaire.getContents().size() > 0){
				
			if(x == -1)
				setX(0);
			if(y == -1)
				setY(0);
			if(((ItemComponent)getComponent(x * getComponents().get(0).getSizeX() + 30, y * getComponents().get(0).getSizeY() + 30)) != null)
				((ItemComponent)getComponent(x * getComponents().get(0).getSizeX() + 30, y * getComponents().get(0).getSizeY() + 30)).hover();
			
			if(ControllersManager.getFirstController().isButton2Released()){
				ControllersManager.getFirstController().setControllerContainer(((Jeu)getRacine()).getMenuJeu());
				if(((ItemComponent)getComponent(x * getComponents().get(0).getSizeX() + 30, y * getComponents().get(0).getSizeY() + 30)) != null)
					((ItemComponent)getComponent(x * getComponents().get(0).getSizeX() + 30, y * getComponents().get(0).getSizeY() + 30)).normal();
				
				((Jeu)getRacine()).inverseInventory();
			}
			if(ControllersManager.getFirstController().isButton1Released()){
				((ItemComponent)getComponent(x * getComponents().get(0).getSizeX() + 30, this.y * getComponents().get(0).getSizeY() + 30)).use();
			}
			if(ControllersManager.getFirstController().isButton3Released()){
				((ItemComponent)getComponent(x * getComponents().get(0).getSizeX() + 30, this.y * getComponents().get(0).getSizeY() + 30)).equip();
			}
				
			if(ControllersManager.getFirstController().isRightReleased()){
				setX(x + 1);
			}
			if(ControllersManager.getFirstController().isLeftReleased()){
				setX(x - 1);
			}
			if(ControllersManager.getFirstController().isUpReleased()){
				setY(y - 1);
			}
			if(ControllersManager.getFirstController().isDownReleased()){
				setY(y + 1);
			}
		}
	}
	public FComponent getComponent(int x, int y){
		FComponent toRet = null;
		for(FComponent comp : getComponents()){
			if(x < comp.getX() + comp.getSizeX() && x > comp.getX()
					&& y < comp.getY() + comp.getSizeY() && y > comp.getY())
				toRet = comp;
		}
		return toRet;
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
