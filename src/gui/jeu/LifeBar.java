package gui.jeu;

import gui.Container;
import gui.ProgressBar;

import org.newdawn.slick.GameContainer;

import ObjetMap.Entity;


public class LifeBar extends ProgressBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8437528975256358698L;
	Entity o;
	public LifeBar(Entity o, Container parent) {
		super(o.getHp() + "/"+ o.getMaxHp(),o.getMaxHp(), o.getHp(), parent);
		this.o = o;
		
	}

	public LifeBar(Entity o, int sizeX, Container parent) {
		super(o.getHp() + "/"+ o.getMaxHp(),o.getMaxHp(), o.getHp(), sizeX, parent);
		this.o = o;
		
	}
	public void update(GameContainer gc, int x, int y){
		value = o.getHp();
		valueMax = o.getMaxHp();
		title = value + "/"+ valueMax;
	}
}
