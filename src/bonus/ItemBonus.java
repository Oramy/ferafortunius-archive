package bonus;

import gui.FontRessources;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Items.Item;
import Level.Camera;
import ObjetMap.Entity;

public class ItemBonus extends SimpleBonus{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Item item;
	
	public ItemBonus(Item i, int amount, Entity o) {
		super(amount, o);
		item = i;
		setText("Item : "+item.getName());
		color = Color.white;
		
	}
	public void paintComponent(Graphics g, Camera cam){
		super.paintComponent(g, cam);
		g.setFont(FontRessources.getFonts().gametext);
		
		g.scale(cam.getZoom(), cam.getZoom());
			g.setColor(color);
			g.drawString(getText(), - g.getFont().getWidth(getText()) / 2, y - (getCible().getSizeX() + getCible().getSizeY()) / 2 - getCible().getSizeZ() );
		g.scale(1 / (cam.getZoom()), 1 / (cam.getZoom()));
		
		g.setFont(FontRessources.getFonts().text);
	}
	@Override
	public void effect() {
		item.setOwner(getCible());
		for(int i = 0, c = amount; i < c; i++)
		getCible().getInventaire().addContent(item.clone());
	}
}
