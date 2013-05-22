package ObjetMap;

import gui.jeu.Jeu;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import Level.Camera;
import Level.MapLoader;



public class Teleporter extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5963852868502436996L;
	protected String mappath;
	protected Terre arrivee;
	public Teleporter(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		mappath = "data/Maps/couloir.dat";
		sizeX = 13;
		sizeY = 13;
		sizeZ = 0;
		nom = "Teleporter";
		arrivee = new Terre(0,0,0, 104,156, 1);
		collideScript = "jeu.setCarte(maploader.loadMap(mappath));" +
						"perso.setPosition(arrivee);" +
						"jeu.getCarte().getChunks(arrivee).addContenu(perso);" +
						"jeu.setPersonnage(perso);";
	}
	protected void hasCollide(ObjetMap objetMap, Jeu jeu){
		if(getCollideScript() != null && getCollideScript() != ""){
			ScriptEngineManager manager = new ScriptEngineManager(); 
		    ScriptEngine moteur = manager.getEngineByName("rhino"); 
		     try { 
		     
		      Bindings bindings = moteur.getBindings(ScriptContext.ENGINE_SCOPE); 
		      bindings.clear(); 
		      //Ajout de la variable entree dans le script
		      bindings.put("himself", this);
		      bindings.put("cible", objetMap);
		      bindings.put("arrivee", arrivee);
		      bindings.put("mappath", mappath);
		      bindings.put("maploader", new MapLoader());
		      bindings.put("newcam", new Camera(0,0,1f));
		      if(jeu != null){
		    	  bindings.put("jeu", jeu);
		    	  ObjetMap perso = jeu.getPlayer().clone();
			      bindings.put("perso", perso);
		      }
		      //Execution du script entr�e
		      moteur.eval(getCollideScript(), bindings);
		    } catch (ScriptException e) { 
		      e.printStackTrace(); 
		    } 
		}
	}
	
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}

}
