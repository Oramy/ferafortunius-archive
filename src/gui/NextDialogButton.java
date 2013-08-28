package gui;

import gui.buttons.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;


public class NextDialogButton extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final PImage normal = new PImage("GUI/nextDialogButton.png");
	private static final PImage shadow = new PImage("GUI/nextDialogButtonShadow.png");
	private int yButton;
	private boolean up;
	protected long tempsPrec;
	protected long vitesse;
	public NextDialogButton(Container parent) {
		super("", parent);
		yButton = 10;
		setSizeY(31);
		setSizeX(20);
		vitesse = 80;
		enable = true;
		tempsPrec = System.currentTimeMillis();
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(System.currentTimeMillis() - vitesse > tempsPrec){
			tempsPrec = System.currentTimeMillis();
			if(yButton < 5)
				up = true;
			if(yButton > 11)
				up = false;
			if(up)
				yButton++;
			else
				yButton--;
			if(this.isInside(gc.getInput().getMouseX(), gc.getInput().getMouseY())){
				if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				yButton = 5;
			}
		}
		setBoutonAct(normal);
	}
	@Override
	public void normal(){
		setBoutonAct(normal);
	}
	@Override
	public void clickPressed(){
		setBoutonAct(normal);
		yButton = 2;
		setState(ComponentState.Clicked);
		
	}
	@Override
	public void clickReleased(){
		setBoutonAct(normal);
		setState(ComponentState.Hover);
		action();
		
	}
	public void draw(Graphics g) {
		g.translate(this.getBounds().x, this.getBounds().y);
			g.translate(0, 11);
				shadow.getImg().draw(0, 0, this.getSizeX(), this.getSizeY() -6, 0, 0, shadow.getImg().getWidth(), shadow.getImg().getHeight());
				g.translate(0, -yButton);
					getBoutonAct().getImg().draw(0, 0, this.getSizeX(), this.getSizeY() - 11, 0, 0, getBoutonAct().getImg().getWidth(), getBoutonAct().getImg().getHeight());
				g.translate(0, yButton);
			g.translate(0, -11);
		g.translate(-this.getBounds().x, -this.getBounds().y);	
		
	}
}
