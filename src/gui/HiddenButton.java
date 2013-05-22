package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class HiddenButton extends Button{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5777212494704971129L;
	private int visibleX;
	private int hideVisX;
	private int disableVisX;
	private PImage icon;
	private static final PImage normal = new PImage("GUI/hiddenButton.png");
	private static final PImage active = new PImage("GUI/hiddenButtonActive.png");
	public HiddenButton(String texte, String img, Container parent) {
		super(texte, parent);
		boutonAct = normal;
		icon = new PImage(img);
		hideVisX = 40;
		disableVisX = 20;
		visibleX = hideVisX;
		setX(0);
		setSizeX(120);
		setSizeY(30);
		
	}
	@Override
	public void normal(){
		boutonAct = normal;
	}
	@Override
	public void hover(){
		
	}
	@Override
	public void clickPressed(){
		super.clickPressed();
		boutonAct = active;
	}
	@Override
	public void clickReleased(){
		super.clickReleased();
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(!isInside(gc.getInput().getMouseX(),gc.getInput().getMouseY())){
			if(visibleX > hideVisX && visibleX - hideVisX > 5)
				visibleX-= 5;
			else if(visibleX > hideVisX)
				visibleX--;
		}
		else{
			if(visibleX < getSizeX() && getSizeX() - visibleX > 5){
				visibleX += 5;
			}
			else if(visibleX < getSizeX())
				visibleX++;
		}
		if(!isEnable()){
			visibleX = disableVisX;
		}
	}
	public void draw(Graphics g) {
		g.translate(-getSizeX() + visibleX, 0);
			g.setColor(Color.white);
			if(this.getBounds().width - 65 < g.getFont().getWidth(getName())){
				this.setBounds(this.getBounds().x, this.getBounds().y, (int) g.getFont().getWidth(getName()) + 65, this.getBounds().height);
			}
			if(this.getBounds().height < g.getFont().getHeight(getName())){
				this.setBounds(this.getBounds().x, this.getBounds().y, this.getBounds().width, (int) ((g.getFont().getHeight(getName()) * 2) * prop));
			}
			g.translate(this.getBounds().x, this.getBounds().y);
				boutonAct.getImg().startUse();
				
				boutonAct.getImg().drawEmbedded(0, 0, 20, this.getBounds().height, 0, 0, 20, boutonAct.getImg().getHeight());
				boutonAct.getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
						20,0, 21, boutonAct.getImg().getHeight());
				boutonAct.getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, boutonAct.getImg().getWidth() - 20, 0, boutonAct.getImg().getWidth(), boutonAct.getImg().getHeight());
				
				boutonAct.getImg().drawEmbedded(0, 0, 20, this.getBounds().height, 0, 0, 20, boutonAct.getImg().getHeight(), maskColor);
				boutonAct.getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
						20,0, 21, boutonAct.getImg().getHeight(), maskColor);
				boutonAct.getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, boutonAct.getImg().getWidth() - 20, 0, boutonAct.getImg().getWidth(), boutonAct.getImg().getHeight(), maskColor);

				boutonAct.getImg().endUse();
				
				icon.getImg().draw(getSizeX() - 35, 5, 20, 20);
				
				g.scale(prop, prop);
				
					g.drawString(getName(),  0,this.getBounds().height / 2 - (g.getFont().getHeight(getName())/ 2));
				
				g.scale(1 / prop, 1 / prop);
				
			g.translate(-this.getBounds().x, -this.getBounds().y);
		g.translate(getSizeX() - visibleX, 0);
	}
}
