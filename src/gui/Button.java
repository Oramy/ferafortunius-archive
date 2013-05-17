package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Button extends FComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Image actuelle du bouton
	 */
	protected PImage boutonAct;
	protected static final PImage boutonHover = new PImage("GUI/buttonHover.png");
	protected static final PImage boutonClicked = new PImage("GUI/buttonHover.png");
	protected static final PImage bouton = new PImage("GUI/button.png");
	protected String name;
	/**
	 * Proportion du bouton
	 */
	protected float prop;
	/**
	 * true si on veux que la taille soit definie
	 */
	protected boolean defineSize;
	protected ComponentState state;
	protected Color maskColor;
	protected Color textColor;
	protected boolean actionWhilePressed;
	protected boolean firstAct;
	protected long lastAct;
	protected ArrayList<Action> action;
	public Button(String name, int posX, int posY, int width, int height, Container parent){
		super(parent);
		init(name, parent);
		this.setBounds(posX, posY, width, height);
	}
	public Button(String nom, Container parent){
		super(parent);
		init(nom,parent);
	}
	public void init(String name, Container parent){
		setName(name);
		actionWhilePressed = false;
		firstAct = true;
		action = new ArrayList<Action>();
		boutonAct = bouton;
		enable = true;
		defineSize = true;
		state = ComponentState.Normal;
		maskColor = new Color(0,0,0,0);
		this.setBounds(0,0,1,1);
		prop = 1f;
		setSizeX(name.length() * 12 + 40);
		textColor = new Color(255,255,255);
	}
	public void update(GameContainer gc, int x, int y){
		
		if(isEnable()){
			if(maskColor.a == 190)
				maskColor = new Color(0,0,0,0);
			if(isInside(gc.getInput().getMouseX(),gc.getInput().getMouseY())){
				
				if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					clickPressed();
				}
				else if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) &&  state == ComponentState.Clicked){
					clickReleased();
				}
				if(state != ComponentState.Hover && !gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					hover();
					state = ComponentState.Hover;
				}
			}
			else{
				if(state == ComponentState.Hover){
					normal();
					state = ComponentState.Normal;
				}
			}
			if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && state == ComponentState.Clicked){
				clickReleased();
			}
		}
		else{
			maskColor = new Color(0,0,0,190);
		}
	}
	/**
	 * Lancé quand le bouton est hover
	 */
	public void hover(){
		boutonAct = boutonHover;
		prop = 0.95f;
	}
	/**
	 * Lancé quand le bouton revient à la normale
	 */
	public void normal(){
		boutonAct = bouton;
		prop = 1f;
	}
	/**
	 * lance les actions du boutons
	 */
	public void action(){
		if(action.size() != 0)
		{
			for(int i =0; i < action.size(); i++)
				action.get(i).actionPerformed(this);
		}
	}
	/**
	 * lancé quand le clic est pressé
	 */
	public void clickPressed(){
		maskColor = new Color(0,0,0,90);
		state = ComponentState.Clicked;
		if(actionWhilePressed){
			if(firstAct){
				action();
				lastAct = System.currentTimeMillis();
			}
			else if(System.currentTimeMillis() - lastAct > 200)
				action();
		}
		firstAct = false;
	}
	/**
	 * lancé quand le clic est relaché
	 */
	public void clickReleased(){
		firstAct = true;
		state = ComponentState.Hover;
		maskColor = new Color(0,0,0,0);
		if(!actionWhilePressed)
			action();
		
	}
	public void draw(Graphics g) {
		
		if(defineSize){
			//Vérification de la taille par rapport au texte
			if(this.getBounds().width - 20 < g.getFont().getWidth(getName())){
				this.setBounds(this.getBounds().x, this.getBounds().y, (int) g.getFont().getWidth(getName()) + 20, this.getBounds().height);
			}
			if(this.getBounds().height < g.getFont().getLineHeight()){
				this.setBounds(this.getBounds().x, this.getBounds().y, this.getBounds().width, (g.getFont().getLineHeight()));
			}
		}
		g.setColor(textColor);
		drawButton(g);
		
	}
	protected void drawButton(Graphics g){
		g.translate(this.getBounds().x, this.getBounds().y);
		//Dessin du bouton avec adaptation de l'image
		boutonAct.getImg().startUse();
		boutonAct.getImg().drawEmbedded(0, 0, 20, this.getBounds().height,
				0, 0, 20, boutonAct.getImg().getHeight());
		
		boutonAct.getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
				20,0, 21, boutonAct.getImg().getHeight());
		
		boutonAct.getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, 
				boutonAct.getImg().getWidth() - 20, 0, boutonAct.getImg().getWidth(), boutonAct.getImg().getHeight());
		
		boutonAct.getImg().drawEmbedded(0, 0, 20, this.getBounds().height, 
				0, 0, 20, boutonAct.getImg().getHeight(), maskColor);
		
		boutonAct.getImg().drawEmbedded(20, 0, this.getBounds().width - 20, this.getBounds().height, 
				20,0, 21, boutonAct.getImg().getHeight(), maskColor);
		
		boutonAct.getImg().drawEmbedded(this.getBounds().width - 20, 0, this.getBounds().width, this.getBounds().height, 
				boutonAct.getImg().getWidth() - 20, 0, boutonAct.getImg().getWidth(), boutonAct.getImg().getHeight(), maskColor);
		
		boutonAct.getImg().endUse();
		//Dessin du texte selon une proportion donnée.
		g.scale(prop, prop);
			
			g.drawString(getName(),  this.getBounds().width / 2 - ((g.getFont().getWidth(getName()))/ 2),this.getBounds().height / 2 - ((g.getFont().getHeight(getName())/ 2)));
		
		g.scale(1/prop, 1/prop);
		
	g.translate(-this.getBounds().x, -this.getBounds().y);
	}
	/**
	 * @return the disable
	 */
	public boolean isEnable() {
		
		return enable;
	}
	public boolean isDisable() {
		return !enable;
	}
	public void enable() {
		this.enable = true;
	}
	/**
	 * @param disable the disable to set
	 */
	public void disable() {
		this.enable = false;
	}
	/**
	 * @return the action
	 */
	public ArrayList<Action> getAction() {
		return action;
	}
	/**
	 * @return the nom
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void updateSize() {
		
	}


}
