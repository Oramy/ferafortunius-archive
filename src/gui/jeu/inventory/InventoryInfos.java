package gui.jeu.inventory;

import gui.Container;
import gui.FComponent;
import gui.PImage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Items.Inventory;


public class InventoryInfos extends FComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4386382492937651225L;
	private Inventory o;
	public PImage plume;
	public static final PImage cirkuloma = new PImage("GUI/Icon/cirkuloma.png");
	public InventoryInfos(Inventory o, Container parent) {
		super(parent);
		this.o = o;
		plume = Container.alpha;	
	}
	public void draw(Graphics g) {
		String text = o.getWeight() + "/" + o.getMaxWeight();
		if(this.getBounds().width - 50 < g.getFont().getWidth(text)){
			this.setBounds(this.getBounds().x, this.getBounds().y, (int) g.getFont().getWidth(text) + 50, this.getBounds().height);
		}
		if(this.getBounds().height < g.getFont().getHeight(text)){
			this.setBounds(this.getBounds().x, this.getBounds().y, this.getBounds().width, (int) g.getFont().getHeight(text));
		}
		long cirkulomaNumber = o.getCirkulomas();
		g.translate(getX(), getY());
			g.setColor(Color.white);
			plume.getImg().draw(10 + g.getFont().getWidth(text) + 5, 0, 20, 20);
			g.drawString(text, 10, 0);
			cirkuloma.getImg().draw(this.getWidth() - 25, this.getHeight() / 2 - 10, 20, 20);
			g.drawString(cirkulomaNumber + "", this.getWidth() - 30 - g.getFont().getWidth(cirkulomaNumber + ""), 2);
		g.translate(-getX(), -getY());
	}
	@Override
	public void updateSize() {
		
	}
}
