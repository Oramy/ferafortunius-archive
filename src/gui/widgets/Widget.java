package gui.widgets;

import gui.Button;
import gui.Container;
import gui.ContainerWithBords;

import org.newdawn.slick.GameContainer;


public abstract class Widget extends ContainerWithBords{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String nom;
	protected boolean resizablex;

	protected boolean resizabley;
	protected boolean draggable;
	protected Button resizable;
	public Widget(Container parent) {
		super(0,0,1,1, parent);
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
	}
}
