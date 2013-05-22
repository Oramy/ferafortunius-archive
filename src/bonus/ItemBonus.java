package bonus;

import org.newdawn.slick.Color;

import Items.Item;
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
	@Override
	public void effect() {
		item.setOwner(getCible());
		for(int i = 0, c = amount; i < c; i++)
		getCible().getInventaire().addContent(item.clone());
	}
}
