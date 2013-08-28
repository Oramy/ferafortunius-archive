package gui.jeu.inventory;

import gui.Container;
import gui.InternalFrame;
import gui.ScrollBar;
import ObjetMap.Entity;

public class InventaireFrame extends InternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3226190247736948979L;

	public InventaireFrame(int posX, int posY, int width, int height,
			String title, Container parent, Entity o) {
		super(posX, posY, width, height, title, parent);
		ScrollBar scroll = new ScrollBar(0,0, 200,200, 400, 0, this.getContainer());
		InventoryContainer inventaire = new InventoryContainer(0,0, getSizeX(), getSizeY(), o.getInventaire(), scroll);
		scroll = new ScrollBar(0,0, getSizeX(),getSizeY() - 25, inventaire.getHeight() - 200,0, this.getContainer());
		scroll.setContainer(inventaire);
		Container weightContainer = new Container(0, getSizeY() - 42, getSizeX(),25, this.getContainer());
		InventoryInfos inventoryInfos = new InventoryInfos(o.getInventaire(), weightContainer);
		inventoryInfos.setSizeX(sizeX);
		inventoryInfos.setSizeY(25);
		weightContainer.addComponent(inventoryInfos);
		weightContainer.setBackground(Container.backGroundUnbordsBlackHorizontal);
		this.getContainer().addComponent(scroll);
		this.getContainer().addComponent(weightContainer);
		
	}

}
