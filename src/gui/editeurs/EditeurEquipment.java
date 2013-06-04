package gui.editeurs;

import gui.Container;
import gui.Onglet;
import gui.OngletManager;
import gui.PImage;
import gui.jeu.PanneauJeuAmeliore;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import Items.EquipmentItem;
import Items.Item;
import ObjetMap.BasicObjetMap;
import ObjetMap.ObjetImage;

public class EditeurEquipment extends EditeurItemBasic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected EquipmentItem equip;
	
	protected OngletManager ongletManager;

	protected PanneauApercu panneauAperc;
	
	private ImgEditor imgEditor;
	public EditeurEquipment(Item editItem, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(editItem, x, y, sizeX, sizeY, parent);
		
		equip = (EquipmentItem)editItem;
		
		ongletManager = new OngletManager(0, 0, sizeX, sizeY, this);
		this.addComponent(ongletManager);
		
		
		//Onglet des caractéristiques
		Onglet carac = new Onglet("Carac", ongletManager);
		
		//Chargement de l'éditeur
		EditeurCaracEquipment editorCarEquip = new EditeurCaracEquipment(0, 50, sizeX, sizeY - 50, ongletManager);
		carac.setContainer(editorCarEquip);
		
		//Ajout de l'onglet
		ongletManager.addComponent(carac);
		
		//Onglet des images
		Onglet images = new Onglet("Images", ongletManager);
		
		//Chargement de l'éditeur
		imgEditor = new ImgEditor(0, 50, sizeX, sizeY - 50, new BasicObjetMap(0,0,0,0,0,0
				), ongletManager);
		images.setContainer(imgEditor);
		
		
		//Ajout de l'onglet
		ongletManager.addComponent(images);
		
		//Onglet des images
		Onglet apercu = new Onglet("Apercu", ongletManager);
		
		//Chargement de l'éditeur
		panneauAperc = new PanneauApercu(imgEditor.getObj(), 0, 50, sizeX, sizeY - 50, this);
		apercu.setContainer(panneauAperc);
		
		
		//Ajout de l'onglet
		ongletManager.addComponent(apercu);
		
		//Set de l'onglet actuel
		ongletManager.setOngletActuel(images);
		
	}
	public void draw(Graphics g){
		super.draw(g);
		ObjetImage objImg = imgEditor.getImgToAdd();
		if(objImg != null){
			PImage img = PanneauJeuAmeliore.loadImage(objImg, imgEditor.getObj());
			Image wildImg = img.getImg();
			if(wildImg != null){
				Image formatImage = wildImg.getSubImage(objImg.getPosSpriteSheetX(), objImg.getPosSpriteSheetY(), wildImg.getWidth(), wildImg.getHeight());
				wildImg = formatImage;
				SpriteSheet sprite = new SpriteSheet(wildImg, objImg.getSizeSpriteX(),  objImg.getSizeSpriteY());
				
				Image image =  sprite.getSprite( objImg.getPosX(),  objImg.getPosY());
				image.setAlpha(1f);
				
				wildImg.setCenterOfRotation(objImg.getRotationCenterX(),
						objImg.getRotationCenterY());
				wildImg.rotate(objImg.getRotation());
				wildImg.draw(0, 0, objImg.getImageSizeInGameX(),
						objImg.getImageSizeInGameY());
			}
		}
		else{
			System.err.print("Objet Image == null !");
		}
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		
		equip.getImages().clear();
		equip.getImages().addAll(imgEditor.getObj().getImagesLists());
	}

}
