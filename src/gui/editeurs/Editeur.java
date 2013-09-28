package gui.editeurs;

import gui.Container;
import gui.GameMain;
import gui.buttons.Onglet;
import gui.buttons.OngletManager;
import gui.editeurs.animations.EditeurAnimationKey;
import gui.editeurs.items.EditeurItem;
import gui.editeurs.objetmaps.EditeurAnimation;
import gui.editeurs.objetmaps.EditeurCollObjMap;
import gui.editeurs.objetmaps.EditeurEntity;
import gui.editeurs.objetmaps.EditeurImageRender;
import gui.editeurs.objetmaps.EditeurObjetMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ObjetMap.BasicObjetMap;
import ObjetMap.ObjetMap;


public class Editeur extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color transitionColor;
	
	private EditeurMap editeurMap;
	
	private GameMain gm;
	
	private OngletManager onglets;
	
	//Objetmap Editor
	private ObjetMap			workedObj;
	private OngletManager		ongletsObj;
	
	private EditeurObjetMap 	editeurObjetMapGeneral;
	private EditeurCollObjMap 	editeurObjetMapColli;
	private EditeurEntity 	editeurEntity;
	
	private Onglet 				editeurObjetOnglet;
	
	private EditeurItem 	editeurItem;
	
	private Onglet editeurAnimationOnglet;
	private EditeurAnimation editeurAnimation;
	
	private Onglet editeurAnimationKeyOnglet;
	private EditeurAnimationKey editeurAnimationKey;
	
	private EditeurImageRender editeurImageRender;
	public Editeur(GameMain gameMain, GameContainer gc) {
		super(0,0, gc.getWidth(), gc.getHeight(), null);
		this.setGm(gameMain);
	}
	public void paintComponent(GameContainer container, Graphics g) {
		this.drawBegin(g);
		this.draw(g);
		g.setColor(transitionColor);
		g.fillRect(0, 0, getSizeX(), getSizeY());
		this.drawEnd(g);
		//System.out.println("Temps d'affichage :" + paintTime + "ms");
	}
	public void update(GameContainer gc, int arg1) {
		super.update(gc, this.getX(), this.getY());
	}
	public void initOnglets(GameContainer gc){
		
		//Initialisation du manager d'onglets
		onglets = new OngletManager(0,0,getSizeX(), getSizeY(), this);
		onglets.setBackground(Container.backGroundUnbordsBlackHorizontal);
		this.addComponent(onglets);
		
		
		//EditeurMap
		Onglet ongletEditeurMap = new Onglet(Messages.getString("Editeur.1"), onglets); //$NON-NLS-1$
		
		setEditeurMap(new EditeurMap(0, 50, gc.getWidth(), gc.getHeight() - 50, onglets, getGm(), gc));
		editeurMap.init(gc);
		
		ongletEditeurMap.setContainer(editeurMap);
		onglets.addComponent(ongletEditeurMap);
		
		//EditeurMap Caracteristics.
		Onglet editeurMapC = new Onglet(Messages.getString("Editeur.2"), onglets); //$NON-NLS-1$
		
		Container mapC = new Container(0, 50, gc.getWidth(), gc.getHeight() - 50, onglets);
		editeurMapC.setContainer(mapC);
		
		onglets.addComponent(editeurMapC);
		
		//Editeur Objets
		setEditeurObjetOnglet(new Onglet(Messages.getString("Editeur.3"), onglets)); //$NON-NLS-1$
		
		ongletsObj = new OngletManager(0, 50,  gc.getWidth(), gc.getHeight() - 50, onglets);
		
			//General
			editeurObjetMapGeneral = new EditeurObjetMap(workedObj, 0, 50, gc.getWidth(), gc.getHeight() - 100, ongletsObj);
			
			Onglet general = new Onglet(Messages.getString("Editeur.0"), ongletsObj); //$NON-NLS-1$
			general.setContainer(editeurObjetMapGeneral);
			
			ongletsObj.addComponent(general);
			
			//Collision
			editeurObjetMapColli = new EditeurCollObjMap(workedObj, 0, 50, gc.getWidth(), gc.getHeight() - 100, ongletsObj);
			
			Onglet collision = new Onglet(Messages.getString("Editeur.9"), ongletsObj); //$NON-NLS-1$
			collision.setContainer(editeurObjetMapColli);
			
			ongletsObj.addComponent(collision);
			
			//Images.
			editeurImageRender = new EditeurImageRender(workedObj, 0, 50, gc.getWidth(), gc.getHeight() - 100, ongletsObj);
			
			Onglet imageRenderOnglet = new Onglet("Images", ongletsObj); //$NON-NLS-1$
			imageRenderOnglet.setContainer(editeurImageRender);
			
			ongletsObj.addComponent(imageRenderOnglet);
			
			//Entity
			Onglet editeurEntityOnglet = new Onglet(Messages.getString("Editeur.8"), ongletsObj); //$NON-NLS-1$
			
			editeurEntity = new EditeurEntity(workedObj, 0, 50, gc.getWidth(), gc.getHeight() - 50, ongletsObj);
			editeurEntityOnglet.setContainer(editeurEntity);
			
			ongletsObj.addComponent(editeurEntityOnglet);
			
			//Animation Keys
			editeurAnimationKeyOnglet  = new Onglet("AnimationsKeys", ongletsObj); //$NON-NLS-1$
			
			editeurAnimationKey = new EditeurAnimationKey(new BasicObjetMap(0,0,0,0,0,0), 0,50, gc.getWidth(), gc.getHeight() - 50, ongletsObj);
			editeurAnimationKeyOnglet.setContainer(editeurAnimationKey);
			
			ongletsObj.addComponent(editeurAnimationKeyOnglet);

			
			general.clickPressed();
			general.clickReleased();
			
		getEditeurObjetOnglet().setContainer(ongletsObj);
		
		onglets.addComponent(getEditeurObjetOnglet());
		
		//Animations.
		setEditeurAnimationOnglet(new Onglet(Messages.getString("Editeur.4"), onglets)); //$NON-NLS-1$
		
		setEditeurAnimation(new EditeurAnimation(new BasicObjetMap(0,0,0,0,0,0), 0,50, gc.getWidth(), gc.getHeight() - 50, onglets));
		getEditeurAnimationOnglet().setContainer(editeurAnimation);
		
		onglets.addComponent(getEditeurAnimationOnglet());

		
		
		//Items
		Onglet editeurItemOnglet = new Onglet(Messages.getString("Editeur.5"), onglets); //$NON-NLS-1$
		
		editeurItem = new EditeurItem(0, 50, gc.getWidth(), gc.getHeight() - 50, onglets);
		editeurItemOnglet.setContainer(editeurItem);
		
		onglets.addComponent(editeurItemOnglet);

		//Quest
		Onglet editeurQuest = new Onglet(Messages.getString("Editeur.6"), onglets); //$NON-NLS-1$
		
		Container quetes = new Container(0, 50, gc.getWidth(), gc.getHeight() - 50, onglets);
		editeurQuest.setContainer(quetes);
		
		onglets.addComponent(editeurQuest);

		//Compétence.
		Onglet editeurComp = new Onglet(Messages.getString("Editeur.7"), onglets); //$NON-NLS-1$
		
		Container comp = new Container(0, 50, gc.getWidth(), gc.getHeight() - 50, onglets);
		editeurComp.setContainer(comp);
		
		onglets.addComponent(editeurComp);


		ongletEditeurMap.clickPressed();
		ongletEditeurMap.clickReleased();
	}
	public void init(GameContainer gc){
		setWorkedObj(new BasicObjetMap(0,0,0,0,0, 0));
		initOnglets(gc);

		this.background = Container.alpha; //$NON-NLS-1$
		Thread t = new Thread(new Runnable(){
			public void run(){

				for(int i = 0; i < 255; i++){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					transitionColor = new Color(0,0,0, 255-i);

				}

			}
		});	
		t.start();
	}
	/**
	 * @return the editeurMap
	 */
	public EditeurMap getEditeurMap() {
		return editeurMap;
	}
	/**
	 * @param editeurMap the editeurMap to set
	 */
	public void setEditeurMap(EditeurMap editeurMap) {
		this.editeurMap = editeurMap;
	}
	/**
	 * @return the onglets
	 */
	public OngletManager getOnglets() {
		return onglets;
	}
	/**
	 * @param onglets the onglets to set
	 */
	public void setOnglets(OngletManager onglets) {
		this.onglets = onglets;
	}
	/**
	 * @return the editeurObjet
	 */
	public EditeurObjetMap getEditeurObjet() {
		return editeurObjetMapGeneral;
	}
	/**
	 * @param editeurObjet the editeurObjet to set
	 */
	public void setEditeurObjet(EditeurObjetMap editeurObjet) {
		this.editeurObjetMapGeneral = editeurObjet;
	}
	/**
	 * @return the editeurObjetOnglet
	 */
	public Onglet getEditeurObjetOnglet() {
		return editeurObjetOnglet;
	}
	/**
	 * @param editeurObjetOnglet the editeurObjetOnglet to set
	 */
	public void setEditeurObjetOnglet(Onglet editeurObjetOnglet) {
		this.editeurObjetOnglet = editeurObjetOnglet;
	}
	/**
	 * @return the gm
	 */
	public GameMain getGm() {
		return gm;
	}
	/**
	 * @param gm the gm to set
	 */
	public void setGm(GameMain gm) {
		this.gm = gm;
	}
	/**
	 * @return the editeurItem
	 */
	public EditeurItem getEditeurItem() {
		return editeurItem;
	}
	/**
	 * @param editeurItem the editeurItem to set
	 */
	public void setEditeurItem(EditeurItem editeurItem) {
		this.editeurItem = editeurItem;
	}
	/**
	 * @return the editeurAnimation
	 */
	public EditeurAnimation getEditeurAnimation() {
		return editeurAnimation;
	}
	/**
	 * @param editeurAnimation the editeurAnimation to set
	 */
	public void setEditeurAnimation(EditeurAnimation editeurAnimation) {
		this.editeurAnimation = editeurAnimation;
	}
	/**
	 * @return the editeurAnimationOnglet
	 */
	public Onglet getEditeurAnimationOnglet() {
		return editeurAnimationOnglet;
	}
	/**
	 * @param editeurAnimationOnglet the editeurAnimationOnglet to set
	 */
	public void setEditeurAnimationOnglet(Onglet editeurAnimationOnglet) {
		this.editeurAnimationOnglet = editeurAnimationOnglet;
	}
	/**
	 * @return the editeurEntity
	 */
	public EditeurEntity getEditeurEntity() {
		return editeurEntity;
	}
	/**
	 * @param editeurEntity the editeurEntity to set
	 */
	public void setEditeurEntity(EditeurEntity editeurEntity) {
		this.editeurEntity = editeurEntity;
	}
	/**
	 * @return the workedObj
	 */
	public ObjetMap getWorkedObj() {
		return workedObj;
	}
	/**
	 * @param workedObj the workedObj to set
	 */
	public void setWorkedObj(ObjetMap workedObj) {
		if(editeurObjetMapColli != null){
			editeurObjetMapColli.setObj(workedObj);
			editeurEntity.setObj(workedObj);
			editeurImageRender.setObj(workedObj);
			editeurAnimationKey.setWorkedObj(workedObj);
		}
		this.workedObj = workedObj;
	}
}
