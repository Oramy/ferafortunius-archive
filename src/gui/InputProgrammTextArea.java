package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class InputProgrammTextArea extends InputTextArea{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean linecomment;
	protected boolean multilinecomment;
	protected boolean string;
	protected boolean keybool;
	protected boolean fieldbool;
	protected ArrayList<String> motscle;
	protected ArrayList<String> fields;
	public InputProgrammTextArea(Container parent) {
		super(parent);
		
		enableLinesNumber();
		
		linecomment = false;
		multilinecomment = false;
		string = false;
		keybool = false;
		fieldbool = false;
		motscle = new ArrayList<String>();
		motscle.add("if");
		motscle.add("else");
		motscle.add("while");
		motscle.add("for");
		motscle.add("null");
		motscle.add("false");
		motscle.add("true");
		motscle.add("var");
		motscle.add("int");
		motscle.add("double");
		motscle.add("boolean");
		motscle.add("char");
		motscle.add("short");
		motscle.add("long");
		motscle.add("float");
		motscle.add("public");
		motscle.add("protected");
		motscle.add("private");
		motscle.add("new");
		motscle.add("extends");
		motscle.add("implements");
		
		fields = new ArrayList<String>();
	}
	public boolean hereAKeyWord(int position, int positionWord){
		String key = motscle.get(positionWord);
		//key test true
			if(contenu.length() >= key.length() + 1 && position <= contenu.length() - key.length() - 1){
				if(contenu.length() >= key.length() + 2 && position <= contenu.length() - key.length() - 2 ){
					if(contenu.substring(position + 1, position + 1 + key.length()).equals(key) 
							&& !Character.isLetter(contenu.substring(position, position + 1).charAt(0))
							&& !Character.isLetter(contenu.substring(position + 1 + key.length(),  position + 2 + key.length()).charAt(0))
							&& !multilinecomment && !string && !linecomment){
							keybool = true;
					}
				}
				else{
					if(contenu.substring(position + 1, position + 1 + key.length()).equals(key) 
							&& !Character.isLetter(contenu.substring(position, position + 1).charAt(0))
							&& !multilinecomment && !string && !linecomment){
							keybool = true;
					}
				}
			}
			if(position == 0 && contenu.length() >= key.length()){
				if(contenu.length() >= key.length() + 1){
					if(contenu.substring(position, position + key.length()).equals(key)
							&& !Character.isLetter(contenu.substring(position + key.length(),  position + 1 + key.length()).charAt(0))
							&& !multilinecomment && !string && !linecomment){
						keybool = true;
					}
				}
				else{
					if(contenu.substring(position, position + key.length()).equals(key) && !multilinecomment && !string && !linecomment){
						keybool = true;
					}
				}
			}
		// key test false
		if(contenu.length() >= key.length() && position >= key.length() + 1){
			if((contenu.substring(position - key.length(), position).equals(key)) && !Character.isLetter(contenu.substring(position - key.length() - 1, position - key.length()).charAt(0)) &&!multilinecomment && !linecomment && !string){
				keybool  = false;
			}
		}
		if(position == key.length() && contenu.length() >= key.length()){
			if((contenu.substring(position - key.length(), position).equals(key)) &&!multilinecomment && !linecomment && !string){
				keybool  = false;
			}
		}
		return keybool;
	}
	public boolean hereAField(int position, int positionWord){
		String key = fields.get(positionWord);
		//key test true
			if(contenu.length() >= key.length() + 1 && position <= contenu.length() - key.length() - 1){
				if(contenu.length() >= key.length() + 2 && position <= contenu.length() - key.length() - 2 ){
					if(contenu.substring(position + 1, position + 1 + key.length()).equals(key) 
							&& !Character.isLetter(contenu.substring(position, position + 1).charAt(0))
							&& !Character.isLetter(contenu.substring(position + 1 + key.length(),  position + 2 + key.length()).charAt(0))
							&& !multilinecomment && !string && !linecomment){
							fieldbool = true;
					}
				}
				else{
					if(contenu.substring(position + 1, position + 1 + key.length()).equals(key) 
							&& !Character.isLetter(contenu.substring(position, position + 1).charAt(0))
							&& !multilinecomment && !string && !linecomment){
							fieldbool = true;
					}
				}
			}
			if(position == 0 && contenu.length() >= key.length()){
				if(contenu.length() >= key.length() + 1){
					if(contenu.substring(position, position + key.length()).equals(key)
							&& !Character.isLetter(contenu.substring(position + key.length(),  position + 1 + key.length()).charAt(0))
							&& !multilinecomment && !string && !linecomment){
						fieldbool = true;
					}
				}
				else{
					if(contenu.substring(position, position + key.length()).equals(key) && !multilinecomment && !string && !linecomment){
						fieldbool = true;
					}
				}
			}
		// key test false
		if(contenu.length() >= key.length() && position >= key.length() + 1){
			if((contenu.substring(position - key.length(), position).equals(key)) && !Character.isLetter(contenu.substring(position - key.length() - 1, position - key.length()).charAt(0)) &&!multilinecomment && !linecomment && !string){
				fieldbool  = false;
			}
		}
		if(position == key.length() && contenu.length() >= key.length()){
			if((contenu.substring(position - key.length(), position).equals(key)) &&!multilinecomment && !linecomment && !string){
				fieldbool  = false;
			}
		}
		return fieldbool;
	}
	public void hereAKeyWord(int position){
		for(int i = 0; i < motscle.size(); i++){
			hereAKeyWord(position, i);
		}
		
	}
	public void hereAField(int position){
		for(int i = 0; i < fields.size(); i++){
			hereAField(position, i);
		}
		
	}
	public void draw(Graphics g)
	{	
		if(precTime + actTime < System.currentTimeMillis() && focus){
			precTime = System.currentTimeMillis();
			cursorEnable = !cursorEnable;
		}
		g.translate(getX(), getY());
			int ancientX = textX;
			int ancientY = textY;
			
			//Text variables.
			int position = 0;
			int x = 0;
			int y = g.getFont().getLineHeight() / 2;
			
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
					linecomment = false;
					multilinecomment = false;
					string = false;
					keybool = false;
					int lastguillemet = -1;
					int lineNumber = 1;
					while(position < contenu.length()){
						if(position == 0)
							drawLineNumber(g, x, y, lineNumber);
						
						// Commentaires
						
						if(mx >= this.getXOnScreen() + x - textX && mx <= this.getXOnScreen() - textX + x + g.getFont().getWidth(contenu.substring(position, position + 1))
								&& my > this.getYOnScreen() - textY +  y - g.getFont().getLineHeight() / 2 && my <= this.getYOnScreen() - textY + y + g.getFont().getLineHeight()
								&& action.equals("mouseclick")){
							cursor = position;
							action = "";
						}
						if(contenu.length() >= 2 && position <= contenu.length() - 2){
							if(contenu.substring(position, position + 2).equals("//") && !multilinecomment && !string){
									linecomment = true;
							}
							else if(contenu.substring(position, position + 2).equals("/*") && !linecomment && !string){
								multilinecomment = true;
							}
							
						}
						if(contenu.length() >= 2 && position >= 2){
							if(contenu.substring(position - 2, position).equals("*/")){
								multilinecomment = false;
							}
						}
						//Strings
						if(contenu.length() >= 1 && position <= contenu.length() - 1){
							if(contenu.substring(position, position + 1).equals("\"") && !multilinecomment && !linecomment && !string){
								string = true;
								lastguillemet = position;
							}
						}
						// Strings 2
						if(contenu.length() >= 1 && position >= 1){
							if(contenu.substring(position - 1, position).equals("\"") && !multilinecomment && !linecomment && string && position - 1 != lastguillemet){
								string = false;
								lastguillemet = position;
							}
						}
						hereAKeyWord(position);
						hereAField(position);
						g.setColor(Color.black);
						if(linecomment  || multilinecomment)
							g.setColor(new Color(63,127,95));
						if(string || fieldbool){
							g.setColor(new Color(0,0,192));
						}
						if(keybool){
							g.setColor(new Color(149,0,85));
						}
						g.drawString(contenu.substring(position, position + 1), x, y);
						if(position < contenu.length() - 2){
							if((int)contenu.charAt(position) != 10 && (int)contenu.charAt(position) != 13)
								x+= g.getFont().getWidth(contenu.substring(position, position + 1));
							else if((int)contenu.charAt(position) == 10){
								x = 0;
								y += g.getFont().getLineHeight();
								lineNumber++;
								drawLineNumber(g, x, y, lineNumber);
								linecomment = false;
							}
							else if((int)contenu.charAt(position) == 13){
								x+= g.getFont().getWidth(" ") * 4;
								if(x > getSizeX()){
									x = 0;
									y += g.getFont().getLineHeight();
									lineNumber++;
									drawLineNumber(g, x, y, lineNumber);
									linecomment = false;
								}
							}
						}
						else{
							if((int)contenu.charAt(position) != 10 && (int)contenu.charAt(position) != 13)
								x+= g.getFont().getWidth(contenu.substring(position, position + 1));
							else if(contenu.charAt(position) == (char)10){
								x = 0;
								y += g.getFont().getLineHeight();
								lineNumber++;
								drawLineNumber(g, x, y, lineNumber);
								linecomment = false;
							}
							else if(contenu.charAt(position) == (char)13){
								x+= g.getFont().getWidth(" ") * 4;
								if(x > getSizeX()){
									x = 0;
									y += g.getFont().getLineHeight();
									lineNumber++;
									drawLineNumber(g, x, y, lineNumber);
									linecomment = false;
								}
							}
						}
						if(position == cursor - 1 && cursorEnable){
							g.drawLine(x, y, x, y + g.getFont().getLineHeight());
							textX = + x + g.getFont().getWidth(contenu.substring(position, position+1)) - getSizeX();
							if(textX < 0)
								textX = 0;
						}
						position ++;
					}
					if(contenu.length() == 0 && cursorEnable){
						g.drawLine(x + 1, y, x, y + g.getFont().getLineHeight());
					}
					y += g.getFont().getLineHeight();
					if(y - textY < getSizeY())
						textY = y - getSizeY();
					if(y < getSizeY())
						textY = 0;
			}
			
			g.translate(ancientX, ancientY);
			
			drawScrollBar(g, y);
		g.translate(-getX(), -getY());
	}
	@Override
	public void keyReleased(int key, char c) {
		if(lastKey == c)
			lastKey = Character.UNASSIGNED;
		
		
	}
	public void keyPressed(int key, char c){
		if(key == Input.KEY_ENTER){
			contenu = contenu.substring(0, cursor) + (char)10 +  contenu.substring(cursor, contenu.length());	
			cursor++;
		}
		else if(key == Input.KEY_TAB){
			contenu = contenu.substring(0, cursor) + (char)13 +  contenu.substring(cursor, contenu.length());	
			cursor++;
		}
		else if(key == Input.KEY_LSHIFT || key == Input.KEY_RSHIFT
				|| key == Input.KEY_LALT || key == Input.KEY_RALT){
			
		}
		else if(key == Input.KEY_LCONTROL || key == Input.KEY_RCONTROL)
			control = true;
		else{
			if(c != lastKey){
				lastKey = c;
				checkCopyPast(key,c);
				String c2 = "";
				c2 += c;
				if(Character.isDefined(c) && key != Input.KEY_BACK){
					if(contenu != null)
					contenu = contenu.substring(0, cursor) + c +  contenu.substring(cursor, contenu.length());
					else
						contenu = c2;
					cursor++;
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
			if(key == Input.KEY_BACK){
				if(cursor != 0){
					if(cursor != contenu.length()){
						if(contenu.substring(cursor - 1, cursor).equals("\"") && contenu.substring(cursor, cursor + 1).equals("\"")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("'") && contenu.substring(cursor, cursor + 1).equals("'")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("(") && contenu.substring(cursor, cursor + 1).equals(")")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("[") && contenu.substring(cursor, cursor + 1).equals("]")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("{") && contenu.substring(cursor, cursor + 1).equals("}")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else{
							cursor--;
							contenu = contenu.substring(0, cursor)  +  contenu.substring(cursor + 1, contenu.length());
						}
					}
					else{
						cursor--;
						contenu = contenu.substring(0, cursor)  +  contenu.substring(cursor + 1, contenu.length());
					}
				}
			}
			
			if(key == Input.KEY_DELETE){
				
				if(cursor != contenu.length()){
					if(cursor > 0){
						if(contenu.substring(cursor - 1, cursor).equals("\"") && contenu.substring(cursor, cursor + 1).equals("\"")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("'") && contenu.substring(cursor, cursor + 1).equals("'")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("(") && contenu.substring(cursor, cursor + 1).equals(")")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("[") && contenu.substring(cursor, cursor + 1).equals("]")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else if(contenu.substring(cursor - 1, cursor).equals("{") && contenu.substring(cursor, cursor + 1).equals("}")){
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor + 1, contenu.length());
							cursor--;
						}
						else {
							contenu = contenu.substring(0, cursor - 1)  +  contenu.substring(cursor, contenu.length());
						}
					}
					else {
						contenu = contenu.substring(cursor + 1, contenu.length());
					}
				}
			}
			if(c == '"'){
				contenu = contenu.substring(0, cursor) + "\"" +  contenu.substring(cursor, contenu.length());
				cursor++;
			}
			else if(c == '\''){
				contenu = contenu.substring(0, cursor) + "'" +  contenu.substring(cursor, contenu.length());
				cursor++;
			}
			else if(c == '('){
				contenu = contenu.substring(0, cursor) + ")" +  contenu.substring(cursor, contenu.length());
				cursor++;
			}
			else if(c == '['){
				contenu = contenu.substring(0, cursor) + "]" +  contenu.substring(cursor, contenu.length());
				cursor++;
			}
			else if(c == '{'){
				contenu = contenu.substring(0, cursor) + "}" +  contenu.substring(cursor, contenu.length());
				cursor++;
			}
		}
	}
}
