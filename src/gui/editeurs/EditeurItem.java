package gui.editeurs;

import gui.Container;
import gui.GridLayout;

import org.newdawn.slick.GameContainer;

import Items.Arme;
import Items.BasicItem;
import Items.EquipmentItem;
import Items.Item;
import Items.ItemType;
import Items.Utility;


public class EditeurItem extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EditeurItemBasic general;
	private EditeurItemBasic special;
	private Item editItem;
	public EditeurItem(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		
		this.setActualLayout(new GridLayout(2,1));
		
		//Initialisation de l'objet
		editItem = new BasicItem(null);
		editItem.setDescriptionPath("");
		//Ajout de l'éditeur basique.
		general = new EditeurItemGeneral(editItem, 0,0, sizeX / 2,sizeY, this);
		this.addComponent(general);
		
		//Ajout de l'éditeur spécialisé.
		updateEditors();
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		updateEditors();
		
	}
	public Item getEditItem() {
		return editItem;
	}
	public void setEditItem(Item editItem) {
		this.editItem = editItem;
		general.setEditItem(editItem);
	}
	public void updateEditors(){
		if(this.components.contains(special))
			this.components.remove(special);
		if(editItem.getType() == ItemType.Basic && !(special instanceof EmptyEditeurItem)){
			special = new EmptyEditeurItem(editItem, 0,0,sizeX / 2,sizeY, this);
		}
		else if(editItem.getType() == ItemType.Equipment && !(special instanceof EditeurEquipment)){
			
			
			//Changement du type de l'item : Equipment
			
			editItem = new Arme(null);
			general.setEditItem(editItem);
			special = new EditeurEquipment((EquipmentItem)editItem, 0,0,sizeX / 2,sizeY, this);
		}
		else if(editItem.getType() == ItemType.Utility && !(special instanceof EditeurUtility)){
			
			//Changement du type de l'item
			editItem = new Utility(null);
			general.setEditItem(editItem);
			special = new EditeurUtility((Utility)editItem, 0,0,sizeX / 2,sizeY, this);
		}
		this.addComponent(special);
	}
	
}
