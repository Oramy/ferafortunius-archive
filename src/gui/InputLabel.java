package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import autre.PressePapiers;


public class InputLabel extends FComponent implements KeyListener{
	/**
	 * 
	 */
	protected String action;
	
	protected char lastKey;
	
	protected int mx;
	protected int my;
	
	protected boolean control;
	
	protected static final long serialVersionUID = 2875226172191474763L;
	
	protected static InputLabel anotherFocus;
	
	protected String contenu = " ";
	protected String defaultContenu;
	
	protected int cursor = 0;
	protected boolean cursorEnable =true;
	
	protected long actTime = 400, precTime = 0;
	
	public InputLabel(Container parent) {
		super(parent);
		init();
	}
	public void init(){
		
		contenu = "";
		defaultContenu = "";
		precTime = System.currentTimeMillis();
		alwaysUpdateOnClick = true;
	}
	public void draw(Graphics g)
	{	
		if(precTime + actTime < System.currentTimeMillis() && focus){
			precTime = System.currentTimeMillis();
			cursorEnable = !cursorEnable;
		}
		g.translate(getX(), getY());
			if(!focus && (contenu == null || contenu.equals(""))){
				g.setColor(Color.gray);
				g.drawString(defaultContenu, 10, getSizeY() / 2 - g.getFont().getHeight(defaultContenu) / 2);
			}
			else{
				g.setColor(Color.black);
				if(contenu == null){
					contenu = "";
				}
					g.drawString(contenu, 10, getSizeY() / 2 - g.getFont().getLineHeight() / 2);
					int position = 0;
					int x = 0;
					int y = getSizeY() / 2;
					while(position < contenu.length()){
						if(mx >= this.getXOnScreen() + 10 + g.getFont().getWidth(contenu.substring(0, position)) && mx <= this.getXOnScreen()  + x +10+ g.getFont().getWidth(contenu.substring(0, position + 1))
								&& my > this.getYOnScreen()  +  y - g.getFont().getLineHeight() / 2 && my <= this.getYOnScreen() + y + g.getFont().getLineHeight()
								&& action.equals("mouseclick")){
							cursor = position;
							action = "";
						}
						
						x+=g.getFont().getWidth(contenu.substring(position, position + 1));
						
						position++;
					}
					if(cursorEnable){
						if(contenu.length() != 0){
							int xCursor =  10 + g.getFont().getWidth(contenu.substring(0, cursor));
							if(cursor == contenu.length())
								xCursor =  10 + g.getFont().getWidth(contenu.substring(0, contenu.length()));
							if(contenu.substring(contenu.length()-1).equals(" ")){
								g.drawLine(xCursor,  getSizeY() / 2 - g.getFont().getLineHeight() / 2, xCursor,  getSizeY() / 2 + g.getFont().getLineHeight() / 2);
							}
							else
								g.drawLine(xCursor,  getSizeY() / 2 - g.getFont().getLineHeight() / 2, xCursor,  getSizeY() / 2 + g.getFont().getLineHeight() / 2);
						}
					}
					//Si ya rien
					if(cursor == 0 && cursorEnable && focus){
						g.drawLine(10,  getSizeY() / 2 - g.getFont().getLineHeight() / 2, 10,  getSizeY() / 2 + g.getFont().getLineHeight() / 2);
					}
			}
		g.translate(-getX(), -getY());
	}
	public void update(GameContainer gc, int x, int y){
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		if(isInside(mx, my) && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			action = "mouseclick";
			this.mx = mx;
			this.my = my;
		}
		if(gc.getInput().getMouseX() >= this.getX() + x
				&& gc.getInput().getMouseX() <= this.getX() + x + getSizeX()
				&& gc.getInput().getMouseY() >= this.getY() + y
				&& gc.getInput().getMouseY() <= this.getY() + y + getSizeY()){
			if(anotherFocus != null){
				if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					
					anotherFocus.setFocus(false);
					focus = true;
					anotherFocus = this;
				}
			}
			else if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				focus = true;
				anotherFocus = this;
			}
		}
		else if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			focus = false;
		}
		if(focus){
			
			gc.getInput().addKeyListener(this);

		}
		else{
			gc.getInput().removeKeyListener(this);
		}
	}
	/**
	 * @return the contenu
	 */
	public String getContenu() {
		return contenu;
	}
	/**
	 * @param contenu the contenu to set
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
		cursor = 0;
	}
	/**
	 * @return the defaultContenu
	 */
	public String getDefaultContenu() {
		return defaultContenu;
	}
	/**
	 * @param defaultContenu the defaultContenu to set
	 */
	public void setDefaultContenu(String defaultContenu) {
		this.defaultContenu = defaultContenu;
	}
	@Override
	public void inputEnded() {
		
		
	}
	@Override
	public void inputStarted() {
		
		
	}
	@Override
	public boolean isAcceptingInput() {
		
		return true;
	}
	@Override
	public void setInput(Input input) {
		
		
	}
	@Override
	public void keyReleased(int key, char c) {
		if(lastKey == c)
			lastKey = Character.UNASSIGNED;
	}
	public void checkCopyPast(int key, char c){
		if(key != Input.KEY_LCONTROL && key != Input.KEY_RCONTROL && key != Input.KEY_V)
			control = false;
		if(key == Input.KEY_V && control == true){
			PressePapiers paste = new PressePapiers();
			String topast = paste.getClipboardContents();
			if(contenu != null)
				contenu = contenu.substring(0, cursor) + topast +  contenu.substring(cursor, contenu.length());
			else
				contenu = "" + topast;
			cursor+= topast.length();
		}
	}
	@Override
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_LCONTROL || key == Input.KEY_RCONTROL)
			control = true;
		else{
			if(c != lastKey){
				lastKey = c;
				checkCopyPast(key,c);
				String c2 = "";
				c2 += c;
			
				if(key != Input.KEY_DELETE 
						&& key != Input.KEY_BACK  
						&& key != Input.KEY_ENTER
						&& key != Input.KEY_LEFT  
						&& key != Input.KEY_RIGHT){
					if(contenu != null)
					contenu = contenu.substring(0, cursor) + c +  contenu.substring(cursor, contenu.length());
					else
						contenu = c2;
					cursor++;
				}
				
				
			}
			if(key == Input.KEY_BACK){
				if(cursor != 0){
					cursor--;
					contenu = contenu.substring(0, cursor)  +  contenu.substring(cursor + 1, contenu.length());
				}
			}
			if(key == Input.KEY_LEFT){
				if(cursor != 0)
					cursor--;
			}
			if(key == Input.KEY_RIGHT){
				if(cursor != contenu.length())
					cursor++;
			}
			if(key == Input.KEY_DELETE){
				if(cursor != contenu.length()){
					contenu = contenu.substring(0, cursor)  +  contenu.substring(cursor + 1, contenu.length());
				}
			}
			
		}
	}
	@Override
	public void updateSize() {
		// TODO Auto-generated method stub
		
	}


}
