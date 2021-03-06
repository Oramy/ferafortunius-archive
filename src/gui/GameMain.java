package gui;

import gui.editeurs.Editeur;
import gui.jeu.Jeu;
import gui.jeu.LoadingScreen;
import gui.jeu.OptionsJeu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

import Level.MapLoader;
import Level.OptionsJeuLoader;


public class GameMain extends BasicGame {
	private Input input;
	private Jeu jeu;
	private Editeur editeur;
	private Menu menu;
	private LoadingScreen loadingScreen;
	private ModeJeu mode;
	private AppGameContainer app;
	public static OptionsJeu options;
	public static GameContainer gc;
	private TextDisplayMode gameTextDisplayMode;
	
	
	public static boolean imprEcran;
	
	public static float delta;
	
	public GameMain() {
		super(OptionsJeuLoader.RESOURCE_BUNDLE.getString("name") + " : " + OptionsJeuLoader.RESOURCE_BUNDLE.getString("version"));
		
	}
	public void applyOptions(GameContainer gc) {
		setOptions(OptionsJeuLoader.loadOptions());
		if(!options.isWindowed()){
			setGameTextDisplayMode(options.getTextDisplayMode());
			//getApp().setMusicVolume(getOptions().getMusicVolume());
			//getApp().setSoundVolume(getOptions().getSoundVolume());
			getApp().setVSync(getOptions().isVsync());
			getApp().setAlwaysRender(getOptions().isAlwaysRend());
			try {
				getApp().setDisplayMode(gc.getScreenWidth(), gc.getScreenHeight(), true);
			} catch (SlickException e) {
				
				e.printStackTrace();
			}	
		}
		else
		{
			setGameTextDisplayMode(options.getTextDisplayMode());
			//getApp().setMusicVolume(getOptions().getMusicVolume());
			//getApp().setSoundVolume(getOptions().getSoundVolume());
			getApp().setVSync(getOptions().isVsync());
			getApp().setAlwaysRender(getOptions().isAlwaysRend());
			try {
				getApp().setDisplayMode(options.getScreenWidth(), options.getScreenHeight(), false);
			} catch (SlickException e) {
				
				e.printStackTrace();
			}
		}
	}
	public void initFenetre(GameContainer gc) {
		applyOptions(gc);
		menu = new Menu(this, gc);
		mode = ModeJeu.Menu;
		imprEcran = false;
		menu.init(gc);
		input = gc.getInput();
		input.enableKeyRepeat();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.gc = container;
		g.clear();
		g.setAntiAlias(true);
		g.setFont(FontRessources.getFonts().text);
			
			if(getMode() == ModeJeu.Menu)
				menu.paintComponent(container, g);
			if(getMode() == ModeJeu.Jeu)
				getJeu().paintComponent(container, g);
			if(getMode() == ModeJeu.Editeur){
				editeur.paintComponent(container, g);
			}
			if(getMode() == ModeJeu.Loading){
				loadingScreen.paintComponent(container, g);
			}
			if(imprEcran){
				imprEcran = false;
				Image target;
				try {
					target = new Image(getApp().getWidth(), getApp().getHeight());
					Image logo = new Image("Images/GUI/logo.png");
					logo.setAlpha(0.5f);
					g.scale(0.25f, 0.25f);
					g.drawImage(logo, 0,0);
					g.scale(4f, 4f);
					g.copyArea(target, 0,0);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
					ImageOut.write(target.getFlippedCopy(false, false), "ScreenShots/ScreenShot-" + dateFormat.format(new Date()) + ".png", false);
					target.destroy();
				} catch (SlickException e) {
					
					e.printStackTrace();
				}
			}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		initFenetre(gc);
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		this.gc = gc;
		ControllersManager.update(gc);
		delta = arg1 / (1000f / 60f);
		if(getMode() == ModeJeu.Menu)
			menu.update(app, arg1);
		if(getMode() == ModeJeu.Jeu)
			getJeu().update(app, arg1);
		if(getMode() == ModeJeu.Editeur)
			editeur.update(app, arg1);
		if(getMode() == ModeJeu.Loading)
			loadingScreen.update(app, arg1);
		if(gc.getInput().isKeyPressed(Input.KEY_F11)){
			imprEcran = true;
		}
	}

	/**
	 * @return the app
	 */
	public AppGameContainer getApp() {
		return app;
	}

	/**
	 * @param app the app to set
	 */
	public void setApp(AppGameContainer app) {
		this.app = app;
	}
	/**
	 * @return the mode
	 */
	public ModeJeu getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void initMode(ModeJeu mode, GameContainer gc) {
		this.mode = mode;
		if(getMode() == ModeJeu.Menu){
			menu = new Menu(this, gc);
			menu.init(app);
			jeu = null;
			editeur = null;
			loadingScreen = null;
			this.mode = mode;
		}
		if(getMode() == ModeJeu.Loading){
			jeu = null;
			loadingScreen = new LoadingScreen(this, 0,0, this.getApp().getWidth(), this.getApp().getHeight(), null);
			loadingScreen.init(app);
			menu = null;
			editeur = null;
			this.mode = mode;
		}
		if(getMode() == ModeJeu.Jeu){
			jeu = new Jeu(this, gc);
			jeu.init(app, MapLoader.loadMap("data/Maps/SnapshotTestMap3.dat"));
			menu = null;
			editeur = null;
			loadingScreen = null;
			this.mode = mode;
		}
		if(getMode() == ModeJeu.Editeur){
			editeur = new Editeur(this, gc);
			editeur.init(app);
			jeu = null; 
			menu = null;
			loadingScreen = null;
			this.mode = mode;
		}
		
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(ModeJeu mode) {
		this.mode = mode;
	}
	/**
	 * @return the options
	 */
	public static OptionsJeu getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public static void setOptions(OptionsJeu options2) {
		options = options2;
	}
	/**
	 * @return the gameTextDisplayMode
	 */
	public TextDisplayMode getGameTextDisplayMode() {
		return gameTextDisplayMode;
	}
	/**
	 * @param gameTextDisplayMode the gameTextDisplayMode to set
	 */
	public void setGameTextDisplayMode(TextDisplayMode gameTextDisplayMode) {
		this.gameTextDisplayMode = gameTextDisplayMode;
	}
	/**
	 * @return the jeu
	 */
	public Jeu getJeu() {
		return jeu;
	}
	/**
	 * @param jeu the jeu to set
	 */
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
}