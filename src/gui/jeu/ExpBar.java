package gui.jeu;

import gui.Container;
import gui.PImage;
import gui.ProgressBar;

import org.newdawn.slick.GameContainer;

import ObjetMap.Entity;


public class ExpBar extends ProgressBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2015643214284829501L;
	private Entity o;
	
	public ExpBar(Entity o, Container parent) {
		super(o.getHp() + "/"+ o.getMaxHp(),o.getMaxHp(), o.getHp(), parent);
		actProgressBarImg = ProgressBar.squareProgressBar;
		this.o = o;
		
	}

	public ExpBar(Entity o, int sizeX, Container parent) {
		super("HP : " + o.getHp() + "/"+ o.getMaxHp(),o.getMaxHp(), o.getHp(), sizeX, parent);
		actProgressBarImg = ProgressBar.squareProgressBar;
		this.o = o;
		
	}
	public void update(GameContainer gc, int x, int y){
		value = o.getHp();
		title = o.getHp() + "/"+ o.getMaxHp();
	}
	
}
