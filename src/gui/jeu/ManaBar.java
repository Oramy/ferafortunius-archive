package gui.jeu;

import gui.Container;
import gui.ProgressBar;

import org.newdawn.slick.GameContainer;

import ObjetMap.Entity;


public class ManaBar extends ProgressBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5392142951585527200L;
	Entity o;
	public ManaBar(Entity o, Container parent) {
		super(o.getMp() + "/"+ o.getMaxMp(),o.getMaxMp(), o.getMp(), parent);
		this.o = o;
		
	}

	public ManaBar(Entity o, int sizeX, Container parent) {
		super(o.getMp() + "/"+ o.getMaxMp(),o.getMaxMp(), o.getMp(), sizeX, parent);
		this.o = o;
		
	}
	public void update(GameContainer gc, int x, int y){
		value = o.getMp();
		title = o.getMp() + "/"+ o.getMaxMp();
	}
}
