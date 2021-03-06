package gui.jeu.inventory;

import gui.Container;
import gui.FComponent;
import gui.ItemRessources;
import gui.PImage;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

import Items.EquipmentItem;
import Items.HumanEquipment;
import Items.Item;
import Items.Utility;


public class ItemComponent extends FComponent implements Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> cibles;
	private PImage background = new PImage("GUI/itemBackground.png");
	private ItemDescription infos;
	private boolean inside = false;
	private boolean rightClick = false;
	private boolean leftClick = false;
	protected long lastClick;
	protected int nbClick;
	protected boolean followingMouse = false;
	public ItemComponent(Item cible, Container parent) {
		super(parent);
		setX(0);
		setY(0);
		setSizeX(56);
		setSizeY(56);
		nbClick = 0;
		setCibles(new ArrayList<Item>());
		getCibles().add(cible);
		
		
	}
	public void hover(){
	
		if(this.parent != this.getRacine()){
			if(!((Container) this.getRacine()).getComponents().contains(infos)){
				if(cibles.get(0) instanceof Utility)
					infos = new UtilityDescription(this, (Container) this.getRacine());
				else if(cibles.get(0) instanceof EquipmentItem)
					infos = new EquipmentDescription(this, (Container) this.getRacine());
				else
					infos = new ItemDescription(this, (Container) this.getRacine());
				
				infos.setX((int) this.getXOnScreen());
				infos.setY((int) this.getYOnScreen() - 5);
				((Container) this.getRacine()).setzMax(((Container) this.getRacine()).getzMax() + 1);
				infos.setzIndex(((Container) this.getRacine()).getzMax());
				if(!((Container) this.getRacine()).getComponents().contains(infos))
					((Container) this.getRacine()).addComponent(infos);	
					
			}
		}
	}
	public void normal(){
		((Container) this.getRacine()).getComponents().remove(infos);
	}
	public void equip(){
		if(cibles.get(0) instanceof EquipmentItem){
			if(cibles.get(0).getOwner().getEquipment() instanceof HumanEquipment){
				if(!cibles.get(0).getOwner().getEquipment().getContents().contains(cibles.get(0))){
					cibles.get(0).getOwner().getEquipment().equip((EquipmentItem) cibles.get(0));
				}else{
					cibles.get(0).getOwner().getEquipment().unequip((EquipmentItem) cibles.get(0));
				}
			}
		}
	}
	public void use(){
		if(getCibles().get(0) instanceof Utility){
			((Utility) getCibles().get(0)).use();
			if(((Utility) getCibles().get(0)).getUseNumber() <= 0){
				((Container) this.getRacine()).getComponents().remove(infos);
				getCibles().remove(0);
			}
		}
	}
	public void update(GameContainer gc, int x, int y){
		if(getCibles().size() > 0){
			if(gc.getInput().getMouseX() >= this.getX() + x
						&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
						&& gc.getInput().getMouseY() >= this.getY() + y
						&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY()){
					if(!inside && !followingMouse){
						hover();
						inside = true;
					}
					if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !leftClick){
						leftClick = true;
						if(nbClick == 0)
						nbClick++;
						long temps = System.currentTimeMillis();
						if(temps - 400 < lastClick){
							nbClick++;
						}
						lastClick = System.currentTimeMillis();
						
						
					}
					if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick){
						rightClick = true;
					}
			}
			else if(inside){
				normal();
				inside = false;
			}
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && rightClick){
				rightClick = false;
				use();
			}
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftClick){
				
				
				leftClick = false;
				if(nbClick >= 2 && followingMouse == false){
					equip();
					nbClick = 0;
				}
			}
			if(followingMouse){
				this.setX(Mouse.getX() - this.getSizeX() / 2);
				this.setY(parent.getSizeY() - Mouse.getY() - this.getSizeY() / 2);
			}
			long temps = System.currentTimeMillis();
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && lastClick + 400 < temps && nbClick == 1){
				//Reset du click
				nbClick = 0;
				/*followingMouse = !followingMouse;
				if(followingMouse){
					ItemComponent c = null;
					c = this.clone();
					if(this.parent != this.getRacine()){
						this.followingMouse = false;
						
						c.followingMouse = true;
						((Container)this.getRacine()).addComponent(c);
						c.setParent((Container) this.getRacine());
						c.setX((int) this.getXOnScreen());
						c.setY((int) this.getYOnScreen());
					}
					else{
						followingMouse = true;
						((Container)this.getRacine()).addComponent(this);
						setParent((Container) this.getRacine());
						x = (int) this.getXOnScreen();
						y = (int) this.getYOnScreen();
					}
				}*/
			}
		}
		else{
			parent.getComponents().remove(this);
		}
	}
	public ItemComponent clone(){
		ItemComponent ic = null;
		ic = (ItemComponent) super.clone();
		ic.setCibles(cibles);
		return ic;
	}
	public void draw(Graphics g){
		g.translate(this.getBounds().x, this.getBounds().y);
			if(!inside)
				background.getImg().draw(11,11, getSizeX() - 19, getSizeY() - 19);
			else{
				background.getImg().draw(5, 5, getSizeX() - 10, getSizeY() - 10);
			}
			//Loading images
			Image wildImg = ItemRessources.getOneItemRessource(getCibles().get(0).getImg().getImage()).getImg();
			if(wildImg != null){
				
				//Operating modifications.
				SpriteSheet sprite = new SpriteSheet(wildImg, getCibles().get(0).getImg().getSizeSpriteX(), getCibles().get(0).getImg().getSizeSpriteY());
				
				Image img =  sprite.getSprite(getCibles().get(0).getImg().getPosX(), getCibles().get(0).getImg().getPosY());
				
				img = wildImg.getScaledCopy(0.4f);
				
				//Drawing
				img.draw(getSizeX() / 2 - getCibles().get(0).getImg().getImageSizeInGameX() / 5,
						 getSizeY() / 2 - getCibles().get(0).getImg().getImageSizeInGameY() / 5);
				
				if(getCibles().size() > 1){
					g.drawString(getCibles().size() + "", (int)(getSizeX() - 10 - g.getFont().getWidth(getCibles().size() + "")), (int)(getSizeY() - 10 - g.getFont().getHeight(getCibles().size() + "")));
				}
				if(getCibles().get(0).getOwner() != null){
					if(getCibles().get(0).getOwner().getEquipment().getContents().contains(getCibles().get(0))){
						//Si l'item ?quip?, on l'indique  !
						EquipmentDescription.equipe.getImg().draw(10, getSizeY() - EquipmentDescription.equipe.getImg().getHeight() / 6 - 10, EquipmentDescription.equipe.getImg().getWidth() / 6,  EquipmentDescription.equipe.getImg().getHeight() / 6);
						
					}
				}
			}
		g.translate(-this.getBounds().x, -this.getBounds().y);
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
	 * @return the cibles
	 */
	public ArrayList<Item> getCibles() {
		return cibles;
	}
	/**
	 * @param cibles the cibles to set
	 */
	public void setCibles(ArrayList<Item> cibles) {
		this.cibles = cibles;
	}
	@Override
	public void updateSize() {
		
	}
}
