package gui.editeurs.objetmaps;

import gui.Container;
import gui.FComponent;
import gui.Text;
import gui.buttons.Button;
import gui.buttons.Onglet;
import gui.buttons.OngletManager;
import gui.editeurs.Editeur;
import gui.editeurs.Messages;
import gui.inputs.ProgrammTextArea;
import gui.jeu.Jeu;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import Level.Camera;
import ObjetMap.BasicObjetMap;
import ObjetMap.Chrono;
import ObjetMap.ObjetMap;


public class EditeurObjetMap extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasicObjetMapEditor objEditor;
	private Button evaluate;
	private Text console;
	
	//Scripts
		//Collide
	private ProgrammTextArea collidescript;
		//Update
	private ProgrammTextArea updatescript;
		//Click
	private ProgrammTextArea clickscript;
	
	//Onglets
	private Onglet ongletClick;
	private Onglet ongletUpdate;
	private Onglet ongletCollide;
	private OngletManager managerOnglet;
	public EditeurObjetMap(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		objEditor = new BasicObjetMapEditor(new BasicObjetMap(0,0,0,0,0,0), 0, 0, sizeX / 2, sizeY, this);
		this.addComponent(objEditor);
		initScriptZone();
	}
	public EditeurObjetMap(ObjetMap o, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		objEditor = new BasicObjetMapEditor(o, 0, 0, sizeX / 2, sizeY, this);
		this.addComponent(objEditor);
		initScriptZone();
	}
	public void initScriptZone(){
			managerOnglet = new OngletManager(sizeX / 2 , 0, sizeX / 2, sizeY, this);
			ongletCollide = new Onglet(Messages.getString("EditeurObjetMap.0"), managerOnglet); //$NON-NLS-1$
			ongletCollide.setContainer(new Container(0,50, managerOnglet.getSizeX(), managerOnglet.getSizeY() - 50, managerOnglet));
			collidescript = new ProgrammTextArea(0, 0, managerOnglet.getSizeX(), managerOnglet.getSizeY() - 50, ongletCollide.getContainer());
			collidescript.getInput().setContenu(objEditor.getObj().getCollideScript());
			ongletCollide.getContainer().addComponent(collidescript);
			managerOnglet.addComponent(ongletCollide);
			
			ongletUpdate = new Onglet(Messages.getString("EditeurObjetMap.1"), managerOnglet); //$NON-NLS-1$
			ongletUpdate.setContainer(new Container(0,50, managerOnglet.getSizeX(), managerOnglet.getSizeY() - 50, managerOnglet));
			updatescript = new ProgrammTextArea(0, 0, managerOnglet.getSizeX(), managerOnglet.getSizeY() - 50, ongletUpdate.getContainer());
			updatescript.getInput().setContenu(objEditor.getObj().getUpdateScript());
			ongletUpdate.getContainer().addComponent(updatescript);
			managerOnglet.addComponent(ongletUpdate);
			
			ongletClick = new Onglet(Messages.getString("EditeurObjetMap.2"), managerOnglet); //$NON-NLS-1$
			ongletClick.setContainer(new Container(0,50, managerOnglet.getSizeX(), managerOnglet.getSizeY() - 50, managerOnglet));
			clickscript = new ProgrammTextArea(0, 0, managerOnglet.getSizeX(), managerOnglet.getSizeY() - 50, ongletClick.getContainer());
			clickscript.getInput().setContenu(objEditor.getObj().getClickScript());
			ongletClick.getContainer().addComponent(clickscript);
			managerOnglet.addComponent(ongletClick);
			
			ongletCollide.clickPressed();
			ongletCollide.clickReleased();
		this.addComponent(managerOnglet);
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		ObjetMap o = objEditor.getObj();
		
		//Changement des scripts.
		o.setCollideScript(collidescript.getInput().getContenu());
		o.setUpdateScript(updatescript.getInput().getContenu());
		o.setClickScript(clickscript.getInput().getContenu());
		
		
		//Vérification du chunk de l'objet a modifier.
		if(o.getChunkX() > ((Editeur)(getRacine())).getEditeurMap().getCarte().getMapSizeX())
			o.setChunkX(0);
		if(o.getChunkY() > ((Editeur)(getRacine())).getEditeurMap().getCarte().getMapSizeY())
			o.setChunkY(0);
		if(o.getChunkZ() > ((Editeur)(getRacine())).getEditeurMap().getCarte().getMapSizeZ())
			o.setChunkZ(0);
		
		
		//Ajout dans la liste des objets a updater
		if(o.isUpdate() 
		&& ((Editeur)(getRacine())).getEditeurMap().getCarte().getChunks()[o.getChunkX()][o.getChunkY()][o.getChunkZ()].getContenu().contains(o)
		&& !((Editeur)(getRacine())).getEditeurMap().getCarte().getChunks()[o.getChunkX()][o.getChunkY()][o.getChunkZ()].getUpdatable().contains(o)){
			((Editeur)(getRacine())).getEditeurMap().getCarte().getChunks()[o.getChunkX()][o.getChunkY()][o.getChunkZ()].getUpdatable().add(o);
		}
	}
	public ObjetMap getObjetCible() {
		return objEditor.getObj();
	}
	public void setObjetCible(ObjetMap o) {
		((Editeur)this.getRacine()).setWorkedObj(o);
		this.components.remove(objEditor);
		objEditor = new BasicObjetMapEditor(o, 0, 0, getSizeX() / 2, getSizeY(), this);
		this.addComponent(objEditor);
		collidescript.getInput().setContenu(o.getCollideScript());
		updatescript.getInput().setContenu(o.getUpdateScript());
		clickscript.getInput().setContenu(o.getClickScript());
	}
	/**
	 * @return the console
	 */
	public Text getConsole() {
		return console;
	}
	/**
	 * @param console the console to set
	 */
	public void setConsole(Text console) {
		this.console = console;
	}
}
