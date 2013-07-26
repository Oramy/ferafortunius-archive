package gui.editeurs;

import gui.Button;
import gui.Container;
import gui.FComponent;
import gui.Onglet;
import gui.OngletManager;
import gui.ProgrammTextArea;
import gui.Text;
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
	private ImgEditor imgEditor;
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
		objEditor = new BasicObjetMapEditor(new BasicObjetMap(0,0,0,0,0,0), 0, 0, sizeX / 2, sizeY / 2, this);
		this.addComponent(objEditor);
		setImgEditor(new ImgEditor(sizeX / 2, 0, sizeX / 2, sizeY / 6 * 5, objEditor.getObj(),this));
		this.addComponent(getImgEditor());
		initScriptZone();
		initDebugZone();
		
	}
	public EditeurObjetMap(ObjetMap o, int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		objEditor = new BasicObjetMapEditor(o, 0, 0, sizeX / 2, sizeY / 2, this);
		this.addComponent(objEditor);
		setImgEditor(new ImgEditor(sizeX / 2, 0, sizeX / 2, sizeY / 6 * 5, objEditor.getObj(),this));
		this.addComponent(getImgEditor());
		
		initScriptZone();
		initDebugZone();
		
		
	}
	public void initDebugZone(){
		evaluate = new Button(Messages.getString("EditeurObjetMap.3"), this); //$NON-NLS-1$
		evaluate.setSizeY(50);
		evaluate.setY(sizeY  - 100);
		evaluate.setX(sizeX / 4 - evaluate.getSizeX() / 2);
		evaluate.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				EditeurObjetMap o = (EditeurObjetMap) c.getParent();
				ScriptEngineManager manager = new ScriptEngineManager(); 
			    ScriptEngine moteur = manager.getEngineByName("rhino");  //$NON-NLS-1$
			     try { 
			     
			      Bindings bindings = moteur.getBindings(ScriptContext.ENGINE_SCOPE); 
			      bindings.clear(); 
			      //Ajout de la variable entree dans le script
			      bindings.put("himself", objEditor.getObj().clone()); //$NON-NLS-1$
			      bindings.put("cible", objEditor.getObj().clone()); //$NON-NLS-1$
			      bindings.put("emptytext", new Text("", o)); //$NON-NLS-1$ //$NON-NLS-2$
			      bindings.put("newcam", new Camera(0,0,1f, null)); //$NON-NLS-1$
			      bindings.put("currentTimeMillis", System.currentTimeMillis()); //$NON-NLS-1$
			      bindings.put("emptychrono", new Chrono(System.currentTimeMillis(), "Real time")); //$NON-NLS-1$ //$NON-NLS-2$
			      bindings.put("jeu", new Jeu(((Editeur)o.getRacine()).getGm(), ((Editeur)o.getRacine()).getGm().getApp())); //$NON-NLS-1$
			      //Execution du script entr�e
			      if(o.collidescript.getInput().getContenu() != null)
				      moteur.eval(o.collidescript.getInput().getContenu(), bindings);
				 } catch (ScriptException e) { 
			    	o.getConsole().setText(e.getStackTrace().toString()); 
			    } 
				
			}
		});
		this.addComponent(evaluate);
		console = new Text("", this); //$NON-NLS-1$
		console.setX(sizeX / 2 + 50);
		console.setY(sizeY / 2 + 50);
		console.setSizeX(sizeX / 2 - 100);
		console.setSizeY(sizeY / 2 - 100);
		this.addComponent(console);
	}
	public void initScriptZone(){
			managerOnglet = new OngletManager(0, sizeY / 2, sizeX / 2, sizeY / 2 - 50, this);
			ongletCollide = new Onglet(Messages.getString("EditeurObjetMap.0"), managerOnglet); //$NON-NLS-1$
			ongletCollide.setContainer(new Container(0,50, sizeX / 2, sizeY / 2 - 150, managerOnglet));
			collidescript = new ProgrammTextArea(0, 0, sizeX / 2, sizeY / 2 - 150, ongletCollide.getContainer());
			collidescript.getInput().setContenu(objEditor.getObj().getCollideScript());
			ongletCollide.getContainer().addComponent(collidescript);
			managerOnglet.addComponent(ongletCollide);
			
			ongletUpdate = new Onglet(Messages.getString("EditeurObjetMap.1"), managerOnglet); //$NON-NLS-1$
			ongletUpdate.setContainer(new Container(0,50, sizeX / 2, sizeY / 2 - 150, managerOnglet));
			updatescript = new ProgrammTextArea(0, 0, sizeX / 2, sizeY / 2 - 150, ongletUpdate.getContainer());
			updatescript.getInput().setContenu(objEditor.getObj().getUpdateScript());
			ongletUpdate.getContainer().addComponent(updatescript);
			managerOnglet.addComponent(ongletUpdate);
			
			ongletClick = new Onglet(Messages.getString("EditeurObjetMap.2"), managerOnglet); //$NON-NLS-1$
			ongletClick.setContainer(new Container(0,50, sizeX / 2, sizeY / 2 - 150, managerOnglet));
			clickscript = new ProgrammTextArea(0, 0, sizeX / 2, sizeY / 2 - 150, ongletClick.getContainer());
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
		o.setCollideScript(collidescript.getInput().getContenu());
		o.setUpdateScript(updatescript.getInput().getContenu());
		o.setClickScript(clickscript.getInput().getContenu());
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
		objEditor = new BasicObjetMapEditor(o, 0, 0, getSizeX() / 2, getSizeY() / 2, this);
		this.addComponent(objEditor);
		this.components.remove(getImgEditor());
		setImgEditor(new ImgEditor(getSizeX() / 2, 0, getSizeX() / 2, getSizeY() / 6 * 5, o,this));
		this.addComponent(getImgEditor());
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
	/**
	 * @return the imgEditor
	 */
	public ImgEditor getImgEditor() {
		return imgEditor;
	}
	/**
	 * @param imgEditor the imgEditor to set
	 */
	public void setImgEditor(ImgEditor imgEditor) {
		this.imgEditor = imgEditor;
	}
}
