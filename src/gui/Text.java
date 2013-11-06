package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Text extends FComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6697825221506363812L;
	private String text;
	private TextDisplayMode display;
	private boolean autoEnter;
	private boolean markDown;
	
	private int marginX;
	private int displayInt;
	private long tempsPrec, tempsReq;
	public Text(String text, Container parent) {
		super(parent);
		this.text = text;
		setAutoEnter(false);
		markDown = false;
		updatable = true;
		setSizeX(parent.getSizeX());
		setDisplay(TextDisplayMode.Instantly);
		setDisplayInt(0);
		
	}
	public Text(String text, boolean autoenter, Container parent) {
		super(parent);
		this.text = text;
		setAutoEnter(autoenter);
		setMarginX(0);
		updatable = true;
		setSizeX(parent.getSizeX());
		setDisplay(TextDisplayMode.Instantly);
		setDisplayInt(0);
		
	}
	public Text clone(){
		Text clone = null;
		clone = (Text) super.clone();
		return clone;
	}
	public boolean drawLine(Color actualColor,Graphics g, String line, int y, boolean surlign){
		int x = 0;
		for(Character c : line.toCharArray()){
			if(c == '*'){
				if(actualColor.r == 0f){
					actualColor.r = 1f;
				}
				else if(actualColor.r == 1f){
					actualColor.r = 0f;
				}
				g.setColor(actualColor);
				
			}
			else if(c == '$'){
				if(actualColor.b == 0f){
					actualColor.b = 1f;
					surlign = true;
				}
				else if(actualColor.b == 1f){
					actualColor.b = 0f;
					surlign = false;
				}
				g.setColor(actualColor);
			}
			else{
				g.setColor(actualColor);
				g.drawString(c+"", x, y);
				x+= g.getFont().getWidth(c+"");
			}
		}
		return surlign;
	}
	public void draw(Graphics g) {
		if(getDisplayInt() > text.length())
			setDisplayInt(text.length());
		if(this.getBounds().width - 10 < g.getFont().getWidth(getText()) && !autoEnter){
			this.setBounds(this.getBounds().x, this.getBounds().y, (int) g.getFont().getWidth(getText().substring(0, getDisplayInt())) + 10, this.getBounds().height);
		}
		int sizeLine = this.getSizeX() / 9;
		if(this.getBounds().height < parent.getSizeY()){
			setSizeY(parent.getSizeY() - 2);
		}
		Color actualColor = Color.black;
		boolean surlign = false;
		g.setColor(actualColor);
		
		g.translate(getX() + getMarginX() / 2, getY());
			
			if(isAutoEnter() == true){
					int i = 0;
					int nextChar = 0;
					while(nextChar < getDisplayInt()){
						
						if(nextChar + sizeLine <= getDisplayInt()){
							String ligne = getText().substring(nextChar, nextChar + sizeLine);
							
							int lastSpace = ligne.lastIndexOf(" ");
							
							String realLigne = ligne.substring(0, lastSpace);
							if(lastSpace != -1){
								while(g.getFont().getWidth(realLigne) < parent.sizeX - getMarginX() - this.getX() + g.getFont().getWidth(" ")){
									int numberSpace = 1;
										while(realLigne.replaceFirst("([^ ])( {1,"+numberSpace+"})([^ ])", "$1$2 $3").equals(realLigne)){
											numberSpace++;
										}
									realLigne = realLigne.replaceFirst("([^ ])( {1,"+numberSpace+"})([^ ])", "$1$2 $3");
								}
								surlign = this.drawLine(actualColor, g, realLigne, g.getFont().getLineHeight() / 2 + i * g.getFont().getLineHeight(), surlign);
								nextChar += ligne.lastIndexOf(" ") + 1;
							}
							else{
								while(g.getFont().getWidth(realLigne) < parent.sizeX - getMarginX() - this.getX() + g.getFont().getWidth(" ")){
									int numberSpace = 1;
										while(realLigne.replaceFirst("([^ ])( {1,"+numberSpace+"})([^ ])", "$1$2 $3").equals(realLigne)){
											numberSpace++;
										}
									realLigne = realLigne.replaceFirst("([^ ])( {1,"+numberSpace+"})([^ ])", "$1$2 $3");
								}
								surlign = this.drawLine(actualColor, g, realLigne, g.getFont().getLineHeight() / 2 + i * g.getFont().getLineHeight(), surlign);
								
							}
						}
						else{
							surlign = this.drawLine(actualColor, g, getText().substring(nextChar, getDisplayInt()), g.getFont().getLineHeight() / 2 + i * g.getFont().getLineHeight(), surlign);
							nextChar = getDisplayInt();
							
						}
						i++;
					}
					//Réinitialisation de la couleur.
					actualColor = Color.black;
					g.setColor(actualColor);
			}
			else{
				surlign = this.drawLine(actualColor, g, getText(), g.getFont().getLineHeight() / 2, surlign);
			}
		g.translate(-getX() - getMarginX() / 2,  -getY());
		updatable = true;
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		if(getDisplay() ==  TextDisplayMode.Instantly){
			setDisplayInt(text.length());
		}
		if(getDisplay() == TextDisplayMode.Slowly){
			tempsReq = 100;
			long temps = System.currentTimeMillis();
			if(temps - tempsPrec > tempsReq){
				
				tempsPrec = temps;
				setDisplayInt(getDisplayInt() + 1);
			}
		}
		if(getDisplay() == TextDisplayMode.Normal){
			tempsReq = 50;
			long temps = System.currentTimeMillis();
			if(temps - tempsPrec > tempsReq){
				tempsPrec = temps;
				setDisplayInt(getDisplayInt() + 1);
			}
		}
		if(getDisplay() == TextDisplayMode.Fast){
			tempsReq = 25;
			long temps = System.currentTimeMillis();
			if(temps - tempsPrec > tempsReq){
				tempsPrec = temps;
				setDisplayInt(getDisplayInt() + 1);
			}
		}
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the display
	 */
	public TextDisplayMode getDisplay() {
		return display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(TextDisplayMode display) {
		this.display = display;
	}
	/**
	 * @return the displayInt
	 */
	public int getDisplayInt() {
		return displayInt;
	}
	/**
	 * @param displayInt the displayInt to set
	 */
	public void setDisplayInt(int displayInt) {
		this.displayInt = displayInt;
	}
	/**
	 * @return the autoEnter
	 */
	public boolean isAutoEnter() {
		return autoEnter;
	}
	/**
	 * @param autoEnter the autoEnter to set
	 */
	public void setAutoEnter(boolean autoEnter) {
		this.autoEnter = autoEnter;
	}
	@Override
	public void updateSize() {
		
	}
	public int getMarginX() {
		return marginX;
	}
	public void setMarginX(int marginX) {
		this.marginX = marginX;
	}
	public boolean isMarkDown() {
		return markDown;
	}
	public void setMarkDown(boolean markDown) {
		this.markDown = markDown;
	}
}
