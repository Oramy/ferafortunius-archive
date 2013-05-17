package gui.jeu;

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
	private PImage background;
	private ItemDescription infos;
	private PImage equipe;
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
		background = new PImage("GUI/itemBackground.png");
		equipe = new PImage("GUI/Icon/equipe.png");
		
	}
	public void update(GameContainer gc, int x, int y){
		if(getCibles().size() > 0){
			if(gc.getInput().getMouseX() >= this.getX() + x
						&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
						&& gc.getInput().getMouseY() >= this.getY() + y
						&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY()){
					if(!inside && !followingMouse){
						if(this.parent != this.getRacine()){
							if(cibles.get(0) instanceof Utility)
								infos = new UtilityDescription(this, (Container) this.getRacine());
							else if(cibles.get(0) instanceof EquipmentItem)
								infos = new EquipmentDescription(this, (Container) this.getRacine());
							else
								infos = new ItemDescription(this, (Container) this.getRacine());
							
							infos.setX((int) this.getXOnScreen());
							infos.setY((int) this.getYOnScreen() - 5);
							((Container) this.getRacine()).addComponent(infos);	
							((Container) this.getRacine()).setzMax(((Container) this.getRacine()).getzMax() + 1);
							infos.setzIndex(((Container) this.getRacine()).getzMax());
						}
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
				((Container) this.getRacine()).getComponents().remove(infos);
				inside = false;
			}
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && rightClick){
				rightClick = false;
				if(getCibles().get(0) instanceof Utility){
					((Utility) getCibles().get(0)).use();
					if(((Utility) getCibles().get(0)).getUseNumber() <= 0){
						((Container) this.getRacine()).getComponents().remove(infos);
						getCibles().remove(0);
					}
				}
			}
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftClick){
				
				
				leftClick = false;
				if(nbClick >= 2 && followingMouse == false){
					if(cibles.get(0) instanceof EquipmentItem){
						if(cibles.get(0).getOwner().getEquipment() instanceof HumanEquipment){
							if(!cibles.get(0).getOwner().getEquipment().getContents().contains(cibles.get(0))){
								cibles.get(0).getOwner().getEquipment().equip((EquipmentItem) cibles.get(0));
							}else{
								cibles.get(0).getOwner().getEquipment().unequip((EquipmentItem) cibles.get(0));
							}
						}
					}
					nbClick = 0;
				}
			}
			if(followingMouse){
				this.setX(Mouse.getX());
				this.setY(parent.getSizeY() - Mouse.getY());
			}
			long temps = System.currentTimeMillis();
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && lastClick + 400 < temps && nbClick == 1){
				nbClick = 0;
				followingMouse = !followingMouse;
				if(followingMouse){
					followingMouse = false;
					
					ItemComponent c = null;
					c = (ItemComponent) this.clone();
					if(this.parent != this.getRacine()){
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
				}
			}
		}
		else{
			parent.getComponents().remove(this);
		}
	}
	public void draw(Graphics g){
		g.translate(this.getBounds().x, this.getBounds().y);
			if(!inside)
				background.getImg().draw(11,11, getSizeX() - 19, getSizeY() - 19);
			else{
				background.getImg().draw(5, 5, getSizeX() - 10, getSizeY() - 10);
			}
			Image wildImg = ItemRessources.getOneItemRessource(getCibles().get(0).getImg().getImage()).getImg();
			if(wildImg != null){
				SpriteSheet sprite = new SpriteSheet(wildImg, getCibles().get(0).getImg().getSizeSpriteX(), getCibles().get(0).getImg().getSizeSpriteY());
				Image img =  sprite.getSprite(getCibles().get(0).getImg().getPosX(), getCibles().get(0).getImg().getPosY());
				img.draw(getSizeX() / 2 - getCibles().get(0).getImg().getImageSizeInGameX() / 5,
				getSizeY() / 2 - getCibles().get(0).getImg().getImageSizeInGameY() / 5,
				getCibles().get(0).getImg().getImageSizeInGameX() / 5 * 2, getCibles().get(0).getImg().getImageSizeInGameX() / 5 * 2);
				if(getCibles().size() > 1){
					g.drawString(getCibles().size() + "", (int)(getSizeX() - 10 - g.getFont().getWidth(getCibles().size() + "")), (int)(getSizeY() - 10 - g.getFont().getHeight(getCibles().size() + "")));
				}
				if(getCibles().get(0).getOwner().getEquipment().getContents().contains(getCibles().get(0))){
						equipe.getImg().draw(10, getSizeY() - equipe.getImg().getHeight() / 6 - 10, equipe.getImg().getWidth() / 6,  equipe.getImg().getHeight() / 6);
					
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
		// TODO Auto-generated method stub
		
	}
}
