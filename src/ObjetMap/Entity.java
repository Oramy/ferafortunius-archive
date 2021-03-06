package ObjetMap;

import gui.jeu.Jeu;
import gui.jeu.PanneauJeuAmeliore;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Items.Equipment;
import Items.EquipmentItem;
import Items.HumanEquipment;
import Items.Inventory;
import Items.Item;
import Level.Camera;
import Level.ChunkMap;
import bonus.Bonus;
import bonus.Life;
import bonus.buffs.Buff;

public abstract class Entity extends ObjetMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3918101920092578430L;
	protected int hp = 0;
	protected int maxHp = 0;
	protected int mp = 0, maxMp = 0;
	protected int hpgain, mpgain;
	protected int speed;
	protected long lastHpgain;
	protected long lastMpgain;
	protected String action;
	protected ArrayList<Bonus> bonus;
	
	protected Inventory inventaire;
	protected Equipment equipment;
	public Entity(int chunkX, int chunkY, int chunkZ, int posX, int posY,
			int posZ) {
		super(chunkX, chunkY, chunkZ, posX, posY, posZ);
		bonus = new ArrayList<Bonus>();
		update = true;
		action = "";
		inventaire = new Inventory(this);
		equipment = new HumanEquipment(this);
		bonus = new ArrayList<Bonus>();
		equipment = null;
		
	}
	public ObjetMap clone(){
		Entity o = (Entity) super.clone();
		o.inventaire = (Inventory) inventaire.clone();
		if(equipment != null)
			o.equipment = (Equipment) equipment.clone();
		else
			o.equipment = new HumanEquipment(this);
		o.inventaire.setOwner(o);
		o.equipment.setOwner(o);
		o.bonus = new ArrayList<Bonus>();
		for(Bonus b : bonus)
			o.bonus.add(b.clone());
		
		return o;
		
	}
	public boolean isUpdate() {
		return true;
	}
	public void walkAnim(Direction direction){
		this.stopLittleAnimation();
		action = "walk" + direction.name();
		setDirection(direction);
		if(getAnimation(action) != null){
			if(!getAnimation(action).isStarted() || (getAnimation(action).isFinish() && getAnimation(action).isStarted())){
				startAnimations();
			}
		}
	}
	public void walk(Jeu jeu){
		/*if(direction == Direction.NW)
			jeu.getCarte().deplacement(this, 0, speed, 0, jeu);
		if(direction == Direction.SW)
			jeu.getCarte().deplacement(this, -speed, 0, 0, jeu);
		if(direction == Direction.NE)
			jeu.getCarte().deplacement(this, speed, 0, 0, jeu);
		if(direction == Direction.SE)
			jeu.getCarte().deplacement(this, 0, -speed, 0, jeu);
		if(direction == Direction.W)
			jeu.getCarte().deplacement(this, -speed, speed, 0, jeu);
		if(direction == Direction.N)
			jeu.getCarte().deplacement(this, speed, speed, 0, jeu);
		if(direction == Direction.E)
			jeu.getCarte().deplacement(this, speed, -speed, 0, jeu);
		if(direction == Direction.S)
			jeu.getCarte().deplacement(this, -speed, -speed, 0, jeu);*/
	}
	public void move(Jeu jeu, ChunkMap carte, int x, int y, int z){
		carte.deplacement(this, x, y, z, jeu);
	}
	public void startAnimations() {
		for(Animation a : animations){
			if(a.getNom().equals(action) && (!a.isStarted() || a.isFinish()))
				a.repeat();
			else if((a.isStarted() && !a.isFinish()) && !a.getNom().equals(action))
			{
				a.finish(this);
			}
		}
	}
	public void paintComponent(PanneauJeuAmeliore pan, Graphics g, Image img, int posX, int posY, ObjetImage c, Camera actualCam){
		super.paintComponent(pan, g, img, posX, posY, c, actualCam);
		
		
		//Liste des ?quipements
		for(EquipmentItem equip : this.equipment.getContents()){
			//Liste des listes d'?quipements
			if(equip.getImages() != null){
				for(ObjetImageList list : equip.getImages()){
					//Si c'est la m?me liste
					if(list.getAlias().equals(this.getCurrentImageList())){
						//On parcourt la liste
						for(ObjetImage objetImg : list.getList()){
							//On parcourt notre liste
							ObjetImage normal = c;
								//Si c'est le m?me alias
								if(objetImg.getAlias().equals(normal.getAlias())){
									super.paintComponent(pan, g, PanneauJeuAmeliore.loadImage(objetImg, (ObjetMap)this).getImg(), posX, posY, c, actualCam);
								}
							
						}
					}
				}
			}
		}
	}
	public void paintBonus(PanneauJeuAmeliore pan, Graphics g, Camera actualCam){
		for(int i = 0; i < bonus.size(); i++){
			bonus.get(i).paintComponent(g, actualCam);
		}
		//Affichage de l'?quipement. 
		
		/*//Liste des ?quipements
		for(EquipmentItem equip : this.equipment.getContents()){
			//Liste des listes d'?quipements
			for(ObjetImageList list : equip.getImages()){
				//Si c'est la m?me liste
				System.out.println(this.getCurrentImageList());
				if(list.getAlias().equals(this.getCurrentImageList())){
					//On parcourt la liste
					for(ObjetImage objetImg : list.getList()){
						//On parcourt notre liste
						for(ObjetImage normal : this.getImage()){
							//Si c'est le m?me alias
							if(objetImg.getAlias().equals(normal.getAlias())){
								//On dessine l'objet
								pan.drawObjectImage(g, (ObjetMap)this, normal, PanneauJeuAmeliore.loadImage(objetImg, (ObjetMap)this).getImg(), false);
							}
						}
					}
				}
			}
		}*/
	}
	public void die(Jeu jeu){
		jeu.getCarte().getChunk(this).remove(this);
		for(Item item : inventaire.getContents()){
			jeu.getCarte().getChunk(this).addContenu(new ItemOnMap(chunkX, chunkY, chunkZ, posX + (int)(Math.random() * sizeX), posY + (int)(Math.random() * sizeY), posZ + (int)(Math.random() * 50), item));
		}
		inventaire.getContents().clear();
		inventaire.setWeight(0);
	}
	public void update(Jeu jeu){
		super.update(jeu);
		
		if(hp <= 0){
			die(jeu);
		}
		long hpdivide;
		if(hpgain == 0){
			hpdivide = 1;
		}
		else
			hpdivide = hpgain;
		if(lastHpgain + (1000 / hpdivide) < System.currentTimeMillis()){
			if(hpgain > 0 && hp != maxHp)
				this.increaseHp(1);
			lastHpgain = System.currentTimeMillis();
		}
		long mpdivide;
		if(mpgain == 0){
			mpdivide = 1;
		}
		else if(mpgain < 0)
		{
			mpdivide = -mpgain;
		}
		else
			mpdivide = mpgain;
		if(lastMpgain + (1000 / mpdivide) < System.currentTimeMillis()){
			this.increaseMp(1);
			lastMpgain = System.currentTimeMillis();
		}
		for(int i = 0; i < bonus.size(); i++){
			bonus.get(i).update(jeu);
		}
	}
	@Override
	public String getCompressScript(){
		String compressScript = "";
		for(int i = 0 ; i < speed; i++)
		{
			compressScript += super.getCompressScript();
		}
		return compressScript;
	}
	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}
	/**
	 * @param hp the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
			if(this.hp > maxHp){
				this.hp = maxHp;
			}
			else if(this.hp < 0){
				this.hp = 0;
			}
	}
	public void increaseHp(int hp){
		this.setHp(this.hp + hp);
	}
	public void decreaseHp(int hp){
		this.setHp(this.hp - hp);
	}
	/**
	 * @return the maxHp
	 */
	public int getMaxHp() {
		return maxHp;
	}
	/**
	 * @param maxHp the maxHp to set
	 */
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	/**
	 * @return the mp
	 */
	public int getMp() {
		return mp;
	}
	/**
	 * @param hp the hp to set
	 */
	public void setMp(int mp) {
		this.mp = mp;
			if(this.mp > maxMp){
				this.mp = maxMp;
			}
			else if(this.mp < 0){
				this.mp = 0;
			}
	}
	public void increaseMp(int mp){
		this.setMp(this.mp + mp);
	}
	public void decreaseMp(int mp){
		this.setMp(this.mp - mp);
	}
	/**
	 * @param mp the mp to set
	 */

	/**
	 * @return the maxMp
	 */
	public int getMaxMp() {
		return maxMp;
	}
	/**
	 * @param maxMp the maxMp to set
	 */
	public void setMaxMp(int maxMp) {
		this.maxMp = maxMp;
	}
	/**
	 * @return the hpgain
	 */
	public int getHpgain() {
		return hpgain;
	}
	/**
	 * @param hpgain the hpgain to set
	 */
	public void setHpgain(int hpgain) {
		this.hpgain = hpgain;
	}
	/**
	 * @return the mpgain
	 */
	public int getMpgain() {
		return mpgain;
	}
	/**
	 * @param mpgain the mpgain to set
	 */
	public void setMpgain(int mpgain) {
		this.mpgain = mpgain;
	}
	/**
	 * @return the bonus
	 */
	public ArrayList<Bonus> getBonus() {
		return bonus;
	}
	public ArrayList<Buff> getBuffs(){
		ArrayList<Buff> buffs = new ArrayList<Buff>();
		for(Bonus aBonus : bonus){
			if(aBonus instanceof Buff)
				buffs.add((Buff)aBonus);
		}
		return buffs;
	}
	public void addBonusLife(int amount){
		addBonus(new Life(amount, this));
	}
	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(ArrayList<Bonus> bonus) {
		this.bonus = bonus;
	}
	/**
	 * @return the inventaire
	 */
	public Inventory getInventaire() {
		if(inventaire == null)
			inventaire = new Inventory(this, 100);
		return inventaire;
	}
	/**
	 * @param inventaire the inventaire to set
	 */
	public void setInventaire(Inventory inventaire) {
		this.inventaire = inventaire;
	}
	/**
	 * @return the equipment
	 */
	public Equipment getEquipment() {
		return equipment;
	}
	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public void addBonus(ArrayList<Bonus> bonus){
		ArrayList<Bonus> clone = new ArrayList<Bonus>();
		for(Bonus oneBonus : bonus){
			Bonus aClone = oneBonus.clone();
			clone.add(aClone);
			aClone.effect();
		}
		this.bonus.addAll(clone);
	}
	public void createDamage(Jeu jeu, int x, int y, int z, int attackBonus){
		ObjetMap damage = new BasicObjetMap(chunkX, chunkY, chunkZ, this.posX + x, this.posY + y, this.posZ + z);
		damage.setSizeX(30);
		damage.setSizeY(30);
		damage.setSizeZ(30);
		damage.addCollisionBlock(new CollisionBlock(0,0,0, 30,30,30));
		int damageNumber = attackBonus;
		damage.collideScript = "cible.addBonusLife(-" + damageNumber + ");" + "carte.getChunk(himself).remove(himself);";
		jeu.getCarte().getChunk(this).accepted(damage, jeu);
		
	}
	public void addBonus(Bonus oneBonus){
		Bonus aClone = oneBonus.clone();
		this.bonus.add(aClone);
		aClone.effect();
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	

}
