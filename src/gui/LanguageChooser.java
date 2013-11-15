package gui;

import java.util.Locale;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import Level.OptionsJeuLoader;

public class LanguageChooser extends Container{
	private static PImage frenchFlag = new PImage("GUI/LanguageGUI/frenchFlag.png");
	private static PImage englishFlag = new PImage("GUI/LanguageGUI/englishFlag.png");
	private static PImage germanFlag = new PImage("GUI/LanguageGUI/germanFlag.png");
	private static PImage languageBar = new PImage("GUI/LanguageGUI/languageBar.png");
	
	private int[] xFlags;
	private int[] yFlags;

	private String[] languages;
	public LanguageChooser(int x, int y, Container parent){
		super(x, y, languageBar.getImg().getWidth() * 2, languageBar.getImg().getHeight(), parent);
		this.setBackground(Container.alpha);
		
		xFlags = new int[3];
		yFlags = new int[3];
		languages = new String[3];
		for(int i = 0, c = 3; i < c; i++){
			xFlags[i] = 0;
			yFlags[i] = 0;
		}
		languages[0] = "";
		languages[1] = "en";
		languages[2] = "de";
	}
	@Override
	public void draw(Graphics g){
		g.translate(x + sizeX / 2, y);
			languageBar.getImg().draw(0, 0);
			
			g.translate(xFlags[0], 0);
				frenchFlag.getImg().draw(11, 90);
			g.translate(-xFlags[0], 0);
			
			g.translate(xFlags[1], 0);
				englishFlag.getImg().draw(11, 90 + frenchFlag.getImg().getHeight() - 35);
			g.translate(-xFlags[1], 0);
			
			g.translate(xFlags[2], 0);
				germanFlag.getImg().draw(11, 90 + frenchFlag.getImg().getHeight() * 2 - 70);
			g.translate(-xFlags[2], 0);
		g.translate(-x - sizeX / 2, -y);
	}
	@Override
	public void update(GameContainer gc, int x, int y)
	{
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		
		Rectangle buttons[] = new Rectangle[3];
		for(int i = 0, c = 3; i < c; i++){
			buttons[i] = new Rectangle(this.getXOnScreen() + 11 + sizeX / 2,
					this.getYOnScreen() + 90 + (frenchFlag.getImg().getHeight() - 35) * i, frenchFlag.getImg().getWidth(), frenchFlag.getImg().getHeight() - 35);
			if(buttons[i].contains(mouseX, mouseY)){
				xFlags[i]-= 5;
				if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					GameMain.options.setLanguage(new Locale(languages[i]));
					OptionsJeuLoader.saveOptions(GameMain.options);
				}
			}
			else
				xFlags[i] += 5;
			if(xFlags[i] < -45)
				xFlags[i] = -45;
			if(xFlags[i] > 0)
				xFlags[i] = 0;
			
		}
	}
}
