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
	
	private Buff buff;
	public BuffComponent(Buff buff, Container parent){
		super(parent);
		sizeX = 34;
		sizeY = 34;
		this.buff = buff;
	}
	@Override
	public void draw(Graphics g){
		g.translate(x, y);
		PImage img = BuffRessources.getOneBuffRessource(buff.getBuffImage());
		img.getImg().draw(0, 0, 34, 34);
		
		g.setColor(new Color(0, 0, 0, 120));
		float coef = 1f - ((float)buff.getRestingTime() / (float)buff.getLength());
		float angle = coef * 360;
		g.fillArc(-17, -17, 68, 68, -90, angle - 90);
		
		g.setColor(Color.red);
		if(buff.getRestingTime() < 60)
			g.drawString(buff.getRestingTime() + "s", 34 - g.getFont().getWidth(buff.getRestingTime() + "s"), 18);
		else
			g.drawString(buff.getRestingTime() / 60 + "m",  34 - g.getFont().getWidth(buff.getRestingTime() / 60 + "m"), 18);
		
		g.setFont(FontRessources.getFonts().text);
		
		g.translate(-x, -y);
	}
	@Override
	public void updateSize() {
		
	}
}
