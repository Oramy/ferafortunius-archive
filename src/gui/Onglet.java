package gui;

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
		this.boutonAct = onglet;
		this.focus = false;
		alwaysUpdateOnClick = true;
		setSizeY(40);
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(parent instanceof OngletManager){
			if(((OngletManager)parent).getOngletActuel() != null){
				if(!((OngletManager)parent).getOngletActuel().equals(this) && this.getSizeY() >= ((OngletManager)parent).getOngletActuel().getSizeY()){
					this.setX(this.getX() + 10);
					setSizeX(getSizeX() - 20);
					this.setY(this.getY() + 10); 
					setSizeY(getSizeY() - 10);
				}
			}
		}
	}
	@Override
	public void clickPressed(){
		if(((OngletManager)parent).getOngletActuel() != null){
			if(!((OngletManager)parent).getOngletActuel().equals(this)){
				boutonAct = ongletActivate;
				state = ComponentState.Clicked;
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
			boutonAct = ongletActivate;
			state = ComponentState.Clicked;
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
		state = ComponentState.Hover;
	}
	@Override
	public void hover() {
		boutonAct = ongletHover;
		textColor = Color.black;
		
	}
	@Override
	public void normal() {
		boutonAct = onglet;
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
