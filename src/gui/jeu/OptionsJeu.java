package gui.jeu;

import gui.TextDisplayMode;

import java.io.Serializable;
import java.util.Locale;


public class OptionsJeu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int screenWidth, screenHeight;
	private boolean windowed;
	private float musicVolume, soundVolume;
	private TextDisplayMode textDisplayMode;
	private Locale language;
	private boolean vsync;
	private boolean alwaysRend;
	
	private boolean gameSpeedPrint;
	//Keys
	private int up;
	private int down;
	private int left;
	private int right;
	private int inventory;
	private int equipment;
	private int shop;
	private int options;
	private int exit; 
	private int action;
	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}
	/**
	 * @param screenWidth the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}
	/**
	 * @param screenHeight the screenHeight to set
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	/**
	 * @return the windowed
	 */
	public boolean isWindowed() {
		return windowed;
	}
	/**
	 * @param windowed the windowed to set
	 */
	public void setWindowed(boolean windowed) {
		this.windowed = windowed;
	}
	/**
	 * @return the musicVolume
	 */
	public float getMusicVolume() {
		return musicVolume;
	}
	/**
	 * @param musicVolume the musicVolume to set
	 */
	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}
	/**
	 * @return the soundVolume
	 */
	public float getSoundVolume() {
		return soundVolume;
	}
	/**
	 * @param soundVolume the soundVolume to set
	 */
	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}
	/**
	 * @return the vsync
	 */
	public boolean isVsync() {
		return vsync;
	}
	/**
	 * @param vsync the vsync to set
	 */
	public void setVsync(boolean vsync) {
		this.vsync = vsync;
	}
	/**
	 * @return the textDisplayMode
	 */
	public TextDisplayMode getTextDisplayMode() {
		return textDisplayMode;
	}
	/**
	 * @param textDisplayMode the textDisplayMode to set
	 */
	public void setTextDisplayMode(TextDisplayMode textDisplayMode) {
		this.textDisplayMode = textDisplayMode;
	}
	/**
	 * @return the language
	 */
	public Locale getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(Locale language) {
		this.language = language;
	}
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public int getEquipment() {
		return equipment;
	}
	public void setEquipment(int equipment) {
		this.equipment = equipment;
	}
	public int getShop() {
		return shop;
	}
	public void setShop(int shop) {
		this.shop = shop;
	}
	public int getExit() {
		return exit;
	}
	public void setExit(int exit) {
		this.exit = exit;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getOptions() {
		return options;
	}
	public void setOptions(int options) {
		this.options = options;
	}
	public boolean isAlwaysRend() {
		return alwaysRend;
	}
	public void setAlwaysRend(boolean alwaysRend) {
		this.alwaysRend = alwaysRend;
	}
	public boolean isGameSpeedPrint() {
		return gameSpeedPrint;
	}
	public void setGameSpeedPrint(boolean gameSpeedPrint) {
		this.gameSpeedPrint = gameSpeedPrint;
	}
}
