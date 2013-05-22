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
	
	private int marginX;
	private int displayInt;
	private long tempsPrec, tempsReq;
	public Text(String text, Container parent) {
		super(parent);
		this.text = text;
		setAutoEnter(false);
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
		g.translate(getX() + getMarginX() / 2, getY());
			g.setColor(Color.black);
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
								g.drawString(realLigne, 0, g.getFont().getLineHeight() / 2 + i * g.getFont().getLineHeight());
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
								g.drawString(realLigne, 0, g.getFont().getLineHeight() / 2 + i * g.getFont().getLineHeight());
							
							}
						}
						else{
							g.drawString(getText().substring(nextChar, getDisplayInt()), 0, g.getFont().getLineHeight() / 2 + i * g.getFont().getLineHeight());
							nextChar = getDisplayInt();
							
						}
						i++;
					}
			}
			else{
				g.drawString(getText(), 0, g.getFont().getLineHeight() / 2);
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
		// TODO Auto-generated method stub
		
	}
	public int getMarginX() {
		return marginX;
	}
	public void setMarginX(int marginX) {
		this.marginX = marginX;
	}
}
