package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ProgressBar extends FComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5691175326853976652L;
	protected int valueMax;
	protected int value;
	protected String title;
	protected Color color, backColor;
	
	public static final PImage progressBarImg = new PImage("GUI/progressBar.png");
	public static final PImage squareProgressBar = new PImage("GUI/squareProgressBar.png");
	
	protected PImage actProgressBarImg = progressBarImg;
	protected Align align;
	protected Align textAlign;
	public ProgressBar(int valueMax, Container parent) {
		super(parent);
		init();
		this.valueMax = valueMax;
		
	}
	public ProgressBar(int valueMax, int value, Container parent) {
		super(parent);
		init();
		this.valueMax = valueMax;
		this.value = value;
		
	}
	public void up(){
		this.setValue(value + 1);
	}
	public void down(){
		this.setValue(value - 1);
	}
	public int getValue(){
		return value;
	}
	public void setValue(int value){
		this.value = value;
		if(value >= valueMax)
			this.value = valueMax;
		if(value <= 0)
			this.value = 0;
	}
	public ProgressBar(String title, int valueMax, Container parent) {
		super(parent);
		init();
		this.valueMax = valueMax;
		this.setTitle(title);
		
	}
	public ProgressBar(String title, int valueMax, int value, Container parent) {
		super(parent);
		init();
		this.valueMax = valueMax;
		this.value = value;
		this.setTitle(title);
		
	}
	public ProgressBar(String title, int valueMax, int value, int sizeX, Container parent) {
		super(parent);
		init();
		this.valueMax = valueMax;
		this.value = value;
		this.setTitle(title);
		this.setSizeX(sizeX);
		
	}
	public ProgressBar(int valueMax, int value, int sizeX, Container parent) {
		super(parent);
		init();
		this.valueMax = valueMax;
		this.value = value;
		this.setSizeX(sizeX);
		
	}
	public void init(){
		valueMax = 0;
		value = 0;
		setTitle("");
		setSizeX(100);
		setSizeY(50);
		backColor = new Color(0,0,0,0);
		color = backColor;
		
		align = Align.Left;
		textAlign = Align.Left;
	}
	public void draw(Graphics g){
		int colorToDraw = (int)((float)(getSizeX()) /  (float)(valueMax) *  (float)(value));
		g.translate(getX(), getY()); 
			actProgressBarImg.getImg().startUse();
			
			actProgressBarImg.getImg().drawEmbedded(0, 0, 10, getSizeY(), 0,0,10, actProgressBarImg.getImg().getHeight());
			actProgressBarImg.getImg().drawEmbedded(10, 0, getSizeX() - 10, getSizeY(), 10, 0, actProgressBarImg.getImg().getWidth() - 10, actProgressBarImg.getImg().getHeight());
			actProgressBarImg.getImg().drawEmbedded(getSizeX() - 10, 0, getSizeX(), getSizeY(), actProgressBarImg.getImg().getWidth() - 10,0,actProgressBarImg.getImg().getWidth(), actProgressBarImg.getImg().getHeight());
			if(align == Align.Left){
				if(colorToDraw > 10){
					actProgressBarImg.getImg().drawEmbedded(0, 0, 10, getSizeY(), 
							0, 0, 10, actProgressBarImg.getImg().getHeight(), color);
					if(colorToDraw > getSizeX() - 10){
						actProgressBarImg.getImg().drawEmbedded(10, 0, getSizeX() - 10, getSizeY(), 
								10, 0, actProgressBarImg.getImg().getWidth() - 10, actProgressBarImg.getImg().getHeight(), color);
						
						actProgressBarImg.getImg().drawEmbedded(getSizeX() - 10, 0, colorToDraw, getSizeY(), 
								actProgressBarImg.getImg().getWidth() - 10, 0, actProgressBarImg.getImg().getWidth(), actProgressBarImg.getImg().getHeight(), color);
					}else{
						actProgressBarImg.getImg().drawEmbedded(10, 0, colorToDraw, getSizeY(), 
								10, 0, actProgressBarImg.getImg().getWidth() - 10, actProgressBarImg.getImg().getHeight(), color);
					}
				}
				else{
					actProgressBarImg.getImg().drawEmbedded(0, 0, colorToDraw, getSizeY(), 
							0, 0, colorToDraw, actProgressBarImg.getImg().getHeight(), color); 
				}
				
				
				//progressBarImg.getImg().draw(0, 0,  (float)(sizeX) /  (float)(valueMax) *  (float)(value), sizeY, 0,0, (float)(progressBarImg.getImg().getWidth()) /  (float)(valueMax) *  (float)(value), progressBarImg.getImg().getHeight());
				//progressBarImg.getImg().draw(0, 0,  (float)(sizeX) /  (float)(valueMax) *  (float)(value), sizeY, 0,0, (float)(progressBarImg.getImg().getWidth()) /  (float)(valueMax) *  (float)(value), progressBarImg.getImg().getHeight(), color);
				
			}
			else if(align == Align.Right){
				g.rotate(0, 0, 180);
					g.translate(-getSizeX(), -getSizeY()); 
					if(colorToDraw > 10){
						actProgressBarImg.getImg().drawEmbedded(0, 0, 10, getSizeY(), 
								0, 0, 10, actProgressBarImg.getImg().getHeight(), color);
						if(colorToDraw > getSizeX() - 10){
							actProgressBarImg.getImg().drawEmbedded(10, 0, getSizeX() - 10, getSizeY(), 
									10, 0, actProgressBarImg.getImg().getWidth() - 10, actProgressBarImg.getImg().getHeight(), color);
							
							actProgressBarImg.getImg().drawEmbedded(getSizeX() - 10, 0, colorToDraw, getSizeY(), 
									actProgressBarImg.getImg().getWidth() - 10, 0, actProgressBarImg.getImg().getWidth(), actProgressBarImg.getImg().getHeight(), color);
						}else{
							actProgressBarImg.getImg().drawEmbedded(10, 0, colorToDraw, getSizeY(), 
									10, 0, actProgressBarImg.getImg().getWidth() - 10, actProgressBarImg.getImg().getHeight(), color);
						}
					}
					else{
						actProgressBarImg.getImg().drawEmbedded(0, 0, colorToDraw, getSizeY(), 
								0, 0, colorToDraw, actProgressBarImg.getImg().getHeight(), color); 
					}
					g.translate(getSizeX() , getSizeY() );
				g.rotate(0, 0, -180);
			}
			actProgressBarImg.getImg().endUse();
			g.setColor(Color.black);
				if(textAlign == Align.Left)
					g.drawString(getTitle(), 15, getSizeY() / 2 - g.getFont().getLineHeight() / 2);
				if(textAlign == Align.Right)
					g.drawString(getTitle(), getSizeX() -  g.getFont().getWidth(title) - 15, getSizeY() / 2 - g.getFont().getLineHeight() / 2);
				if(textAlign == Align.Center)
					g.drawString(getTitle(), getSizeX()  / 2 -  g.getFont().getWidth(title) / 2, getSizeY() / 2 - g.getFont().getLineHeight()/ 2);
		g.translate(-getX(), -getY());
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the colora
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param colora the colora to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @return the backColora
	 */
	public Color getBackColor() {
		return backColor;
	}
	/**
	 * @param backColora the backColora to set
	 */
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}
	/**
	 * @return the textAligne
	 */
	public Align getAlign() {
		return align;
	}
	/**
	 * @param textAligne the textAligne to set
	 */
	public void setAlign(Align align) {
		this.align = align;
	}
	/**
	 * @return the textAligne
	 */
	public Align getTextAlign() {
		return textAlign;
	}
	/**
	 * @param textAligne the textAligne to set
	 */
	public void setTextAlign(Align textAlign) {
		this.textAlign = textAlign;
	}
	@Override
	public void updateSize() {
		
	}
}
