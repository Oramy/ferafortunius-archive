package Level;

import gui.jeu.Jeu;

import java.io.Serializable;
import java.util.ArrayList;

import ObjetMap.Entity;
import ObjetMap.ObjetMap;



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
	public synchronized ObjetMap verifyPosition(ObjetMap o){
		ObjetMap clone = o;
		if(getChunk(o).getModeActuel() != Chunk.GOD_MOD){
			ObjetMap ref = (ObjetMap) o.clone();
			if(clone.getPosX() + clone.getSizeX() >= chunksSize){
				
				clone.setChunkX(clone.getChunkX() + 1);
				clone.setPosX(clone.getPosX() - chunksSize);
			}
			if(clone.getPosY() + clone.getSizeY() >= chunksSize){
				clone.setChunkY(clone.getChunkY() + 1);
				clone.setPosY(clone.getPosY() - chunksSize);
			}
			if(clone.getPosZ() + clone.getSizeZ() >= chunksSize){
				clone.setChunkZ(clone.getChunkZ() + 1);
				clone.setPosZ(clone.getPosZ() - chunksSize);
			}
			if(clone.getPosX() + clone.getSizeX() < 0){
				clone.setChunkX(clone.getChunkX() - 1);
				clone.setPosX(chunksSize - clone.getSizeX() - 1);
			}
			if(clone.getPosY() + clone.getSizeY() < 0){
				clone.setChunkY(clone.getChunkY() - 1);
				clone.setPosY(chunksSize - clone.getSizeY() - 1);
			}
			if(clone.getPosZ() + clone.getSizeZ() < 0){
				clone.setChunkZ(clone.getChunkZ() - 1);
				clone.setPosZ(chunksSize - clone.getSizeZ() - 1);
			}
			if(clone.getChunkX() < 0)
			{
				clone.setChunkX(0);
				clone.setPosX(0);
			}
			if(clone.getChunkY() < 0)
			{
				clone.setChunkY(0);
				clone.setPosY(0);
			}
			if(clone.getChunkZ() < 0)
			{
				clone.setChunkZ(0);
				clone.setPosZ(0);
			}
			if( clone.getChunkX() >= mapSizeX)
			{
				clone.setChunkX(mapSizeX - 1);
				clone.setPosX(chunksSize);
			}
				
			if( clone.getChunkY() >= mapSizeY)
			{
				clone.setChunkY(mapSizeY - 1);
				clone.setPosY(chunksSize);
			}
				
			if( clone.getChunkZ() >= mapSizeZ)
			{
				clone.setChunkZ(mapSizeZ - 1);
				clone.setPosZ(chunksSize);
			}
			if(ref.getChunkX() != clone.getChunkX() || ref.getChunkY() != clone.getChunkY() || ref.getChunkZ() != clone.getChunkZ()){
				getChunk(ref).remove(o);
				getChunk(o).addContenu(o);
			}
			getChunk(o).sort(o);
		}
		return clone;
	}
	public void update(Jeu jeu){
		for(int i = 0, c = mapSizeX; i < c; i++){
			for(int j = 0, c2 = mapSizeY; j < c2; j++){
				for(int k = 0, c3 =  mapSizeZ; k < c3; k++){
					chunks[i][j][k].update(jeu);
				}
			}
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
		verifyPosition(o);
		return objetActuel;
	}
	private synchronized  ObjetMap teleportation(ObjetMap o, int x, int y, int z, Jeu jeu) {
		if(o != null){
			o.setPosX(o.getPosX() + x);
			o.setPosY(o.getPosY() + y);
			o.setPosZ(o.getPosZ() + z);
			if(!getChunks()[o.getChunkX()][o.getChunkY()][o.getChunkZ()].accepted(o,o, jeu)){
				o.setPosX(o.getPosX() - x);
				o.setPosY(o.getPosY() - y);
				o.setPosZ(o.getPosZ() - z);
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
	public synchronized void trierTout() {
		for(int i = 0; i < mapSizeX; i++){
			for(int j = 0; j < mapSizeY; j++){
				for(int k = 0; k < mapSizeZ; k++){
					chunks[i][j][k].trier();
				}
			}
			
		}
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
	 * @return la première entité trouvée dans la map
	 */
	public Entity getFirstEntity() {
		//L'entité à retourner.
		Entity entity = null;
		
		//Recherche Chunk par Chunk d'une entité.
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
}
