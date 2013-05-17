package gui.jeu;

import gui.Container;
import gui.DialogsRessources;
import gui.FontRessources;
import gui.GameMain;
import gui.Text;
import gui.TextDisplayMode;

import java.util.ArrayList;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import thread.RunJump;
import Items.Item;
import Items.Potion;
import Level.Camera;
import Level.ChunkMap;
import Level.ItemLoader;
import Level.MapLoader;
import ObjetMap.Chrono;
import ObjetMap.Direction;
import ObjetMap.Entity;
import ObjetMap.ObjetMap;
import ObjetMap.Perso;
import bonus.BuffRegenLife;
import bonus.ItemBonus;
import bonus.Life;
import bonus.MaxLife;

public class Jeu extends Container implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5986543142095506791L;

	public static long lastUpdate, timeRest;
	
	//Manager et moteur de script
	public static final ScriptEngineManager managerScript = new ScriptEngineManager();
	public static final ScriptEngine moteurScript = managerScript
			.getEngineByName("rhino");
	
	//Affichage
	private PanneauJeuAmeliore panneauDuJeu;
	
	//Camera par défaut
	private Camera cameraPerso;
	
	//Carte
	private ChunkMap carte;
	
	//Joueur
	private Entity player;
	private int idPersoJoueur;
	
	//Application
	private GameMain gm;
	
	//Thread de saut
	private Thread sauter;
	
	//Vitesse
	private int vitesseDep = 1;
	private long vitessePerso;
	
	//Régulateur de vitesse
	private long tempsDepPrec;
	
	//Fenêtre des options
	private OptionsJeuFrame optionsFen;

	//Fenêtre de l'inventaire
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

	public Jeu(GameMain gameMain, GameContainer gc) {
		super(0, 0, gc.getWidth(), gc.getHeight(), null);
		
		setGm(gameMain);
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

	public void init(GameContainer gc, ChunkMap c) {
		//Initialisation des variables
		alphaTitreMap = 0;
		
		//Afficher les FPS
		gc.setShowFPS(true);
		
		//Régulateur d'update
		lastUpdate = (int) System.currentTimeMillis();
		timeRest = 0;
		
		keyI = false;
		
		//Initialisation de la carte
		setDialogList(new ArrayList<Text>());
		carte = c;
		setPlayer(carte.getFirstEntity());
		if (player == null) {
			player = new Perso(0, 0, 0, 0, 0, 0);
		}
		player.setSpeed(1);
		setIdPersoJoueur(-1);
		
		//Initialisation de la caméra
		cameraPerso = new Camera(0, 0, 1f);
		cameraPerso.teleportToObject(player);
		cameraPerso.setFollowHim(player);
		
		//Initialisation du joueur
		player.getInventaire().setMaxWeight(3000);
		player.getInventaire().addContent(new Potion(player));
		
		player.addBonus(new MaxLife(player));
		player.addBonus(new BuffRegenLife(player));
		
		//Initialisation de la GUI
		cleanGUI();
		initGUI();

		//Ajout des dialogues du Prologue.
		addFileDialog("minimap-1"); //$NON-NLS-1$
		addFileDialog("minimap-2"); //$NON-NLS-1$
		addFileDialog("minimap-3"); //$NON-NLS-1$
		addFileDialog("minimap-4"); //$NON-NLS-1$
		
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

			}
		});
		t.start();
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
		
		/* Désactivé pour la version snapshot.
		 this.addComponent(new FastMenuContainer(0, this.getHeight() - 86,
		 this.getWidth(), this.getHeight() / 2, this));*/
		
		//Menu du jeu
		this.addComponent(new MenuJeuContainer(0, 30, 170, 210, this));
		
		//Feêtre de l'inventaire
		inventaireFrame = new InventaireFrame(this.getWidth() / 2 - 100,
				this.getHeight() / 2 - 100, 260, 330,
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
		
		g.setFont(FontRessources.getFonts().titres);
		g.setColor(new Color(255, 255, 255, (alphaTitreMap)));

		// g.drawString(carte.getNom() + "", sizeX / 2 -
		// g.getFont().getWidth(carte.getNom()) / 2, 100);

		g.setColor(transitionColor);
		
		g.fillRect(0, 0, getSizeX(), getSizeY());
		
		this.drawEnd(g);
		
		System.out.println("Affichage : "
				+ (System.currentTimeMillis() - temps) + "ms");
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
		
		long temps = System.currentTimeMillis();
		
		//Mise à jours des touches
		
		
		//Titre de la map
		if (alphaTitreMap > 0)
			alphaTitreMap--;
		
		// Gravité
		if (player != null) {
			if (!player.isFly()) {
				getCarte().deplacement(player, 0, 0, -player.getSizeZ() / 10,
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
		
		//Mise à jour de la souris
		updateMouse(gc);
		
		//Mise à jour de la Map
		carte.update(this);
		updateKeys(gc);
		
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
		
		
		System.out.println("Update : " + (System.currentTimeMillis() - temps)
				+ "ms");
		lastUpdate = System.currentTimeMillis();
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
				Bindings bindings = moteurScript
						.getBindings(ScriptContext.ENGINE_SCOPE);
				bindings.clear();
				// Ajout de la variable entree dans le script
				bindings.put("himself", panneauDuJeu.getSurlignObject()); //$NON-NLS-1$
				bindings.put("newcam", new Camera(0, 0, 1f)); //$NON-NLS-1$
				bindings.put("emptytext", new Text("", getDialogBar())); //$NON-NLS-1$ //$NON-NLS-2$
				bindings.put(
						"emptychrono", new Chrono(System.currentTimeMillis(), "Real time")); //$NON-NLS-1$ //$NON-NLS-2$
				bindings.put("currentTimeMillis", System.currentTimeMillis()); //$NON-NLS-1$
				bindings.put("jeu", this); //$NON-NLS-1$
				
				if (panneauDuJeu.getSurlignObject() != null) {
					if (panneauDuJeu.getSurlignObject().getClickScript() != null
							&& panneauDuJeu.getSurlignObject().getClickScript() != "")
						try {
							moteurScript.eval(panneauDuJeu.getSurlignObject()
									.getClickScript(), bindings);
						} catch (ScriptException e) {
							e.printStackTrace();
						}
				}
				clickonsurlign = true;
		}
	}
	private void updateKeys(GameContainer gc) {
		if (tempsDepPrec + vitessePerso < System.currentTimeMillis()) {
			tempsDepPrec = System.currentTimeMillis();
			
			//Course
			if (gc.getInput().isKeyDown(Input.KEY_LSHIFT)
					|| gc.getInput().isKeyDown(Input.KEY_RSHIFT)) {
				vitesseDep = 2;
				player.setSpeed(vitesseDep);
			} else {
				vitesseDep = 1;
				player.setSpeed(vitesseDep);
			}
			
			//Saut
			if (gc.getInput().isKeyDown(Input.KEY_SPACE)
					|| gc.getInput().isControlPressed(5)) {
				if (sauter != null) {
					if (!sauter.isAlive()) {
						sauter = new Thread(new RunJump(this, carte, player,
								player.getSizeZ(), 250, player.getId()));
						sauter.start();
						player.addBonus(new Life(-5, player));
					}
				} else {
					sauter = new Thread(new RunJump(this, carte, player,
							player.getSizeZ(), 250, player.getId()));
					sauter.start();
					player.addBonus(new Life(-5, player));
				}
				player.decreaseMp(2);
			}
			// Touches directionnelles.
			if ((gc.getInput().isKeyDown(Input.KEY_Q) || gc.getInput()
					.isControllerLeft(0))
					&& (gc.getInput().isKeyDown(Input.KEY_Z) || gc.getInput()
							.isControllerUp(0)) && player.getAnimationLaunchedCount() == 0){
				player.walkAnim(Direction.NW);
				player.walk(this);
			
			} else if ((gc.getInput().isKeyDown(Input.KEY_Q) || gc.getInput()
					.isControllerLeft(0))
					&& (gc.getInput().isKeyDown(Input.KEY_S) || gc.getInput()
							.isControllerDown(0)) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.SW);
				player.walk(this);
			} else if ((gc.getInput().isKeyDown(Input.KEY_D) || gc.getInput()
					.isControllerRight(0))
					&& (gc.getInput().isKeyDown(Input.KEY_Z) || gc.getInput()
							.isControllerUp(0)) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.NE);
				player.walk(this);
			} else if ((gc.getInput().isKeyDown(Input.KEY_D) || gc.getInput()
					.isControllerRight(0))
					&& (gc.getInput().isKeyDown(Input.KEY_S) || gc.getInput()
							.isControllerDown(0)) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.SE);
				player.walk(this);
			} else if ((gc.getInput().isKeyDown(Input.KEY_Q)
					|| gc.getInput().isControllerLeft(0)) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.W);
				player.walk(this);
			} else if ((gc.getInput().isKeyDown(Input.KEY_Z)
					|| gc.getInput().isControllerUp(0)) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.N);
				player.walk(this);
			} else if ((gc.getInput().isKeyDown(Input.KEY_D)
					|| gc.getInput().isControllerRight(0)) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.E);
				player.walk(this);
			} else if ((gc.getInput().isKeyDown(Input.KEY_S)
					|| gc.getInput().isControllerDown(0)) && player.getAnimationLaunchedCount() == 0) {
				player.walkAnim(Direction.S);
				player.walk(this);
			}
			if(player.getAllAnimationLaunchedCount() == 0)
			{
				player.launchAnimation("stay" + player.getDirection().name());
			}
		}
		updateShortcuts(gc);
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
