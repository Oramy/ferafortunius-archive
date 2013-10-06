package gui;

import gui.buttons.BasicButtonImage;
import gui.buttons.Button;
import gui.layouts.GridLayout;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import observer.ActionListener;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ObjetMap.BasicObjetMap;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;

import com.ferafortunius.animations.AnimationReactor;
import com.oramy.balises.Balise;
import com.oramy.balises.BaliseRecognizer;

public class Menu extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameMain gm;
	private BasicButtonImage logo;
	private ArrayList<PImage> backgroundimages;
	private int backgroundId;
	private long tempsPrec;
	private PImage transitionBackground;
	
	private Color transitionColor;
	private String toDo;

	private Container buttonContainer;
	private Button nouvPart;
	private Button chargerPart;
	private Button editer;
	private Button optionsButton;
	private Button quitter;
	private Shader test;
	
	private float offset;
	
	private ControllerDetection controllerDetection;
	private static final int TRANSITION_TIME = 12000;
	public Menu(GameMain gameMain, GameContainer gc) {
		super(0,0, gc.getWidth(), gc.getHeight(), null);
		//Focus the controller
		ControllersManager.getFirstController().setControllerContainer(this);
	
		offset = 0.000f;
		//		String f = "Music/Brittle Rille.ogg";
		try {
			test = Shader.makeShader("data/Shaders/test.vert", "data/Shaders/test.frag");
			test.setUniformFVariable("offset", offset);
			
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		//Initialisation
		toDo = ""; //$NON-NLS-1$
		transitionColor = new Color(0,0,0, 0);
		gm = gameMain;
		tempsPrec = 0;
		
		//Backgrounds.
		backgroundimages = new ArrayList<PImage>();
		
		File f = new File("Images/Menu/");
		File[] fonds = f.listFiles();
		for(int i = 0; i < fonds.length; i++){
			File fond = fonds[i];
			if(!fond.isHidden())
			backgroundimages.add(new PImage("Menu/" + fond.getName()));
		}
		
		//Init background ID
		backgroundId = (int) (Math.random() * backgroundimages.size());
		
		//Init backgrounds.
		this.backgroundMode = Affichage.ImageScaled;
		this.setBackground(backgroundimages.get(backgroundId)); //$NON-NLS-1$
		transitionBackground = backgroundimages.get((backgroundId + 1) % backgroundimages.size());
		transitionBackground.getImg().setAlpha(1f);
		background.getImg().setAlpha(0f);
		//Init logo
		logo = new BasicButtonImage("GUI/logo.png", this);//$NON-NLS-1$
		logo.x = sizeX / 2 - logo.sizeX / 2;
		logo.alwaysUpdateOnClick = true;
		logo.y = 40;
		logo.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent comp){
				Desktop desktop = null; 
				java.net.URI url; 
				try {
					url = new java.net.URI(Messages.getString("Menu.link.0")); //$NON-NLS-1$
				
					if (Desktop.isDesktopSupported()) 
					{ 
						desktop = Desktop.getDesktop(); 
						desktop.browse(url); 
					}
				} catch (URISyntaxException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				} 
			}
		});
		this.addComponent(logo);
		
		
		buttonContainer = new Container(sizeX / 2 - 250, 350, 500, 250, this);
		buttonContainer.background = Container.alpha;
		
		//Création du layout
		GridLayout layout = new GridLayout(1,3);
		buttonContainer.setActualLayout(layout);
		this.addComponent(buttonContainer);
		
		nouvPart = new Button(Messages.getString("Menu.text.0"), buttonContainer); //$NON-NLS-1$
		if(!ControllersManager.hasController(gc))
			nouvPart.setName(nouvPart.getName().substring(1));
		nouvPart.getAction().add(new ActionListener(){
			private Menu c;
			public void actionPerformed(FComponent c2){
				c = (Menu) c2.getRacine();
				
				Thread t = new Thread(new Runnable(){
					public void run(){
						if(c.toDo.equals("")){ //$NON-NLS-1$
							for(int i = 0; i < 255; i++){
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									
									e.printStackTrace();
								}
								offset+= 1f/255f;
							}
						}
						c.toDo = "nouvPart";
							 //$NON-NLS-1$
					}
				});	
				t.start();
			}
		});
		nouvPart.setSizeX(500);
		buttonContainer.addComponent(nouvPart);
		
		/*chargerPart = new Button(Messages.getString("Menu.text.1"), buttonContainer); //$NON-NLS-1$
		chargerPart.action.add(new ActionListener(){
			private Menu c;
			public void actionPerformed(FComponent c2){
				c = (Menu) c2.getRacine();
				Thread t = new Thread(new Runnable(){
					public void run(){
						if(c.toDo.equals("")){ //$NON-NLS-1$
							for(int i = 0; i < 256; i++){
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									
									e.printStackTrace();
								}
								c.transitionColor = new Color(0,0,0, i);
							}
						}
						c.toDo = "chargerPart"; //$NON-NLS-1$
					}
				});	
				t.start();
			}
		});
		/*chargerPart.setSizeX(500);
		buttonContainer.addComponent(chargerPart);*/
		editer = new Button(Messages.getString("Menu.text.2"), buttonContainer); //$NON-NLS-1$
		editer.getAction().add(new ActionListener(){
			private Menu c;
			public void actionPerformed(FComponent c2){
				c = (Menu) c2.getRacine();
				c.toDo = "editer"; //$NON-NLS-1$
			}
		});
		editer.setSizeX(500);
		buttonContainer.addComponent(editer);
		/*
		optionsButton = new Button(Messages.getString("Menu.text.3"), buttonContainer); //$NON-NLS-1$
		optionsButton.setSizeX(500);
		buttonContainer.addComponent(optionsButton);*/

		quitter = new Button(Messages.getString("Menu.text.4"), buttonContainer); //$NON-NLS-1$
		quitter.getAction().add(new ActionListener(){
			private Menu c;
			public void actionPerformed(FComponent c2){
				c = (Menu) c2.getRacine();
				Thread t = new Thread(new Runnable(){
					public void run(){
						if(c.toDo.equals("")){ //$NON-NLS-1$
							for(int i = 0; i < 256; i++){
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									
									e.printStackTrace();
								}
								c.transitionColor = new Color(0,0,0, i);
							}
						}
						c.toDo = "quitter"; //$NON-NLS-1$
					}
				});	
				t.start();
			}
		});
		quitter.setSizeX(500);
		buttonContainer.addComponent(quitter);
		
		
		controllerDetection = new ControllerDetection(gc, this);
		this.addComponent(controllerDetection);
		
	
	}
	protected void nouvPart() {
		gm.setMode(ModeJeu.Jeu, gm.getApp());
	}
	@Override
	public void updateController(GameContainer gc) {
		GridLayout layout = ((GridLayout)buttonContainer.actualLayout);
		if(layout.getChoice() == -1)
			layout.setChoice(0);
		if(ControllersManager.getFirstController().isDownReleased()){
			Button ancientButton = (Button)layout.getObjectChoice();
			ancientButton.setName(ancientButton.getName().substring(1));
			
			layout.increaseChoice();
			Button b = (Button)layout.getObjectChoice();
			b.setName(">"+b.getName());
		}
		if(ControllersManager.getFirstController().isUpReleased()){
			Button ancientButton = (Button)layout.getObjectChoice();
			ancientButton.setName(ancientButton.getName().substring(1));
			
			layout.decreaseChoice();
			
			Button b = (Button)layout.getObjectChoice();
			b.setName(">"+b.getName());
		}
		if(ControllersManager.getFirstController().isButton1Released()){
			layout.actionChoice();
		}
		if(ControllersManager.getFirstController().isButton2Released()){
			Button ancientButton = (Button)layout.getObjectChoice();
			ancientButton.setName(ancientButton.getName().substring(1));
			
			layout.setChoice(this.getComponents().size());
			
			Button b = (Button)layout.getObjectChoice();
			b.setName(">"+b.getName());
		}
		//((GridLayout)buttonContainer.actualLayout).updateChoice();
	}
	public void update(GameContainer gc, int arg1) {
		super.update(gc, this.getX(), this.getY());
		test.setUniformFVariable("offset", offset);
		//Le layout du menu
		int mx = Mouse.getX();
		int my = getSizeY() - Mouse.getY();
		
		
		if(toDo.equals("nouvPart")){ //$NON-NLS-1$
			gm.setMode(ModeJeu.Loading, gm.getApp());
			
		}
		else if(toDo.equals("chargerPart")){ //$NON-NLS-1$
			gm.setMode(ModeJeu.Jeu, gm.getApp());
		}
		else if(toDo.equals("editer")){ //$NON-NLS-1$
			gm.setMode(ModeJeu.Editeur, gm.getApp());
		}
		else if(toDo.equals("quitter")){ //$NON-NLS-1$
			System.exit(1);
		}
		if(System.currentTimeMillis() - TRANSITION_TIME > tempsPrec){
			tempsPrec = System.currentTimeMillis();
			backgroundId++;
			backgroundId  %= backgroundimages.size();
			background = backgroundimages.get(backgroundId);
			transitionBackground = backgroundimages.get((backgroundId + 1) % backgroundimages.size());
		}
			nouvPart.setBoutonAct(Container.alpha);
			//chargerPart.boutonAct = alpha;
			editer.setBoutonAct(Container.alpha);
			//optionsButton.boutonAct = alpha;
			quitter.setBoutonAct(Container.alpha);
	}
	@Override
	public void drawBackground(Image img){
		super.drawBackground(img);
			
	}
	public void paintComponent(GameContainer container, Graphics g) {
		
		g.setFont(FontRessources.getFonts().titres);
		g.setColor(Color.white);
		g.fillRect(0, 0, sizeX, sizeY);
		test.startShader();
		
		this.drawBegin(g);
			if((float)(System.currentTimeMillis() - tempsPrec - (TRANSITION_TIME - 1000)) / (1000f) <= 1f
					&& (float)(System.currentTimeMillis() - tempsPrec - (TRANSITION_TIME - 1000)) / (1000f) >= 0f){
				transitionBackground.getImg().setAlpha((float)(System.currentTimeMillis() - tempsPrec - (TRANSITION_TIME - 1000)) / (1000f));
				background.getImg().setAlpha(1f - (float)(System.currentTimeMillis() - tempsPrec - (TRANSITION_TIME - 1000)) / (1000f));		
			}
			drawBackground(transitionBackground.getImg());
			
			this.draw(g);
			
			g.setColor(transitionColor);
			g.fillRect(0, 0, getSizeX(), getSizeY());
		this.drawEnd(g);
		Shader.forceFixedShader();
		
		
	}
	public void init(GameContainer gc) {
		toDo = "";
		transitionColor = new Color(0,0,0,0);
	}
}
