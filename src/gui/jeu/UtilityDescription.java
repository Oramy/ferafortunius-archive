package gui.jeu;

import gui.Container;
import gui.ItemRessources;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import Items.Utility;


public class UtilityDescription extends ItemDescription {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199863060828435097L;
	public UtilityDescription(ItemComponent cible, Container parent) {
		super(cible, parent);
		
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
					setSizeX(img.getWidth() + g.getFont().getWidth(item.getCibles().get(0).getDescription().substring(0, 19)) + 40);
					setSizeY(img.getHeight());
					if(getSizeY() < ((item.getCibles().get(0).getDescription().length() / 20) + 1) * 15 + ((((Utility) (item.getCibles().get(0))).getEffectDescription().length() / 20) + 1) * 15 + 130){
						setSizeY(((item.getCibles().get(0).getDescription().length() / 20) + 1) * 15 + ((((Utility) (item.getCibles().get(0))).getEffectDescription().length() / 20) + 1) * 15 + 130);
					}
				}
				imgSizeX = img.getWidth();
				img.setAlpha(0.8f);
				img.draw(0,0);
				if(item.getCibles().size() > 1)
					g.drawString(item.getCibles().size() + "", img.getWidth() - g.getFont().getWidth(item.getCibles().size() + "") - 20, img.getHeight() - g.getFont().getHeight(item.getCibles().size() + "") - 7); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
		int actX = 10;
		g.setColor(Color.black);
		g.drawString(item.getCibles().get(0).getName(), imgSizeX, actX);
		g.setColor(new Color(80,80,80));
		actX += 20;
		g.drawString(item.getCibles().get(0).getOwner().getNom(), imgSizeX, actX);
		g.setColor(new Color(80,80,140));
		actX += 20;
		g.drawString(Messages.getString("UtilityDescription.3") + ((Utility) item.getCibles().get(0)).getUseNumber() + "/" + ((Utility) item.getCibles().get(0)).getUseNumberMax(), imgSizeX, actX); //$NON-NLS-1$ //$NON-NLS-2$
		actX += 20;
		g.drawString(Messages.getString("UtilityDescription.5") + item.getCibles().get(0).getWeight(), imgSizeX, actX); //$NON-NLS-1$
		actX += 20;
		g.setColor(new Color(80,140,80));
		for(int i = 0; i <= ((Utility) (item.getCibles().get(0))).getEffectDescription().length() / 20; i++){
			if(i ==((Utility) (item.getCibles().get(0))).getEffectDescription().length() / 20){
				g.drawString(((Utility) (item.getCibles().get(0))).getEffectDescription().substring(i * 20), imgSizeX, actX);
			}else{
				String todraw = ((Utility) (item.getCibles().get(0))).getEffectDescription().substring(i * 20, (i + 1) * 20);
				String tiret = "-._ /;?,:!)(|"; //$NON-NLS-1$
				if(!tiret.contains(todraw.substring(todraw.length() - 1, todraw.length())) && 
						!tiret.contains(((Utility) (item.getCibles().get(0))).getEffectDescription().substring((i + 1) * 20, (i+1) * 20 + 1))){
					todraw += "-"; //$NON-NLS-1$
				}
				g.drawString(todraw, imgSizeX, actX);
			}
			actX += 15;
		}
		if(getX() + getSizeX() > parent.getSizeX()){
			setX(parent.getSizeX() - getSizeX());
		}
		g.setColor(new Color(50, 50, 50));
		actX += 20;
		for(int i = 0; i <= item.getCibles().get(0).getDescription().length() / 20; i++){
			if(i == item.getCibles().get(0).getDescription().length() / 20){
				g.drawString(item.getCibles().get(0).getDescription().substring(i * 20), imgSizeX, actX);
			}else{
				String todraw = item.getCibles().get(0).getDescription().substring(i * 20, (i + 1) * 20);
				String tiret = "-._ /;?,:!)(|"; //$NON-NLS-1$
				if(!tiret.contains(todraw.substring(todraw.length() - 1, todraw.length())) && 
						!tiret.contains(item.getCibles().get(0).getDescription().substring((i + 1) * 20, (i+1) * 20 + 1))){
					todraw += "-"; //$NON-NLS-1$
				}
				g.drawString(todraw, imgSizeX, actX);
			}
			actX += 15;
		}
		g.setColor(Color.black);
		g.translate(-getX(), -getY());
	}
}
