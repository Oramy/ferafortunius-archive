package gui.buttons;

import gui.ComponentState;
import gui.Container;
import gui.PImage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

public class Onglet extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final PImage ongletHover = new PImage("GUI/ongletHover.png");
	protected static final PImage ongletActivate = new PImage("GUI/ongletActive.png");
	protected static final PImage onglet = new PImage("GUI/onglet.png");
	protected Container container;
	public Onglet(String nom,Container parent) {
		super(nom, parent);
		this.setBoutonAct(onglet);
		this.focus = false;
		alwaysUpdateOnClick = true;
		setSizeY(40);
		
	}
	/**
	 * Updates the manager if the Onglet class is controlled by it.
	 * @param gc the game environment.
	 */
	public void updateManager(GameContainer gc){
		if(parent instanceof OngletManager){
			if(((OngletManager)parent).getOngletActuel() != null){
				if(!((OngletManager)parent).getOngletActuel().equals(this) && this.getSizeY() >= 50){
					this.setX(this.getX() + 10);
					setSizeX(getSizeX() - 20);
					this.setY(this.getY() + 10); 
					setSizeY(getSizeY() - 10);
				}
			}
		}
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		updateManager(gc);
	}
	@Override
	public void clickPressed(){
		if(((OngletManager)parent).getOngletActuel() != null){
			if(!((OngletManager)parent).getOngletActuel().equals(this)){
				setBoutonAct(ongletActivate);
				setState(ComponentState.Clicked);
				setX(getX() - 10);
				setSizeX(getSizeX() + 20);
				setY(getY() - 10);
				setSizeY(getSizeY() + 10);
				if(getX() < 0){
					setX(0);
				}
				if(getX() + getSizeX() > parent.getSizeX()){
					setSizeX(parent.getSizeX() - getX());
				}
				if(parent instanceof OngletManager){
					if(getzIndex() != parent.getzMax()){
						parent.setzMax(parent.getzMax() + 1);
						setzIndex(parent.getzMax());
					}
					((OngletManager)parent).setOngletActuel(this);
				}
			}
		}else{
			setBoutonAct(ongletActivate);
			setState(ComponentState.Clicked);
			setX(getX() - 10);
			setSizeX(getSizeX() + 20);
			setY(getY() - 10);
			setSizeY(getSizeY() + 10);
			if(getX() < 0){
				setX(0);
			}
			if(getX() + getSizeX() > parent.getSizeX()){
				setSizeX(parent.getSizeX() - getX());
			}
			if(parent instanceof OngletManager){
				if(getzIndex() != parent.getzMax()){
					parent.setzMax(parent.getzMax() + 1);
					setzIndex(parent.getzMax());
				}
				((OngletManager)parent).setOngletActuel(this);
			}
		}
	}
	@Override
	public void clickReleased(){
		setState(ComponentState.Hover);
	}
	@Override
	public void hover() {
		setBoutonAct(ongletHover);
		textColor = Color.black;
		
	}
	@Override
	public void normal() {
		setBoutonAct(onglet);
		textColor = Color.white;
	}
	/**
	 * @return the container
	 */
	public Container getContainer() {
		return container;
	}
	/**
	 * @param container the container to set
	 */
	public void setContainer(Container container) {
		this.container = container;
	}
}
