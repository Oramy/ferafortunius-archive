package gui.jeu;

import gui.Container;
import gui.FComponent;
import gui.ItemRessources;
import gui.PImage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import Items.Item;


public class ItemDescription extends FComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ItemComponent item;
	protected PImage background;
	protected int actY;
	public ItemDescription(ItemComponent item, Container parent) {
		super(parent);
		this.item = item;
		background = Container.normalBackground; //$NON-NLS-1$
		background.getImg().setAlpha(0.8f);
		this.setSizeX(0);
		this.setSizeY(0);
		this.setX(0);
		this.setY(0);
		clickable = false;
		
	}
	public void draw(Graphics g){
		g.translate(getX(), getY());
		//Haut
		getBackground().getImg().draw(0, 0, 10,10, 0,0,10,10);
		getBackground().getImg().draw(10, 0, this.getWidth() - 10,10, 10,0,background.getImg().getWidth() - 10,10);
		getBackground().getImg().draw(this.getWidth() - 10, 0, this.getWidth(),10, background.getImg().getWidth() - 10,0,background.getImg().getWidth(),10);
		//Gauche
		getBackground().getImg().draw(0, 10, 10,this.getHeight() - 10, 0,10,10,background.getImg().getHeight() - 10);
		//Droite
		getBackground().getImg().draw(this.getWidth() - 10,  10,this.getWidth(),this.getHeight() - 10, background.getImg().getWidth() - 10,10,background.getImg().getWidth(),background.getImg().getHeight() - 10);
		//Bas
		getBackground().getImg().draw(0, this.getHeight() - 10, 10, this.getHeight(), 0,background.getImg().getHeight() - 10,10,background.getImg().getHeight());
		getBackground().getImg().draw(10, this.getHeight() - 10, this.getWidth() - 10, this.getHeight(), 10,background.getImg().getHeight() - 10,background.getImg().getWidth() - 10,background.getImg().getHeight());
		getBackground().getImg().draw(this.getWidth() - 10, this.getHeight() - 10, this.getWidth(), this.getHeight(), background.getImg().getWidth() - 10,background.getImg().getHeight() - 10,background.getImg().getWidth(),background.getImg().getHeight());
		//Milieu
		getBackground().getImg().draw(10, 10, this.getWidth() - 10,this.getHeight() - 10, 10,10,background.getImg().getWidth() - 10,background.getImg().getHeight() - 10);
		int imgSizeX = 0;
		for(int i = 0; i < ItemRessources.getItemRessource().size(); i++){
			if(ItemRessources.getItemRessource().get(i).getNom().equals(item.getCibles().get(0).getImg().getImage())){
				Image wildImg = ItemRessources.getItemRessource().get(i).getImg();
				SpriteSheet sprite = new SpriteSheet(wildImg, item.getCibles().get(0).getImg().getSizeSpriteX(), item.getCibles().get(0).getImg().getSizeSpriteY());
				Image img =  sprite.getSprite(item.getCibles().get(0).getImg().getPosX(), item.getCibles().get(0).getImg().getPosY());
				if(getSizeX() < img.getWidth() + g.getFont().getWidth(item.getCibles().get(0).getName()) || getSizeY() < img.getHeight()){
					setSizeX(img.getWidth() + g.getFont().getWidth("0123456789123456789") + 40);
					setSizeY(img.getHeight());
					if(getSizeY() < ((item.getCibles().get(0).getDescription().length() / 20) + 1) * 15 + 90){
						setSizeY(((item.getCibles().get(0).getDescription().length() / 20) + 1) * 15 + 90);
					}
				}
				imgSizeX = img.getWidth();
				img.setAlpha(0.8f);
				img.draw(0,0);
				g.setColor(Color.black);
				if(item.getCibles().size() > 1)
					g.drawString(item.getCibles().size() + "", img.getWidth() - g.getFont().getWidth(item.getCibles().size() + "") - 20, img.getHeight() - g.getFont().getHeight(item.getCibles().size() + "") - 7); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
		if(getX() + getSizeX() > parent.getSizeX()){
			setX(parent.getSizeX() - getSizeX());
		}
		g.setColor(Color.black);
		g.drawString(item.getCibles().get(0).getName(), imgSizeX, 10);
		g.setColor(new Color(80,80,80));
		g.drawString(item.getCibles().get(0).getOwner().getNom(), imgSizeX, 30);
		g.setColor(new Color(80,80,140));
		g.drawString(Messages.getString("ItemDescription.4") + item.getCibles().get(0).getWeight(), imgSizeX, 50); //$NON-NLS-1$
		g.setColor(new Color(50,50,50));
		for(int i = 0; i <= item.getCibles().get(0).getDescription().length() / 20; i++){
			if(i == item.getCibles().get(0).getDescription().length() / 20){
				g.drawString(item.getCibles().get(0).getDescription().substring(i * 20), imgSizeX, 70 + i * 15);
			}else{
				String todraw = item.getCibles().get(0).getDescription().substring(i * 20, (i + 1) * 20);
				String tiret = "-._ /;?,:!)(|"; //$NON-NLS-1$
				if(!tiret.contains(todraw.substring(todraw.length() - 1, todraw.length())) && 
						!tiret.contains(item.getCibles().get(0).getDescription().substring((i + 1) * 20, (i+1) * 20 + 1))){
					todraw += "-"; //$NON-NLS-1$
				}
				g.drawString(todraw, imgSizeX, 70 + i * 15);
			}
		}
		g.setColor(Color.black);
		g.translate(-getX(), -getY());
	}
	public void update(GameContainer gc, int x, int y){
		Item i = item.getCibles().get(0);
		if(!i.getOwner().getInventaire().getContents().contains(i))
			this.parent.getComponents().remove(this);
	}
	/**
	 * @return the backgrounde
	 */
	public PImage getBackground() {
		return background;
	}
	/**
	 * @param backgrounde the backgrounde to set
	 */
	public void setBackground(PImage backgrounde) {
		this.background = backgrounde;
	}
	@Override
	public void updateSize() {
		// TODO Auto-generated method stub
		
	}
}
