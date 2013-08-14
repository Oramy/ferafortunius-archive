package gui;

import gui.layouts.GridLayout;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import observer.ActionListener;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

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
	private static final PImage logoAFTS = new PImage("aftsLogo.png");;
	
	private Color transitionColor;
	private boolean onLogo;
	private boolean onLogoAFTS;
	private String toDo;
	private boolean onClickLogo;
	
	private Container buttonContainer;
	private Button nouvPart;
	private Button chargerPart;
	private Button editer;
	private Button optionsButton;
	private Button quitter;
	private Shader test;
	
	private float offset;
	
	public Menu(GameMain gameMain, GameContainer gc) {
		super(0,0, gc.getWidth(), gc.getHeight(), null);
		
		//Focus the controller
		ControllersManager.getFirstController().setControllerContainer(this);
		
		onClickLogo = false;
		
		/*
		offset = 10f;
		//		String f = "Music/Brittle Rille.ogg";
		try {
			test = Shader.makeShader("data/Shaders/test.vert", "data/Shaders/test.frag");
			test.setUniformFVariable("offset", offset);
			
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
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
		
		
		//Init logo
		logo = new BasicButtonImage("GUI/logo.png", this);//$NON-NLS-1$
		logo.x = sizeX / 2 - logo.sizeX / 2;
		logo.alwaysUpdateOnClick = true;
		logo.y = 40;
		logo.action.add(new ActionListener(){
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
		nouvPart.action.add(new ActionListener(){
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
								c.transitionColor = new Color(0,0,0, i);
							}
						}
							c.toDo = "nouvPart"; //$NON-NLS-1$
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
		editer.action.add(new ActionListener(){
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
						c.toDo = "editer"; //$NON-NLS-1$
					}
				});	
				t.start();
			}
		});
		editer.setSizeX(500);
		buttonContainer.addComponent(editer);
		/*
		optionsButton = new Button(Messages.getString("Menu.text.3"), buttonContainer); //$NON-NLS-1$
		optionsButton.setSizeX(500);
		buttonContainer.addComponent(optionsButton);*/

		quitter = new Button(Messages.getString("Menu.text.4"), buttonContainer); //$NON-NLS-1$
		quitter.action.add(new ActionListener(){
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
		//Le layout du menu
		int mx = Mouse.getX();
		int my = getSizeY() - Mouse.getY();
		
		
		
		if(mx > 36 && mx < 135 + 36 && my > 371 && my < 471 + 102){
			onLogo = true;
		}
		else{
			onLogo = false;
		}
		if(onLogo && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !onClickLogo){
			onClickLogo = true;
			
		}
		else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && onClickLogo){
			onClickLogo = false;
		}
		if(toDo.equals("nouvPart")){ //$NON-NLS-1$
			gm.setMode(ModeJeu.Jeu, gm.getApp());
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
		if(System.currentTimeMillis() - 120000 > tempsPrec){
			tempsPrec = System.currentTimeMillis();
			backgroundId++;
			backgroundId  %= backgroundimages.size();
			background = backgroundimages.get(backgroundId);
			transitionBackground = backgroundimages.get((backgroundId + 1) % backgroundimages.size());
		}
			nouvPart.boutonAct = Container.alpha;
			//chargerPart.boutonAct = alpha;
			editer.boutonAct = Container.alpha;
			//optionsButton.boutonAct = alpha;
			quitter.boutonAct = Container.alpha;
	}
	public void paintComponent(GameContainer container, Graphics g) {
		g.setFont(FontRessources.getFonts().titres);
		g.setColor(Color.white);
		g.fillRect(0, 0, sizeX, sizeY);
		this.drawBegin(g);
			transitionBackground.getImg().setAlpha((float)(System.currentTimeMillis() - tempsPrec - 119745) / 255f);
			background.getImg().setAlpha(1f - (float)(System.currentTimeMillis() - tempsPrec - 119745) / 255f);
			drawBackground(transitionBackground.getImg());
			this.draw(g);
			if(!onLogoAFTS){
				logoAFTS.getImg().draw(getSizeX() - 300, getSizeY() - 400, 256, 354);
			}else{
				
				logoAFTS.getImg().draw(31, 466, 145, 112);
			}
			g.setColor(transitionColor);
			g.fillRect(0, 0, getSizeX(), getSizeY());
		this.drawEnd(g);
	}
	public void init(GameContainer gc) {
		toDo = "";
		transitionColor = new Color(0,0,0,0);
	}
}
