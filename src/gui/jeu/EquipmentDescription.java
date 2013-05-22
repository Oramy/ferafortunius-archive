package gui.jeu;

import gui.Container;
import gui.ItemRessources;
import gui.PImage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import Items.Arme;
import Items.Bouclier;
import Items.EquipmentItem;


public class EquipmentDescription extends ItemDescription{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4735423938735672L;
	private PImage equipe;
	public EquipmentDescription(ItemComponent item, Container parent) {
		super(item, parent);
		equipe = new PImage("GUI/Icon/equipe.png"); //$NON-NLS-1$
		
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
		int imgSizeY = 0;
		for(int i = 0; i < ItemRessources.getItemRessource().size(); i++){
			if(ItemRessources.getItemRessource().get(i).getNom().equals(item.getCibles().get(0).getImg().getImage())){
				Image wildImg = ItemRessources.getItemRessource().get(i).getImg();
				SpriteSheet sprite = new SpriteSheet(wildImg, item.getCibles().get(0).getImg().getSizeSpriteX(), item.getCibles().get(0).getImg().getSizeSpriteY());
				Image img =  sprite.getSprite(item.getCibles().get(0).getImg().getPosX(), item.getCibles().get(0).getImg().getPosY());
				if(getSizeX() < img.getWidth() + g.getFont().getWidth(item.getCibles().get(0).getName()) || getSizeY() < img.getHeight()){
					setSizeX(img.getWidth() + g.getFont().getWidth("aaaaaaaaaaaaaaaaaaa") + 40); //$NON-NLS-1$
					setSizeY(img.getHeight());
					
				}
				imgSizeX = img.getWidth();
				imgSizeY = img.getHeight();
				img.setAlpha(0.8f);
				img.draw(0,0);
				if(item.getCibles().size() > 1)
					g.drawString(item.getCibles().size() + "", img.getWidth() - g.getFont().getWidth(item.getCibles().size() + "") - 20, img.getHeight() - g.getFont().getHeight(item.getCibles().size() + "") - 7); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
		if(getSizeY() < actY){
			setSizeY(actY + 20);
		}
		if(getX() + getSizeX() > parent.getSizeX()){
			setX(parent.getSizeX() - getSizeX());
		}
		if(item.getCibles().get(0).getOwner().getEquipment().getContents().contains(item.getCibles().get(0))){
				equipe.getImg().draw(10, imgSizeY - equipe.getImg().getHeight() / 3 - 10, equipe.getImg().getWidth() / 3,  equipe.getImg().getHeight() / 3);
		}
		actY = 10;
		g.setColor(Color.black);
		g.drawString(item.getCibles().get(0).getName(), imgSizeX, actY);
		g.setColor(new Color(80,80,80));
		actY += 20;
		g.drawString(item.getCibles().get(0).getOwner().getNom(), imgSizeX, actY);
		if(item.getCibles().get(0) instanceof Arme){
			actY += 20;
			if(item.getCibles().get(0) instanceof Bouclier){
				g.drawString(Messages.getString("EquipmentDescription.5"), imgSizeX, actY); //$NON-NLS-1$
			}
			else{
				if(((Arme)item.getCibles().get(0)).isTwoHands()){
					g.drawString(Messages.getString("EquipmentDescription.6"), imgSizeX, actY); //$NON-NLS-1$
				}
				else{
					g.drawString(Messages.getString("EquipmentDescription.7"), imgSizeX, actY); //$NON-NLS-1$
				}
			}
		}
		g.setColor(new Color(80,80,140));
		actY += 20;
		g.drawString(Messages.getString("EquipmentDescription.8") + item.getCibles().get(0).getWeight(), imgSizeX, actY); //$NON-NLS-1$
		if(((EquipmentItem) item.getCibles().get(0)).getMaxHpBonus() != 0)
		{
			actY += 20;
			if(((EquipmentItem) item.getCibles().get(0)).getMaxHpBonus() > 0){
				g.setColor(new Color(80,140,80));
				g.drawString(Messages.getString("EquipmentDescription.9") + ((EquipmentItem) item.getCibles().get(0)).getMaxHpBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
			else{
				g.setColor(new Color(140,80,80));
				g.drawString(Messages.getString("EquipmentDescription.10") + ((EquipmentItem) item.getCibles().get(0)).getMaxHpBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
		}
		if(((EquipmentItem) item.getCibles().get(0)).getHpGainBonus() != 0)
		{
			actY += 20;
			if(((EquipmentItem) item.getCibles().get(0)).getHpGainBonus() > 0){
				g.setColor(new Color(80,140,80));
				g.drawString(Messages.getString("EquipmentDescription.11") + ((EquipmentItem) item.getCibles().get(0)).getHpGainBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
			else{
				g.setColor(new Color(140,80,80));
				g.drawString(Messages.getString("EquipmentDescription.12") + ((EquipmentItem) item.getCibles().get(0)).getHpGainBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
		}
		if(((EquipmentItem) item.getCibles().get(0)).getMaxMpBonus() != 0)
		{
			actY += 20;
			if(((EquipmentItem) item.getCibles().get(0)).getMaxMpBonus() > 0){
				g.setColor(new Color(80,140,80));
				g.drawString(Messages.getString("EquipmentDescription.13") + ((EquipmentItem) item.getCibles().get(0)).getMaxMpBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
			else{
				g.setColor(new Color(140,80,80));
				g.drawString(Messages.getString("EquipmentDescription.14") + ((EquipmentItem) item.getCibles().get(0)).getMaxMpBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
		}
		if(((EquipmentItem) item.getCibles().get(0)).getMpGainBonus() != 0)
		{
			actY += 20;
			if(((EquipmentItem) item.getCibles().get(0)).getMpGainBonus() > 0){
				g.setColor(new Color(80,140,80));
				g.drawString(Messages.getString("EquipmentDescription.15") + ((EquipmentItem) item.getCibles().get(0)).getMpGainBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
			else{
				g.setColor(new Color(140,80,80));
				g.drawString(Messages.getString("EquipmentDescription.16") + ((EquipmentItem) item.getCibles().get(0)).getMpGainBonus(), imgSizeX, actY); //$NON-NLS-1$
			}
		}
		g.setColor(new Color(50,50,50));
		actY += 20;
		for(int i = 0; i <= item.getCibles().get(0).getDescription().length() / 20; i++){
			if(i == item.getCibles().get(0).getDescription().length() / 20){
				g.drawString(item.getCibles().get(0).getDescription().substring(i * 20), imgSizeX, actY);
			}else{
				String todraw = item.getCibles().get(0).getDescription().substring(i * 20, (i + 1) * 20);
				String tiret = "-._ /;?,:!)(|"; //$NON-NLS-1$
				if(!tiret.contains(todraw.substring(todraw.length() - 1, todraw.length())) && 
						!tiret.contains(item.getCibles().get(0).getDescription().substring((i + 1) * 20, (i+1) * 20 + 1))){
					todraw += "-"; //$NON-NLS-1$
				}
				g.drawString(todraw, imgSizeX, actY);
			}
			actY+= 15;
		}
		g.setColor(Color.black);
		g.translate(-getX(), -getY());
	}

}
