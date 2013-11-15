package gui.editeurs;

import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.GameMain;
import gui.InternalFrame;
import gui.ModeJeu;
import gui.ScrollBar;
import gui.buttons.Button;
import gui.buttons.ButtonObjetMap;
import gui.buttons.CheckBox;
import gui.layouts.GridLayout;
import gui.layouts.MinHeightLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import Level.Camera;
import Level.ChunkMap;
import Level.EnsembleLoader;
import Level.MapLoader;
import Level.ObjetMapLoader;
import ObjetMap.BasicObjetMap;
import ObjetMap.Ensemble;
import ObjetMap.ObjetMap;


public class EditeurMap extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2069536217993306581L;
	//Containers de l'éditeur.
	private PanneauEditeur panneau;
	private Container choixObjets;
	private Container choixEnsembles;
	
	private ScrollBar scrollbar;
	private ChunkMap carte;
	private ObjetMap editChoice;
	private Ensemble ensembleChoice;
	private ArrayList<ObjetMap> editObject;
	private ArrayList<Ensemble> editEnsemble;
	private Camera cam;
	private GameMain gm;
	private MenuEditeur menu;
	private	String maptoLoad = ""; //$NON-NLS-1$
	private String toDo = ""; //$NON-NLS-1$
	private EditeurMode editeurMode;
	
	/*
	 * Recharge tous les objets de l'éditeur
	 */
	public void reloadObjects(){
		getEditObject().clear();
		loadObjetMap("data/ObjetMap/");
	}
	public ArrayList<ObjetMap> loadObjetMap(String path){
		if(this.components.contains(choixObjets))
			this.components.remove(choixObjets);
		choixObjets = new Container(sizeX / 10 * 8, 0, sizeX / 10 * 2, sizeY / 20 * 18, this);
		this.addComponent(choixObjets);
		scrollbar = new ScrollBar(0,0, choixObjets.getSizeX(), choixObjets.getSizeY() - 30, 0, 0, choixObjets);
		

		Container contgrid = new Container(0, 0, choixObjets.getSizeX()- 20, 1, scrollbar);
		contgrid.setActualLayout(new MinHeightLayout());
		ArrayList<ObjetMap> liste = loadFolder(0, 0,path, contgrid);
		
			//Bouton "Reload"
			Button reload = new Button(Messages.getString("EditeurMap.0"), choixObjets); //$NON-NLS-1$
			reload.getAction().add(new ActionListener(){
				public void actionPerformed(FComponent C){
					reloadObjects();
				}
			});
			choixObjets.addComponent(reload);
			reload.setBounds(0, choixEnsembles.getSizeY() - 30, choixEnsembles.getSizeX(), 30);
			
			
			contgrid.setBackground(Container.backGroundUnbordsBlack); //$NON-NLS-1$
			scrollbar.setContainer(contgrid);
			choixObjets.getComponents().add(scrollbar);
			choixObjets.setBackground(Container.backGroundUnbordsBlackHorizontal); //$NON-NLS-1$
		return liste;
	} 
	public int getObjectSavedCount(String path, int count){
		File[] files = null;
		File directory = new File(path);
		files = directory.listFiles();
		Arrays.sort(files);
		if(files != null){
			for(int i = 0; i < files.length; i++){
				File file = files[i];
				if(file.isDirectory()){
					count += getObjectSavedCount(file.getPath(), 0);
				}
				else if((!file.isDirectory()) && file.getPath().contains(".obj")){ //$NON-NLS-1$
					count++;
				}
			}
		}
		return count;
					
	}
	public ArrayList<ObjetMap> loadFolder(int x, int y, String path, Container parent){
		
		ArrayList<ObjetMap> liste = new ArrayList<ObjetMap>();
		File[] files = null;
		File directory = new File(path);
		files = directory.listFiles();
		Arrays.sort(files);
		if(files != null){
			for(int i = 0; i < files.length; i++){
				File file = files[i];
				if(file.isDirectory()){
					int ancientY = y;
					y = 0;
					InternalFrame contFolder = new InternalFrame(10,ancientY, parent.getSizeX() - 20,1, file.getName().toUpperCase(), parent);
					contFolder.setDocked(true);
					contFolder.getContainer().setActualLayout(new MinHeightLayout());
					parent.addComponent(contFolder);
					
					ArrayList<ObjetMap> folder = loadFolder(x, y, file.getPath() + "/", contFolder.getContainer());
					contFolder.getContainer().setSizeY(160 * folder.size());
					liste.addAll(folder);
					y += ancientY + 160 * (folder.size());
				}
				else if((!file.isDirectory()) && file.getPath().contains(".obj")){ //$NON-NLS-1$
					ObjetMap o = ObjetMapLoader.loadObject(file.getPath());
					parent.addComponent(getObjetMapContainer(x, y,o, parent));
					y += 160;
					parent.setSizeY(parent.getSizeY() + 160);
					liste.add(o);
				}
			}
		}
		return liste;
	}
	
	public Container getObjetMapContainer(int x, int y, ObjetMap o, Container parent){
		ContainerWithBords objContainer = new ContainerWithBords(x + 10, y, parent.getSizeX() - 20,160, parent);
		parent.setSizeY(parent.getSizeY() + 160);
		objContainer.setActualLayout(new GridLayout(2,2));
		((GridLayout) objContainer.getActualLayout()).setHgap(6);
		if(o != null){
			ButtonObjetMap selectButton = (ButtonObjetMap) new ButtonObjetMap(o, 0,0,200,200,objContainer);
			selectButton.getAction().add(new ActionListener(){
				public void actionPerformed(FComponent c){
					EditeurMap edit = ((Editeur)(c.getRacine())).getEditeurMap();
					ObjetMap ed = editChoice;
					ed.setInvisible(panneau.isNateditChoiceInvisible());
					
					ObjetMap o = (ObjetMap) ((ButtonObjetMap)c).getObjet();
					
					//Changement des positions du nouvel objet.
					o.setChunkX(ed.getChunkX());
					o.setChunkY(ed.getChunkY());
					o.setChunkZ(ed.getChunkZ());
					o.setPosX(ed.getPosX());
					o.setPosY(ed.getPosY());
					o.setPosZ(ed.getPosZ());
					
					
					//Transformation de l'objet en objet à déplacer.
					panneau.setNateditChoiceInvisible(o.isInvisible());
					o.setInvisible(true);
					if(!editeurMode.equals(EditeurMode.Selection))
						carte.getChunks()[ed.getChunkX()][ed.getChunkY()][ed.getChunkZ()].remove(ed);
					edit.setEditChoice(o);
					carte.getChunks()[ed.getChunkX()][ed.getChunkY()][ed.getChunkZ()].addContenu(o);
					
					if(editeurMode.equals(EditeurMode.Supprimer))
						editeurMode = EditeurMode.Placer;
				}
			});
			//Boutons disponibles pour chaque objets.
			
			objContainer.addComponent(selectButton);
		}
		
		ContainerWithBords buttonContainer = new ContainerWithBords(0,0,1,1, objContainer);
		buttonContainer.setActualLayout(new GridLayout(1, 2));
		((GridLayout) buttonContainer.getActualLayout()).setVgap(5);
		
		
		Button acheter = new Button(Messages.getString("EditeurMap.6"), buttonContainer); //$NON-NLS-1$
		
		Button infos = new Button(Messages.getString("EditeurMap.7"), buttonContainer); //$NON-NLS-1$
		infos.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				EditeurMap edit = ((Editeur)(c.getRacine())).getEditeurMap();
				ObjetMap o = ((ButtonObjetMap)(c.getParent().getParent().getComponents().get(0))).getObjet().clone();
				InternalFrame frame = new InternalFrame(200,200,getGm().getApp().getGraphics().getFont().getWidth(Messages.getString("EditeurMap.8") + o.getNom() + Messages.getString("EditeurMap.9")) + 85,200,Messages.getString("EditeurMap.10") + o.getNom() + Messages.getString("EditeurMap.11"), edit); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				edit.addComponent(frame);
			}
		});
		
		objContainer.addComponent(buttonContainer);
		
		
		buttonContainer.addComponent(acheter);		
		buttonContainer.addComponent(infos);
		Container checkbox = new Container(1,1,1,1, objContainer);
		checkbox.setBackground(Container.alpha); //$NON-NLS-1$
		CheckBox miroir = new CheckBox(Messages.getString("EditeurMap.13"), checkbox); //$NON-NLS-1$
		miroir.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				ObjetMap o = ((ButtonObjetMap)(c.getParent().getParent().getComponents().get(0))).getObjet();
				o.setMirror(((CheckBox)c).isCheck());
			}
		});
		checkbox.addComponent(miroir);
		objContainer.addComponent(checkbox);
		
		Button modifier = new Button(Messages.getString("EditeurMap.14"), objContainer); //$NON-NLS-1$
		modifier.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				EditeurMap edit = ((Editeur)(c.getRacine())).getEditeurMap();
				Editeur editR = ((Editeur)(c.getRacine()));
				ObjetMap o = ((ButtonObjetMap)(c.getParent().getComponents().get(0))).getObjet();
				if(edit.getEditChoice() == o){
					o = o.clone();
				}
				editR.getEditeurObjetOnglet().clickPressed();
				editR.getEditeurObjetOnglet().clickReleased();
				editR.getEditeurObjet().setObjetCible(o);
			}
		});
		objContainer.addComponent(modifier);
		return objContainer;
	}
	/*
	 * Recharge tous les ensembles de l'éditeur
	 */
	public void reloadEnsemble(){
		editEnsemble.clear();
		editEnsemble = EnsembleLoader.loadFolder("data/Ensembles/"); //$NON-NLS-1$
		initChoixEnsemble();
	}
	public EditeurMap(int x, int y, int sizeX, int sizeY, Container parent, GameMain gameMain, GameContainer gc) {
		super(x, y, sizeX, sizeY, parent);
		setGm(gameMain);
		toDo = ""; //$NON-NLS-1$
		
	}
	public EditeurMap(GameMain gameMain, GameContainer gc) {
		super(0,0,1600,900, null);
		setGm(gameMain);
		toDo = ""; //$NON-NLS-1$
		init(gc);
	}
	public void update(GameContainer gc, int x, int y ) {
		panneau.setEditChoice(editChoice);
		super.update(gc, x, y);
		if(toDo.equals("playMap")){ //$NON-NLS-1$
			playMap();
		}
		this.panneau.getActualCam().setMinzoom(0.2f);
	}
	public int allSize(){
		int i = 0;
		for(ObjetMap o : editObject){
			i += o.getImage().get(0).getImageSizeInGameY();
		}
		return i;
	}
	public void initChoixEnsemble(){
		if(this.components.contains(choixEnsembles))
			this.components.remove(choixEnsembles);
		choixEnsembles = new Container(sizeX / 10 * 6, 0, sizeX / 10 * 2, sizeY / 20 * 18, this);
		this.addComponent(choixEnsembles);
		scrollbar = new ScrollBar(0,0, choixEnsembles.getSizeX(), choixEnsembles.getSizeY() - 30, 0, 0, choixEnsembles);
		
		Container cont = new Container(0, 0, choixEnsembles.getSizeX(), (editEnsemble.size()) * 30, scrollbar);
		
		Container contgrid = new Container(0, 0, choixEnsembles.getSizeX() - 75, (editEnsemble.size()) * 30, cont);
		contgrid.setActualLayout(new GridLayout(1, editEnsemble.size()));
		((GridLayout) contgrid.getActualLayout()).setVgap(2);
		
		for(Ensemble o : editEnsemble){
			
			Button selectButton = new Button(o.getNom(), 0,0, 30, 30, contgrid);
			
			selectButton.getAction().add(new ActionListener(){
				public void actionPerformed(FComponent c){
					EditeurMap edit = ((Editeur)(c.getRacine())).getEditeurMap();
					
					Ensemble lastEnsemble = ensembleChoice;
					Ensemble o = editEnsemble.get((c.getY()) / 30);
					
					if(lastEnsemble != null){
						//Changement des positions du nouvel objet.
						o.setPosX(lastEnsemble.getPosX());
						o.setPosY(lastEnsemble.getPosY());
						o.setPosZ(lastEnsemble.getPosZ());
						carte.getChunks()[0][0][0].remove(lastEnsemble);
						System.out.println("ouf");
					}
					edit.setEnsembleChoice(o);
					System.out.println(o.getNom());
					editeurMode = EditeurMode.Ensemble;
				}
			});
			//Boutons disponibles pour chaque objets.
			contgrid.addComponent(selectButton);
			
		}
		//Bouton "Reload"
		Button reload = new Button(Messages.getString("EditeurMap.0"), choixEnsembles); //$NON-NLS-1$
		reload.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent C){
				reloadEnsemble();
			}
		});
		choixEnsembles.addComponent(reload);
		reload.setBounds(0, choixEnsembles.getSizeY() - 30, choixEnsembles.getSizeX(), 30);
		
		
		contgrid.setBackground(null);
		cont.setBackground(Container.backGroundUnbordsBlack); //$NON-NLS-1$
		cont.addComponent(contgrid);
		scrollbar.setContainer(cont);
		choixEnsembles.getComponents().add(scrollbar);
		choixEnsembles.setBackground(Container.backGroundUnbordsBlackHorizontal); //$NON-NLS-1$
	}
	public void initCarte(){
		setCarte(MapLoader.loadMap("data/Maps/"+ getMaptoLoad() + ".dat")); //$NON-NLS-1$ //$NON-NLS-2$
		carte.getChunks()[0][0][0].addContenu(editChoice);
		setEditeurMode(EditeurMode.Placer);
	}
	public void initPanneauEdit(){
		setPanneau(new PanneauEditeur(this, getCarte(), editChoice, 0,0, sizeX / 10 * 6, sizeY / 20 * 18, this));
		setCam(new Camera(0, 0, 1f, getCarte()));
		getPanneau().setActualCam(getCam());
		this.addComponent(getPanneau());
	}
	public void initMenu(){
		menu = new MenuEditeur(0, sizeY / 20 * 18, sizeX, sizeY / 20 * 2, this);
		this.addComponent(menu);	
	}
	public void init(GameContainer gc){
		gc.setShowFPS(false);
		this.background = Container.alpha; //$NON-NLS-1$
		//Chargement
			//Chargement des objets
				setEditObject(new ArrayList<ObjetMap>());
				
				editEnsemble = new ArrayList<Ensemble>();
				reloadEnsemble();
				setEditChoice(new BasicObjetMap(0,0,0,0,0,0));
			//Chargement de la carte
				initCarte();
			//Composants
				
				//Affichage de la carte
					initPanneauEdit();
				//Affichage du menu
					initMenu();
					editObject = loadObjetMap("data/ObjetMap/");
					initChoixEnsemble();
				
				
				
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
	 * @return the carte
	 */
	public ChunkMap getCarte() {
		return carte;
	}
	/**
	 * @param carte the carte to set
	 */
	public void setCarte(ChunkMap carte) {
		this.carte = carte;
		if(panneau != null)
		panneau.setCarte(carte);
		
	}
	/**
	 * @return the scrollbarr
	 */
	public ScrollBar getScrollBar() {
		return scrollbar;
	}
	/**
	 * @param scrollbarr the scrollbarr to set
	 */
	public void setScrollBar(ScrollBar scrollbar) {
		this.scrollbar = scrollbar;
	}
	/**
	 * @return the panneau
	 */
	public PanneauEditeur getPanneau() {
		return panneau;
	}
	/**
	 * @param panneau the panneau to set
	 */
	public void setPanneau(PanneauEditeur panneau) {
		this.panneau = panneau;
	}
	/**
	 * @return the editObject
	 */
	public ArrayList<ObjetMap> getEditObject() {
		return editObject;
	}
	/**
	 * @param editObject the editObject to set
	 */
	public void setEditObject(ArrayList<ObjetMap> editObject) {
		this.editObject = editObject;
	}
	/**
	 * @return the cam
	 */
	public Camera getCam() {
		return cam;
	}
	/**
	 * @param cam the cam to set
	 */
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	/**
	 * @return the editeurMode
	 */
	public EditeurMode getEditeurMode() {
		return editeurMode;
	}
	/**
	 * @param editeurMode the editeurMode to set
	 */
	public void setEditeurMode(EditeurMode editeurMode) {
		this.editeurMode = editeurMode;
	}
	/**
	 * @return the maptoLoad
	 */
	public String getMaptoLoad() {
		return maptoLoad;
	}
	/**
	 * @param maptoLoad the maptoLoad to set
	 */
	public void setMaptoLoad(String maptoLoad) {
		this.maptoLoad = maptoLoad;
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
	public void playMap() {
		getGm().initMode(ModeJeu.Jeu, getGm().getApp());
		setCarte(MapLoader.loadMap("data/Maps/" + menu.getBarre().getPathMap().getInput().getContenu() + ".dat")); //$NON-NLS-1$ //$NON-NLS-2$
		getGm().getJeu().setCarte(getCarte());
		getGm().getJeu().getPanneauDuJeu().setCarte(getCarte());
		getGm().getJeu().init(getGm().getApp(), getCarte());
	}
	public Ensemble getEnsembleChoice() {
		return ensembleChoice;
	}
	public void setEnsembleChoice(Ensemble ensembleChoice) {
		this.ensembleChoice = ensembleChoice;
	}

}
