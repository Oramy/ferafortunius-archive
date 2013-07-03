package gui.editeurs;

import gui.Container;
import gui.jeu.PanneauJeuAmeliore;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Level.ChunkMap;
import ObjetMap.Ensemble;
import ObjetMap.ObjetMap;


public class PanneauEditeur extends PanneauJeuAmeliore {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3104433715488521888L;
	private ObjetMap editChoice;
	private boolean nateditChoiceInvisible;
	private int oldx, oldy;
	private boolean clickr;
	private boolean clickl;
	private EditeurMap editeur;
	public PanneauEditeur(EditeurMap e,ChunkMap c, ObjetMap edit,  int x, int y , int sizeX, int sizeY, Container parent) {
		super(c, x,y,sizeX,sizeY, parent);
		this.editeur = e;
		oldx = sizeX / 2;
		oldy = sizeY / 2;
		setEditChoice(edit);
		clickr = false;
		clickl = false;
		
	
	}
	public void draw(Graphics g){
		super.draw(g);
		g.translate(getX(),getY());
		if(getEditChoice() != null){
			g.translate(this.getWidth()/2, this.getHeight()/2);
				g.translate(-actualCam.getX() * actualCam.getZoom(), -actualCam.getY() * actualCam.getZoom());
					this.drawMapLines(g);
					g.translate((float)(( + (float)(getEditChoice().getChunkX()* carte.getChunksSize()) - (float)(getEditChoice().getChunkY() * carte.getChunksSize())) + (float)(getEditChoice().getDecalageX())
							+ ( + (float)(getEditChoice().getPosX()) -  (float)(getEditChoice().getPosY()))) * actualCam.getZoom(),
			
							(float)((float)-(getEditChoice().getChunkY() * carte.getChunksSize() * 0.5) - (float)(getEditChoice().getChunkX()* carte.getChunksSize() * 0.5) - getEditChoice().getChunkZ()* carte.getChunksSize() + (float)(getEditChoice().getDecalageY())
									- (float)(getEditChoice().getPosY() * 0.5) - (float)(getEditChoice().getPosX() * 0.5)  - (float)(getEditChoice().getPosZ())) * actualCam.getZoom());
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
						else if(editeur.getEditeurMode() == EditeurMode.Placer){
							this.drawLines(g, editChoice);
						}
						
					g.translate(-(float)(( + (float)(getEditChoice().getChunkX()* carte.getChunksSize()) - (float)(getEditChoice().getChunkY() * carte.getChunksSize())) + (float)(getEditChoice().getDecalageX())
						+ ( + (float)(getEditChoice().getPosX()) -  (float)(getEditChoice().getPosY()))) * actualCam.getZoom(),
		
						-(float)((float)-(getEditChoice().getChunkY() * carte.getChunksSize() * 0.5) - (float)(getEditChoice().getChunkX()* carte.getChunksSize() * 0.5) - getEditChoice().getChunkZ()* carte.getChunksSize() + (float)(getEditChoice().getDecalageY())
								- (float)(getEditChoice().getPosY() * 0.5) - (float)(getEditChoice().getPosX() * 0.5)  - (float)(getEditChoice().getPosZ())) * actualCam.getZoom());
					
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
				}else if(editeur.getEditeurMode() == EditeurMode.Supprimer){
					if(surlignObject != null)
						carte.getChunks()[surlignObject.getChunkX()][surlignObject.getChunkY()][surlignObject.getChunkZ()].remove(surlignObject);
				}else if(editeur.getEditeurMode() == EditeurMode.Ensemble){
					Ensemble esb = editeur.getEnsembleChoice().clone();
					esb.setPosX(getEditChoice().getPosX());
					esb.setPosY(getEditChoice().getPosY());
					esb.setPosZ(getEditChoice().getPosZ());
					carte.getChunks()[0][0][0].addContenu(esb);
				}
			}
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !clickr){
				clickr = true;
				oldx =  gc.getInput().getMouseX();
				oldy =  gc.getInput().getMouseY();
			}
			else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && clickr){
				clickr = false;
				if(getSurlignObject() != null){
					Editeur editR = (Editeur)editeur.getRacine();
					editR.getEditeurObjetOnglet().clickPressed();
					editR.getEditeurObjetOnglet().clickReleased();
					editR.getEditeurObjet().setObjetCible(getSurlignObject());
					editR.getEditeurAnimation().setObjetCible(getSurlignObject().clone());
				}
			}
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clickl){
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
				}else if(editeur.getEditeurMode() == EditeurMode.Supprimer){
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
			if(gc.getInput().isKeyPressed(Input.KEY_R)){
				carte.deplacement(getEditChoice(), 0, 0, 1, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_F)){
				carte.deplacement(getEditChoice(), 0, 0, -1, null);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_F3)){
				//for(int i = 0; i < editeur.getCarte().getChunk(0,0,0).getContenu().size(); i++)
					//editeur.getCarte().getChunk(0,0,0).melanger(editeur.getCarte().getChunk(0,0,0).getContenu().get(i));
				
			}
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
			{
					
			
				int newx = gc.getInput().getMouseX();
				int newy = gc.getInput().getMouseY();
				getActualCam().move((oldx - newx) / actualCam.getZoom(), (oldy-newy)/ actualCam.getZoom());
				oldx = newx;
				oldy = newy;
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
