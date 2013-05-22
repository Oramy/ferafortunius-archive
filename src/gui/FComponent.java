package gui;

import java.awt.Rectangle;
import java.io.Serializable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
public abstract class FComponent implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1952101183237573594L;
	protected boolean focus;
	/**
	 * le parent de l'objet
	 */
	protected Container parent;
	protected int x;
	protected int y;
	protected int sizeX;
	protected int sizeY;
	/**
	 * niveau de priorité d'affichage
	 */
	protected int zIndex;
	/**
	 * le dernier rectangle de restriction enregistré pour l'objet
	 */
	private Rectangle lastClip;
	protected boolean enable;
	protected boolean updatable;
	protected boolean clickable;
	protected boolean alwaysUpdateOnClick;
	/**
	 * Crée un FComponent avec un parent
	 * @param parent le parent du FComponent
	 */
	public FComponent(Container parent) {
		this.parent = parent;
		init();
	}
	/**
	 * Crée un FComponent sans parent.
	 */
	public FComponent() {
		this.parent = null;
		init();
		
	}
	/**
	 * Initialise l'objet
	 */
	private void init(){
		setzIndex(0);
		clickable = true;
		enable = true;
		alwaysUpdateOnClick = false;
		focus = false;
		x = 0;
		y = 0;
		sizeX = 0;
		sizeY = 0;
	}
	/**
	 * @return le clone de l'objet
	 */
	public FComponent clone(){
		FComponent clone = null;
		try {
			clone = (FComponent) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		return clone;
	}
	/**
	 * @return clickable
	 */
	public boolean isClickable(){
		return clickable;
	}
	/**
	 * Dessine le composant à l'écran.
	 * @param  g la zone graphique ou dessiner le composant
	 */
	public void draw(Graphics g) {
		
	}
	/**
	 * 
	 * @return la racine de l'objet
	 */
	public FComponent getRacine(){
		FComponent c = this;
		while(c.parent != null){
			c = c.parent;
		}
		return c;
	}
	/**
	 * 
	 * @param gc l'application
	 * @param x la position x
	 * @param y la position y
	 */
	public void update(GameContainer gc, int x, int y){
		updateSize();
	}
	/**
	 * @return the focus
	 */
	public boolean isFocus() {
		return focus;
	}
	/**
	 * @param focus the focus to set
	 */
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	/**
	 * 
	 * @param x la position x à tester
	 * @param y la position y à tester
	 * @return true si la position est dans l'objet
	 */
	public boolean isInside(int x, int y){
		int xonscreen =  (int) getXOnScreen();
		int yonscreen =  (int) getYOnScreen();
		if(x > xonscreen && x < xonscreen + getSizeX()
			&& y > yonscreen && y < yonscreen + getSizeY())
			return true;
		return false;
	}
	/**
	 * Permet de créer des restrictions d'affichage du composant
	 * @param g la zone graphique ou appliquer les restrictions
	 */
	public void drawBegin(Graphics g){
		updatable = false;
		if(g.getClip() == null){
			g.setClip(this.getX(), this.getY(), this.getSizeX(), this.getSizeY());
		}
		org.newdawn.slick.geom.Rectangle w = g.getClip();
		lastClip = new Rectangle((int)(w.getX()), (int)(w.getY()), (int)(w.getWidth()), (int)(w.getHeight()));
		boolean xA = false, xB = false, yA = false, yB = false;
		if(w.getX() <= this.getXOnScreen()){
			g.setClip(new org.newdawn.slick.geom.Rectangle(this.getXOnScreen(), w.getY(), w.getWidth() -  (this.getXOnScreen() - w.getX()), w.getHeight()));
			xA = true;
		}
		w = g.getClip();
		if(w.getX() + w.getWidth() >= this.getXOnScreen() + this.getSizeX()){
			g.setClip(new org.newdawn.slick.geom.Rectangle(w.getX(), w.getY(),w.getWidth() - (w.getX() + w.getWidth() - (this.getXOnScreen() + this.getSizeX())), w.getHeight()));
			xB = true;
		}
		w = g.getClip();
		if(w.getY() <= this.getYOnScreen()){
			g.setClip(new org.newdawn.slick.geom.Rectangle( w.getX(), this.getYOnScreen(), w.getWidth(), w.getHeight() - (this.getYOnScreen() - w.getY())));
			yA = true;
		}
		w = g.getClip();
		if(w.getY() + w.getHeight() >= this.getYOnScreen() + this.getSizeY()){
			g.setClip(new org.newdawn.slick.geom.Rectangle(w.getX(), w.getY(), w.getWidth(), w.getHeight() -(w.getY() + w.getHeight() - (this.getYOnScreen() + this.getSizeY()))));
			yB = true;
		}
		if((xA && xB) && (yA && yB))
			updatable = true;
	}
	/**
	 * 
	 * @return la position y sur l'écran
	 */
	public float getYOnScreen() {
		
		FComponent p = this;
		int y = 0;
		while(p != null){
			y += p.getY();
			p = p.parent;
		}
		return y;
	}
	/**
	 * 
	 * @return la position x sur l'écran
	 */
	public float getXOnScreen() {
		
		FComponent p = this;
		int x = 0;
		while(p != null){
			x += p.getX();
			p = p.parent;
		}
		return x;
	}
	/**
	 * Permet d'enlever les restrictions du composant
	 * @param g la zone graphique ou enlever les restrictions
	 */
	public void drawEnd(Graphics g){
		org.newdawn.slick.geom.Rectangle conversion = new org.newdawn.slick.geom.Rectangle(lastClip.x, lastClip.y, lastClip.width, lastClip.height);
		g.setClip(conversion);
	}
	public abstract void updateSize();
	/**
	 * @return the parent
	 */
	public Container getParent() {
		return parent;
	}
	/**
	 * 
	 * @return le rectangle qui contient le composant
	 */
	public Rectangle getBounds(){
		return new Rectangle(getX(),getY(),getSizeX(),getSizeY());
	}
	/**
	 * 
	 * @param r le rectangle qui contient le composant
	 */
	public void setBounds(Rectangle r){
		setX((int) r.getX());
		setY((int) r.getY());
		setSizeX((int) r.getWidth());
		setSizeY((int) r.getHeight());
	}
	/**
	 * 
	 * @param sizeX la taille x du composant
	 * @param sizeY la taille y du composant
	 */
	public void setSize(int sizeX, int sizeY){
		this.setSizeX(sizeX);
		this.setSizeY(sizeY);
	}
	/**
	 * 
	 * @param x la position x du composant
	 * @param y la position y du composant
	 * @param sizeX la taille x du composant
	 * @param sizeY la taille y du composant
	 */
	public void setBounds(int x, int y,int sizeX, int sizeY){
		this.setX(x);
		this.setY(y);
		this.setSizeX(sizeX);
		this.setSizeY(sizeY);
	}
	public int getWidth(){
		return getSizeX();
	}
	public void setWidth(int width){
		this.setSizeX(width);
	}
	public int getHeight(){
		return getSizeY();
	}
	public void setHeight(int height){
		this.setSizeY(height);
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Container parent) {
		this.parent = parent;
	}
	/**
	 * @return the zIndex
	 */
	public int getzIndex() {
		return zIndex;
	}
	/**
	 * @param zIndex the zIndex to set
	 */
	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}
	/**
	 * @return enable
	 */
	public boolean isEnable() {
		return enable;
	}
	/**
	 * @param zIndex the zIndex to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public void enable(){
		this.enable = true;
	}
	public void disable(){
		this.enable = false;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the sizeY
	 */
	public int getSizeY() {
		return sizeY;
	}
	/**
	 * @param sizeY the sizeY to set
	 */
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}
	/**
	 * @param sizeX the sizeX to set
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
}
