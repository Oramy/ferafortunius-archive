package gui.jeu;

import gui.Container;
import gui.FComponent;
import gui.FontRessources;
import gui.PImage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bonus.buffs.Buff;
import bonus.buffs.BuffRessources;

public class BuffComponent extends FComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final PImage buffBorder = new PImage("GUI/Buff/buffBorder.png");
	private Buff buff;
	public BuffComponent(Buff buff, Container parent){
		super(parent);
		sizeX = 40;
		sizeY = 54;
		this.buff = buff;
	}
	@Override
	public void draw(Graphics g){
		g.translate(x, y);
		PImage img = BuffRessources.getOneBuffRessource(buff.getBuffImage());
		img.getImg().draw(0, 0);
		
		g.setColor(new Color(0, 0, 0, 120));
		float coef = 1f - ((float)buff.getRestingTime() / (float)buff.getLength());
		float angle = coef * 360;
		g.fillArc(-20, -20, 80, 80, -90, angle - 90);
		
		buffBorder.getImg().draw(0,0);
		g.setColor(Color.white);
		g.scale(0.9f, 0.9f);
		if(buff.getRestingTime() < 60)
			g.drawString(buff.getRestingTime() + "s", 34 - g.getFont().getWidth(buff.getRestingTime() + "s"), sizeY - g.getFont().getLineHeight() + 4);
		else
			g.drawString(buff.getRestingTime() / 60 + "m",  34 - g.getFont().getWidth(buff.getRestingTime() / 60 + "m"), sizeY - g.getFont().getLineHeight() + 4);
		g.scale(1/0.9f, 1/0.9f);
		g.setFont(FontRessources.getFonts().text);
		
		g.translate(-x, -y);
	}
	@Override
	public void updateSize() {
		
	}
}
