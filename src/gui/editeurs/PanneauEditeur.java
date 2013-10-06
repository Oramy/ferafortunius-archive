package gui.editeurs;

import gui.Container;
import gui.jeu.PanneauJeuAmeliore;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Level.ChunkMap;
import ObjetMap.BasicObjetMap;
import ObjetMap.CylinderBlock;
import ObjetMap.Ensemble;
import ObjetMap.ObjetMap;


public class PanneauEditeur extends PanneauJeuAmeliore {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3104433715488521888L;
	private ObjetMap editChoice;
	
	private ObjetMap modelObject;
	private boolean nateditChoiceInvisible;
	private int oldX, oldY;
	private boolean clickr;
	private boolean clickl;
	private EditeurMap editeur;
	
	protected int difCameraPosX, difCameraPosY;
	private int draggedMarged = 10;
	public PanneauEditeur(EditeurMap e,ChunkMap c, ObjetMap edit,  int x, int y , int sizeX, int sizeY, Container parent) {
		super(c, x,y,sizeX,sizeY, parent);
		this.editeur = e;
		oldX = sizeX / 2;
		oldY = sizeY / 2;
		setEditChoice(edit);
		clickr = false;
		clickl = false;
		modelObject = new BasicObjetMap(0,0,0,0,0,0);
		modelObject.setBounds(0, 0, 0, 200, 200, 1000);
		modelObject.addCollisionBlock(new CylinderBlock(0,0,0, 200,200, 1000));
		modelObject.setInvisible(false);
		
	
	}
	public void draw(Graphics g){
		super.draw(g);
		g.translate(getX(),getY());
		
		ObjetMap toDraw = getEditChoice();
		
		if(editeur.getEditeurMode() == EditeurMode.Modelisation)
			toDraw = modelObject;
		if(toDraw != null){
			g.translate(this.getWidth()/2, this.getHeight()/2);
				g.translate(-actualCam.getX() * actualCam.getZoom(), -actualCam.getY() * actualCam.getZoom());
					this.drawMapLines(g);
					g.translate((float)(( + (float)(toDraw.getChunkX()* carte.getChunksSize()) - (float)(toDraw.getChunkY() * carte.getChunksSize()))
							+ ( + (float)(toDraw.getPosX()) -  (float)(toDraw.getPosY()))) * actualCam.getZoom(),
			
							(float)((float)-(toDraw.getChunkY() * carte.getChunksSize() * 0.5) - (float)(toDraw.getChunkX()* carte.getChunksSize() * 0.5) - toDraw.getChunkZ()* carte.getChunksSize()
									- (float)(toDraw.getPosY() * 0.5) - (float)(toDraw.getPosX() * 0.5)  - (float)(toDraw.getPosZ())) * actualCam.getZoom());
						//Gestion des ensembles
						if(editeur.getEditeurMode() == EditeurMode.Ensemble && editeur.getEnsembleChoice() != null){
							for(int i = 0; i < editeur.getEnsembleChoice().getContenu().size(); i++){
								ObjetMap o = editeur.getEnsembleChoice().getContenu().get(i);
								float opacity =  o.getOpacity();
								o.setOpacity(0.5f);
								this.drawObject(g, o, false, false);
								o.setOpacity(opacity);
							}
						}
						this.drawLines(g, toDraw);
						
					g.translate(-(float)(( + (float)(toDraw.getChunkX()* carte.getChunksSize()) - (float)(toDraw.getChunkY() * carte.getChunksSize()))
						+ ( + (float)(toDraw.getPosX()) -  (float)(toDraw.getPosY()))) * actualCam.getZoom(),
		
						-(float)((float)-(toDraw.getChunkY() * carte.getChunksSize() * 0.5) - (float)(toDraw.getChunkX()* carte.getChunksSize() * 0.5) - toDraw.getChunkZ()* carte.getChunksSize()
								- (float)(toDraw.getPosY() * 0.5) - (float)(toDraw.getPosX() * 0.5)  - (float)(toDraw.getPosZ())) * actualCam.getZoom());
					
				g.translate(actualCam.getX() * actualCam.getZoom(), actualCam.getY() * actualCam.getZoom());
			g.translate(-this.getWidth()/2, -this.getHeight()/2);
		}
		if(surlignObject != null){
			g.drawString(Messages.getString("PanneauEditeur.0") + surlignObject.getPosX() + Messages.getString("PanneauEditeur.1") + surlignObject.getPosY() + Messages.getString("PanneauEditeur.2") + surlignObject.getPosZ(), 0, getSizeY() - 10 - g.getFont().getLineHeight()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			g.drawString(Messages.getString("PanneauEditeur.3") + surlignObject.getChunkX() + Messages.getString("PanneauEditeur.4") + surlignObject.getChunkY() + Messages.getString("PanneauEditeur.5") + surlignObject.getChunkZ(), 0, getSizeY() - g.getFont().getLineHeight()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		g.translate(-getX(),-getY());
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(gc.getInput().getMouseX() >= this.getX() + x
				&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
				&& gc.getInput().getMouseY() >= this.getY() + y
				&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY()){
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) &&  gc.getInput().getMouseX() < this.getSizeX() && gc.getInput().getMouseY() < this.getSizeY()){
				clickl = true;
				this.setFocus(true);
				if(editeur.getEditeurMode() == EditeurMode.Placer){
					getEditChoice().setInvisible(isNateditChoiceInvisible());
					ObjetMap o = (ObjetMap) getEditChoice().clone();
					getEditChoice().setInvisible(true);
					if(!o.isInvisible()){
						
						if(carte.getChunks()[o.getChunkX()][o.getChunkY()][o.getChunkZ()].addContenu(o)){
							//click.stop();
							//click.play(7f, 0.2f);
						}
					}
					carte.verifyPosition(getEditChoice());
				}else if(editeur.getEditeurMode() == EditeurMode.Modelisation){
					int value = 1;
					
					if(gc.getInput().isKeyPressed(Input.KEY_LSHIFT)
							|| gc.getInput().isKeyPressed(Input.KEY_RSHIFT)){
						value = - 1;
					}
					if(surlignObject != null){
						modelObject.setPosition(surlignObject);
						modelObject.setZ(0);
						carte.getChunk(modelObject).accepted(modelObject, getEditChoice(), null);
							
						for(ObjetMap touch : modelObject.getTouchedObjects()){
							carte.deplacement(touch, 0, 0, value, null);
							
						}
						if(modelObject.collide(surlignObject, null))
							carte.deplacement(surlignObject, 0, 0, value * 2, null);
						
					}
				}	
				else if(editeur.getEditeurMode() == EditeurMode.Supprimer){
					if(surlignObject != null)
						carte.getChunks()[surlignObject.getChunkX()][surlignObject.getChunkY()][surlignObject.getChunkZ()].remove(surlignObject);
				}else if(editeur.getEditeurMode() == EditeurMode.Ensemble){
					Ensemble esb = editeur.getEnsembleChoice().clone();
					esb.setPosX(getEditChoice().getPosX());
					esb.setPosY(getEditChoice().getPosY());
					esb.setPosZ(getEditChoice().getPosZ());
					esb.setChunkX(getEditChoice().getChunkX());
					esb.setChunkY(getEditChoice().getChunkY());
					esb.setChunkZ(getEditChoice().getChunkZ());
					carte.addContenu(esb);
				}else if(editeur.getEditeurMode() == EditeurMode.Selection){
					editeur.setEditChoice(this.surlignObject);
					editChoice = this.surlignObject;
				}
			}
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !clickr){
				clickr = true;
				
				int newX = gc.getInput().getMouseX();
				int newY = gc.getInput().getMouseY();
				oldX = newX;
				oldY = newY;
			}
			else if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
				clickr = true;
					int newX = gc.getInput().getMouseX();
					int newY = gc.getInput().getMouseY();
					getActualCam().move((oldX - newX) / actualCam.getZoom(), (oldY-newY)/ actualCam.getZoom());
					difCameraPosX += (oldX - newX) / actualCam.getZoom();
					difCameraPosY += (oldY-newY)/ actualCam.getZoom();
					oldX = newX;
					oldY = newY;
			}
			else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && clickr){
				clickr = false;
				if(Math.abs(difCameraPosX) < draggedMarged && Math.abs(difCameraPosY) < draggedMarged ){
					if(getSurlignObject() != null){
						Editeur editR = (Editeur)editeur.getRacine();
						editR.getEditeurObjetOnglet().clickPressed();
						editR.getEditeurObjetOnglet().clickReleased();
						editR.getEditeurObjet().setObjetCible(getSurlignObject());
						editR.getEditeurAnimation().setObjetCible(getSurlignObject().clone());
					}
				}
				difCameraPosX = 0;
				difCameraPosY = 0;
			}
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clickl && this.isFocus()){
				clickl = false;
				if(editeur.getEditeurMode() == EditeurMode.Placer){
					getEditChoice().setInvisible(isNateditChoiceInvisible());
					ObjetMap o = (ObjetMap) getEditChoice().clone();
					getEditChoice().setInvisible(true);
					if(o.isInvisible()){
						if(carte.getChunks()[o.getChunkX()][o.getChunkY()][o.getChunkZ()].addContenu(o)){
							//click.stop();
							//click.play(7f, 0.2f);
						}
					}
					
					carte.verifyPosition(getEditChoice());
				}else if(editeur.getEditeurMode() == EditeurMode.SupprimerRel){
					if(surlignObject != null)
						carte.getChunks()[surlignObject.getChunkX()][surlignObject.getChunkY()][surlignObject.getChunkZ()].remove(surlignObject);
				}
			}
			if(gc.getInput().isKeyPressed(Input.KEY_Z)){
				carte.deplacement(getEditChoice(), 0, getEditChoice().getSizeY(), 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_S)){
				carte.deplacement(getEditChoice(), 0, -getEditChoice().getSizeY(), 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_D)){
				carte.deplacement(getEditChoice(), getEditChoice().getSizeX(), 0, 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_Q)){
				carte.deplacement(getEditChoice(), -getEditChoice().getSizeX(), 0, 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_R)){
				carte.deplacement(getEditChoice(), 0, 0, getEditChoice().getSizeZ(), null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_F)){
				carte.deplacement(getEditChoice(), 0, 0, -getEditChoice().getSizeZ(), null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_I)){
				carte.deplacement(getEditChoice(), 0, 1, 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_K)){
				carte.deplacement(getEditChoice(), 0, -1, 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_L)){
				carte.deplacement(getEditChoice(), 1, 0, 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_J)){
				carte.deplacement(getEditChoice(), -1, 0, 0, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_P)){
				carte.deplacement(getEditChoice(), 0, 0, 1, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_M)){
				carte.deplacement(getEditChoice(), 0, 0, -1, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_ADD)){
				modelObject.setBounds(0, 0, 0, (int)(modelObject.getSizeX() * 1.1f), (int)(modelObject.getSizeY() * 1.1f), modelObject.getSizeZ());
				modelObject.getCollision().clear();
				modelObject.addCollisionBlock(new CylinderBlock(0,0,0, modelObject.getSizeX(),modelObject.getSizeY(), modelObject.getSizeZ()));	
			}
			if(gc.getInput().isKeyPressed(Input.KEY_SUBTRACT)){
				modelObject.setBounds(0, 0, 0, (int)(modelObject.getSizeX() * 0.9f), (int)(modelObject.getSizeY() * 0.9f), modelObject.getSizeZ());
				modelObject.getCollision().clear();
				modelObject.addCollisionBlock(new CylinderBlock(0,0,0, modelObject.getSizeX(),modelObject.getSizeY(), modelObject.getSizeZ()));
				
			}
			/*if(gc.getInput().isKeyPressed(Input.KEY_F3)){
				if(carte.getChunk(editChoice) instanceof LayeredChunkMap){
					for(int i = 0; i < carte.getChunk(editChoice).getContenu().size(); i++)
						((LayeredChunkMap) carte.getChunk(editChoice)).sortByLayers(carte.getChunk(editChoice).getContenu().get(i));
				}
			}*/
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
			{
				//carte.verifyPosition(editChoice);
			}
			int mouse = Mouse.getDWheel();
			if(mouse > 0){
				this.actualCam.setZoom(this.actualCam.getZoom() * 1.2f);
			}
			if(mouse < 0){
				this.actualCam.setZoom(this.actualCam.getZoom() / 1.2f);
			}
		}
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
	 * @return the nateditChoiceInvisible
	 */
	public boolean isNateditChoiceInvisible() {
		return nateditChoiceInvisible;
	}
	/**
	 * @param nateditChoiceInvisible the nateditChoiceInvisible to set
	 */
	public void setNateditChoiceInvisible(boolean nateditChoiceInvisible) {
		this.nateditChoiceInvisible = nateditChoiceInvisible;
	}
}
