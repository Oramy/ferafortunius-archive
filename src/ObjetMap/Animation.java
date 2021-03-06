package ObjetMap;

import gui.jeu.Jeu;
import gui.jeu.ScriptManager;

import java.io.Serializable;
import java.util.ArrayList;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.ferafortunius.animations.AnimationKey;

public class Animation implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<TimedScript> scripts;
	
	private ArrayList<AnimationKey> keys;
	
	private int cursor;
	private int framesSize;
	
	private long millisBegin, millisCursor;
	private String nom;
	private boolean repeat;
	private boolean started;
	public Animation(String nom, int size){
		this.setNom(nom);
		this.framesSize = size;
		cursor = 0;
		repeat = false;
		started = false;
		scripts = new ArrayList<TimedScript>();
		setKeys(new ArrayList<AnimationKey>());
	}
	public Animation clone(){
		Animation anim = null; 
		try {
			anim = (Animation) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		anim.scripts = new ArrayList<TimedScript>();
		for(int i = 0; i < scripts.size(); i++)
			anim.scripts.add(scripts.get(i).clone());
		
		anim.setKeys(new ArrayList<AnimationKey>());
		for(int i = 0; i < getKeys().size(); i++)
			anim.getKeys().add(getKeys().get(i).cloneAnimation());
		return anim;
	}
	public String getCompressScript(ObjetMap o){
		String compressScript = "";
		update(o);
		for(TimedScript script : scripts){
			if(script.hastoBe(cursor))
			compressScript += script.getScript();
			
			
		}
		return compressScript;
	}
	public void update(ObjetMap o){
		millisCursor = System.currentTimeMillis() - millisBegin;
		for(AnimationKey key : getKeys())
			key.execute(o, millisCursor);
	}
	@Deprecated
	public void executeScripts(ObjetMap o){
		if(cursor > framesSize && repeat){
			repeat();
		}
		String compressScript = getCompressScript(o);
		try { 

			Bindings bindings = ScriptManager.moteurScript.getBindings(ScriptContext.ENGINE_SCOPE); 
			bindings.clear(); 
			//Ajout de la variable entree dans le script
			bindings.put("cible", o);
			bindings.put("cursor", cursor);
			bindings.put("images", o.getImage());
			for(ObjetImage objImg : o.getImage()){
				if(objImg.getAlias() != null && objImg.getAlias() != "")
					bindings.put(objImg.getAlias(), objImg);
			}
			//Execution du script entr???e
			ScriptManager.moteurScript.eval(compressScript, bindings);
		} catch (ScriptException e) { 
			e.printStackTrace(); 
		} 
		cursor++;
	}
	@Deprecated
	public void executeScripts(ObjetMap o, Jeu jeu){
		if(cursor > framesSize && repeat){
			repeat();
		}
		String compressScript = getCompressScript(o);
		try { 

			Bindings bindings = ScriptManager.moteurScript.getBindings(ScriptContext.ENGINE_SCOPE); 
			bindings.clear(); 
			//Ajout de la variable entree dans le script
			bindings.put("cible", o);
			bindings.put("cursor", cursor);
			bindings.put("images", o.getImage());
			bindings.put("jeu", jeu);
			bindings.put("carte", jeu.getCarte());
			for(ObjetImage objImg : o.getImage()){
				if(objImg.getAlias() != null && objImg.getAlias() != "")
					bindings.put(objImg.getAlias(), objImg);
			}
			//Execution du script entr???e
			ScriptManager.moteurScript.eval(compressScript, bindings);
		} catch (ScriptException e) { 
			e.printStackTrace(); 
		} 
		cursor++;
	}
	public void finish(ObjetMap o) {
		
	}
	public void start(){
		if(!started){
			started = true;
			cursor = 0;
			millisBegin = System.currentTimeMillis();
		}
	}
	private void restart(){
		started = false;
		cursor = 0;
		start();
	}
	public void repeat(){
		restart();
	}
	public void stop(){
		started = false;
	}
	/**
	 * @return the scripts
	 */
	public ArrayList<TimedScript> getScripts() {
		return scripts;
	}
	public void addScript(TimedScript script){
		scripts.add(script);
	}
	public TimedScript getScript(int scriptID){
		return scripts.get(scriptID);
	}
	public void removeScript(TimedScript script){
		scripts.remove(script);
	}
	/**
	 * @param scripts the scripts to set
	 */
	public void setScripts(ArrayList<TimedScript> scripts) {
		this.scripts = scripts;
	}

	/**
	 * @return the cursor
	 */
	public int getCursor() {
		return cursor;
	}

	/**
	 * @param cursor the cursor to set
	 */
	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	/**
	 * @return the framesSize
	 */
	public int getFramesSize() {
		return framesSize;
	}

	/**
	 * @param framesSize the framesSize to set
	 */
	public void setFramesSize(int framesSize) {
		this.framesSize = framesSize;
	}
	public boolean isRepeat(){
		return repeat;
	}
	public void setRepeat(boolean repeat){
		this.repeat = repeat;
	}
	public boolean isStarted(){
		return started;
	}
	public boolean isFinish(){
		if(cursor > framesSize && started)
			return true;
		return false;
	}
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	public ArrayList<AnimationKey> getKeys() {
		if(keys == null)
			keys = new ArrayList<AnimationKey>();
		return keys;
	}
	public void setKeys(ArrayList<AnimationKey> keys) {
		this.keys = keys;
	}
	
}
