package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

public class InternalFrame extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5260024665717311592L;
	private String title;
	private PImage windowBar;
	private PImage closeButton;
	private PImage dockButton;
	private PImage borders;
	private boolean docked = false;
	private Color color;
	private Color closeColor, dockColor;
	private boolean hover;
	private int natX, natY, natSizeX, natSizeY;
	private boolean fullscreen = false;
	private boolean clicked = false;
	private int oldX, oldY;
	private boolean dragged = false;
	protected Container container;
	public InternalFrame(int posX, int posY, int width, int height, String title, Container parent) {
		super(posX, posY, width, height, parent);
		this.title = title;
		init();
		
	}
	public InternalFrame(int posX, int posY, String title, Container parent) {
		super(posX, posY, 0,0, parent);
		this.title = title;
		init();
		
	}
	public InternalFrame(String title, Container parent) {
		super(0, 0, 0, 0, parent);
		this.title = title;
		init();
		
		
	}
	public void update(GameContainer gc, int x, int y){
		if(!docked){
			container.update(gc, this.getX() + x, this.getY() + y);
		}		
		int mx = gc.getInput().getMouseX() - x;
		int my = gc.getInput().getMouseY() - y;
		if(mx >= this.getX()
				&& mx <= this.getX()  + getSizeX()
				&& my >= this.getY() 
				&& my <= this.getY()  + getSizeY()){
			if(!hover)
				hover = true;
			color = new Color(0,0,0,10);
			if(mx >= this.getX() + this.getWidth() - 40 && mx <= this.getX() + this.getWidth() - 40 + closeButton.getImg().getWidth()
					&& my >= this.getY() && my <= this.getY() + windowBar.getImg().getHeight()){
				closeButton = new PImage("GUI/closeButton.png");
				Image i = closeButton.getImg().getScaledCopy(0.24f);
				SpriteSheet s = new SpriteSheet(i, i.getWidth(), i.getHeight());
				closeButton.setImg(s);
				if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked){
					clicked = true;
					this.parent.getComponents().remove(this);
				}
			}
			else{
				closeButton = new PImage("GUI/frameButtonsHover.png");
				Image i = closeButton.getImg().getScaledCopy(0.24f);
				SpriteSheet s = new SpriteSheet(i, i.getWidth(), i.getHeight());
				closeButton.setImg(s);
			}
			if(mx >= this.getX() + this.getWidth() - 40 - closeButton.getImg().getWidth()&& mx <= this.getX() + this.getWidth() - 40
					&& my >= this.getY() && my <= this.getY() + windowBar.getImg().getHeight()){
				dockButton = new PImage("GUI/dockButton.png");
				Image i = dockButton.getImg().getScaledCopy(0.24f);
				SpriteSheet s = new SpriteSheet(i, i.getWidth(), i.getHeight());
				dockButton.setImg(s);
				if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked){
					clicked = true;
					if(fullscreen){
						fullscreen = false;
						this.setX(natX);
						this.setY(natY);
						setSizeX(natSizeX);
						setSizeY(natSizeY);
					}
					if(docked){
						docked = false;
						this.setX(natX);
						this.setY(natY);
						setSizeX(natSizeX);
						setSizeY(natSizeY);
					}
					else if(!docked){
						docked = true;
						natX = this.getX();
						natY = this.getY();
						natSizeX = getSizeX();
						natSizeY = getSizeY();
						setSizeY(windowBar.getImg().getHeight());
					}
				}
			}
			else{
				dockButton = new PImage("GUI/frameButtonsHover.png");
				Image i = dockButton.getImg().getScaledCopy(0.24f);
				SpriteSheet s = new SpriteSheet(i, i.getWidth(), i.getHeight());
				dockButton.setImg(s);
			}
			if(!(mx >= this.getX() + this.getWidth() - 40 - closeButton.getImg().getWidth()&& mx <= this.getX() + this.getWidth() - 40)
					&& !(mx >= this.getX() + this.getWidth() - 40 && mx <= this.getX() + this.getWidth() - 40 + closeButton.getImg().getWidth())
					&& !(mx >= this.getX() + this.getWidth() - 40 - closeButton.getImg().getWidth() * 2&& mx <=  this.getX() + this.getWidth() - 40 - closeButton.getImg().getWidth())
					&& my >= this.getY() && my <= this.getY() + windowBar.getImg().getHeight()){
				if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked){
					clicked = true;
					dragged = true;
					oldX = Mouse.getX();
					oldY = Mouse.getY();
				}
				
			}
			
		}
		else if(hover){
			hover = false;
			color = new Color(0,0,0,0);
		}
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked && dragged){
			if(getzIndex() != parent.getzMax()){
				parent.setzMax(parent.getzMax() + 1);
				setzIndex(parent.getzMax());
			}
			this.setX(this.getX() + Mouse.getX() - oldX);
			this.setY(this.getY() - Mouse.getY() + oldY);
			natX = this.getX();
			natY = this.getY();
			oldX = Mouse.getX();
			oldY = Mouse.getY();
		}
		if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked){
			clicked = false;
			dragged = false;
		}
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked){
			clicked = true;
		}
		if(this.getX() <= 0){
			this.setX(0);
		}
		if(this.getSizeX() >= this.parent.getSizeX()){
			this.setSizeX(this.parent.getSizeX());
		}
		if(this.getX() + this.getSizeX() >= this.parent.getSizeX()){
			this.setX(this.parent.getSizeX() - this.getSizeX());
		}
		if(this.getY() <= 0){
			this.setY(0);
		}
		if(this.getSizeY() >= this.parent.getSizeY()){
			this.setSizeY(this.parent.getSizeY());
		}
		if(this.getY() + this.getSizeY() >= this.parent.getSizeY()){
			this.setY(this.parent.getSizeY() - this.getSizeY());
		}
	}
	public void init(){
		hover = false;
		color = new Color(0,0,0,0);
		dockColor = new Color(0,0,0,0);
		closeColor = new Color(0,0,0,0);
		docked = false;
		windowBar = new PImage("GUI/windowBar.png");
		Image i = windowBar.getImg().getScaledCopy(0.33f);
		SpriteSheet s = new SpriteSheet(i, i.getWidth(), i.getHeight());
		windowBar.setImg(s);
		closeButton = new PImage("GUI/frameButtonsHover.png");
		i = closeButton.getImg().getScaledCopy(0.24f);
		s = new SpriteSheet(i, i.getWidth(), i.getHeight());
		closeButton.setImg(s);
		dockButton = new PImage("GUI/frameButtonsHover.png");
		i = dockButton.getImg().getScaledCopy(0.24f);
		s = new SpriteSheet(i, i.getWidth(), i.getHeight());
		dockButton.setImg(s);
		container = new Container(0,20, this.getBounds().width, this.getBounds().height - 20, this);
		this.addComponent(container);
		borders = new PImage("GUI/internalFrame.png");
	}
	public void draw(Graphics g) {
		g.translate(this.getX(), this.getY());
			if(background != null){
				getBackground().getImg().draw(0, 18, this.getWidth(), this.getHeight());
			}
			if(getComponents().size() != 0)
			{
	
				for(int i = 0; i < getComponents().size(); i++){
	
					getComponents().get(i).drawBegin(g);
					getComponents().get(i).draw(g);
					getComponents().get(i).drawEnd(g);
	
				}
	
			}
			g.setColor(Color.black);
			g.translate(0, -2);
				//Coté gauche
				borders.getImg().startUse();
				
				borders.getImg().drawEmbedded(-1, windowBar.getImg().getHeight() - 6, 3,  getSizeY() - 8, 
						17, 20, 21, 21);
				borders.getImg().drawEmbedded(-1,  getSizeY() - 8, 9,  getSizeY() + 2, 
						17, 70, 27, 80);
				//Bas
				borders.getImg().drawEmbedded(9,  getSizeY() - 2, getSizeX() - 7,  getSizeY() + 2, 
						27, 76, 28, 80);
				//Coté droit
				borders.getImg().drawEmbedded(getSizeX() - 3, windowBar.getImg().getHeight() - 6, getSizeX() + 1,  getSizeY() - 8, 
						17, 20, 21, 21);
				borders.getImg().drawEmbedded(getSizeX() - 7,  getSizeY() - 8, getSizeX() + 3,  getSizeY() + 2, 
						173, 70, 183, 80);
				
				borders.getImg().endUse();
			g.translate(0, 2);
			g.translate(-2, -3);
				windowBar.getImg().startUse();
				
				windowBar.getImg().setColor(0, color.r, color.g, color.b, color.a);
				windowBar.getImg().setColor(1, color.r, color.g, color.b, color.a);
				windowBar.getImg().setColor(2, color.r, color.g, color.b, color.a);
				windowBar.getImg().setColor(3, color.r, color.g, color.b, color.a);
				
				windowBar.getImg().drawEmbedded(0,0, 20, 30, 0,0, 20,30);
				windowBar.getImg().drawEmbedded(20,0, this.getWidth() - 14, 30, 20,0, 21,30);
				windowBar.getImg().drawEmbedded(this.getWidth() - 14,0, this.getWidth() + 6, 30, windowBar.getImg().getWidth() - 20, 0, windowBar.getImg().getWidth(),30);
				
				windowBar.getImg().endUse();
				
				g.drawString(title, 20, 5);
				
				closeButton.getImg().draw(this.getWidth() - 40, windowBar.getImg().getHeight() - closeButton.getImg().getHeight()- 3);
				dockButton.getImg().draw(this.getWidth() - 40 - closeButton.getImg().getHeight(), windowBar.getImg().getHeight() - closeButton.getImg().getHeight()- 3);
				
			g.translate(2, 3);
		g.translate(-this.getX(), -this.getY());
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
