package gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class ScrollBar extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = -860520415651633186L;

	public static final PImage bardedef  = new PImage("GUI/scrollbar.png"), curseur = new PImage("GUI/cursor.png"), scrollsqr = new PImage("GUI/scrollBarSquare.png");
	protected float posX;
	protected float posY;
	protected static final float defaultSizeX = 17, defaultSizeY = 212;
	protected static final float beginSizeX = 17, beginSizeY = 20;
	protected static final float middleSizeX = 17, middleSizeY = 1;
	protected static final float endSizeX = 17, endSizeY = 20;
	protected float value;
	private float valueMax;
	protected Container container;
	protected HScrollBar hscroll;
	protected int lastCursorPos, lastCursorSize;
	public ScrollBar(int posX, int posY, int sizeX, int sizeY, float valueMax, float valueYMax, Container parent){
		super(posX,posY,sizeX,sizeY, parent);
		this.value = 0;
		this.setValueMax(valueMax);
		this.alwaysUpdateOnClick = true;
		if(valueMax < 0)
		{
			valueMax = 0;
		}
		this.posX = posX;
		this.posY = posY;
		this.sizeY = sizeY;
		this.sizeX = sizeX;
		container = new Container(0,0,(int)this.sizeX, (int)this.sizeY, this);
		hscroll = new HScrollBar(0,0, 1,  sizeX, sizeY,valueYMax, this);
	}
	public void draw(Graphics g){
		//Affichage du haut
		setValueMax(container.getSizeY() - sizeY - 60);
		g.translate(posX, posY);
			if(container.getX() != - hscroll.value)
				container.setX((int) -hscroll.value);
			if(container.getY() != - value)
				container.setY((int) -value);
			if(container != null)
			{
				getContainer().draw(g);
			}
			hscroll.drawBegin(g);
			hscroll.draw(g);
			hscroll.drawEnd(g);
			if(getValueMax() > sizeY / 2){
				g.translate(sizeX - beginSizeX, 0);
							//Affichage de la ScrollBar
								//Affichage du haut
								bardedef.getImg().draw(0,
										0,
										beginSizeX,
										beginSizeY,
										0, 0, bardedef.getImg().getWidth(), 60);
								//Affichage du milieu
								bardedef.getImg().draw(0,
										beginSizeY,
										middleSizeX,
										sizeY- beginSizeY - 17,
										0, 60, bardedef.getImg().getWidth(), 80);
								//Affichage du bas
								bardedef.getImg().draw(0,
										sizeY - endSizeY - 17,
										endSizeX,
										sizeY - 17,
										0, bardedef.getImg().getHeight() - 60,bardedef.getImg().getWidth(), bardedef.getImg().getHeight());
								scrollsqr.getImg().draw(0, 
										sizeY - 17, 
										17, 
										17);
								//Calcul des coefs
								
								//Taille de la barre
								int sizeBar = getSizeY() - 54;
								//Taille du container
								int tailleCont = this.getContainer().getSizeY();
								//Coef de taille de la barre. 
								float coef = (float)tailleCont / (float)sizeBar;
								
								if(coef < 1)
									coef = 1;
								//Taille du curseur
								int sizeCursor = (int) ((float)sizeBar / coef);
								
								//c
								float coefPos = (float)getValueMax() / ((float)sizeBar - sizeCursor / 2);
								int posCursor = (int) ((int) ((float)(getValue()) / coefPos)) + 13;
								if(posCursor + sizeCursor > sizeY - 34)
									posCursor = sizeY - 34 - sizeCursor;
								lastCursorPos = posCursor;
								lastCursorSize = sizeCursor;
								//Affichage du curseur
								//Affichage du haut
								curseur.getImg().draw(0,
										posCursor, 17,
										posCursor + 17,
										0, 0, 54, 33);
								//Affichage du milieu
								curseur.getImg().draw(0,
										posCursor + 17, 
										17,
										posCursor + sizeCursor - 17,
										0, 34, 54, 35);
								//Affichage du bas
								curseur.getImg().draw(0,
										posCursor + sizeCursor - 17,
										17,
										posCursor + sizeCursor, 0, 149, 54, 182);
					g.translate(- sizeX + beginSizeX, 0);
			}
		g.translate(- posX, - posY );
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(gc.getInput().getMouseX() >= this.getXOnScreen() +  sizeX - beginSizeX
			&& gc.getInput().getMouseX() <= this.getXOnScreen() + sizeX
			&& gc.getInput().getMouseY() >= this.getYOnScreen()
			&& gc.getInput().getMouseY() <= this.getYOnScreen() + beginSizeY && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			this.up();
		}
		else if(gc.getInput().getMouseX() >= this.getXOnScreen()  + sizeX - beginSizeX
				&& gc.getInput().getMouseX() <= this.getXOnScreen() + sizeX
				&& gc.getInput().getMouseY() >= this.getYOnScreen() + beginSizeY
				&& gc.getInput().getMouseY() <= this.getYOnScreen() + sizeY - beginSizeY && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			if(gc.getInput().getMouseY() - this.getYOnScreen() > lastCursorPos + lastCursorSize / 2){
				this.down((int) ((gc.getInput().getMouseY() - this.getYOnScreen()) - (lastCursorPos + lastCursorSize / 2)));
			}
			if(gc.getInput().getMouseY() - this.getYOnScreen() < lastCursorPos  + lastCursorSize / 2 ){
				this.up((int) ((lastCursorPos + lastCursorSize / 2) - (gc.getInput().getMouseY() - this.getYOnScreen())));
			}
		}
		else if(gc.getInput().getMouseX() >= this.getXOnScreen()  - beginSizeX
				&& gc.getInput().getMouseX() <= this.getXOnScreen() + sizeX
				&& gc.getInput().getMouseY() >= this.getYOnScreen() + (sizeY - beginSizeY * 2) 
				&& gc.getInput().getMouseY() <=this.getYOnScreen()  + (sizeY - beginSizeY) && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
				this.down();
		}
		//hscroll.update(gc, x, y);
		if(container != null)
			container.update(gc, this.getX() + x, this.getY() + y);
	}
	public void updateSize(){
		super.updateSize();
		container.setSizeX(sizeX);
		this.setValueMax(container.sizeY - sizeY);
		container.updateSize();
	}
	/**
	 * @return the posX
	 */
	public float getPosX() {
		return posX;
	}
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}
	/**
	 * @return the posY
	 */
	public float getPosY() {
		return posY;
	}
	/**
	 * @param posY the posY to set
	 */
	public void setPosY(float posY) {
		this.posY = posY;
	}
	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		if(value >= getValueMax()){
			value = getValueMax();
		}
		else if(value < 0){
			value = 0;
		}
		this.value = value;
	}
	public void down(){
		setValue(getValue() + getValueMax() / 100 + 5);
	}
	private void down(int i) {
		setValue(getValue() + i);
	}
	public void up(){
		setValue(getValue() - getValueMax() / 100 - 5);
	}
	private void up(int i) {
		setValue(getValue() - i);
	}
	public void right(){
		hscroll.setValue(hscroll.getValue() + hscroll.valueMax / 100 + 5);
	}
	public void left(){
		hscroll.setValue(hscroll.getValue() - hscroll.valueMax / 100 - 5);
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
	/**
	 * @return the valueMax
	 */
	public float getValueMax() {
		return this.getContainer().getSizeY() - sizeY;
	}
	/**
	 * @param valueMax the valueMax to set
	 */
	public void setValueMax(float valueMax) {
		this.valueMax = valueMax;
	}
	
}
