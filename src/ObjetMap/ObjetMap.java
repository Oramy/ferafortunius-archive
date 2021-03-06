
package ObjetMap;

import gui.Text;
import gui.jeu.Jeu;
import gui.jeu.PanneauJeuAmeliore;
import gui.jeu.ScriptManager;
import gui.jeu.utils.Isometric;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import Level.ArrayIterator;
import Level.Camera;
import Level.ChunkMap;
import Level.Iterator;

public abstract class ObjetMap implements Serializable, Cloneable, Comparable<ObjetMap> {

	/**
	 * 
	 */
	protected static final long serialVersionUID = -4798026844348206694L;
	protected String nom;
	
	protected ArrayList<ObjetImage> image;
	
	protected ArrayList<CollisionBlock> collision;
	
	//Blocks qui sont touch?s par un objets actuellement
	protected transient ArrayList<CollisionBlock> actualTouchedBlocks;
	
	protected transient ArrayList<ObjetMap> touchedObjects;
	
	protected ArrayList<Animation> animations;
	
	protected int posX, posY, posZ, chunkX, chunkY, chunkZ;
	
	//Script;
	protected String updateScript;
	protected String collideScript;
	protected String clickScript;
	
	//ID
	protected int id;
	
	protected boolean fly = false;
	protected boolean mirror = false;
	protected boolean invisible = false;
	protected boolean update;
	protected transient boolean surligned;
	protected boolean applyZShadow = false;
	
	
	protected Color maskColor;
	
	protected ArrayList<Chrono> timesHelps;

	protected Direction direction;
	
	protected String currentImageList;
	
	private ArrayList<ObjetImageList> imagesLists;
	
	
	public static int instanceNumber = 0;
	protected int sizeX, sizeY, sizeZ;
	protected float opacity;
	private transient int ombre;

	private transient boolean drawing;

	public ObjetMap(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		setUpdate(false);
		setDrawing(false);
		opacity = 1.0f;
		invisible = false;
		maskColor = new Color(0, 0, 0, 0);
		this.id = instanceNumber;
		instanceNumber++;
		this.setChunkX(chunkX);
		this.setChunkY(chunkY);
		this.setChunkZ(chunkZ);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		setOmbre(0);
		setUpdateScript(""); 
		setCollideScript("");
		setNom("");
		setSizeX(0);
		setSizeY(0);
		setSizeZ(0);
		image = new ArrayList<ObjetImage>();
		setCollision(new ArrayList<CollisionBlock>());
		setTimesHelps(new ArrayList<Chrono>());
		animations = new ArrayList<Animation>();
	}


	public void addChrono(String name, long chrono) {
		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		if(isNotAnExistingChrono(name)){
			Chrono c = new Chrono(chrono, name);
			timesHelps.add(c);
		}
	}

	public boolean addCollisionBlock(CollisionBlock c) {
		getCollision().add(c);
		return true;
	}

	public ObjetMap clone() {
		ObjetMap o = null;
		try {
			o = (ObjetMap) super.clone();
		} catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		o.id = this.id;

		o.setChunkX(chunkX);
		o.setChunkY(chunkY);
		o.setChunkZ(chunkZ);
		o.posX = posX;
		o.posY = posY;
		o.posZ = posZ;
		o.setNom(nom);
		o.setSizeX(sizeX);
		o.setSizeY(sizeY);
		o.setSizeZ(sizeZ);
		
		//Copie de la liste d'image actuelle
		o.image = new ArrayList<ObjetImage>();
		for (int i = 0; i < image.size(); i++)
			o.image.add(image.get(i).clone());
		
		//Copie des animations
		o.animations = new ArrayList<Animation>();
		if (animations != null) {
			for (int i = 0; i < animations.size(); i++)
				o.animations.add(animations.get(i).clone());
		}
		o.fly = fly;
		o.mirror = mirror;
		o.maskColor = new Color(o.maskColor.r, o.maskColor.g, o.maskColor.b,
				o.maskColor.a);
		o.opacity = opacity;
		o.update = update;
		o.invisible = invisible;
		
		//Copie des blocs de collisions
		o.collision = new ArrayList<CollisionBlock>();
		for (int i = 0; i < collision.size(); i++)
			o.collision.add(collision.get(i).clone());
		
		//Copie des chronos
		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		o.timesHelps = new ArrayList<Chrono>();
		for (int i = 0; i < timesHelps.size(); i++)
			o.timesHelps.add(timesHelps.get(i).clone());
		
		
		//Copie des listes d'images
		o.setImagesLists(new ArrayList<ObjetImageList>());
		for (ObjetImageList b : getImagesLists()){
			//Copie des images
			ObjetImageList clone = b.clone();
			o.getImagesLists().add(clone);
		}
		// on renvoie le clone
		return o;

	}

	public boolean collide(ObjetMap o, Jeu jeu) {
		boolean collide = false;	
		if (!isInvisible() && !o.isInvisible()) {
			if(ObjetMapUtils.acceptableXYZ(this, o)) {
				for (int i = 0, l = this.getCollision().size(); i < l; i++) {
					for (int j = 0, l2 = o.getCollision().size(); j < l2; j++) {
						CollisionBlock c = this.getCollision().get(i);
						CollisionBlock co = o.getCollision().get(j);
						if (!c.accept(this, o, co)) {
							this.getActualTouchedBlocks().add(c);
							o.getActualTouchedBlocks().add(co);
							this.getTouchedObjects().add(o);
							o.getTouchedObjects().add(this);
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
		return collide;
	}
	public void reduce(float ratio){
		extend(1f / ratio);
	}
	public void extend(float ratio){
		sizeX *= ratio;
		sizeY *= ratio;
		sizeZ *= ratio;
		
		posX -= (sizeX / ratio / 2) - sizeX / 2;
		posY -= (sizeY / ratio / 2) - sizeY / 2;
		for(CollisionBlock c : collision){
			c.setPosX((int) (c.getPosX() * ratio));
			c.setPosY((int) (c.getPosY() * ratio));
			c.setPosZ((int) (c.getPosZ() * ratio));
			c.setSizeX((int) (c.getSizeX() * ratio));
			c.setSizeY((int) (c.getSizeY() * ratio));
			c.setSizeZ((int) (c.getSizeZ() * ratio));
		}
		for(ObjetImageList list : imagesLists){
			for(ObjetImage i : list.getList()){
				i.setRatio(i.getRatio() * ratio);
			}
		}
	}
	public ArrayList<CollisionBlock> getActualTouchedBlocks(){
		if(actualTouchedBlocks == null)
			actualTouchedBlocks = new ArrayList<CollisionBlock>();
		return actualTouchedBlocks;
	}
	public Animation getAnimation(int i) {
		return animations.get(i);
	}

	public Animation getAnimation(String nom) {
		for (Animation a : animations) {
			if (a.getNom().equals(nom))
				return a;
		}
		return null;
	}

	public int getAnimationLaunchedCount() {
		int count = 0;
		for (Animation a : animations) {
			if (a.isStarted() && !a.isFinish() && !(a.getNom().contains("stay")))
				count++;
		}

		return count;

	}
	public int getAllAnimationLaunchedCount() {
		int count = 0;
		for (Animation a : animations) {
			if (a.isStarted() && !a.isFinish())
				count++;
		}

		return count;

	}
	
	public void stopLittleAnimation() {
		for (Animation a : animations) {
			if (a.isStarted() && !a.isFinish() && (a.getNom().contains("stay")))
				a.stop();
		}
	}

	public ArrayList<Animation> getAnimations() {
		return animations;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkY() {
		return chunkY;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public String getClickScript() {
		return clickScript;
	}

	public String getCollideScript() {
		return collideScript;
	}

	public ArrayList<CollisionBlock> getCollision() {
		return collision;
	}

	public int getId() {
		return id;
	}

	public ArrayList<ObjetImage> getImage() {
		return image;
	}

	public ArrayList<ObjetImage> getImage(Direction direction) {
		if(this.getImageList(direction.toString()) != null)
		{
			return this.getImageList(direction.toString()).getList();
		}
		else
			return null;
	}

	public ObjetImage getImage(int i) {
		return image.get(i);
	}

	public ObjetImage getImage(String t) {
		if (t != null && t != "") {
			for (ObjetImage i : image) {
				if (i.getAlias() != null) {
					if (i.getAlias().equals(t)) {
						return i;
					}
				}
			}
		}
		return null;
	}

	public Iterator getImagesIterator() {
		return new ArrayIterator(image);
	}
	public Iterator getImagesIterator(Direction direction2) {
		return new ArrayIterator(getImage(direction2));
	}

	public Color getMaskColor() {
		return maskColor;
	}

	public String getNom() {
		return nom;
	}

	public int getOmbre() {
		return ombre;
	}

	public float getOpacity() {
		return opacity;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getPosZ() {
		return posZ;
	}
	
	public int getAbsPosX(int chunkSize) {
		return chunkX * (chunkSize) + posX;
	}

	public int getAbsPosY(int chunkSize) {
		return  chunkY * (chunkSize) + posY;
	}

	public int getAbsPosZ(int chunkSize) {
		return chunkZ * (chunkSize) + posZ;
	}
	
	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getSizeZ() {
		return sizeZ;
	}
	
	public void move(Jeu jeu, ChunkMap carte, float x, float y, float z){
		carte.deplacement(this, (int)x, (int)y, (int)z, jeu);
	}

	public Chrono getThisChrono(String name) {

		Chrono c = new Chrono(0, "");
		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		for (int i = 0; i < timesHelps.size(); i++) {
			if (timesHelps.get(i).getNom().equals(name))
				c = timesHelps.get(i);
		}
		return c;

	}
	
	public Chrono getChrono(String name) {
		return getThisChrono(name);
	}

	public ArrayList<Chrono> getTimesHelps() {
		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		return timesHelps;
	}

	public String getUpdateScript() {
		return updateScript;
	}

	public int getX() {
		return getPosX();
	}

	public int getY() {
		return getPosY();
	}

	public int getZ() {
		return getPosZ();
	}
	/**
	 * M?thode appel?e lors d'une collision avec un autre objet.
	 * @param objetMap 	objet touch?.
	 * @param jeu 		environnement de d?veloppement.
	 */
	protected void hasCollide(ObjetMap objetMap, Jeu jeu) {
		if (getCollideScript() != null && (!getCollideScript().equals(""))
				&& jeu != null) {
				String idHimself = "sc"+jeu.getCarte().getScriptId();
				String idCible = "sc" + jeu.getCarte().getScriptId();
				
				String collideScript = this.collideScript;
				collideScript = collideScript.replaceAll("himself", idHimself);
				jeu.getCarte().putValueToAdd(idHimself, this);
				
				collideScript  = collideScript .replaceAll("cible", idCible);
				jeu.getCarte().putValueToAdd(idCible, objetMap);
				
				jeu.getCarte().setScriptToLaunch(jeu.getCarte().getScriptToLaunch() + collideScript);
		}
		//this.actualTouchedBlocks.clear();
	}

	public boolean isAnExistingChrono(String name) {
		if (!getThisChrono(name).getNom().equals(""))
			return true;
		return false;
	}

	public boolean isDrawing() {
		return drawing;
	}

	public boolean isFly() {
		return fly;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public boolean isMirror() {

		return mirror;
	}

	public boolean isNotAnExistingChrono(String name) {
		return !isAnExistingChrono(name);
	}
	public boolean isUpdate() {
		return update;
	}

	/**
	 * Lance une animation 
	 * @param anim Alias associ? ? l'animation.
	 */
	public void launchAnimation(String anim) {
		Animation animation = this.getAnimation(anim);
		if (animation != null) { //$NON-NLS-1$
			if (animation.isFinish() || !animation.isStarted()) //$NON-NLS-1$ //$NON-NLS-2$
			{
				for (int i = 0, c = animations.size(); i < c; i++) {
					if (!animations.get(i).isFinish()
							|| animations.get(i).isStarted()) {
						animations.get(i).stop();
					}
				}
				animation.start();
			}
		}
	}

	protected abstract ObjetMap newMe(int chunkX, int chunkY, int chunkZ,
			int posX, int posY, int posZ);

	
	public int getXOnScreen(PanneauJeuAmeliore pan, Camera cam){
		ChunkMap map = pan.getCarte();
		Point p = new Point(this.getAbsPosX(map.getChunksSize()), this.getAbsPosY(map.getChunksSize()));
		Point p2 = Isometric.worldToScreen(p, this.getAbsPosZ(map.getChunksSize()), pan, cam);
		return p2.x;
		
	}
	public int getXOnScreen(Jeu jeu){
		return getXOnScreen(jeu.getPanneauDuJeu(), jeu.getPanneauDuJeu().getActualCam());
		
	}
	public int getYOnScreen(PanneauJeuAmeliore pan, Camera cam){
		ChunkMap map = pan.getCarte();
		Point p = new Point(this.getAbsPosX(map.getChunksSize()), this.getAbsPosY(map.getChunksSize()));
		Point p2 = Isometric.worldToScreen(p, this.getAbsPosZ(map.getChunksSize()), pan, cam);
		return p2.y;
	}
	public int getYOnScreen(Jeu jeu){
		return getYOnScreen(jeu.getPanneauDuJeu(), jeu.getPanneauDuJeu().getActualCam());
		
	}
	/**
	 * 
	 * @param pan the drawing panel
	 * @param actualCam the actual camera.
	 * @return a shape which is the polygon made with the sizes and positions of the objects. 
	 */
	public Shape getClickPolygon(PanneauJeuAmeliore pan, Camera actualCam){
		//Chunks size
		int cs = pan.getCarte().getChunksSize();
		Polygon polygon = new Polygon();
		//Cr?ation du Polygone de clic.
		//point 1
		polygon.addPoint(getXOnScreen(pan, actualCam), getYOnScreen(pan, actualCam));
		
		//Point 2
		Point limit = Isometric.worldToScreen(new Point(getAbsPosX(cs) + sizeX, getAbsPosY(cs)), getAbsPosZ(cs), pan, actualCam);
		polygon.addPoint(limit.x, limit.y);
		
		//Point 3
		 limit = Isometric.worldToScreen(new Point(getAbsPosX(cs) + sizeX, getAbsPosY(cs)), getAbsPosZ(cs) + sizeZ, pan, actualCam);
		polygon.addPoint(limit.x, limit.y);
		
		//Point 4
		  limit = Isometric.worldToScreen(new Point(getAbsPosX(cs) + sizeX, getAbsPosY(cs) + sizeY), getAbsPosZ(cs) + sizeZ, pan, actualCam);
		polygon.addPoint(limit.x, limit.y);
		//Point 5
		  limit = Isometric.worldToScreen(new Point(getAbsPosX(cs), getAbsPosY(cs) + sizeY), getAbsPosZ(cs) + sizeZ, pan, actualCam);
		polygon.addPoint(limit.x, limit.y);
		//Point 6
		 limit = Isometric.worldToScreen(new Point(getAbsPosX(cs), getAbsPosY(cs) + sizeY), getAbsPosZ(cs), pan, actualCam);
		polygon.addPoint(limit.x, limit.y);
		return polygon;
	}
	public void paintComponent(PanneauJeuAmeliore pan, Graphics g, Image img,
			int posX, int posY, ObjetImage c, Camera actualCam) {

		ObjetMap o = this;
		
		//Travail sur l'ombre.
		if(this.applyZShadow)
		ombre = posZ;
		else
			ombre = 0;
		if (getOmbre() > 150)
			setOmbre(150);
		if (getOmbre() < 0) {
			setOmbre(0);
		}
		//Obtention de la position du panneau dans le jeu
		float mouseX = (int) (Mouse.getX() + pan.getXOnScreen());
		float mouseY = (int) (pan.getRacine().getHeight() - Mouse.getY() - pan
				.getYOnScreen());
		
			if (getClickPolygon(pan, actualCam).contains(mouseX, mouseY)) {
				pan.setSurlign(true);
				if( !(pan.getParent() instanceof Jeu))
					setOmbre(getOmbre() + 50);
				if (pan.getSurlignObject() != null) {
					if (!pan.getSurlignObject().equals(this)) {
						pan.getSurlignObject().surligned = false;
						pan.setSurlignObject(this);

						if( !(pan.getParent() instanceof Jeu)){
							if (this.surligned == true)
								setOmbre(getOmbre() + 150);
							surligned = true;
						}
					}
				} else
					pan.setSurlignObject(this);
			}
			else{
				surligned = false;
			}
			
			
		//Modification de l'image.
		if(img != null)
		{
			img.setAlpha(opacity);
			
			//Chargement du masque
			Color maskColor = o.getMaskColor().scaleCopy(1f);
			//Application de l'opacit? de l'objet
			if(maskColor.a > opacity)
				maskColor.a = opacity;
			
			//Changement de la taille
			img = img.getScaledCopy((int)(c.getImageSizeInGameX() * actualCam.getZoom()),
					(int)(c.getImageSizeInGameY() * actualCam.getZoom()));
			
			//Changement du centre de rotation
			img.setCenterOfRotation(c.getRotationCenterX() * actualCam.getZoom(),
					c.getRotationCenterY() * actualCam.getZoom());
					
			//Rotation
			img.rotate(c.getRotation());
			
			
			if(maskColor.a == 0f){
				maskColor.a = 1f;
				maskColor.r = 1f;
				maskColor.g = 1f;
				maskColor.b = 1f;
			}
		//	GregorianCalendar calendar = new GregorianCalendar();
		//	float heure = calendar.get(Calendar.HOUR_OF_DAY) + (float)calendar.get(Calendar.MINUTE) / 60f;
		
			if(posX > pan.getCarte().getChunksSize() || posY > pan.getCarte().getChunksSize() || posZ > pan.getCarte().getChunksSize())
				pan.getCarte().getChunk(this).remove(this);
			if(pan.getParent() instanceof Jeu){
				Jeu jeu = (Jeu) pan.getParent();
				float nightValue = jeu.getTimeController().getNightValue();
		
				g.setAntiAlias(false);
				//Dessin
				img.draw(0, 0, new Color((float) (maskColor.r - (float)ombre / 255f - 0.7f * nightValue + 0.7f * (1f-nightValue)),maskColor.g  - (float)ombre / 255f - 0.7f * nightValue, maskColor.b   - (float)ombre / 255f  - 0.4f * (1f-nightValue), opacity));
			}
			else{
				img.draw(0, 0, new Color((float) (maskColor.r - (float)ombre / 255f),maskColor.g  - (float)ombre / 255f, maskColor.b   - (float)ombre / 255f, opacity));
			}
		}
	}

	public void removeChrono(String name) {
		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		timesHelps.remove(getThisChrono(name));
	}

	public void rotate(int numberOfRotation) {
		if (getDirection() == null)
			setDirection(Direction.S);
		setDirection(Direction.values()[(getDirection().ordinal() + numberOfRotation) % 8]);
	}

	public void setAnimations(ArrayList<Animation> animations) {
		this.animations = animations;
	}

	public void setBounds(int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
		setX(x);
		setY(y);
		setZ(z);
		setSizeX(sizeX);
		setSizeY(sizeY);
		setSizeZ(sizeZ);
	}

	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}

	public void setChunkY(int chunkY) {
		this.chunkY = chunkY;
	}

	public void setChunkZ(int chunkZ) {
		this.chunkZ = chunkZ;
	}

	public void setClickScript(String clickScript) {
		this.clickScript = clickScript;
	}

	public void setCollideScript(String collideScript) {
		this.collideScript = collideScript;
	}

	public void setCollision(ArrayList<CollisionBlock> collision) {
		this.collision = collision;
	}

	public void setDirection(Direction dir) {
		direction = dir;
		setImage(dir);
	}



	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public void setFly(boolean fly) {
		this.fly = fly;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setImage(ArrayList<ObjetImage> image) {
		this.image = image;
	}

	protected void setImage(Direction dir) {
		setImageList(dir.name());
	}
	public void setImageList(String alias){
		image = new ArrayList<ObjetImage>();
		ObjetImageList imageList = getImageList(alias);
		this.setCurrentImageList(alias);
		if(imageList != null){
			for (ObjetImage i : imageList.getList())
				image.add(i.clone());
		}
	}
	public ObjetImageList getImageList(String alias) {
		if(getImagesLists() != null){
			for(ObjetImageList i : getImagesLists()){
				if(i.getAlias().equals(alias))
					return i;
			}
		}else{
			setImagesLists(new ArrayList<ObjetImageList>());
		}
		return null;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public void setMaskColor(Color maskColor) {
		this.maskColor = maskColor;
	}

	public void setMirror(boolean b) {
		if (b && !mirror) {
			mirror = true;
			int a;
			a = this.sizeY;
			this.sizeY = this.sizeX;
			this.sizeX = a;
			for (int i = 0; i < image.size(); i++) {
				image.get(i).setMirror(!image.get(i).isMirror());
			}
			for (int i = 0; i < collision.size(); i++) {
				collision.get(i).setMirror(b);
			}

		} else if (!b && mirror) {
			mirror = false;
			int a;
			a = this.sizeX;
			this.sizeX = this.sizeY;
			this.sizeY = a;
			for (int i = 0; i < image.size(); i++) {
				image.get(i).setMirror(!image.get(i).isMirror());
			}
			for (int i = 0; i < collision.size(); i++) {
				collision.get(i).setMirror(b);
			}
		}
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setOmbre(int ombre) {
		this.ombre = ombre;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public void setPosition(int chunkx, int chunky, int chunkz, int x, int y,
			int z) {
		chunkX = chunkx;
		chunkY = chunky;
		chunkZ = chunkz;
		posX = x;
		posY = y;
		posZ = z;
	}

	public void setPosition(ObjetMap o) {
		chunkX = o.chunkX;
		chunkY = o.chunkY;
		chunkZ = o.chunkZ;
		posX = o.posX + o.getSizeX() / 2 - getSizeX() / 2;
		posY = o.posY + o.getSizeY() / 2 - getSizeY() / 2;
		posZ = o.posZ;
	}

	public void setPosX(int posX) {
		if (posX < 0 && chunkX == 0)
			posX = 0;
		this.posX = posX;
	}

	public void setPosY(int posY) {
		if (posY < 0 && chunkY == 0)
			posY = 0;

		this.posY = posY;
	}

	public void setPosZ(int posZ) {
		if (posZ < 0 && chunkZ == 0)
			posZ = 0;
		this.posZ = posZ;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}


	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}

	public void setTimesHelps(ArrayList<Chrono> timesHelps) {
		this.timesHelps = timesHelps;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public void setUpdateScript(String updateScript) {
		this.updateScript = updateScript;
	}

	public void setX(int x) {
		setPosX(x);
	}

	public void setY(int y) {
		setPosY(y);
	}

	public void setZ(int z) {
		setPosZ(z);
	}

	public String toString() {
		return this.nom;
	}
	/**
	 * Permet d'obtenir une version compress?e du script de cet objet.
	 * @return script compress?.
	 */
	public String getCompressScript(){
		String compressScript = "";
		if(updateScript != null)
			compressScript += updateScript;
		if (animations != null) {
			for (Animation anim : animations) {
				if (anim.isStarted() && !anim.isFinish()) {
					compressScript += anim.getCompressScript(this);
					anim.setCursor(anim.getCursor() + 1);
				}
				else if(anim.isFinish() && anim.isRepeat()){
					anim.repeat();
				}
			}
		} else {
			animations = new ArrayList<Animation>();
		}
		return compressScript;
		
	}
	/**
	 * Permet de mettre ? jour l'objet, ne permet pas d'ex?cuter des scripts.
	 * @param jeu Environnement de d?veloppement du jeu.
	 */
	public void update(Jeu jeu) {

		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		
		String compressScript = getCompressScript();
		String id = "a"+jeu.getCarte().getScriptId();
		
		compressScript = compressScript.replaceAll("cible", id);
		compressScript = compressScript.replaceAll("himself", id);
		jeu.getCarte().putValueToAdd(id, this);
		
		//compressScript = compressScript.replaceAll("images", id + "Images");
		//bindings.put(id + "Images", updatable.get(i).getImage());
		
		for(ObjetImage objImg : getImage()){
			if(objImg.getAlias() != null && !objImg.getAlias().equals("")){
				String id2 = "a"+jeu.getCarte().getScriptId();
				compressScript = compressScript.replaceAll(objImg.getAlias(), id2);
				jeu.getCarte().putValueToAdd(id2, objImg);
			}
		}
		jeu.getCarte().setScriptToLaunch(jeu.getCarte().getScriptToLaunch() + compressScript);
	}
	/** 
	 * Permet d'updater seul, sans prendre en compte les autres. 
	 * Prend en compte les scripts.
	 * @param jeu Environnement de d?veloppement du jeu.
	 * @param carte Carte de la map actuelle.
	 */
	public void updateAlone(Jeu jeu, ChunkMap carte) {

		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		String finalScript = "";
		if(updateScript != null)
			finalScript += updateScript;
		if (animations != null) {
			for (Animation anim : animations) {
				if (anim.isStarted() && !anim.isFinish()) {
					finalScript += anim.getCompressScript(this);
					anim.setCursor(anim.getCursor() + 1);
				}
				else if(anim.isFinish() && anim.isRepeat()){
					anim.repeat();
				}
			}
		} else {
			animations = new ArrayList<Animation>();
		}
		if (!finalScript.equals("")) {
			try {

				Bindings bindings = ScriptManager.moteurScript.getBindings(ScriptContext.ENGINE_SCOPE); 
				bindings.clear();
				// Ajout de la variable entree dans le script
				bindings.put("himself", this);
				bindings.put("cible", this);
				if(jeu != null)
				bindings.put("newcam", new Camera(0, 0, 1f, jeu.getCarte()));
				else
				bindings.put("newcam", new Camera(0, 0, 1f, null));
				bindings.put("E", Direction.E);
				bindings.put("SE", Direction.SE);
				bindings.put("S", Direction.S);
				bindings.put("SW", Direction.SW);
				bindings.put("W", Direction.W);
				bindings.put("NW", Direction.NW);
				bindings.put("N", Direction.N);
				bindings.put("NE", Direction.NE);
				bindings.put("Random", new Random());
				bindings.put("direction", Arrays.asList(Direction.values()));
				bindings.put("images", getImage());
				for(ObjetImage objImg : getImage()){
					if(objImg.getAlias() != null && !objImg.getAlias().equals(""))
						bindings.put(objImg.getAlias(), objImg);
				}
				bindings.put("emptychrono",
						new Chrono(System.currentTimeMillis(), "Real time"));
				bindings.put("currentTimeMillis", System.currentTimeMillis());
				bindings.put("carte", carte);
				if (jeu != null) {
					bindings.put("jeu", jeu);
					bindings.put("emptytext", new Text("", jeu.getDialogBar()));
					bindings.put("actualcam", jeu.getPanneauDuJeu()
							.getActualCam());
				}
				else{
					bindings.put("jeu", null);
				}
				//Suppression des caract?res louches.
				finalScript = finalScript.replaceAll(Character.toString((char) 22), "");
				// Execution du script entr�e
				ScriptManager.moteurScript.eval(finalScript, bindings);
				
			} catch (ScriptException e) {
				e.printStackTrace();
				for(int i = 0; i < finalScript.length(); i++){
					if(!(Character.isDigit(finalScript.charAt(i)) || Character.isLetter(finalScript.charAt(i))))
					System.out.print((int)finalScript.charAt(i)+"("+Character.valueOf(finalScript.charAt(i))+")");
					
				}
			}
		}

		
	}
	/**
	 * Compare la profondeur de cet objet ? un autre.
	 * @param other l'objet ? comparer.
	 * @return r?sultat de la comparaison.
	 */
	public int compare(ObjetMap other){
		 int endPosZ = posZ + sizeZ; 
	      if(endPosZ < other.getPosZ()
								|| (((posY > other.getPosY() + other.getSizeY() 
										&& posX >= other.getPosX()) 
										|| (posX > other.getPosX() + other.getSizeX() 
										&& posY >= other.getPosY()))))
	    	  return -1;
	      else
	    	  return 0;
	}
	
	@Override
	public int compareTo(ObjetMap other) { 
	     if(compare(other) == -1)
	    	  return -1;
	      else if(other.compare(this) == -1)
	    	  return 1;
	      else 
	    	  return 0; 
	   } 
	
	public Direction getDirection() {
		if(direction == null)
			direction = Direction.S;
		return direction;
	}

	/**
	 * 
	 * @return imagesLists Une liste de listes d'information sur des images.
	 */
	public ArrayList<ObjetImageList> getImagesLists() {
		if(imagesLists == null)
			imagesLists = new ArrayList<ObjetImageList>();
		if(imagesLists.size() == 0){
			//Cr?ation d'une liste par d?faut si aucune liste n'est existante.
			ObjetImageList defaultList = new ObjetImageList("default");
			
			defaultList.getList().addAll(image);
			
			this.imagesLists.add(defaultList);
			
			this.currentImageList = "default";
		}
		
		return imagesLists;
	}

	public void setImagesLists(ArrayList<ObjetImageList> imagesLists) {
		this.imagesLists = imagesLists;
	}

	public String getCurrentImageList() {
		if(currentImageList == null)
			currentImageList = "";
		return currentImageList;
	}

	public void setCurrentImageList(String currentImageList) {
		this.currentImageList = currentImageList;
	}

	public boolean isApplyZShadow() {
		return applyZShadow;
	}

	public void setApplyZShadow(boolean applyZShadowe) {
		this.applyZShadow = applyZShadowe;
	}


	public ArrayList<ObjetMap> getTouchedObjects() {
		if(touchedObjects == null)
		touchedObjects = new ArrayList<ObjetMap>();
		return touchedObjects;
	}


	public void setTouchedObjects(ArrayList<ObjetMap> touchedObjects) {
		this.touchedObjects = touchedObjects;
	}

	
}
