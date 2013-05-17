
package ObjetMap;

import gui.Text;
import gui.jeu.Jeu;
import gui.jeu.PanneauJeuAmeliore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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
	protected boolean surligned;
	
	
	protected Color maskColor;
	
	protected ArrayList<Chrono> timesHelps;

	protected Direction direction;
	
	protected String currentImageList;
	
	private ArrayList<ObjetImageList> imagesLists;
	
	/**
	 * decalage X sur l'ecran pour les objets speciaux
	 */
	protected int decalageX;
	/**
	 * decalage Y sur l'ecran pour les objets speciaux
	 */
	protected int decalageY;
	public static int instanceNumber = 0;
	protected int sizeX, sizeY, sizeZ;
	protected float opacity;
	private int ombre;

	// Variables de performances
	protected transient ArrayList<Integer> drawImage;
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
		decalageX = 0;
		decalageY = 0;
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
		setDrawImage(new ArrayList<Integer>());
		setTimesHelps(new ArrayList<Chrono>());
		animations = new ArrayList<Animation>();
	}

	private synchronized boolean acceptableX(ObjetMap o) {
		boolean accepted = true;
		if (o.getPosX() >= getPosX() && o.getPosX() <= getPosX() + getSizeX())
			accepted = false;
		if (getPosX() >= o.getPosX() && getPosX() <= o.getPosX() + o.getSizeX())
			accepted = false;

		return accepted;
	}

	private synchronized boolean acceptableY(ObjetMap o) {
		boolean accepted = true;
		if (o.getPosY() >= getPosY() && o.getPosY() <= getPosY() + getSizeY())
			accepted = false;
		if (getPosY() >= o.getPosY() && getPosY() <= o.getPosY() + o.getSizeY())
			accepted = false;
		return accepted;
	}

	private synchronized boolean acceptableZ(ObjetMap o) {
		boolean accepted = true;
		if (o.getPosZ() >= getPosZ() && o.getPosZ() <= getPosZ() + getSizeZ())
			accepted = false;
		if (getPosZ() >= o.getPosZ() && getPosZ() <= o.getPosZ() + o.getSizeZ())
			accepted = false;

		return accepted;
	}

	public void addChrono(String name, long chrono) {
		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		Chrono c = new Chrono(chrono, name);
		timesHelps.add(c);
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
		o.decalageX = decalageX;
		o.decalageY = decalageY;
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
			if (!acceptableX(o) && !acceptableY(o) && !acceptableZ(o)) {
				for (int i = 0, l = this.getCollision().size(); i < l; i++) {
					for (int j = 0, l2 = o.getCollision().size(); j < l2; j++) {
						CollisionBlock c = this.getCollision().get(i);
						CollisionBlock co = o.getCollision().get(j);
						if (!c.accept(this, o, co)) {
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

	public int getDecalageX() {
		return decalageX;
	}

	public int getDecalageY() {
		return decalageY;
	}

	public ArrayList<Integer> getDrawImage() {
		return drawImage;
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
	 * M�thode appel�e lors d'une collision avec un autre objet.
	 * @param objetMap 	objet touch�.
	 * @param jeu 		environnement de d�veloppement.
	 */
	protected void hasCollide(ObjetMap objetMap, Jeu jeu) {
		if (getCollideScript() != null && (!getCollideScript().equals(""))
				&& jeu != null) {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine moteur = manager.getEngineByName("rhino");
			try {

				Bindings bindings = moteur
						.getBindings(ScriptContext.ENGINE_SCOPE);
				bindings.clear();
				// Ajout de la variable entree dans le script
				bindings.put("himself", this);
				bindings.put("cible", objetMap);
				bindings.put("emptytext", new Text("", jeu.getDialogBar()));
				bindings.put("newcam", new Camera(0, 0, 1f));
				bindings.put("actualcam", jeu.getPanneauDuJeu().getActualCam());
				bindings.put("currentTimeMillis", System.currentTimeMillis());
				bindings.put("emptychrono",
						new Chrono(System.currentTimeMillis(), "Real time"));
				bindings.put("jeu", jeu);
				// Execution du script entr�e
				moteur.eval(getCollideScript(), bindings);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
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
	 * @param anim Alias associ� � l'animation.
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

	public void paintComponent(PanneauJeuAmeliore pan, Graphics g, Image img,
			int posX, int posY, ObjetImage c, Camera actualCam) {

		ObjetMap o = this;
		int x = (int) (Mouse.getX() + pan.getXOnScreen());
		int y = (int) (pan.getRacine().getHeight() - Mouse.getY() - pan
				.getYOnScreen());
		int minX = posX
				+ (int) ((c.getImageSizeInGameX()) * actualCam.getZoom() / 2);
		minX -= sizeY * actualCam.getZoom();
		int maxX = posX
				+ (int) (c.getImageSizeInGameX() * actualCam.getZoom() / 2);
		maxX += sizeX * actualCam.getZoom();
		int minY = (int) (posY + c.getImageSizeInGameY() * actualCam.getZoom());
		minY -= ((sizeY / 2 + sizeX / 2 + sizeZ) * actualCam.getZoom());
		int maxY = (int) (posY + c.getImageSizeInGameY() * actualCam.getZoom());
		if (x > minX && x < maxX && y > minY && y < maxY) {
			pan.setSurlign(true);

			if (pan.getSurlignObject() != null) {
				if (!pan.getSurlignObject().equals(this)) {
					pan.getSurlignObject().surligned = false;
					pan.setSurlignObject(this);
					if (this.surligned == true)
						setOmbre(getOmbre() + (100 + getOmbre()));
					surligned = true;
				}
			} else
				pan.setSurlignObject(this);

		}
		ombre = posZ;
		if (getOmbre() > 255)
			setOmbre(255);
		if (getOmbre() < 0) {
			setOmbre(0);
		}
		img.setAlpha(opacity);
		Color maskColor = o.getMaskColor();
		img.setCenterOfRotation(c.getRotationCenterX() * actualCam.getZoom(),
				c.getRotationCenterY() * actualCam.getZoom());
		img.rotate(c.getRotation());
		img.draw(0, 0, c.getImageSizeInGameX() * actualCam.getZoom(),
				c.getImageSizeInGameY() * actualCam.getZoom());
		img.draw(0, 0, c.getImageSizeInGameX() * actualCam.getZoom(),
				c.getImageSizeInGameY() * actualCam.getZoom(), maskColor);
		img.draw(0, 0, c.getImageSizeInGameX() * actualCam.getZoom(),
				c.getImageSizeInGameY() * actualCam.getZoom(), new Color(0, 0,
						0, getOmbre()));
	}

	public void removeChrono(String name) {
		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
		timesHelps.remove(getThisChrono(name));
	}

	public void rotate(int numberOfRotation) {
		if (getDirection() == null)
			setDirection(Direction.S);
		System.out.println(getDirection());
		setDirection(Direction.values()[(getDirection().ordinal() + numberOfRotation) % 8]);
		System.out.println(getDirection());
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

	public void setDecalageX(int decalageX) {
		this.decalageX = decalageX;
	}

	public void setDecalageY(int decalageY) {
		this.decalageY = decalageY;
	}

	public void setDirection(Direction dir) {
		direction = dir;
		setImage(dir);
	}

	public void setDrawImage(ArrayList<Integer> drawImage) {
		this.drawImage = drawImage;
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
		posX = o.posX;
		posY = o.posY;
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
	 * Permet d'obtenir une version compress�e du script de cet objet.
	 * @return script compress�.
	 */
	public String getCompressScript(){
		String compressScript = "";
		if(updateScript != null)
			compressScript += updateScript;
		if (animations != null) {
			for (Animation anim : animations) {
				if (anim.isStarted() && !anim.isFinish()) {
					compressScript += anim.getCompressScript();
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
	 * Permet de mettre � jour l'objet, ne permet pas d'ex�cuter des scripts.
	 * @param jeu Environnement de d�veloppement du jeu.
	 */
	public void update(Jeu jeu) {

		if (timesHelps == null)
			timesHelps = new ArrayList<Chrono>();
	}
	/** 
	 * Permet d'updater seul, sans prendre en compte les autres. 
	 * Prend en compte les scripts.
	 * @param jeu Environnement de d�veloppement du jeu.
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
					finalScript += anim.getCompressScript();
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

				Bindings bindings = Jeu.moteurScript.getBindings(ScriptContext.ENGINE_SCOPE); 
				bindings.clear();
				// Ajout de la variable entree dans le script
				bindings.put("himself", this);
				bindings.put("cible", this);
				bindings.put("newcam", new Camera(0, 0, 1f));
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
				
				// Execution du script entr�e
				Jeu.moteurScript.eval(finalScript, bindings);
				
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}

		
	}
	/**
	 * Compare la profondeur de cet objet � un autre.
	 * @param other l'objet � comparer.
	 * @return r�sultat de la comparaison.
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
			//Cr�ation d'une liste par d�faut si aucune liste n'est existante.
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

	
}
