package gui.jeu;

import gui.Container;
import gui.PImage;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;

import ObjetMap.Entity;
import bonus.Buff;

public class BuffBar extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entity cible;
	public BuffBar(Entity entite, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, 50, parent);
		cible = entite;
		this.background = new PImage("alpha.png");
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		ArrayList<Buff> buffs = cible.getBuffs();
		if(this.getComponents().size() != buffs.size()){
			this.getComponents().clear();
			this.sizeX = buffs.size() * 34;
			for(Buff buff :  buffs){
				BuffComponent comp = new BuffComponent(buff, this);
				comp.setX(buffs.indexOf(buff) * 34);
				this.addComponent(comp);
			}
		}
	}
}
