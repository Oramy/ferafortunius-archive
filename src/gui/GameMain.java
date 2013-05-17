package gui;

import gui.editeurs.Editeur;
import gui.jeu.Jeu;
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
	private ModeJeu mode;
	private AppGameContainer app;
	public static OptionsJeu options;
	private TextDisplayMode gameTextDisplayMode;
	private boolean imprecran;
	public GameMain() {
		super(OptionsJeuLoader.RESOURCE_BUNDLE.getString("name"));
	}
	public void applyOptions(GameContainer gc) {
		setOptions(OptionsJeuLoader.loadOptions());
		if(!options.isWindowed()){
			setGameTextDisplayMode(options.getTextDisplayMode());
			getApp().setMusicVolume(getOptions().getMusicVolume());
			getApp().setSoundVolume(getOptions().getSoundVolume());
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
			getApp().setMusicVolume(getOptions().getMusicVolume());
			getApp().setSoundVolume(getOptions().getSoundVolume());
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
		setJeu(new Jeu(this, gc));
		editeur = new Editeur(this, gc);
		menu = new Menu(this, gc);
		mode = ModeJeu.Menu;
		imprecran = false;
		menu.init(gc);
		input = gc.getInput();
		input.enableKeyRepeat();
		
		
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		
		g.setFont(FontRessources.getFonts().text);
			
			if(getMode() == ModeJeu.Menu)
				menu.paintComponent(container, g);
			if(getMode() == ModeJeu.Jeu)
				getJeu().paintComponent(container, g);
			if(getMode() == ModeJeu.Editeur){
				editeur.paintComponent(container, g);
			}
			if(imprecran){
				imprecran = false;
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
		if(getMode() == ModeJeu.Menu)
			menu.update(app, arg1);
		if(getMode() == ModeJeu.Jeu)
			getJeu().update(app, arg1);
		if(getMode() == ModeJeu.Editeur)
			editeur.update(app, arg1);
		if(gc.getInput().isKeyPressed(Input.KEY_F11)){
			imprecran = true;
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
	public void setMode(ModeJeu mode, GameContainer gc) {
		this.mode = mode;
		if(getMode() == ModeJeu.Menu){
			menu.init(app);
			this.mode = mode;
		}
		if(getMode() == ModeJeu.Jeu){
			getJeu().init(app, MapLoader.loadMap("data/Maps/snapshot1.dat"));
			this.mode = mode;
		}
		if(getMode() == ModeJeu.Editeur){
			editeur.init(app);
			this.mode = mode;
		}
		
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