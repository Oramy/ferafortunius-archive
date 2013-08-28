package gui.buttons;

import gui.Container;
import gui.GameMain;
import gui.PImage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

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
	
	private boolean hover;
	private boolean normalB;
	public HiddenButton(String texte, String img, Container parent) {
		super(texte, parent);
		setBoutonAct(normal);
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
		
		hover = false;
		normalB = true;
	}
	@Override
	public void hover(){
		Sound son = null;
		try {
			son = new Sound("Sounds/click.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		son.play();
		
		hover = true;
		normalB = false;
	}
	@Override
	public void clickPressed(){
		super.clickPressed();
		setBoutonAct(active);
	}
	@Override
	public void clickReleased(){
		super.clickReleased();
		setBoutonAct(normal);
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(hover){
			if(visibleX < getSizeX() && getSizeX() - visibleX > 10f * GameMain.delta){
				visibleX += (int)(10f * GameMain.delta);
			}
			else if(visibleX < getSizeX())
				visibleX++;
			
		}
		else if(normalB){
			if(visibleX > hideVisX && visibleX - hideVisX > 10f * GameMain.delta)
				visibleX-= (int)(10f * GameMain.delta);
			else if(visibleX > hideVisX)
				visibleX--;
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
				getBoutonAct().getImg().startUse();
				
				getBoutonAct().getImg().drawEmbedded(0, 0, 20, this.getBounds().height, 0, 0, 20, getBoutonAct().getImg().getHeight());
				getBoutonAct().getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
						20,0, 21, getBoutonAct().getImg().getHeight());
				getBoutonAct().getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, getBoutonAct().getImg().getWidth() - 20, 0, getBoutonAct().getImg().getWidth(), getBoutonAct().getImg().getHeight());
				
				getBoutonAct().getImg().drawEmbedded(0, 0, 20, this.getBounds().height, 0, 0, 20, getBoutonAct().getImg().getHeight(), maskColor);
				getBoutonAct().getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
						20,0, 21, getBoutonAct().getImg().getHeight(), maskColor);
				getBoutonAct().getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, getBoutonAct().getImg().getWidth() - 20, 0, getBoutonAct().getImg().getWidth(), getBoutonAct().getImg().getHeight(), maskColor);

				getBoutonAct().getImg().endUse();
				
				icon.getImg().draw(getSizeX() - 35, 5, 20, 20);
				
				g.scale(prop, prop);
				
					g.drawString(getName(),  0,this.getBounds().height / 2 - (g.getFont().getHeight(getName())/ 2));
				
				g.scale(1 / prop, 1 / prop);
				
			g.translate(-this.getBounds().x, -this.getBounds().y);
		g.translate(getSizeX() - visibleX, 0);
	}
}
