package Level;

import gui.jeu.Jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import ObjetMap.Direction;
import ObjetMap.Ensemble;
import ObjetMap.Entity;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;
import ObjetMap.Teleporter;

public class Chunk implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7005584734886292589L;
	public static final int GOD_MOD = 0;
	public static final int VERIFY_MOD = 1;
	public static final int ERASE_MOD = 2;
	private int modeActuel = VERIFY_MOD;
	
	private int sizeX;
	private int sizeY;
	private int sizeZ;
	
	protected boolean hasXYtoSort = true;
	private boolean hasZtoSort = true;
	protected boolean drawing = true;
	
	private ArrayList<ObjetMap> updatable;
	protected ArrayList<ObjetMap> contenu;
	
	private ArrayList<Teleporter> teleporters;
	// Variables de performances
	protected transient long lastSort = 0;
	protected transient long sortDelay = 10000;
	
	protected transient String megaCompressScript;

	public Chunk() {
		sizeX = 1;
		sizeY = 1;
		sizeZ = 1;
		setContenu(new ArrayList<ObjetMap>());
		setUpdatable(new ArrayList<ObjetMap>());
	}

	public Chunk(int sizeX, int sizeY, int sizeZ, ArrayList<ObjetMap> contenu) {
		this.setSizeX(sizeX);
		this.setSizeY(sizeY);
		this.setSizeZ(sizeZ);
		setContenu(new ArrayList<ObjetMap>());
		setUpdatable(new ArrayList<ObjetMap>());
		setContenu(contenu);
	}

	public synchronized boolean accepted(ObjetMap o, Jeu jeu) {
		boolean accepted = true;
		if ((!o.isInvisible()
				&& !(o.getPosX() <= this.sizeX && o.getPosY() <= this.sizeY && o
				.getPosZ() <= this.sizeZ)) && modeActuel != GOD_MOD) {
			accepted = false;
		}
		if (accepted && modeActuel == VERIFY_MOD) {
			for (int i = 0; i < getTeleporters().size(); i++) {
				if (getTeleporters().get(i).collide(o, jeu)) {
					accepted = false;
				}
			}
		}
		if (accepted && modeActuel == VERIFY_MOD) {
			for (int i = 0; i < getContenu().size(); i++) {
				if (getContenu().get(i).collide(o, jeu)) {
					accepted = false;
				}
			}
		}

		return accepted;
	}

	public synchronized boolean accepted(ObjetMap o, ObjetMap without, Jeu jeu) {
		boolean accepted = true;

		if ((!o.isInvisible()
				&& !(o.getPosX() <= this.sizeX && o.getPosY() <= this.sizeY && o
				.getPosZ() <= this.sizeZ)) && modeActuel != GOD_MOD) {
			accepted = false;
		}
		if (accepted && modeActuel == VERIFY_MOD) {
			for (int i = 0; i < getTeleporters().size(); i++) {
				if (getTeleporters().get(i).collide(o, jeu)) {
					accepted = false;
				}
			}
		}
		if (accepted && modeActuel == VERIFY_MOD) {
			for (int i = 0; i < getContenu().size(); i++) {
				if (!getContenu().get(i).equals(without)) {
					if (getContenu().get(i).collide(o, jeu)) {
						accepted = false;
					}
				}
			}
		}

		return accepted;
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
			while(obj.getPosX() > this.getSizeX()){
				obj.setPosX(obj.getPosX() - this.getSizeX());
				obj.setChunkX(obj.getChunkX() + 1);
			}
			while(obj.getPosY() > this.getSizeY()){
				obj.setPosY(obj.getPosY() - this.getSizeY());
				obj.setChunkY(obj.getChunkY() + 1);
			}
			while(obj.getPosZ() > this.getSizeZ()){
				obj.setPosZ(obj.getPosZ() - this.getSizeZ());
				obj.setChunkZ(obj.getChunkZ() + 1);
			}
			addContenu(obj);
		}
		return true;
	}

	public synchronized boolean addContenu(ObjetMap o) {
		switch (getModeActuel()) {
		case GOD_MOD:
			getContenu().add(o);
			break;
		case VERIFY_MOD:
			if (accepted(o, null)) {
				if (o.isUpdate()) {
					updatable.add(o);
				}
				if(o instanceof Teleporter && acceptedTeleporter(o, null)){
					this.getTeleporters().add((Teleporter) o);
				}
				else
					getContenu().add(o);
			} else {
				return false;
			}
			break;
		case ERASE_MOD:
			eraseObstaclesFrom(o, null);
			getContenu().add(o);
			break;

		}
		return true;
	}

	public synchronized boolean addContenu(ObjetMap o, int id) {
		switch (getModeActuel()) {
		case GOD_MOD:
			getContenu().add(id, o);
			break;
		case VERIFY_MOD:
			if (accepted(o, null)) {
				if (o.isUpdate()) {
					updatable.add(o);
				}
				if(o instanceof Teleporter && acceptedTeleporter(o, null)){
					this.getTeleporters().add((Teleporter) o);
				}
				else
					getContenu().add(o);
			} else {
				return false;
			}
			break;
		case ERASE_MOD:
			eraseObstaclesFrom(o, null);
			getContenu().add(id, o);
			break;

		}
		return true;
	}
	public void whenLoaded(){
		
	}
	private synchronized boolean acceptedTeleporter(ObjetMap o, Jeu jeu) {
		boolean accepted = true;
		if ((!o.isInvisible()
				&& !(o.getPosX() <= this.sizeX && o.getPosY() <= this.sizeY && o
				.getPosZ() <= this.sizeZ)) && modeActuel != GOD_MOD) {
			accepted = false;
		}
		if (accepted && modeActuel == VERIFY_MOD) {
			for (int i = 0; i < getTeleporters().size(); i++) {
				if (getTeleporters().get(i).collide(o, jeu)) {
					accepted = false;
				}
			}
		}

		return accepted;
	}

	private synchronized boolean acceptedTeleporter(ObjetMap o, ObjetMap without, Jeu jeu) {
		boolean accepted = true;

		if ((!o.isInvisible()
				&& !(o.getPosX() <= this.sizeX && o.getPosY() <= this.sizeY && o
				.getPosZ() <= this.sizeZ)) && modeActuel != GOD_MOD) {
			accepted = false;
		}
		if (accepted && modeActuel == VERIFY_MOD) {
			for (int i = 0; i < getTeleporters().size(); i++) {
				if (!getTeleporters().get(i).equals(without)) {
					if (getTeleporters().get(i).collide(o, jeu)) {
						accepted = false;
					}
				}
			}
		}
		return accepted;
	}

	public synchronized boolean addContenu(ObjetMap o, ObjetMap clone) {
		switch (getModeActuel()) {
		case GOD_MOD:
			getContenu().add(o);
			break;
		case VERIFY_MOD:
			if (accepted(o, clone, null)) {
				if (o.isUpdate()) {
					updatable.add(o);
				}
				if(o instanceof Teleporter && acceptedTeleporter(o, clone, null)){
					this.getTeleporters().add((Teleporter) o);
				}
				else
					getContenu().add(o);
			} else {
				return false;
			}
			break;
		case ERASE_MOD:
			eraseObstaclesFrom(o, null);
			getContenu().add(o);
			break;

		}
		return true;
	}

	
	public Chunk clone() {
		Chunk o = null;
		try {
			// On r�cup�re l'instance � renvoyer par l'appel de la
			// m�thode super.clone()
			o = (Chunk) super.clone();
		} catch (CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous impl�mentons
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		o.updatable = new ArrayList<ObjetMap>();
		for (ObjetMap obj : updatable) {
			o.updatable.add(obj.clone());
		}
		o.contenu = new ArrayList<ObjetMap>();
		for (ObjetMap obj : getContenu()) {
			o.contenu.add(obj.clone());
		}
		// on renvoie le clone
		return o;

	}

	public void enable(int i) {
		setModeActuel(i);
	}

	private synchronized void eraseObstaclesFrom(ObjetMap o, Jeu jeu) {
		for (int i = 0; i < getContenu().size(); i++) {
			if (getContenu().get(i).collide(o, jeu)) {
				getContenu().remove(i);
			}
		}
	}

	public synchronized ArrayList<ObjetMap> getContenu() {
		return contenu;
	}

	public synchronized ObjetMap getContenu(int x, int y, int z) {
		for (int i = 0; i < getContenu().size(); i++) {
			if (getContenu().get(i).getPosX() == x
					&& getContenu().get(i).getPosY() == y
					&& getContenu().get(i).getPosZ() == z) {
				return getContenu().get(i);
			}

		}
		return null;
	}

	// Constructors
	public Iterator getContenuIterator() {
		return new ArrayIterator(contenu);
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

	/**
	 * @return the updatable
	 */
	public ArrayList<ObjetMap> getUpdatable() {
		return updatable;
	}

	public boolean isDrawing() {
		return drawing;
	}

	/**
	 * @return the hastoSort
	 */
	public boolean isHasXYtoSort() {
		return hasXYtoSort;
	}

	/**
	 * @return the hasZtoSort
	 */
	public boolean isHasZtoSort() {
		return hasZtoSort;
	}

	public synchronized ObjetMap nextLowerXYZ(int number) {
		int xyz = 2000000;
		ObjetMap lower = null;
		for (int i = number, c = contenu.size() - 1; i < c; i++) {
			ObjetMap other = contenu.get(i);
			if (other.getX() + other.getSizeX() < xyz) {
				xyz = other.getX() + other.getSizeX();
				lower = other;
			}
			if (other.getY() + other.getSizeY() < xyz) {
				xyz = other.getY() + other.getSizeY();
				lower = other;
			}
		}
		return lower;
	}

	public synchronized ObjetMap nextLowerZ(int x, int y) {
		int z = 2000000;
		ObjetMap lower = null;
		for (int i = 0, c = contenu.size() - 1; i < c; i++) {
			ObjetMap other = contenu.get(i);
			if (other.getX() == x && other.getY() == y
					&& other.getZ() + other.getSizeZ() < z) {
				z = other.getZ();
				lower = other;
			}
		}
		return lower;
	}

	public synchronized void remove(Ensemble ensembleChoice) {
		for (int i = 0, c = ensembleChoice.getContenu().size(); i < c; i++) {
			ObjetMap obj = ensembleChoice.getContenu().get(i);
			if (contenu.contains(obj)) {
				contenu.remove(ensembleChoice.getContenu().get(i));
				updatable.remove(ensembleChoice.getContenu().get(i));
			}
		}
	}

	public boolean remove(int id) {

		return false;
	}

	public synchronized void remove(int x, int y, int z) {
		for (int i = 0; i < getContenu().size(); i++) {
			if (getContenu().get(i).getPosX() == x
					&& getContenu().get(i).getPosY() == y
					&& getContenu().get(i).getPosZ() == z) {
				if (getContenu().get(i).isUpdate())
					updatable.remove(getContenu().get(i));
				getContenu().remove(i);
			}

		}
	}

	public synchronized boolean remove(int x, int y, int z, int id) {
		for (int i = 0; i < getContenu().size(); i++) {
			if (getContenu().get(i).getPosX() == x
					&& getContenu().get(i).getPosY() == y
					&& getContenu().get(i).getPosZ() == z
					&& getContenu().get(i).getId() == id) {
				if (getContenu().get(i).isUpdate())
					updatable.remove(getContenu().get(i));
				getContenu().remove(i);
				i = getContenu().size();
				return true;

			}

		}
		return false;
	}

	public synchronized void remove(int x, int y, int z, String nom) {
		for (int i = 0; i < getContenu().size(); i++) {
			if (getContenu().get(i).getPosX() == x
					&& getContenu().get(i).getPosY() == y
					&& getContenu().get(i).getPosZ() == z
					&& getContenu().get(i).getNom().equals(nom)) {
				if (getContenu().get(i).isUpdate())
					updatable.remove(getContenu().get(i));
				getContenu().remove(i);

				i = getContenu().size();
			}

		}
	}

	public synchronized void remove(ObjetMap o) {
		contenu.remove(o);
		updatable.remove(o);
		this.getTeleporters().remove(o);
	}

	public synchronized ObjetMap searchId(int l) {
		for (int i = 0; i < getContenu().size(); i++) {
			if (getContenu().get(i).getId() == l)
				return getContenu().get(i);
		}
		return null;
	}

	public synchronized ObjetMap searchLowerY(int x) {
		int y = 2000000;
		int z = 2000000;
		ObjetMap lower = null;
		for (int i = 0, c = contenu.size() - 1; i < c; i++) {
			ObjetMap other = contenu.get(i);
			if (other.getX() == x && other.getY() < y) {
				y = other.getY();
				z = other.getZ();
				lower = other;
			} else if (other.getX() == x && other.getY() == y
					&& other.getZ() + other.getSizeZ() == z) {
				y = other.getY();
				z = other.getZ();
				lower = other;
			}
		}
		return lower;
	}

	// Getters setters
	private synchronized void setContenu(ArrayList<ObjetMap> contenu) {
		if (contenu != null)
			this.contenu = contenu;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	/**
	 * @param hastoSort
	 *            the hastoSort to set
	 */
	public void setHasXYtoSort(boolean hastoSort) {
		this.hasXYtoSort = hastoSort;
	}

	/**
	 * @param hasZtoSort
	 *            the hasZtoSort to set
	 */
	public void setHasZtoSort(boolean hasZtoSort) {
		this.hasZtoSort = hasZtoSort;
	}

	public void setSizeX(int sizeX) {
		if (sizeX >= 0)
			this.sizeX = sizeX;
		else
			this.sizeX = 1;
	}

	public void setSizeY(int sizeY) {
		if (sizeY >= 0)
			this.sizeY = sizeY;
		else
			this.sizeY = 1;
	}

	public void setSizeZ(int sizeZ) {
		if (sizeZ >= 0)
			this.sizeZ = sizeZ;
		else
			this.sizeZ = 1;
	}

	/**
	 * @param updatable
	 *            the updatable to set
	 */
	public void setUpdatable(ArrayList<ObjetMap> updatable) {
		this.updatable = updatable;
	}
/*
	public synchronized void sort(ObjetMap cle) {
		int id = -1;
		for (int i = 0, c = contenu.size() - 1; i < c; i++) {
			ObjetMap other = contenu.get(i);
			if (!other.equals(cle)) {
				// int devant = 0;
				int zA = cle.getPosZ(), ezA = zA + cle.getSizeZ();
				int zB = other.getPosZ(), ezB = zB + other.getSizeZ();
				/*
				 * int xA = cle.getPosX(), exA = xA + cle.getSizeX(); int xB =
				 * other.getPosX(), exB = xB + other.getSizeX();
				 * 
				 * int yA = cle.getPosY(), eyA = yA + cle.getSizeY(); int yB =
				 * other.getPosY(), eyB = yB + other.getSizeY();
				 
				if (zA > ezB)
					id = i;
			}
		}
		contenu.remove(cle);
		contenu.add(id + 1, cle);
	}
	
	public synchronized void sortByOriginDistance(ObjetMap cle){
		int id = -1;
		for (int i = 0, c = contenu.size() - 1; i < c; i++) {
			ObjetMap other = contenu.get(i);
			if (!other.equals(cle)) {
				// int devant = 0;
				int distanceA = (int) (Math.pow(cle.getPosZ() + cle.getSizeZ(), 2) + Math.pow(cle.getPosY() + cle.getSizeY(), 2) + Math.pow(cle.getPosX() + cle.getSizeX(), 2));
				int distanceB = (int) (Math.pow(other.getPosZ() + other.getSizeZ(), 2) + Math.pow(other.getPosY() + other.getSizeY(), 2) + Math.pow(other.getPosX() + other.getSizeX(), 2));
				/*
				 * int xA = cle.getPosX(), exA = xA + cle.getSizeX(); int xB =
				 * other.getPosX(), exB = xB + other.getSizeX();
				 * 
				 * int yA = cle.getPosY(), eyA = yA + cle.getSizeY(); int yB =
				 * other.getPosY(), eyB = yB + other.getSizeY();
				 
				if (distanceA <= distanceB)
					id = i;
			}
		}
		contenu.remove(cle);
		contenu.add(id + 1, cle);
	}
	public synchronized void sortByCaseOriginDistance(int x, int y, int z, ObjetMap cle){
		int id = -1;
		for (int i = 0, c = contenu.size() - 1; i < c; i++) {
			ObjetMap other = contenu.get(i);
			if (!other.equals(cle)) {
				// int devant = 0;
				double zA = cle.getPosZ() / z;
				double zB = other.getPosZ() / z;
				
				int distanceA = (int) ( + Math.pow(cle.getPosY()  + cle.getSizeY(), 2) + Math.pow(cle.getPosX()  + cle.getSizeX(), 2));
				int distanceB = (int) ( + Math.pow(other.getPosY()  + other.getSizeY(), 2) + Math.pow(other.getPosX()  + other.getSizeX(), 2));
				
				if (zA > zB)
					id = i;
				else if (distanceA <= distanceB && zA == zB)
					id = i;
			}
		}
		contenu.remove(cle);
		contenu.add(id + 1, cle);
	}
	public synchronized void sort(ObjetMap cle, int depart) {
		int id = -1;
		for (int i = depart, c = contenu.size() - 1; i < c; i++) {
			ObjetMap other = contenu.get(i);
			if (!other.equals(cle)) {
				int devant = 0;
				int zA = cle.getPosZ(), ezA = zA + cle.getSizeZ();
				int zB = other.getPosZ(), ezB = zB + other.getSizeZ();
				int xA = cle.getPosX(), exA = xA + cle.getSizeX();
				int xB = other.getPosX(), exB = xB + other.getSizeX();
				int yA = cle.getPosY(), eyA = yA + cle.getSizeY();
				int yB = other.getPosY(), eyB = yB + other.getSizeY();
				if (zA > ezB)
					devant = 1;
				else if (zB > ezA) {
					devant = -1;
				} else if ((zA >= zB && zA <= ezB)) {
					if ((xA < xB && yA <= yB) || (xA <= xB && yA < yB))
						devant = 1;
				}

				if (devant == 1)
					id = i;
			}
		}
		contenu.remove(cle);
		contenu.add(id + 1, cle);
	}

	public String toString() {
		String description = "";
		for (int i = 0; i < getContenu().size(); i++) {
			description += getContenu().get(i).toString() + " ";
		}
		return description;
	}

	public void trier() {
		if(/*lastSort + sortDelay < System.currentTimeMillis() &&  this.isDrawing()){
			long tempsPrec  = System.currentTimeMillis();
			lastSort = System.currentTimeMillis();
			sortDelay = 10000;
			for(int i = 0; i < contenu.size(); i++){
				sortByOriginDistance(contenu.get(i));
			}
			trierXYZ();
			drawing = false;
			hasXYtoSort = false;
			
			//Si on veut afficher les informations de vitesse
			if(GameMain.options.isGameSpeedPrint())
			System.out.println("Temps de tri :" + (System.currentTimeMillis() -
					tempsPrec) + "ms");
		}
	}

	public synchronized void trierX() {
		for (int j = 1; j < getContenu().size(); j++) {
			ObjetMap cle = getContenu().get(j);
			int i = j - 1;
			while (i > -1
					&& cle.getPosX() >= getContenu().get(i).getPosX()
					+ getContenu().get(i).getSizeX() - 1) {
				getContenu().set(i + 1, getContenu().get(i));
				i--;
			}
			getContenu().set(i + 1, cle);
		}
	}

	public synchronized void trierXYA() {
		for (int j = 1; j < getContenu().size(); j++) {
			ObjetMap cle = getContenu().get(j);
			int i = j - 1;
			while (i > -1
					&& Math.sqrt(Math.pow(getContenu().get(i).getPosY(), 2)
							+ Math.pow(getContenu().get(i).getPosX(), 2))
							- getContenu().get(i).getPosZ() < Math
							.sqrt(Math.pow(cle.getPosY(), 2)
									+ Math.pow(cle.getPosX(), 2))
									- cle.getPosZ() - cle.getSizeZ()) {
				getContenu().set(i + 1, getContenu().get(i));
				i--;
			}
			getContenu().set(i + 1, cle);
		}
	}

	public synchronized void trierXYZ() {
		for (int j = 1; j < contenu.size(); j++) {
			ObjetMap cle = contenu.get(j);
			if (cle.isDrawing()) {
				int i = j - 1;
				int endPosZ = cle.getPosZ() + cle.getSizeZ();
				int posY = cle.getPosY();
				int posX = cle.getPosX();
				while (i > -1
						&& (endPosZ <= contenu.get(i).getPosZ()
						|| (posY >= contenu.get(i).getPosY()
						+ contenu.get(i).getSizeY()) || (posX >= contenu
						.get(i).getPosX() + contenu.get(i).getSizeX()))) {
					i--;
				}
				contenu.remove(cle);
				contenu.add(i + 1, cle);
				cle.setDrawing(false);
			}
		}
	}

	public synchronized void trierY() {
		for (int j = 1; j < getContenu().size(); j++) {
			ObjetMap cle = getContenu().get(j);
			int i = j - 1;

			while (i > -1
					&& cle.getPosY() >= getContenu().get(i).getPosY()
					+ getContenu().get(i).getSizeY() - 1) {
				getContenu().set(i + 1, getContenu().get(i));
				i--;
			}
			getContenu().set(i + 1, cle);
		}
	}

	public synchronized void trierZ() {
		for (int j = 1; j < getContenu().size(); j++) {
			ObjetMap cle = getContenu().get(j);
			int i = j - 1;
			while (i > -1
					&& getContenu().get(i).getPosZ()
					+ getContenu().get(i).getSizeZ() > cle.getPosZ()) {
				getContenu().set(i + 1, getContenu().get(i));
				i--;
			}
			getContenu().set(i + 1, cle);
		}
	}
*/
	public synchronized void update(Jeu jeu) {
		for (int i = 0; i < updatable.size(); i++) {
			updatable.get(i).update(jeu);
		}
		System.out.println(updatable.size());
	}

	public Entity searchEntity() {
		for (int i = 0; i < updatable.size(); i++) {
			if(updatable.get(i) instanceof Entity)
				return (Entity) updatable.get(i);
		}
		return null;
	}

	public int getModeActuel() {
		return modeActuel;
	}

	public void setModeActuel(int modeActuel) {
		this.modeActuel = modeActuel;
	}

	public ArrayList<Teleporter> getTeleporters() {
		if(teleporters == null){
			teleporters = new ArrayList<Teleporter>();
		}
		return teleporters;
	}

	public void setTeleporters(ArrayList<Teleporter> teleporters) {
		this.teleporters = teleporters;
	}

	
	public Teleporter getTeleporter(String id) {
		for(Teleporter tp : teleporters){
			tp.updateAlone(null, null);
			System.out.println(tp.getTeleporterId());
			if(tp.getTeleporterId().equals(id)){
				return tp;
			}
		}
		return null;
	}

	public String getMegaCompressScript() {
		return megaCompressScript;
	}

	public void setMegaCompressScript(String megaCompressScript) {
		this.megaCompressScript = megaCompressScript;
	}


}
