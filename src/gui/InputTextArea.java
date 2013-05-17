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
	protected int textX, textY;
	public InputTextArea(Container parent) {
		super(parent);
		textX = 0;
		textY = 0;
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		
		int dwheel = Mouse.getDWheel();
		if(dwheel > 0){
			textY -= 5;
			if(textY < 0)
				textY = 0;
		}
		if(dwheel < 0){
			textY += 5;
		}
	}
	public void draw(Graphics g)
	{	
		if(precTime + actTime < System.currentTimeMillis() && focus){
			precTime = System.currentTimeMillis();
			cursorEnable = !cursorEnable;
		}
		g.translate(getX(), getY());
			g.translate(-textX, -textY);
			if(!focus && (contenu == null || contenu.equals(""))){
				g.setColor(Color.gray);
				g.drawString(defaultContenu, 10, getSizeY() / 2 - g.getFont().getHeight(defaultContenu) / 2);
			}
			else{
				g.setColor(Color.black);
				if(contenu == null){
					contenu = "";
				}
					int position = 0;
					int x = 0;
					int y = g.getFont().getLineHeight() / 2;
					while(position < contenu.length()){
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
							}
							else{
								if((int)contenu.charAt(position) != 10 && (int)contenu.charAt(position) != 13)
									x+= g.getFont().getWidth(contenu.substring(position, position + 1));
								else if((int)contenu.charAt(position) == 10){
									x = 0;
									y += g.getFont().getLineHeight();
								}
								else if((int)contenu.charAt(position) == 13){
									x+= g.getFont().getWidth(" ") * 4;
									if(x > getSizeX()){
										x = 0;
										y += g.getFont().getLineHeight();
									}
								}
							}
						}
						else{
							if(x + g.getFont().getWidth(contenu.substring(position, position + 1)) > getSizeX()){
								x = 0;
								y += g.getFont().getLineHeight();
							}
							else{
								if((int)contenu.charAt(position) != 10 && (int)contenu.charAt(position) != 13)
									x+= g.getFont().getWidth(contenu.substring(position, position + 1));
								else if(contenu.charAt(position) == (char)10){
									x = 0;
									y += g.getFont().getLineHeight();
								}
								else if(contenu.charAt(position) == (char)13){
									x+= g.getFont().getWidth(" ") * 4;
									if(x > getSizeX()){
										x = 0;
										y += g.getFont().getLineHeight();
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
						textY = 0;
			}
			
			g.translate(textX, textY);
		g.translate(-getX(), -getY());
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
