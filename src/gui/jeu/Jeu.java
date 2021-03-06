package gui.jeu;

import gui.Container;
import gui.ControllerManager;
import gui.ControllersManager;
import gui.DialogsRessources;
import gui.FontRessources;
import gui.GameMain;
import gui.ModeJeu;
import gui.Text;
import gui.TextDisplayMode;
import gui.jeu.inventory.InventaireFrame;
import gui.jeu.multi.Client;
import gui.jeu.multi.Serveur;
import gui.jeu.utils.TimeController;

import java.util.ArrayList;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

import maths.Trigonometry;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import Items.Item;
import Level.Camera;
import Level.ChunkMap;
import Level.ItemLoader;
import Level.MapLoader;
import Level.ObjetMapLoader;
import ObjetMap.Chrono;
import ObjetMap.Direction;
import ObjetMap.Entity;
import ObjetMap.ObjetMap;
import bonus.ItemBonus;

public class Jeu extends Container implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5986543142095506791L;

	public static long lastUpdate, timeRest;
	
	
	
	//Affichage
	private PanneauJeuAmeliore panneauDuJeu;
	
	//Camera par d�faut
	private Camera cameraPerso;
	
	//Carte
	private ChunkMap carte;
	
	//Joueur
	private Entity player;
	private int idPersoJoueur;
	
	//Application
	private GameMain gm;
	
	//Vitesse
	private int vitesseDep = 1;
	private long vitessePerso;
	
	//R�gulateur de vitesse
	private long tempsDepPrec;
	
	//Fen�tre des options
	private OptionsJeuFrame optionsFen;

	//Fen�tre de l'inventaire
	protected InventaireFrame inventaireFrame;
	
	//Barre de Buff
	protected BuffBar buffBar;
	
	//Zone de texte
	protected ArrayList<Text> dialogList;
	private DialogBar dialogBar;
	
	private Color transitionColor;
	private boolean clickonsurlign;

	//Multiplayers
	private boolean server;
	private boolean client;
	private Client clientManager;
	private Serveur serveurManager;

	//Alpha du titre
	private int alphaTitreMap;

	// Test pour les touches internationales.
	private boolean keyI;

	private Music ambianceMusic;

	private MenuJeuContainer menuJeu;
	
	//Time controller
	private TimeController timeController;
	
	private transient ObjetMap dialogFollow;
	public Jeu(GameMain gameMain, GameContainer gc) {
		super(0, 0, gc.getWidth(), gc.getHeight(), null);
		
		setGm(gameMain);
		ControllersManager.getFirstController().setControllerContainer(this);
	}

	public void addDialog(String t) {
		Text text = null;
		if (getDialogBar() != null)
			text = new Text(t, true, getDialogBar());
		else
			text = new Text(t, true, this);
		addDialog(text);
	}
	public void addDialog(Text t) {
		t.setDisplay(getGameTextDisplayMode());
		t.setDisplayInt(0);
		if (dialogList == null)
			dialogList = new ArrayList<Text>();
		dialogList.add(t);
	}
	public void addFileDialog(String file) {
		addDialog(DialogsRessources.loadText(file));
	}

	public void addItem(Item t) {
		player.getInventaire().addContent(t);
	}
	

	public void addItem(String s) {
		Item t = ItemLoader.loadObject("data/Items/" + s); //$NON-NLS-1$
		t.setOwner(player);
		player.getInventaire().addContent(t);
	}

	public void addItemBonus(Item t) {
		player.addBonus(new ItemBonus(t, 1, player));
	}

	public void addItemBonus(String s) {
		Item t = ItemLoader.loadObject("data/Items/" + s); //$NON-NLS-1$
		t.setOwner(player);
		player.addBonus(new ItemBonus(t, 1, player));
	}

	public void applyOptions(OptionsJeu options) {
	}
	public void cleanGUI() {
		while (components.size() != 0)
			components.remove(0);
	}
	public void closeInventory() {
		if (this.components.contains(inventaireFrame))
			this.components.remove(inventaireFrame);
	}
	@Override
	public void draw(Graphics g) {
		g.translate(this.getX(), this.getY());
		if(background != null)
		drawBackground(background.getImg());
		if(getComponents().size() != 0)
		{
			if((GameMain.options.isScreenshotGUI() && GameMain.imprEcran) || !GameMain.imprEcran){
				for(int i = 0; i < getComponents().size(); i++){
					
					getComponents().get(i).drawBegin(g);
					getComponents().get(i).draw(g);
					getComponents().get(i).drawEnd(g);
					
				}
			}
			else{
				panneauDuJeu.drawBegin(g);
				panneauDuJeu.draw(g);
				panneauDuJeu.drawEnd(g);
			}
				
		}
		g.translate(-this.getX(), -this.getY());
	}
	/**
	 * @return the cameraPerso
	 */
	public Camera getCameraPerso() {
		return cameraPerso;
	}

	/**
	 * @return the cart
	 */
	public ChunkMap getCarte() {
		return carte;
	}

	public String getDialog(String file) {
		return DialogsRessources.loadText(file);
	}

	/**
	 * @return the dialogBar
	 */
	public DialogBar getDialogBar() {
		return dialogBar;
	}

	public ObjetMap getDialogFollow() {
		return dialogFollow;
	}

	/**
	 * @return the dialogList
	 */
	public ArrayList<Text> getDialogList() {
		return dialogList;
	}

	/**
	 * @return the gameTextDisplayMode
	 */
	public TextDisplayMode getGameTextDisplayMode() {
		return gm.getGameTextDisplayMode();
	}

	/**
	 * @return the gm
	 */
	public GameMain getGm() {
		return gm;
	}

	/**
	 * @return the idPersoJoueur
	 */
	public int getIdPersoJoueur() {
		return idPersoJoueur;
	}

	public MenuJeuContainer getMenuJeu() {
		return menuJeu;
	}

	private Direction getMiddleToPointDirection(float x, float y){
		return getMiddleToPointDirection(new Vector2f(x,y));
	}

	/**
	 * Return a 2Diso direction with the mouse coordinates.
	 * It uses the middle of the screen as a reference point.
	 * @param point A vector with a point position.
	 * @return an iso direction.
	 */
	private Direction getMiddleToPointDirection(Vector2f point){
		float angle = Trigonometry.getXAxisAngle(new Vector2f(this.getWidth() / 2, this.getHeight() / 2), point);
			
		int dir = ((int) ((angle + 22.5f) / 45) + 2) % 8;
		if(dir != 0  && dir != 4){
			dir = (8 - dir) % 8;
		}
		return Direction.values()[dir];
	}

	/**
	 * @return the optionsFen
	 */
	public OptionsJeuFrame getOptionsFen() {
		return optionsFen;
	}

	/**
	 * @return the panneauDuJeu
	 */
	public PanneauJeuAmeliore getPanneauDuJeu() {
		return panneauDuJeu;
	}

	/**
	 * @return the personnage
	 */
	public Entity getPlayer() {
		return player;
	}

	public TimeController getTimeController() {
		return timeController;
	}
	public void firstInit(GameContainer gc, ChunkMap c){
		//Initialisation des variables
		alphaTitreMap = 0;
		
		//TimeController
		timeController = new TimeController(1);
		//Afficher les FPS
		gc.setShowFPS(true);
		
		//R�gulateur d'update
		lastUpdate = (int) System.currentTimeMillis();
		timeRest = 0;
		
		keyI = false;
		
		setDialogList(new ArrayList<Text>());
		
		//Initialisation de la carte
		this.carte = c;
		alphaTitreMap = 300;
		
		setPlayer(carte.getFirstEntity());
		if (player == null) {
			player = (Entity) ObjetMapLoader.loadObject("data/ObjetMap/Entities/entitylanguide.obj");
			player.setPosition(0, 0, 0, 0, 0, 0);
		}
		player.setSpeed(1);
		setIdPersoJoueur(-1);
		
		//Initialisation de la cam�ra
		cameraPerso = new Camera(0, 0, 0.5f, getCarte());
		cameraPerso.teleportToObject(player);
		cameraPerso.setFollowHim(player);
		
		//Initialisation du joueur -- ZONE DE TEST
		
		
		
		player.getInventaire().setMaxWeight(3000);
		//player.getEquipment().equip((EquipmentItem) player.getInventaire().getContents().get(0));
		
		/*player.addBonus(new MaxLife(player));
		player.addBonus(new BuffRegenLife(player));*/			
	}
	public void afterGUIInit(GameContainer gc){
		if(!ControllersManager.hasController(gc)){
			addFileDialog("minimap-1"); //$NON-NLS-1$
			addFileDialog("minimap-2"); //$NON-NLS-1$
			addFileDialog("minimap-3"); //$NON-NLS-1$
			addFileDialog("minimap-4"); //$NON-NLS-1$
		}
		else if(ControllersManager.isXBox(gc)){
			addFileDialog("minimap-1xbox"); //$NON-NLS-1$
			addFileDialog("minimap-2xbox"); //$NON-NLS-1$
			addFileDialog("minimap-3xbox"); //$NON-NLS-1$
			addFileDialog("minimap-4xbox"); //$NON-NLS-1$
		}
		
		//Lancement de l'affichage du titre.
		Thread t = new Thread(new Runnable() {
	
			public void run() {
	
				for (int i = 0; i < 255; i++) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
	
						e.printStackTrace();
					}
					transitionColor = new Color(0, 0, 0, 255 - i);
	
				}
				transitionColor.a = 0f;
			}
		});
		t.start();
		
				
	}
	public void init(GameContainer gc, ChunkMap c) {
		firstInit(gc, c);
		//Initialisation de la GUI
		cleanGUI();
		initGUI();
		afterGUIInit(gc);
	}

	public void initGUI() {
		//Panneau d'affichage
		panneauDuJeu = new PanneauJeuAmeliore(carte, 0, 0, getSizeX(),
				getSizeY(), this);

		//Zone de dialogue
		setDialogBar(new DialogBar(0, getSizeY() - 250, 500, 200, dialogList,
				panneauDuJeu));
		panneauDuJeu.addComponent(getDialogBar());
		panneauDuJeu.setActualCam(cameraPerso);
		this.addComponent(panneauDuJeu);
		
		//D�sactiv� pour la version snapshot.
		 this.addComponent(new FastMenuContainer(0, this.getHeight() - 66,
		 this.getWidth(), this.getHeight() / 2, this));
		
		 setMenuJeu(new MenuJeuContainer(0, 50, 170, 210, this));
		 //Menu du jeu
		this.addComponent(getMenuJeu());
		
		//Fen�tre de l'inventaire
		inventaireFrame = new InventaireFrame(this.getWidth() - 330,
				this.getHeight() - 400, 260, 330,
				Messages.getString("Jeu.1"), this, player); //$NON-NLS-1$
		
		//Barre de buff
		buffBar = new BuffBar(player, 300, 0, 1, 1, this);
		this.addComponent(buffBar);
		
		//Options
		initOptionsGUI();

	}

	public void initOptionsGUI() {
		setOptionsFen(new OptionsJeuFrame(
				Messages.getString("Jeu.0"), GameMain.getOptions(), this)); //$NON-NLS-1$
	}


	public void inverseInventory() {
		if (this.components.contains(inventaireFrame))
			closeInventory();
		else
			openInventory();
	}

	/**
	 * @return the cliente
	 */
	public boolean isClient() {
		return client;
	}

	/**
	 * @return the servere
	 */
	public boolean isServer() {
		return server;
	}

	public void moveDialogBarTo(ObjetMap o){
		dialogBar.setX(o.getXOnScreen(this) - dialogBar.getSizeX() / 2);
		dialogBar.setY(o.getYOnScreen(this) - 50);
	}

	public boolean noDialogs() {
		if (dialogList.size() == 0)
			return true;
		return false;
	}
	public void openInventory() {
		this.addComponent(inventaireFrame);
	}

	public void paintComponent(GameContainer container, Graphics g) {
		long temps = System.currentTimeMillis();
			
		this.drawBegin(g);
		
		this.draw(g);
		

		g.setColor(Color.black);
		String hourString = timeController.getStringHour() + ":" + timeController.getStringMinute();	
		g.drawString(hourString, this.getWidth() - g.getFont().getWidth("00:00:00") - 20,  20);
		
		g.setFont(FontRessources.getFonts().gametitles);
		g.setColor(new Color(0,0,0, (alphaTitreMap)));

		if(carte.getNom() != null)
		{
			 g.drawString(carte.getNom() + "", sizeX / 2 -
				 g.getFont().getWidth(carte.getNom()) / 2, 100);
		}
		g.setColor(transitionColor);
		g.fillRect(0, 0, getSizeX(), getSizeY());
		
		this.drawEnd(g);
		
		//Si on veut afficher les informations de vitesse
		if(GameMain.options.isGameSpeedPrint())
			System.out.println("Affichage : "
				+ (System.currentTimeMillis() - temps) + "ms");
		
		g.setColor(Color.white);				
	}

	public void playSound(String path, float pitch, float volume){
		try {
			Sound ambianceMusic = new Sound("Music/" + path);
			ambianceMusic.play(pitch, volume);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setAmbianceMusic(String path){
		setAmbianceMusic(path, 1.0f, 1.0f);
	}

	public void setAmbianceMusic(String path, float pitch){
		setAmbianceMusic(path, pitch, 1.0f);
	}

	public void setAmbianceMusic(String path, float pitch, float volume){
		if(ambianceMusic != null)
		ambianceMusic.stop();
		try {
			ambianceMusic = new Music("Music/" + path);
			ambianceMusic.loop(pitch, volume);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param cameraPerso
	 *            the cameraPerso to set
	 */
	public void setCameraPerso(Camera cameraPerso) {
		this.cameraPerso = cameraPerso;
	}

	/**
	 * @param cart
	 *            the cart to set
	 */
	public void setCarte(ChunkMap carte) {
		this.carte = carte;
		alphaTitreMap = 300;
		panneauDuJeu.setCarte(carte);
	}

	public void setCarte(String path) {
		ChunkMap carte = MapLoader.loadMap(path);
		this.carte = carte;
		alphaTitreMap = 300;
		panneauDuJeu.setCarte(carte);
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setClient(boolean cliente) {
		this.client = cliente;
	}

	/**
	 * @param dialogBar
	 *            the dialogBar to set
	 */
	public void setDialogBar(DialogBar dialogBar) {
		this.dialogBar = dialogBar;
	}

	public void setDialogFollow(ObjetMap dialogFollow) {
		this.dialogFollow = dialogFollow;
	}

	/**
	 * @param dialogList
	 *            the dialogList to set
	 */
	public void setDialogList(ArrayList<Text> dialogList) {
		this.dialogList = dialogList;
	}

	/**
	 * @param gameTextDisplayMode
	 *            the gameTextDisplayMode to set
	 */
	public void setGameTextDisplayMode(TextDisplayMode gameTextDisplayMode) {
		gm.setGameTextDisplayMode(gameTextDisplayMode);
	}

	/**
	 * @param gm
	 *            the gm to set
	 */
	public void setGm(GameMain gm) {
		this.gm = gm;
	}

	/**
	 * @param idPersoJoueur
	 *            the idPersoJoueur to set
	 */
	public void setIdPersoJoueur(int idPersoJoueur) {
		this.idPersoJoueur = idPersoJoueur;
	}
	public void setMenuJeu(MenuJeuContainer menuJeu) {
		this.menuJeu = menuJeu;
	}
	/**
	 * @param optionsFen
	 *            the optionsFen to set
	 */
	public void setOptionsFen(OptionsJeuFrame optionsFen) {
		this.optionsFen = optionsFen;
	}
	/**
	 * @param panneauDuJeu
	 *            the panneauDuJeu to set
	 */
	public void setPanneauDuJeu(PanneauJeuAmeliore panneauDuJeu) {
		this.panneauDuJeu = panneauDuJeu;
	}
	/**
	 * @param personnage
	 *            the personnage to set
	 */
	public void setPlayer(Entity player) {
		this.player = player;
	}
	
	/**
	 * @param servere
	 *            the servere to set
	 */
	public void setServer(boolean servere) {
		this.server = servere;
	}
	public void setTimeController(TimeController timeController) {
		this.timeController = timeController;
	}
	public void teleport(int chunkx, int chunky, int chunkz, int x,
			int y, int z) {
		player.setPosition(chunkx, chunky, chunkz, x, y, z);
		carte.getChunk(player).addContenu(player);
	}
	public void teleport(ObjetMap o) {
		player.setPosition(o);
		carte.getChunk(player).addContenu(player);
	}
	public void teleport(String map, int chunkx, int chunky, int chunkz, int x,
			int y, int z) {
		setCarte(MapLoader.loadMap("data/Maps/" + map + ".dat")); //$NON-NLS-1$ //$NON-NLS-2$
		player.setPosition(chunkx, chunky, chunkz, x, y, z);
		carte.getChunk(player).addContenu(player);
	}
	public void teleport(String map, ObjetMap o) {
		setCarte(MapLoader.loadMap("data/Maps/" + map + ".dat")); //$NON-NLS-1$ //$NON-NLS-2$
		player.setPosition(o);
		carte.getChunk(o).addContenu(player);
	}
	public void update(GameContainer gc, int arg1) {
		super.update(gc, this.getX(), this.getY());
		
		updateGame(gc, arg1);
	}

	@Override
	public void updateController(GameContainer gc) {
		updateKeys(gc);
	}

	public void updateGame(GameContainer gc, int arg1){
		long temps = System.currentTimeMillis();
		
		//Game Over code.
		if(!carte.getChunk(player).getContenu().contains(player) && transitionColor.a == 0f){
			Thread t = new Thread(new Runnable(){
				public void run(){
					for(int i = 0; i < 256; i++){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						transitionColor = new Color(0,0,0, i);
					}
				}
			});	
			t.start();
		}
		if(transitionColor != null){
			if(transitionColor.a >= 1f)
				gm.initMode(ModeJeu.Menu, gm.getApp());
		}
		
		timeController.updateTime(GameMain.delta);
		
		if(dialogFollow != null)
		moveDialogBarTo(dialogFollow);
		
		//Titre de la map
		if (alphaTitreMap > 0)
			alphaTitreMap--;
		
		// Gravit�
		if (player != null) {
			if (!player.isFly()) {
				getCarte().deplacement(player, 0, 0, -player.getSizeZ() / 100,
						this);
			}
		}
		
		//Multiplayer
		if (server && serveurManager == null) {
			serveurManager = new Serveur(this);
		}
		if (client && clientManager == null) {
			clientManager = new Client(this);
		}
		
		//Mise � jour de la souris
		updateMouse(gc);
		
		//Mise � jour de la Map
		if(!ControllersManager.hasController(gc))
		updateKeys(gc);
		carte.update(this);
		
		/*int coef = (int) (System.currentTimeMillis() - (lastUpdate) - 17);
		if(coef == 0){
			coef = -1;
			timeRest = 0;
		}else
			timeRest += 60 % coef;
		for(int i = 0; i < 60 / coef; i++){
			carte.update(this);
			updateKeys(gc);
		}*/
		
		//Si on veut afficher les informations de vitesse
		if(GameMain.options.isGameSpeedPrint())
			System.out.println("Update : " + (System.currentTimeMillis() - temps)
				+ "ms");
		lastUpdate = System.currentTimeMillis();
	}

	private void updateKeys(GameContainer gc) {
		if (tempsDepPrec + vitessePerso < System.currentTimeMillis()) {
			tempsDepPrec = System.currentTimeMillis();
			ControllerManager cm = ControllersManager.getFirstController();
			Input input = gc.getInput();
			
			//Course
			if (input.isKeyDown(Input.KEY_LSHIFT)
					|| input.isKeyDown(Input.KEY_RSHIFT) 
					|| cm.isButton4Pressed() 
					|| cm.isButton5Pressed()) {
				vitesseDep = 3;
				player.setSpeed(vitesseDep);
			} else if(vitesseDep > 1 && player.getAnimationLaunchedCount() == 0){
				vitesseDep = 2;
				player.setSpeed(vitesseDep);
			}
			
			
			// Touches directionnelles.
			if ((input.isKeyDown(Input.KEY_Q) || cm.isLeftPressed())
					&& (input.isKeyDown(Input.KEY_Z) || cm.isUpPressed())
					&& player.getAnimationLaunchedCount() == 0){
				player.walkAnim(Direction.NW);
				player.walk(this);
			
			} else if ((input.isKeyDown(Input.KEY_Q) || cm.isLeftPressed())
					&& (input.isKeyDown(Input.KEY_S) || cm.isDownPressed())
					&& player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.SW);
				player.walk(this);
			} else if ((input.isKeyDown(Input.KEY_D) || cm.isRightPressed())
					&& (input.isKeyDown(Input.KEY_Z) || cm.isUpPressed()) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.NE);
				player.walk(this);
			} else if ((input.isKeyDown(Input.KEY_D) || cm.isRightPressed())
					&& (input.isKeyDown(Input.KEY_S) || cm.isUpPressed()) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.SE);
				player.walk(this);
			} else if ((input.isKeyDown(Input.KEY_Q)
					|| cm.isLeftPressed()) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.W);
				player.walk(this);
			} else if ((input.isKeyDown(Input.KEY_Z)
					|| cm.isUpPressed()) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.N);
				player.walk(this);
			} else if ((input.isKeyDown(Input.KEY_D)
					|| cm.isRightPressed()) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.E);
				player.walk(this);
			} else if ((input.isKeyDown(Input.KEY_S)
					|| cm.isDownPressed()) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.S);
				player.walk(this);
			} 
			
			
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)  && player.getAnimationLaunchedCount() == 0) {
				player.setDirection(getMiddleToPointDirection(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()));
				player.launchAnimation("attack"+player.getDirection().name());
			}
			
			if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)  && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(getMiddleToPointDirection(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()));
				player.walk(this);
			}
			
			//Controller
			if ((cm.isButton1Pressed() || input.isKeyDown(Input.KEY_X)) && player.getAnimationLaunchedCount() == 0) {
				player.launchAnimation("attack"+player.getDirection().name());
			}
			if (cm.isStartReleased()) {
				cm.setControllerContainer(getMenuJeu());
			}
			if (cm.isButton2Released()) {
				dialogBar.nextDialog();
			}
			if(player.getAllAnimationLaunchedCount() == 0)
			{
				player.launchAnimation("stay" + player.getDirection().name());
			}
		}
		updateShortcuts(gc);
	}

	private void updateMouse(GameContainer gc){
		int mouse = Mouse.getDWheel();
		if (mouse > 0) {
			panneauDuJeu.getActualCam().setZoom(
					panneauDuJeu.getActualCam().getZoom() * 1.2f);
		} else if (mouse < 0) {
			panneauDuJeu.getActualCam().setZoom(
					panneauDuJeu.getActualCam().getZoom() / 1.2f);
		}
		
		if (!getGm().getApp().getInput()
				.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)
				&& clickonsurlign) {
			clickonsurlign = false;
		}
		if (getGm().getApp().getInput()
				.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)
				&& !clickonsurlign) {
				Bindings bindings = ScriptManager.moteurScript
						.getBindings(ScriptContext.ENGINE_SCOPE);
				bindings.clear();
				// Ajout de la variable entree dans le script
				bindings.put("himself", panneauDuJeu.getSurlignObject()); //$NON-NLS-1$
				bindings.put("newcam", new Camera(0, 0, 1f, getCarte())); //$NON-NLS-1$
				bindings.put("emptytext", new Text("", getDialogBar())); //$NON-NLS-1$ //$NON-NLS-2$
				bindings.put(
						"emptychrono", new Chrono(System.currentTimeMillis(), "Real time")); //$NON-NLS-1$ //$NON-NLS-2$
				bindings.put("currentTimeMillis", System.currentTimeMillis()); //$NON-NLS-1$
				bindings.put("jeu", this); //$NON-NLS-1$
				
				if (panneauDuJeu.getSurlignObject() != null) {
					if (panneauDuJeu.getSurlignObject().getClickScript() != null
							&& panneauDuJeu.getSurlignObject().getClickScript() != "")
						try {
							ScriptManager.moteurScript.eval(panneauDuJeu.getSurlignObject()
									.getClickScript(), bindings);
						} catch (ScriptException e) {
							e.printStackTrace();
						}
				}
				clickonsurlign = true;
		}
	}

	private void updateShortcuts(GameContainer gc) {

		if (Keyboard.isKeyDown(Keyboard.KEY_I) && !keyI) {
			keyI = true;
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_I) && keyI) {
			keyI = false;
			inverseInventory();
		}
	}

}
