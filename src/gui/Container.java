package gui;

import gui.layouts.BorderLayout;
import gui.layouts.Layout;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Container extends FComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8883595685878534354L;
	protected ArrayList<FComponent> components;
	protected Layout actualLayout;
	protected PImage background;
	protected Affichage backgroundMode;
	
	public static final PImage alpha = new PImage("alpha.png");
	public static final PImage backGroundUnbords = new PImage("GUI/containerBackgroundwithoutBords.png");
	public static final PImage backGroundUnbordsBlackHorizontal = new PImage("GUI/containerBackgroundwithoutBordsBlackHorizontal.png");
	public static final PImage backGroundUnbordsBlack = new PImage("GUI/containerBackgroundwithoutBordsBlackHorizontal.png");
	public static final PImage normalBackground = new PImage("GUI/containerBackground.png");
	private int zMax;
	public Container(int x, int y, int sizeX, int sizeY, Container parent){
		super(parent);
		this.setBounds(x, y, sizeX, sizeY);
		setComponents(new ArrayList<FComponent>());
		setActualLayout(new Layout(){

			@Override
			public void updateLayout() {
				// TODO Auto-generated method stub
				
			}});
		backgroundMode = Affichage.Normal;
		setBackground(backGroundUnbords);
		setzMax(0);
		alwaysUpdateOnClick = true;
	}
	/**
	 * Ajoute un composant
	 * @param c le composant à ajouter
	 */
	public void addComponent(FComponent c){
		setzMax(getzMax() + 1);
		c.setzIndex(getzMax());
		getActualLayout().addComponent(this, c);
	}
	public void addComponent(String name,FComponent c){
		if(actualLayout instanceof BorderLayout){
			setzMax(getzMax() + 1);
			c.setzIndex(getzMax());
			((BorderLayout)getActualLayout()).addComponent(name, this, c);
		}
	}
	/**
	 * Update et trie ces propres composants
	 */
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		sortComponents();
		updateComponents(gc, x,y);
		if(ControllersManager.getFirstController().getControllerContainer() == this)
			updateController(gc);
	}
	
	public void updateController(GameContainer gc){
		
	}
	@Override
	public void updateSize() {
		updateComponentsSize();
		actualLayout.updateLayout();
	}
	private void updateComponentsSize() {
		for(FComponent c : components)
			c.updateSize();
	}
	/**
	 * Update seulement les composants
	 */
	public void updateComponents(GameContainer gc, int x, int y){
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		
		if(getComponents().size() != 0)
		{
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				for(int i = getComponents().size() - 1; i > -1; i--){
					
					if(getComponents().get(i).isClickable()){
						
						if(getComponents().get(i).isInside(mouseX, mouseY)){
							getComponents().get(i).update(gc, this.getX() + x, this.getY() + y);
							i = -1;	
						}
						else if(getComponents().get(i).alwaysUpdateOnClick){	
							getComponents().get(i).update(gc, this.getX() + x, this.getY() + y);
						}
					}
				}
			}
			else{
				
				for(int i = 0; i < getComponents().size(); i++){
					
					if(getComponents().get(i).updatable || getComponents().get(i).alwaysUpdateOnClick || getComponents().get(i) instanceof Container)
						getComponents().get(i).update(gc, this.getX() + x, this.getY() + y);
				}
			}
		}
	}
	/**
	 * Trie les composants en fonction de leur Z-index
	 */
	private void sortComponents() {
		for(int j = 1; j < getComponents().size(); j++){
			FComponent cle = getComponents().get(j);
			int i  = j-1;
			while(i > -1 && getComponents().get(i).getzIndex() > cle.getzIndex()){
				getComponents().set(i + 1, getComponents().get(i));
				i--;
			}
			getComponents().set(i + 1, cle);
		}
	}
	/**
	 * Dessine le background sur le container
	 * @param img le background à dessiner
	 */
	public void drawBackground(Image img){
		if(backgroundMode == Affichage.Normal){
			img.draw(0, 0, this.getWidth(), this.getHeight());
		}
		else if(backgroundMode == Affichage.ImageBrute){
			img.draw(sizeX / 2 - img.getWidth() / 2, 
					sizeY / 2 - img.getHeight() / 2);
		}
		else if(backgroundMode == Affichage.ImageScaled){
			float coefx = (float)(sizeX) / (float)img.getWidth();
			float coefy = (float)(sizeY) / (float)img.getHeight();
			int width = 0;
			int height = 0;
			if(coefx < coefy){
				width = (int) (coefx * img.getWidth());
				height = (int) (coefx * img.getHeight());
			}
			else{
				width = (int) (coefy * img.getWidth());
				height = (int) (coefy * img.getHeight());
			}
			img.draw(sizeX / 2 - width / 2, sizeY / 2 - height / 2, width, height);
		}
	}
	public void draw(Graphics g) {
		g.translate(this.getX(), this.getY());
		if(background != null)
			drawBackground(background.getImg());
		if(getComponents().size() != 0)
		{
			for(int i = 0; i < getComponents().size(); i++){
				
				getComponents().get(i).drawBegin(g);
				getComponents().get(i).draw(g);
				getComponents().get(i).drawEnd(g);
				
			}
				
		}
		g.translate(-this.getX(), -this.getY());
	}
	/**
	 * @return the background
	 */
	public PImage getBackground() {
		return background;
	}
	/**
	 * @param background the background to set
	 */
	public void setBackground(PImage background) {
		this.background = background;
	}
	/**
	 * @return the components
	 */
	public ArrayList<FComponent> getComponents() {
		return components;
	}
	/**
	 * @param components the components to set
	 */
	public void setComponents(ArrayList<FComponent> components) {
		this.components = components;
	}
	/**
	 * @return the actualLayout
	 */
	public Layout getActualLayout() {
		return actualLayout;
	}
	/**
	 * @param actualLayout the actualLayout to set
	 */
	public void setActualLayout(Layout actualLayout) {
		this.actualLayout = actualLayout;
		this.actualLayout.setContainer(this);
	}
	/**
	 * @return the zMax
	 */
	public int getzMax() {
		return zMax;
	}
	/**
	 * @param zMax the zMax to set
	 */
	public void setzMax(int zMax) {
		this.zMax = zMax;
	}
}
