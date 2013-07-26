package gui.editeurs;

import observer.ActionListener;
import gui.Button;
import gui.Container;
import gui.FComponent;
import gui.GridLayout;
import gui.Label;
import gui.PImage;
import Level.EnsembleLoader;
import Level.MapLoader;
import ObjetMap.Ensemble;

public class MenuEditeurBar extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container labelNameSaveLoad;
	private Label nomMap;
	private Label pathMap;
	private EditeurMap editeur;
	public MenuEditeurBar(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		editeur = null;
		editeur = ((Editeur)getRacine()).getEditeurMap();
		this.setBackground(new PImage("GUI/containerBackgroundwithoutBordsBlackHorizontal.png")); //$NON-NLS-1$
		this.setActualLayout(new GridLayout(2, 1));
		
		labelNameSaveLoad =  new Container(0, 0, 1, 1, this);
			this.addComponent(labelNameSaveLoad);
			labelNameSaveLoad.setBackground(new PImage("alpha.png")); //$NON-NLS-1$
			labelNameSaveLoad.setActualLayout(new GridLayout(2,1));
			nomMap = new Label(Messages.getString("MenuEditeurBar.2"), Messages.getString("MenuEditeurBar.3"), labelNameSaveLoad); //$NON-NLS-1$ //$NON-NLS-2$
				
				nomMap.getInput().setContenu(editeur.getCarte().getNom());
			
			labelNameSaveLoad.addComponent(nomMap);
			
			Container saveLoad = new Container(0, 0, 1, 1, labelNameSaveLoad);
				labelNameSaveLoad.addComponent(saveLoad);
				saveLoad.setActualLayout(new GridLayout(3,1));
				saveLoad.setBackground(new PImage("alpha.png")); //$NON-NLS-1$
				((GridLayout) saveLoad.getActualLayout()).setHgap(2);
				
				Button save = new Button(Messages.getString("MenuEditeurBar.5"), saveLoad); //$NON-NLS-1$
				
					save.getAction().add(new ActionListener(){
						public void actionPerformed(FComponent c){
							EditeurMap edit = ((Editeur)(c.getRacine())).getEditeurMap();
							save();
							edit.getCarte().getChunks()[edit.getEditChoice().getChunkX()][edit.getEditChoice().getChunkY()][edit.getEditChoice().getChunkZ()].addContenu(editeur.getEditChoice());
						}
					});
					
				saveLoad.addComponent(save);
				Button saveAsEnsemble = new Button("Ensemble S", saveLoad); //$NON-NLS-1$
				
				saveAsEnsemble.getAction().add(new ActionListener(){
					public void actionPerformed(FComponent c){
						saveAsEnsemble();
					}
				});	
				saveLoad.addComponent(saveAsEnsemble);
				Button load = new Button(Messages.getString("MenuEditeurBar.6"), saveLoad); //$NON-NLS-1$
				
					load.getAction().add(new ActionListener(){
						public void actionPerformed(FComponent c){
							EditeurMap edit = ((Editeur)(c.getRacine())).getEditeurMap();
							load();
							edit.getCarte().getChunks()[edit.getEditChoice().getChunkX()][edit.getEditChoice().getChunkY()][edit.getEditChoice().getChunkZ()].addContenu(editeur.getEditChoice());
						}
					});
					
				saveLoad.addComponent(load);
			
		
		Container pathMapPlay = new Container(0, 0, 1, 1, this);
			this.addComponent(pathMapPlay);
			pathMapPlay.setBackground(new PImage("alpha.png")); //$NON-NLS-1$
			pathMapPlay.setActualLayout(new GridLayout(2, 1));
				
				setPathMap(new Label(Messages.getString("MenuEditeurBar.8"), "", pathMapPlay)); //$NON-NLS-1$ //$NON-NLS-2$
					getPathMap().getInput().setContenu(editeur.getMaptoLoad());
					
					Button play = new Button(Messages.getString("MenuEditeurBar.10"), pathMapPlay); //$NON-NLS-1$
					play.getAction().add(new ActionListener(){
						public void actionPerformed(FComponent c){
							editeur.playMap();
							
						}
					});
					
			pathMapPlay.addComponent(getPathMap());
			pathMapPlay.addComponent(play);
	
	}
	protected void save() {
		editeur.getCarte().getChunks()[editeur.getEditChoice().getChunkX()][editeur.getEditChoice().getChunkY()][editeur.getEditChoice().getChunkZ()].remove(editeur.getEditChoice());
		editeur.getCarte().setNom(nomMap.getInput().getContenu());
		MapLoader.saveMap(editeur.getCarte(), "data/Maps/" + getPathMap().getInput().getContenu() + ".dat"); //$NON-NLS-1$ //$NON-NLS-2$
		editeur.getCarte().getChunks()[editeur.getEditChoice().getChunkX()][editeur.getEditChoice().getChunkY()][editeur.getEditChoice().getChunkZ()].addContenu(editeur.getEditChoice());
	}
	protected void saveAsEnsemble() {
		
		editeur.getCarte().getChunks()[editeur.getEditChoice().getChunkX()][editeur.getEditChoice().getChunkY()][editeur.getEditChoice().getChunkZ()].remove(editeur.getEditChoice());
		editeur.getCarte().setNom(nomMap.getInput().getContenu());
		
		EnsembleLoader.saveEnsemble(Ensemble.convertMap(editeur.getCarte()), "data/Ensembles/" + getPathMap().getInput().getContenu() + ".esb"); //$NON-NLS-1$ //$NON-NLS-2$
		editeur.getCarte().getChunks()[editeur.getEditChoice().getChunkX()][editeur.getEditChoice().getChunkY()][editeur.getEditChoice().getChunkZ()].addContenu(editeur.getEditChoice());
	}
	protected void load() {
		String path = "data/Maps/" + getPathMap().getInput().getContenu() + ".dat";
		if(MapLoader.hasAutoSave(path)){
			//TODO Prompt "Some more recents auto-saves are availables, do you want to load them ? Y/N
		}
		else{
			editeur.setCarte(MapLoader.loadMap(path)); //$NON-NLS-1$ //$NON-NLS-2$
		}
		nomMap.getInput().setContenu(editeur.getCarte().getNom());
		editeur.getPanneau().setCarte(editeur.getCarte());
	}
	/**
	 * @return the pathMap
	 */
	public Label getPathMap() {
		return pathMap;
	}
	/**
	 * @param pathMap the pathMap to set
	 */
	public void setPathMap(Label pathMap) {
		this.pathMap = pathMap;
	}
	
}
