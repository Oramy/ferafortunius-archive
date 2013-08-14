package gui.editeurs;

import gui.Container;
import gui.jeu.PanneauJeuAmeliore;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

import Level.ChunkMap;
import Level.Iterator;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;


public class PanneauApercu extends PanneauJeuAmeliore {
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected ObjetMap editChoice;
	protected ObjetImage draggedObjImg;
	protected int oldx;
	protected int oldy;
	protected boolean clicked;
	protected boolean dragged;
	protected boolean moveImage;
	
	protected int fps;
	
	protected boolean translate = false;
 	public PanneauApercu(ObjetMap obj, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(new ChunkMap(10000000,1,1,1), x, y, sizeX, sizeY, parent);
		editChoice = obj;
		chunkcolor = Color.black;
		moveImage = true;
		dragged = false;
		actualCam.setMinzoom(0.2f);
	}
	public void draw(Graphics g){
		draggedObjImg = null;
		g.translate(getX(),getY());
		if(getEditChoice() != null){
			g.translate(this.getWidth()/2, this.getHeight()/2);
				g.translate(-actualCam.getX() * actualCam.getZoom(), -actualCam.getY() * actualCam.getZoom());
				ObjetMap o = editChoice;
				if(translate)
					this.translateToObject(g, o);
				if(o != null){
					//Initialisation d'un iterateur
					if(o.getImageList(o.getCurrentImageList()) != null){
						Iterator it2 = o.getImagesIterator();
						ObjetImage objetImg = null;
						//Affichage des images
						do{
							objetImg = (ObjetImage) it2.getNextElement();
							if(objetImg != null){
									
								
								Image wildImg = loadImage(objetImg, o).getImg();
								Image formatImage = wildImg.getSubImage(objetImg.getPosSpriteSheetX(), objetImg.getPosSpriteSheetY(), wildImg.getWidth(), wildImg.getHeight());
								wildImg = formatImage;
								SpriteSheet sprite = new SpriteSheet(wildImg, objetImg.getSizeSpriteX(),  objetImg.getSizeSpriteY());
								Image image =  sprite.getSprite( objetImg.getPosX(),  objetImg.getPosY());
								image.setAlpha(o.getOpacity());
								if(objetImg.isMirror()){
									image = image.getFlippedCopy(true, false);
								}
								translateToObjectImage(g, o, objetImg);
									
									int posX = this.getWidth()/2;
									posX += -actualCam.getX() * actualCam.getZoom();
									
									int posY = this.getHeight()/2;
									posY += -actualCam.getY() * actualCam.getZoom();
										
									int mx = (int) (Mouse.getX() - this.getXOnScreen());
									int my = (int) (getRacine().getHeight() - Mouse.getY() - this.getYOnScreen());
									int posImgX = (int) ((float)(+objetImg.getDecalageX() - (float)(objetImg.getImageSizeInGameX() / 2))); 
									int posImgY = (int) ((float)(+objetImg.getDecalageY() - (float)(objetImg.getImageSizeInGameY()))); 
									ObjetImage parentImg = o.getImage(objetImg.getParentAlias());
									while(parentImg != null){
										posImgX += parentImg.getDecalageX();
										posImgY += parentImg.getDecalageY();
										parentImg = o.getImage(parentImg.getParentAlias());
									}
									posImgX *= actualCam.getZoom();
									posImgY *= actualCam.getZoom();
									if(mx > posX + posImgX && mx < posX + posImgX + (float)(objetImg.getImageSizeInGameX()) * actualCam.getZoom()
											&&  my > posY + posImgY && my < posY + + posImgY+ objetImg.getImageSizeInGameY() * actualCam.getZoom()){
										// Prend l'image du vrai objet, pour la déplacer.
										for(ObjetImage img : editChoice.getImageList(editChoice.getCurrentImageList()).getList()){
											if(objetImg.getAlias() != null){
												if(objetImg.getAlias().equals(img.getAlias())){
													draggedObjImg = img;
												}
											}
										}
									}
									if(isMoveImage()){
										g.setColor(new Color(255,0,0,25));
										
										g.translate(objetImg.getRotationCenterX() * actualCam.getZoom(), objetImg.getRotationCenterY() * actualCam.getZoom());
											g.rotate(1, 1, objetImg.getRotation());
										g.translate(-objetImg.getRotationCenterX() * actualCam.getZoom(), -objetImg.getRotationCenterY() * actualCam.getZoom());
									
										g.fillRoundRect(0,0, objetImg.getImageSizeInGameX() * actualCam.getZoom(), objetImg.getImageSizeInGameY() * actualCam.getZoom(), 10);
										
										g.translate(objetImg.getRotationCenterX() * actualCam.getZoom(), objetImg.getRotationCenterY() * actualCam.getZoom());
											g.rotate(1, 1, -objetImg.getRotation());
										g.translate(-objetImg.getRotationCenterX() * actualCam.getZoom(), -objetImg.getRotationCenterY() * actualCam.getZoom());
										
									}
									o.paintComponent(this, g, image, posX, posY, objetImg, actualCam);
									if(isMoveImage()){
										g.setColor(new Color(0,100,100, 255));
										g.fillRoundRect(objetImg.getRotationCenterX()  * actualCam.getZoom() - 5, objetImg.getRotationCenterY() * actualCam.getZoom() - 5, 10,10,10);
									}
								untranslateToObjectImage(g, o, objetImg);

								}

							}while(objetImg != null);
						}
					}
					if(translate)
						this.untranslateToObject(g, o);
					g.setColor(Color.black);
					Polygon p = new Polygon();
					p.addPoint((float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2)))* actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2))) * actualCam.getZoom() / 2));
					p.addPoint(0, 0);
					p.addPoint((float) (+(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom() / 2));
					p.addPoint((float) (+(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom()-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2)))* actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom() / 2 -(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2))) * actualCam.getZoom() / 2));
					
					g.draw(p);
					Polygon p2 = new Polygon();
					p2.addPoint((float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2)))* actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2))) * actualCam.getZoom() / 2) - getEditChoice().getSizeZ() * actualCam.getZoom());
					p2.addPoint(0, - getEditChoice().getSizeZ() * actualCam.getZoom());
					p2.addPoint((float) (+(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom() / 2)- getEditChoice().getSizeZ() * actualCam.getZoom());
					p2.addPoint((float) (+(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom()-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2)))* actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom() / 2 -(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2))) * actualCam.getZoom() / 2)- getEditChoice().getSizeZ() * actualCam.getZoom());
					
					g.draw(p2);
					g.drawLine(0,0, 0, - getEditChoice().getSizeZ() * actualCam.getZoom());
					g.drawLine((float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2)) * actualCam.getZoom())),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2))) * actualCam.getZoom() / 2),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2))) * actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeY(), 2))) * actualCam.getZoom() / 2) - getEditChoice().getSizeZ() * actualCam.getZoom());
					g.drawLine((float) (+(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom() / 2),
							(float) (+(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom()),
							(float) (-(Math.sqrt(Math.pow(getEditChoice().getSizeX(), 2))) * actualCam.getZoom() / 2) - getEditChoice().getSizeZ() * actualCam.getZoom());
					this.drawLines(g, o);
					
				g.translate(actualCam.getX() * actualCam.getZoom(), actualCam.getY() * actualCam.getZoom());
			g.translate(-this.getWidth()/2, -this.getHeight()/2);
		}
		g.drawString("FPS :"+fps, sizeX - 100, sizeY - g.getFont().getLineHeight());
		g.translate(-getX(),-getY());
	}
	public void update(GameContainer gc, int x, int y){
		fps = gc.getFPS();
		if(gc.getInput().getMouseX() >= this.getX() + x
				&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
				&& gc.getInput().getMouseY() >= this.getY() + y
				&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY()){
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !clicked){
				clicked = true;
				oldx =  gc.getInput().getMouseX();
				oldy =  gc.getInput().getMouseY();
			}
			else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && clicked){
				clicked = false;
			}
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !dragged){
				dragged = true;
				oldx =  gc.getInput().getMouseX();
				oldy =  gc.getInput().getMouseY();
			}	
			else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && dragged){
				dragged = false;
			}
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
			{
					
			
				int newx = gc.getInput().getMouseX();
				int newy = gc.getInput().getMouseY();
				getActualCam().move(oldx - newx, oldy-newy);
				oldx = newx;
				oldy = newy;
				//carte.verifyPosition(editChoice);
			}
			if(dragged)
			{
					
				if(draggedObjImg != null && isMoveImage()){
					int newx = gc.getInput().getMouseX();
					int newy = gc.getInput().getMouseY();
					draggedObjImg.setDecalageX((int) (draggedObjImg.getDecalageX()+(- oldx + newx)));
					draggedObjImg.setDecalageY((int) (draggedObjImg.getDecalageY()+(- oldy + newy)));
					if(parent.getParent().getParent() instanceof EditeurObjetMap){
						ImgEditor ie = ((EditeurObjetMap)parent.getParent().getParent()).getImgEditor();
						for(ObjetImage img : ie.getObj().getImageList(ie.getObj().getCurrentImageList()).getList()){
							if(draggedObjImg.getAlias() != null){
								if(draggedObjImg.getAlias().equals(img.getAlias())){
									ie.setImgToAdd(img);
								}
							}
						}
					}
					oldx = newx;
					oldy = newy;
				}
				//carte.verifyPosition(editChoice);
			}
			int mouse = Mouse.getDWheel();
			if(mouse > 0){
				this.actualCam.setZoom(this.actualCam.getZoom() *1.2f);
			}
			if(mouse < 0){
				this.actualCam.setZoom(this.actualCam.getZoom() / 1.2f);
			}
		}
		super.update(gc, x, y);
	}
	/**
	 * @return the editChoice
	 */
	public ObjetMap getEditChoice() {
		return editChoice;
	}
	/**
	 * @param editChoice the editChoice to set
	 */
	public void setEditChoice(ObjetMap editChoice) {
		this.editChoice = editChoice;
	}

	/**
	 * @return the moveImage
	 */
	public boolean isMoveImage() {
		return moveImage;
	}

	/**
	 * @param moveImage the moveImage to set
	 */
	public void setMoveImage(boolean moveImage) {
		this.moveImage = moveImage;
	}
}
