package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class ButtonScrollImage extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3530013425428614191L;
	private ScrollImage scrollImg;
	private PImage bouton;
	private boolean clicked = false;
	private Color color;
	private ArrayList<Action> action;
	public ButtonScrollImage(ScrollImage scrollImg, int posX, int posY, int width, int height, Container parent){
		super(posX, posY, width,height,parent);
		this.scrollImg = scrollImg;
		action = new ArrayList<Action>();
		bouton = new PImage("GUI/button.png");
		setEnable(true);
		color = new Color(0,0,0,0);
	}

	public ButtonScrollImage(ScrollImage scrollImg, Container parent){
		super(0,0,1,1,parent);
		this.scrollImg = scrollImg;
		action = new ArrayList<Action>();
		bouton = new PImage("GUI/button.png");
		this.setBounds(0,0,1,1);
		setEnable(true);
		color = new Color(0,0,0,0);
	}
	public void update(GameContainer gc, int x, int y){
		
		if(isEnable()){
			scrollImg.update(gc, this.getX() + x, this.getY() +y);
			if(gc.getInput().getMouseX() >= this.getX() + x
				&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
				&& gc.getInput().getMouseY() >= this.getY() + y
				&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY() && !clicked){
				bouton = new PImage("GUI/buttonHover.png");
				if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked){
					color = new Color(0,0,0,90);
					clicked = true;
				
					if(action.size() != 0)
					{
						for(int i =0; i < action.size(); i++)
							action.get(i).actionPerformed(this);
					}
				}
				else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked){
					clicked = false;
					color = new Color(0,0,0,0);
				}
			}
			else{
				bouton = new PImage("GUI/button.png");
			}
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !clicked)
			{
				clicked = true;
			}
			else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked){
				clicked = false;
			}
		}
		else{
			color = new Color(0,0,0,190);
		}
	}
	public void draw(Graphics g) {
		g.setColor(Color.black);

		g.translate(this.getBounds().x, this.getBounds().y);
			bouton.getImg().startUse();
			bouton.getImg().drawEmbedded(0, 0, 20, this.getBounds().height, 0, 0, 20, bouton.getImg().getHeight());
			bouton.getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
					20,0, 21, bouton.getImg().getHeight());
			bouton.getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, bouton.getImg().getWidth() - 20, 0, bouton.getImg().getWidth(), bouton.getImg().getHeight());
			
			bouton.getImg().drawEmbedded(0, 0, 20, this.getBounds().height, 0, 0, 20, bouton.getImg().getHeight(), color);
			bouton.getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
					20,0, 21, bouton.getImg().getHeight(), color);
			bouton.getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, bouton.getImg().getWidth() - 20, 0, bouton.getImg().getWidth(), bouton.getImg().getHeight(), color);
			
			bouton.getImg().endUse();
			scrollImg.drawBegin(g);
			scrollImg.draw(g);
			scrollImg.drawEnd(g);
		g.translate(-this.getBounds().x, -this.getBounds().y);
			
	}
	
	/**
	 * @return the action
	 */
	public ArrayList<Action> getAction() {
		return action;
	}
	/**
	 * @param actione the action to set
	 */
	public void setAction(ArrayList<Action> action) {
		this.action = action;
	}
}
