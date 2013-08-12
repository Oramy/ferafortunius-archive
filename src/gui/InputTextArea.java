package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class InputTextArea extends InputLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final float SCROLL_ALPHA_LIMIT = 0.5f;
	
	protected boolean linesNumber;
	protected int textX, textY;
	
	protected float scrollAlpha;
	
	
	//Variable contenant la dernière position de la scrollbar
	protected int tempScrollY;
	
	protected int lastContHeight;
	public InputTextArea(Container parent) {
		super(parent);
		textX = 0;
		textY = 0;
		scrollAlpha = 0f;
		tempScrollY = 0;
	}
	public void enableLinesNumber(){
		if(!linesNumber){
			linesNumber = true;
			textX -= 50;
		}
	}
	public void disableLinesNumber(){
		if(linesNumber){
			linesNumber = false;
			textX += 50;
		}
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		
		int dwheel = Mouse.getDWheel();
		if(dwheel > 0){
			textY -= 5;
			if(textY < 0)
				textY = 0;
			setScrollAlpha(scrollAlpha + 0.1f);
		}
		else if(dwheel < 0){
			textY += 5;
			setScrollAlpha(scrollAlpha + 0.1f);
		}else{
			setScrollAlpha(scrollAlpha - 0.05f);
		}
		
		int mx = Mouse.getX() - x - this.x;
		
		int my = getRacine().sizeY - Mouse.getY() - y - this.y;
		if(mx >= sizeX - 20 && mx <= sizeX){
			if(my >= tempScrollY && my <= tempScrollY + 100)
				setScrollAlpha(scrollAlpha + 0.1f);
			int mousediff = tempScrollY + 50 - my;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				textY -= mousediff;
				if(textY < 0)
					textY = 0;
				if(textY > lastContHeight - sizeY)
					textY = lastContHeight - sizeY;
			}
		}
	}
	private void setScrollAlpha(float scroll){
		scrollAlpha = scroll;
		if(scrollAlpha > SCROLL_ALPHA_LIMIT)
			scrollAlpha = SCROLL_ALPHA_LIMIT;
		if(scrollAlpha < 0f)
			scrollAlpha = 0f;
	}
	public void drawLineNumber(Graphics g, int x, int y, int line){
		if(linesNumber){
			g.setColor(new Color(0,0,0,0.5f));
			g.drawString(line + "", x - 50, y);
			g.drawString("|", x - 50 + 40, y);
			g.setColor(Color.black);
		}
	}
	public void draw(Graphics g)
	{	
		if(precTime + actTime < System.currentTimeMillis() && focus){
			precTime = System.currentTimeMillis();
			cursorEnable = !cursorEnable;
		}
		g.translate(getX(), getY());
			
			int y = g.getFont().getLineHeight() / 2;
			int position = 0;
			int x = 0;
			
			//Garde l'ancienne valeur de décalage pour ne pas avoir de bug de translation.
			int ancientTextX = textX;
			int ancientTextY = textY; 
			
			g.translate(-ancientTextX, -ancientTextY);
			if(!focus && (contenu == null || contenu.equals(""))){
				g.setColor(Color.gray);
				g.drawString(defaultContenu, 10, getSizeY() / 2 - g.getFont().getHeight(defaultContenu) / 2);
			}
			else{
				g.setColor(Color.black);
				if(contenu == null){
					contenu = "";
				}
					int lineNumber = 1;
					
					while(position < contenu.length()){
						if(position == 0)
							drawLineNumber(g, x, y, lineNumber);
						g.drawString(contenu.substring(position, position + 1), x, y);
						if(mx >= this.getXOnScreen() + x - textX && mx <= this.getXOnScreen() - textX + x + g.getFont().getWidth(contenu.substring(position, position + 1))
								&& my > this.getYOnScreen() - textY +  y - g.getFont().getLineHeight() / 2 && my <= this.getYOnScreen() - textY + y + g.getFont().getLineHeight()
								&& action.equals("mouseclick")){
							cursor = position;
							action = "";
						}
						if(position < contenu.length() - 2){
							if(x + g.getFont().getWidth(contenu.substring(position, position + 2)) > getSizeX()){
								x = 0;
								y += g.getFont().getLineHeight();
								lineNumber++;
								drawLineNumber(g, x, y, lineNumber);
							}
							else{
								if((int)contenu.charAt(position) != 10 && (int)contenu.charAt(position) != 13)
									x+= g.getFont().getWidth(contenu.substring(position, position + 1));
								else if((int)contenu.charAt(position) == 10){
									x = 0;
									y += g.getFont().getLineHeight();
									lineNumber++;
									drawLineNumber(g, x, y, lineNumber);
								}
								else if((int)contenu.charAt(position) == 13){
									x+= g.getFont().getWidth(" ") * 4;
									if(x > getSizeX()){
										x = 0;
										y += g.getFont().getLineHeight();
										lineNumber++;
										drawLineNumber(g, x, y, lineNumber);
									}
								}
							}
						}
						else{
							if(x + g.getFont().getWidth(contenu.substring(position, position + 1)) > getSizeX()){
								x = 0;
								y += g.getFont().getLineHeight();
								lineNumber++;
								drawLineNumber(g, x, y, lineNumber);
							}
							else{
								if((int)contenu.charAt(position) != 10 && (int)contenu.charAt(position) != 13)
									x+= g.getFont().getWidth(contenu.substring(position, position + 1));
								else if(contenu.charAt(position) == (char)10){
									x = 0;
									y += g.getFont().getLineHeight();
									lineNumber++;
									drawLineNumber(g, x, y, lineNumber);
								}
								else if(contenu.charAt(position) == (char)13){
									x+= g.getFont().getWidth(" ") * 4;
									if(x > getSizeX()){
										x = 0;
										y += g.getFont().getLineHeight();
										lineNumber++;
										drawLineNumber(g, x, y, lineNumber);
									}
								}
							}
						}
						if(position == cursor - 1 && cursorEnable){
							g.drawLine(x, y, x, y + g.getFont().getLineHeight());
						}
						position ++;
					}
					if(contenu.length() == 0 && cursorEnable){
						g.drawLine(x, y, x, y + g.getFont().getLineHeight());
					}
					y += g.getFont().getLineHeight();
					if(y - textY < getSizeY())
						textY = y - getSizeY();
					if(y < getSizeY())
						textY = 1;
			}
			g.translate(ancientTextX, ancientTextY);
			
				
			drawScrollBar(g, y);
			
		
		g.translate(-getX(), -getY());
	}
	public void drawScrollBar(Graphics g, int sizeContenu){
		
		//Valeur de la zone
		float tailleZone = sizeContenu - sizeY;
		float posZone = textY;
		float coefZone = posZone/tailleZone;
		
		lastContHeight = sizeContenu;
		//Vérifications
		if(posZone > tailleZone)
			coefZone = 0f;
		if(coefZone > 1f)
			coefZone = 1f;
		if(coefZone < 0f)
			coefZone = 0f;
		
		//valeur a afficher.
		float taille = sizeY - 100;
		float pos = coefZone * taille;
		tempScrollY = (int) pos;
		//float coef = ((float) (((float)textY + sizeY) / (float)(pos)));
		g.translate(sizeX - 10, pos);
			g.setColor(new Color(0,0,0, scrollAlpha));
			
			g.fillRoundRect(0, 0, 5, 100, 5);
		g.translate(- sizeX + 10, - pos);
	}
		@Override
	public void keyReleased(int key, char c) {
		if(key == Input.KEY_ENTER){
			contenu = contenu.substring(0, cursor) + (char)10 +  contenu.substring(cursor, contenu.length());	
			cursor++;
		}
		if(key == Input.KEY_TAB){
			contenu = contenu.substring(0, cursor) + (char)13 +  contenu.substring(cursor, contenu.length());	
			cursor++;
		}
		if(key == Input.KEY_LSHIFT || key == Input.KEY_RSHIFT || key == Input.KEY_LCONTROL 
				|| key == Input.KEY_LALT || key == Input.KEY_RCONTROL || key == Input.KEY_RALT){
			
		}
		super.keyReleased(key, c);
		
		
	}
}
