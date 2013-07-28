package ObjetMap;

import gui.jeu.Jeu;



public class Teleporter extends ObjetMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5963852868502436996L;
	
	private boolean otherMap;
	
	private String mapPath;
	
	private String teleporterId;
	
	private String arrivedId;
	public Teleporter(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		setTeleporterId("default");
		setArrivedId("default");
		setMapPath("data/Maps/.dat");
		setOtherMap(false);
		update = true; 
		collideScript ="if(!himself.getTeleporterId().equals(himself.getArrivedId())){ \n" +
				"if(himself.isOtherMap()){ \n" +
		"	jeu.setCarte(\"data/Maps/\" + himself.getMapPath() + \".dat\"); \n" +
		"}jeu.teleport(jeu.getCarte().getTeleporter(himself.getArrivedId())); \n" +
		"}";
		
		updateScript = "if(himself.isNotAnExistingChrono(\"init\")){ \n" +
				"himself.addChrono(\"init\", 0);\n" +
				"himself.setUpdate(false); \n" +
				"himself.setTeleporterId(\"\"); // ID of the teleporter \n" +
				" himself.setArrivedId(\"\"); // ID of the finish teleporter \n" + 
				"himself.setOtherMap(false); // If you want to change the map \n"+
		"himself.setMapPath(\"\"); // The map Path\n" +
		"}\n";
		
		nom = "Teleporter";
		sizeX = 50;
		sizeY = 50;
		sizeZ = 50;
		
		this.addCollisionBlock(new CollisionBlock(0, 0, 0, 50, 50, 50));
	}

	public boolean collide(ObjetMap o, Jeu jeu) {
		boolean collide = false;	
		if (!isInvisible() && !o.isInvisible()) {
			if (ObjetMapUtils.acceptableXYZ(this, o)) {
				for (int i = 0, l = this.getCollision().size(); i < l; i++) {
					for (int j = 0, l2 = o.getCollision().size(); j < l2; j++) {
						CollisionBlock c = this.getCollision().get(i);
						CollisionBlock co = o.getCollision().get(j);
						if (!c.accept(this, o, co)) {
							this.getActualTouchedBlocks().add(c);
							o.getActualTouchedBlocks().add(co);
							collide = true;
							i = l;
							j = l2;
						}
					}
				}

			}
		}
		if (collide) {
			o.hasCollide(this, jeu);
			hasCollide(o, jeu);
		}
		if(jeu != null)
			return false;
		else 
			return collide;
	}
	
	@Override
	public void setSizeX(int sizeX){
		this.sizeX = sizeX;
		if(this.getCollision() != null)
			this.getCollision().get(0).setSizeX(sizeX);
	}
	
	@Override
	public void setSizeY(int sizeY){
		this.sizeY = sizeY;
		if(this.getCollision() != null)
			this.getCollision().get(0).setSizeY(sizeY);
	}
	
	@Override
	public void setSizeZ(int sizeZ){
		this.sizeZ = sizeZ;
		if(this.getCollision() != null)
		this.getCollision().get(0).setSizeZ(sizeZ);
	}
	@Override
	protected ObjetMap newMe(int chunkX, int chunkY, int chunkZ, int posX,
			int posY, int posZ) {
		
		return null;
	}
	
	public boolean isOtherMap() {
		return otherMap;
	}
	public void setOtherMap(boolean otherMap) {
		this.otherMap = otherMap;
	}
	public String getMapPath() {
		return mapPath;
	}
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	public String getTeleporterId() {
		return teleporterId;
	}
	public void setTeleporterId(String id) {
		this.teleporterId = id;
	}
	public String getArrivedId() {
		return arrivedId;
	}
	public void setArrivedId(String arrivedId) {
		this.arrivedId = arrivedId;
	}

}
