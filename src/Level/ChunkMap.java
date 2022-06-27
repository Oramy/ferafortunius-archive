package Level;

import gui.jeu.Jeu;
import gui.jeu.ScriptManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import maths.Trigonometry;
import ObjetMap.Direction;
import ObjetMap.Ensemble;
import ObjetMap.Entity;
import ObjetMap.ObjetMap;
import ObjetMap.Teleporter;



public class ChunkMap implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1376273615901036752L;
	private Chunk[][][] chunks;
	private int mapSizeX;
	private int mapSizeY;
	private int mapSizeZ;
	private int chunksSize;
	private String nom;
	
	protected transient Bindings valuesToAdd;
	private transient String scriptToLaunch;
	private transient int scriptId;
	public ChunkMap(int chunksSize, int mapSizeX, int mapSizeY, int mapSizeZ){
		chunks = new Chunk[mapSizeX][mapSizeY][mapSizeZ];
		this.setChunksSize(chunksSize);
		this.mapSizeX = mapSizeX;
		this.mapSizeY = mapSizeY;
		this.mapSizeZ = mapSizeZ;
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					chunks[i][j][k] = new Chunk(chunksSize, chunksSize, chunksSize, null);
				}
			}
		}
	}
	public ChunkMap(int chunksSize, int mapSizeX, int mapSizeY, int mapSizeZ, boolean layered) {
		chunks = new Chunk[mapSizeX][mapSizeY][mapSizeZ];
		this.setChunksSize(chunksSize);
		this.mapSizeX = mapSizeX;
		this.mapSizeY = mapSizeY;
		this.mapSizeZ = mapSizeZ;
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					if(layered)
						chunks[i][j][k] = new LayeredChunkMap(chunksSize, chunksSize, chunksSize, new ArrayList<ObjetMap>());
					else{
						chunks[i][j][k] = new Chunk(chunksSize, chunksSize, chunksSize, new ArrayList<ObjetMap>());
					}
				}
			}
		}
	}
	public void initChunks(int mapSizeX, int mapSizeY, int mapSizeZ){
		chunks = new Chunk[mapSizeX][mapSizeY][mapSizeZ];
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					chunks[i][j][k] = new Chunk(getChunksSize(), getChunksSize(), getChunksSize(), null);
				}
			}
		}
	}
	public ChunkMap clone(){
		ChunkMap o = new ChunkMap(chunksSize, mapSizeX, mapSizeY, mapSizeZ);
		o.setNom(nom);
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					o.chunks[i][j][k] = chunks[i][j][k].clone();
				}
			}
		}
		// on renvoie le clone
		return o;

	}
	public String toString(){
		String description = "";
		description += "X = " + mapSizeX + "\n";
		description += "Y = " + mapSizeY + "\n";
		description += "Z = " + mapSizeZ + "\n";
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					description += chunks[i][j][k].toString();
					description += "|";
				}
				description += "\n\n";
			}
			description += "\n";
		}
		return description;
	}
	public Chunk[][][] getChunks() {
		return chunks;
	}
	public Chunk getChunk(ObjetMap o ) {
		return chunks[o.getChunkX()][o.getChunkY()][o.getChunkZ()];
	}
	public Chunk getChunk(int chunkx, int chunky, int chunkz) {
		return chunks[chunkx][chunky][chunkz];
	}
	public int getMapSizeX() {
		return mapSizeX;
	}
	public void setMapSizeX(int mapSizeX) {
		this.mapSizeX = mapSizeX;
		this.initChunks(mapSizeX, mapSizeY, mapSizeZ);
	}
	public int getMapSizeY() {
		return mapSizeY;
	}
	public void setMapSizeY(int mapSizeY) {
		this.mapSizeY = mapSizeY;
		this.initChunks(mapSizeX, mapSizeY, mapSizeZ);
	}
	public int getMapSizeZ() {
		return mapSizeZ;
	}
	public void setMapSizeZ(int mapSizeZ) {
		this.mapSizeZ = mapSizeZ;
		this.initChunks(mapSizeX, mapSizeY, mapSizeZ);
	}
	/**
	 * @return the chunksSize
	 */
	public int getChunksSize() {
		return chunksSize;
	}
	/**
	 * @param without 
	 * 
	 */
	public synchronized boolean verifyPosition(ObjetMap objet){
		if(getChunk(objet).getModeActuel() != Chunk.GOD_MOD){
			ObjetMap ref = (ObjetMap) objet.clone();
			
			//X
			if(objet.getPosX() + objet.getSizeX() >= chunksSize){
				objet.setChunkX(objet.getChunkX() + objet.getPosX() / chunksSize);
				objet.setPosX(objet.getPosX() -  (objet.getPosX() / chunksSize) * chunksSize);
			}
			if(objet.getPosX() < 0){
				objet.setChunkX(objet.getChunkX() + (objet.getPosX()) / chunksSize - 1);
				objet.setPosX(chunksSize + objet.getPosX());
			}
			//Y
			if(objet.getPosY() + objet.getSizeY() >= chunksSize){
				objet.setChunkY(objet.getChunkY() + objet.getPosY() / chunksSize);
				objet.setPosY(objet.getPosY() -  (objet.getPosY() / chunksSize) * chunksSize);
			}
			if(objet.getPosY() < 0){
				objet.setChunkY(objet.getChunkY() + (objet.getPosY()) / chunksSize - 1);
				objet.setPosY(chunksSize + objet.getPosY());
			}		
			//Z
			if(objet.getPosZ() + objet.getSizeZ() >= chunksSize){
				objet.setChunkZ(objet.getChunkZ() + objet.getPosZ() / chunksSize);
				objet.setPosZ(objet.getPosZ() -  (objet.getPosZ() / chunksSize) * chunksSize);
			}
			if(objet.getPosZ() < 0){
				objet.setChunkZ(objet.getChunkZ() + (objet.getPosZ()) / chunksSize - 1);
				objet.setPosZ(chunksSize + objet.getPosZ());
			}
			
			if(objet.getChunkX() < 0)
			{
				objet.setChunkX(0);
				objet.setPosX(0);
			}
			if(objet.getChunkY() < 0)
			{
				objet.setChunkY(0);
				objet.setPosY(0);
			}
			if(objet.getChunkZ() < 0)
			{
				objet.setChunkZ(0);
				objet.setPosZ(0);
			}
			if( objet.getChunkX() >= mapSizeX)
			{
				objet.setChunkX(mapSizeX - 1);
				objet.setPosX(chunksSize);
			}
				
			if( objet.getChunkY() >= mapSizeY)
			{
				objet.setChunkY(mapSizeY - 1);
				objet.setPosY(chunksSize);
			}
				
			if( objet.getChunkZ() >= mapSizeZ)
			{
				objet.setChunkZ(mapSizeZ - 1);
				objet.setPosZ(chunksSize);
			}
			if(ref.getChunkX() != objet.getChunkX() || ref.getChunkY() != objet.getChunkY() || ref.getChunkZ() != objet.getChunkZ()){
				if(!getChunk(objet).addContenu(objet)){
					return false;
				}
				else
					getChunk(ref).remove(objet);
			}
			//getChunk(o).sort(o);
		}
		return true;
	}
	public synchronized boolean addContenu(Ensemble o) {
		for (int i = 0, c = o.getContenu().size(); i < c; i++) {
			ObjetMap obj = o.getContenu().get(i);
			obj.setInvisible(false);
			obj.setChunkX(o.getChunkX());
			obj.setChunkY(o.getChunkY());
			obj.setChunkZ(o.getChunkZ());
			obj.setPosX(o.getPosX() + obj.getPosX());
			obj.setPosY(o.getPosY() + obj.getPosY());
			obj.setPosZ(o.getPosZ() + obj.getPosZ());
			while(obj.getPosX() > this.getChunksSize()){
				obj.setPosX(obj.getPosX() - this.getChunksSize());
				obj.setChunkX(obj.getChunkX() + 1);
			}
			while(obj.getPosY() > this.getChunksSize()){
				obj.setPosY(obj.getPosY() - this.getChunksSize());
				obj.setChunkY(obj.getChunkY() + 1);
			}
			while(obj.getPosZ() > this.getChunksSize()){
				obj.setPosZ(obj.getPosZ() - this.getChunksSize());
				obj.setChunkZ(obj.getChunkZ() + 1);
			}
			this.getChunk(obj).addContenu(obj);
		}
		return true;
	}
	public void update(Jeu jeu){
		scriptId = 0;
		for(int i = 0, c = mapSizeX; i < c; i++){
			for(int j = 0, c2 = mapSizeY; j < c2; j++){
				for(int k = 0, c3 =  mapSizeZ; k < c3; k++){
					chunks[i][j][k].update(jeu);
				}
			}
		}
		
		Bindings bindings = ScriptManager.moteurScript.getBindings(ScriptContext.ENGINE_SCOPE); 
		bindings.clear();
		
		bindings.clear();
		// Ajout de la variable entree dans le script
		bindings.put("newcam", new Camera(0, 0, 1f, jeu.getCarte()));
		bindings.put("E", Direction.E);
		bindings.put("SE", Direction.SE);
		bindings.put("S", Direction.S);
		bindings.put("SW", Direction.SW);
		bindings.put("W", Direction.W);
		bindings.put("NW", Direction.NW);
		bindings.put("N", Direction.N);
		bindings.put("NE", Direction.NE);
		bindings.put("Random", new Random());
		bindings.put("Trigonometry", new Trigonometry());
		bindings.put("ObjetMapLoader", new ObjetMapLoader());
		bindings.put("ItemLoader", new ItemLoader());
		bindings.put("direction", Arrays.asList(Direction.values()));
		bindings.put("currentTimeMillis", System.currentTimeMillis());
		
		if (jeu != null) {
			bindings.put("jeu", jeu);
			bindings.put("carte", jeu.getCarte());
			bindings.put("actualcam", jeu.getPanneauDuJeu()
					.getActualCam());
		}
		for(int i = 0, c = getValuesToAdd().size(); i < c; i++){
			String key  = (String)getValuesToAdd().keySet().toArray()[i];
			bindings.put(key, getValuesToAdd().get(key));
		}
		valuesToAdd.clear();
		if(getScriptToLaunch() != null && !getScriptToLaunch().equals("")){
			setScriptToLaunch(getScriptToLaunch().replaceAll(Character.valueOf((char) 22).toString(), ""));
			String toLaunch = getScriptToLaunch();
			setScriptToLaunch("");
			
			// Execution du script entr�e
			try {
				ScriptManager.moteurScript.eval(toLaunch, bindings);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0, c = getValuesToAdd().size(); i < c; i++){
			String key  = (String)getValuesToAdd().keySet().toArray()[i];
			bindings.put(key, getValuesToAdd().get(key));
		}
		
	}
	public synchronized  boolean accepted(ObjetMap o, ObjetMap without, Jeu jeu){
		return getChunk(o).accepted(o, without, jeu);
	}
	public synchronized ObjetMap deplacement(ObjetMap o,int x1, int y1, int z1, Jeu jeu){
		ObjetMap objetActuel = o;
		int plusGrandDeplacement;
		int x = 0;
		int y = 0;
		int z = 0;
		x = x1;
		if(x1 < 0)
		{
			x = -x1;
		}
		y = y1;
		if(y1 < 0)
		{
			y = -y1;
		}
		z = z1;
		if(z1 < 0)
		{
			z = -z1;
		}
		if( x < y)
		{
			plusGrandDeplacement = y;
			if( y < z)
			{
				plusGrandDeplacement = z;
			}
		}	
		else
		{
			plusGrandDeplacement = x;
			if( x < z)
			{
				plusGrandDeplacement = z;
			}
		}
		for(int i = 0; i < plusGrandDeplacement; i ++){
			if(x1 > i){
				teleportation(objetActuel, 1, 0, 0, jeu);
			}
			if(y1 > i){	
				teleportation(objetActuel, 0, 1, 0, jeu);
			}
			if(z1 > i){
				teleportation(objetActuel, 0, 0, 1, jeu);	
			}
			
			if(x1 < -i && x1 < 0){
				teleportation(objetActuel, -1, 0, 0, jeu);
			}
			if(y1 < -i && y1 < 0){
				teleportation(objetActuel, 0, -1, 0, jeu);
			}
			if(z1 < -i && z1 < 0){
				teleportation(objetActuel, 0, 0, -1, jeu);
			}
		}
		getChunk(o).hasXYtoSort = true;
		return objetActuel;
	}
	private synchronized  ObjetMap teleportation(ObjetMap o, int x, int y, int z, Jeu jeu) {
		if(o != null){
			o.setPosX(o.getPosX() + x);
			o.setPosY(o.getPosY() + y);
			o.setPosZ(o.getPosZ() + z);
			if(!getChunk(o).accepted(o,o, jeu)){
				o.setPosX(o.getPosX() - x);
				o.setPosY(o.getPosY() - y);
				o.setPosZ(o.getPosZ() - z);
			}
			if(!verifyPosition(o)){
				o.setPosX(o.getPosX() - x);
				o.setPosY(o.getPosY() - y);
				o.setPosZ(o.getPosZ() - z);
				verifyPosition(o);
			}
		 return null;
		}
		return null;
	}
	public void setChunksSize(int chunksSize) {
		this.chunksSize = chunksSize;
	}
	public ObjetMap searchId(int l){
		ObjetMap found = null;
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					found = chunks[i][j][k].searchId(l);
					if(found != null){
						return found; 
					}
				}
			}
		}
		return found;
	}
	public ObjetMap searchId(int l, String nom){
		ObjetMap found = null;
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					found = chunks[i][j][k].searchId(l);
					if(found != null && found.getNom().equals(nom)){
						return found; 
					}
				}
			}
		}
		return found;
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
	/**
	 * 
	 * @return la premi�re entit� trouv�e dans la map
	 */
	public Entity getFirstEntity() {
		//L'entit� � retourner.
		Entity entity = null;
		
		//Recherche Chunk par Chunk d'une entit�.
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					if(entity == null)
						entity = getChunk(i,j,k).searchEntity();
				}
			}	
		}
		return entity;
	}
	public Teleporter getTeleporter(String id) {
		//L'entit� � retourner.
		Teleporter teleporter = null;
		
		//Recherche Chunk par Chunk d'une entit�.
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					if(teleporter == null)
						teleporter = getChunk(i,j,k).getTeleporter(id);
				}
			}	
		}
		return teleporter;
	}

	public Bindings getValuesToAdd() {
		if(valuesToAdd == null)
			valuesToAdd = new SimpleBindings();
		return valuesToAdd;
	}

	public void putValueToAdd(String key,  Object value) {
		getValuesToAdd().put(key, value);
	}

	public void setValuesToAdd(Bindings valuesToAdd) {
		this.valuesToAdd = valuesToAdd;
	}
	public String getScriptToLaunch() {
		return scriptToLaunch;
	}

	public void setScriptToLaunch(String scriptToLaunch) {
		this.scriptToLaunch = scriptToLaunch;
	}
	public int getScriptId() {
		return scriptId++;
	}
	public void setScriptId(int scriptId) {
		this.scriptId = scriptId;
	}
	public void verifyAll() {
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					for(int l = 0; l < getChunk(i,j,k).getContenu().size(); l++){
						verifyPosition(getChunk(i,j,k).getContenu().get(l));
						if(!getChunk(i,j,k).getContenu().contains(getChunk(i,j,k).getContenu().get(l)))
							l--;
					}
				}
			}	
		}
	}
}
