package gui.jeu;

import gui.Container;
import gui.GameMain;
import gui.PImage;
import gui.Shader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import Level.ArrayIterator;
import Level.Camera;
import Level.Chunk;
import Level.ChunkMap;
import Level.Iterator;
import Level.LayeredChunkMap;
import ObjetMap.BasicObjetMap;
import ObjetMap.CollisionBlock;
import ObjetMap.CylinderBlock;
import ObjetMap.Entity;
import ObjetMap.LosangeBlock;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;



public class PanneauJeuAmeliore extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7556556011384024293L;
	public static ArrayList<PImage> img= new ArrayList<PImage>();
	protected Camera actualCam;
	protected ChunkMap carte;
	protected Color chunkcolor;
	private boolean surlign;
	protected ObjetMap surlignObject;
	protected Animation a;
	protected int debugMode;
	
	private int surlignPercent;
	
	private Shader blackWhite;
	public static void loadFolder(String path){
		File[] files = null;
		File directory = new File(path);
		files = directory.listFiles();
		Arrays.sort(files);
		for(int i = 0; i < files.length; i++){
			File file = files[i];
			if(file.isDirectory())
				loadFolder(file.getPath());
			else if(file.isFile() && file.getName().contains(".png")){
				img.add(new PImage(file.getPath().substring(7).replaceAll("\\\\", "/")));
			}
		}
	}
	public void clearCache(){
		img.clear();
	}
	public void loadMapImages(){
		if(carte != null){
			for(int i = 0; i < carte.getMapSizeX(); i++){
				for(int j = 0; j < carte.getMapSizeY(); j++){
					for(int k = 0; k < carte.getMapSizeZ(); k++){
						for(ObjetMap o : carte.getChunks()[i][j][k].getContenu()){
							for(ObjetImage img : o.getImage()){
								loadImage(img, o);
							}
						}
					}
				}
			}
		}
	}
	public PanneauJeuAmeliore(ChunkMap c, int x, int y , int sizeX, int sizeY, Container parent) {
		super(x,y,sizeX, sizeY, parent);
		carte = c;
		chunkcolor = Color.white;
		actualCam = new Camera(0,0, 2f, c);
		this.setBounds(x,y, sizeX, sizeY);
		a = new Animation();
		debugMode = 0;
		
		try {
			blackWhite = Shader.makeShader("data/Shaders/blackWhite.vert", "data/Shaders/blackWhite.frag");	
			blackWhite.setUniformFVariable("offset", 1f);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
	}
	public void update(GameContainer gc, int x, int y ){
		super.update(gc, x, y);
		actualCam.update();
		if(gc.getInput().isKeyPressed(Input.KEY_F1))
			debugMode = (debugMode + 1)%3;
	}
	public static PImage loadImage(ObjetImage objetImg, ObjetMap o){
		PImage imgToDraw = null;
		boolean loadImage = false;
		
		//Chargement de l'id de l'image enregistr?7
		if(img.size() > objetImg.getImageID()){
			PImage imageIt = img.get(objetImg.getImageID());
			if(imageIt.getNom().equals(objetImg.getImage())){
				loadImage = true;
				imgToDraw = imageIt;
			}
		}
		//Si l'image n'a pas ?t? charg?e.
		if(!loadImage){
			
			boolean inList = false;
			for (int l = 0, l2 = img.size(); l < l2; l++) {
				if (img.get(l).getNom().equals(objetImg.getImage())) {
					o.setId(l);
					imgToDraw = img.get(l);
					inList = true;
					l = l2;
				}
			}
			if(!inList){
				PImage toload = new PImage(objetImg.getImage());
				img.add(toload);
				o.setId(img.size() - 1);
				imgToDraw = toload;
			}
		}
		return imgToDraw;
	}
	public void drawObject(Graphics g, ObjetMap o, boolean lines, boolean checkScreen){
		if(o != null){
			o.setDrawing(true);
			//Affichage des images
			//Initialisation d'un iterateur
			Iterator it2 =  o.getImagesIterator();
			ObjetImage objetImg = null;
			//Affichage des images
			translateToObject(g, o);
			do{
				objetImg = (ObjetImage) it2.getNextElement();
				//Si l'objet doit ?tre affich?
				if(objetImg != null)
				{
						
					Image wildImg = loadImage(objetImg, o).getImg();
					Image image = null;
					if(wildImg != null){
						Image formatImage = wildImg.getSubImage(objetImg.getPosSpriteSheetX(), objetImg.getPosSpriteSheetY(), wildImg.getWidth(), wildImg.getHeight());
						
						SpriteSheet sprite = new SpriteSheet(formatImage, objetImg.getSizeSpriteX(),  objetImg.getSizeSpriteY());
						
						image = sprite.getSprite( objetImg.getPosX(),  objetImg.getPosY());
						image.setAlpha(o.getOpacity());
						
						if(objetImg.isMirror()){
							image = image.getFlippedCopy(true, false);
						}
					}
					
					drawObjectImage(g, o, objetImg, image, checkScreen);
						
				}
				
				
			}while(objetImg != null);
			if(o.getImage().size() == 0){
				objetImg = new ObjetImage("ObjetMap/quitter.png", 20,20, 50, 50, 0,0);
				PImage imgToDraw = loadImage(objetImg, o);
				
				Image wildImg = imgToDraw.getImg();
				Image image =  wildImg;
				image.setAlpha(o.getOpacity());
				
				if(objetImg.isMirror()){
					image = image.getFlippedCopy(true, false);
					g.translate(image.getHeight(), 0);
					this.untranslateToObjectImage(g, o, objetImg);
				}
				
				drawObjectImage(g, o, objetImg, image, checkScreen);
				
				if(objetImg.isMirror()){
					this.translateToObjectImage(g, o, objetImg);
					g.translate(-image.getHeight(), 0);
					
				}
			}
			if(lines){
				drawLines(g, o);
			}
			if(o instanceof Entity)
				((Entity) o).paintBonus(this, g, actualCam);
			untranslateToObject(g, o);
		}
		
	}
	public void drawCollisionBlock(Graphics g, CollisionBlock b){
		g.setColor(new Color(0, 255, 0, 130));
		//Formule utilis?e :  pointX = -sqrt((y+sy)^2)+sqrt((x+sx)^2)
		float point1X = (float) (-(Math.sqrt(Math.pow(b.getPosY() + b.getSizeY(), 2)))* actualCam.getZoom() 
				+ (b.getPosX()) * actualCam.getZoom());
		
		float point1Y = (float) (-(Math.sqrt(Math.pow(b.getPosY() + b.getSizeY(), 2))) * actualCam.getZoom() / 2 
				- (b.getPosX()) * actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom();
		
		float point2X = (float) (+(b.getPosX()) * actualCam.getZoom()
				- (b.getPosY())* actualCam.getZoom());
		
		float point2Y = (float) (-(b.getPosX()) * actualCam.getZoom() / 2 
				- (b.getPosY()) * actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom(); 
		
		float point3X = (float) (+(Math.sqrt(Math.pow(b.getPosX() + b.getSizeX(), 2))) * actualCam.getZoom() 
				- (b.getPosY())* actualCam.getZoom());
		
		float point3Y = (float) (-(Math.sqrt(Math.pow(b.getPosX() +  b.getSizeX(), 2))) * actualCam.getZoom() / 2 
				- (b.getPosY())* actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom();
		
		float point4X = (float) (+(Math.sqrt(Math.pow(b.getPosX() +b.getSizeX(), 2))) * actualCam.getZoom()
				- (Math.sqrt(Math.pow(b.getPosY() +b.getSizeY(), 2)))* actualCam.getZoom());
		
		float point4Y = (float) (-(Math.sqrt(Math.pow(b.getPosX() +b.getSizeX(), 2))) * actualCam.getZoom() / 2
				- (Math.sqrt(Math.pow(b.getPosY() +b.getSizeY(), 2))) * actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom();
		Polygon p3 = new Polygon();
		p3.addPoint(point1X, point1Y);
		p3.addPoint(point2X, point2Y);
		p3.addPoint(point3X, point3Y);
		p3.addPoint(point4X, point4Y);
		g.draw(p3);
		g.setColor(new Color(0, 0, 255, 130));
		g.translate(0, -b.getSizeZ() * actualCam.getZoom());
		g.draw(p3);
		g.translate(0, b.getSizeZ() * actualCam.getZoom());
		
		g.setColor(new Color(255, 0, 0, 130));
		g.drawLine(point1X, point1Y, point1X, point1Y -b.getSizeZ() * actualCam.getZoom());
		g.drawLine(point2X, point2Y, point2X, point2Y -b.getSizeZ() * actualCam.getZoom());
		g.drawLine(point3X, point3Y, point3X, point3Y -b.getSizeZ() * actualCam.getZoom());
	}
	public void drawLosangeBlock(Graphics g,LosangeBlock b){
		g.setColor(new Color(0, 255, 0, 130));
		//Formule utilis?e :  pointX = -sqrt((y+sy)^2)+sqrt((x+sx)^2)
		float point1X = (float) (-(Math.sqrt(Math.pow(b.getPosY() + b.getSizeY(), 2)))* actualCam.getZoom() 
				+ (b.getPosX()) * actualCam.getZoom());
		
		float point1Y = (float) (-(Math.sqrt(Math.pow(b.getPosY() + b.getSizeY(), 2))) * actualCam.getZoom() / 2 
				- (b.getPosX()) * actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom();
		
		float point2X = (float) (+(b.getPosX()) * actualCam.getZoom()
				- (b.getPosY())* actualCam.getZoom());
		
		float point2Y = (float) (-(b.getPosX()) * actualCam.getZoom() / 2 
				- (b.getPosY()) * actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom(); 
		
		float point3X = (float) (+(Math.sqrt(Math.pow(b.getPosX() + b.getSizeX(), 2))) * actualCam.getZoom() 
				- (b.getPosY())* actualCam.getZoom());
		
		float point3Y = (float) (-(Math.sqrt(Math.pow(b.getPosX() +  b.getSizeX(), 2))) * actualCam.getZoom() / 2 
				- (b.getPosY())* actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom();
		
		float point4X = (float) (+(Math.sqrt(Math.pow(b.getPosX() +b.getSizeX(), 2))) * actualCam.getZoom()
				- (Math.sqrt(Math.pow(b.getPosY() +b.getSizeY(), 2)))* actualCam.getZoom());
		
		float point4Y = (float) (-(Math.sqrt(Math.pow(b.getPosX() +b.getSizeX(), 2))) * actualCam.getZoom() / 2
				- (Math.sqrt(Math.pow(b.getPosY() +b.getSizeY(), 2))) * actualCam.getZoom() / 2)
				-(float)b.getPosZ() * actualCam.getZoom();
		
		Polygon p3 = new Polygon();
		
		float losangePoint1X = point1X + ((point2X - point1X) / 2);
		float losangePoint1Y = point1Y + ((point2Y - point1Y) / 2);
		
		float losangePoint2X = point2X + ((point3X - point2X) / 2);
		float losangePoint2Y = point2Y + ((point3Y - point2Y) / 2);
		
		float losangePoint3X = point3X + ((point4X - point3X) / 2);
		float losangePoint3Y = point3Y + ((point4Y - point3Y) / 2);
		
		float losangePoint4X = point4X + ((point1X - point4X) / 2);
		float losangePoint4Y = point4Y + ((point1Y - point4Y) / 2);
		
		p3.addPoint(losangePoint1X, losangePoint1Y);
		p3.addPoint(losangePoint2X, losangePoint2Y);
		p3.addPoint(losangePoint3X, losangePoint3Y);
		p3.addPoint(losangePoint4X, losangePoint4Y);
		g.draw(p3);
		g.setColor(new Color(0, 0, 255, 130));
		g.translate(0, -b.getSizeZ() * actualCam.getZoom());
		g.draw(p3);
		g.translate(0, b.getSizeZ() * actualCam.getZoom());
		
		g.setColor(new Color(255, 0, 0, 130));
		g.drawLine(losangePoint1X, losangePoint1Y, losangePoint1X, losangePoint1Y -b.getSizeZ() * actualCam.getZoom());
		g.drawLine(losangePoint2X, losangePoint2Y, losangePoint2X, losangePoint2Y -b.getSizeZ() * actualCam.getZoom());
		g.drawLine(losangePoint3X, losangePoint3Y, losangePoint3X, losangePoint3Y -b.getSizeZ() * actualCam.getZoom());
	}
	public void drawCylinderBlock(Graphics g,CylinderBlock b){
		g.setColor(new Color(0, 255, 0, 130));
		//Formule utilis?e :  pointX = -sqrt((y+sy)^2)+sqrt((x+sx)^2)
		
		Shape p3 = b.getShape(new BasicObjetMap(0,0,0,0,0,0));
		
		g.scale(actualCam.getZoom(), actualCam.getZoom() / 2);
		
		g.translate(-p3.getBoundingCircleRadius(), -p3.getBoundingCircleRadius() * 2);
		g.draw(p3);
		g.setColor(new Color(0, 0, 255, 130));
		g.translate(0, -b.getSizeZ());
		g.draw(p3);
		g.translate(0, b.getSizeZ());
		g.translate(p3.getBoundingCircleRadius(), p3.getBoundingCircleRadius() * 2);
		
		g.scale(1/actualCam.getZoom(), 1/(actualCam.getZoom() / 2));
		
	}
	public void drawLines(Graphics g, ObjetMap o){
		for(int i = 0; i < o.getCollision().size(); i++){
			CollisionBlock b = o.getCollision().get(i);
			if(b instanceof LosangeBlock)
				drawLosangeBlock(g,(LosangeBlock) b);
			else if(b instanceof CylinderBlock)
				drawCylinderBlock(g,(CylinderBlock) b);
			else
				drawCollisionBlock(g,b);
		}
	}
	public void drawMapLines(Graphics g){
		g.setColor(new Color(255,255,255, 90));
		Chunk chunk = carte.getChunk(0, 0, 0);
		
		if(!(chunk instanceof LayeredChunkMap)){
			for(int i = 0; i < carte.getMapSizeX(); i++){
				for(int j = 0; j < carte.getMapSizeX(); j++){
					for(int k = 0; k < carte.getMapSizeX(); k++){
						Polygon p = new Polygon();
						p.addPoint((+ ( + i * carte.getChunksSize() - (int)(j * carte.getChunksSize()))) * actualCam.getZoom(),
								((int)-(j * carte.getChunksSize() * 0.5) - (int)(i * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
						p.addPoint(( + ( + (i + 1) * carte.getChunksSize() - (int)(j * carte.getChunksSize()))) * actualCam.getZoom(),
								((int)-(j * carte.getChunksSize() * 0.5)-(int)((i + 1) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
						p.addPoint(( + ( + (i + 1) * carte.getChunksSize() - (int)((j  + 1)* carte.getChunksSize()))) * actualCam.getZoom(),
								((int)(-(j + 1)* carte.getChunksSize() * 0.5) - (int)((i + 1) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
						p.addPoint(( + ( + (i) * carte.getChunksSize() + (int)(-(j  + 1)* carte.getChunksSize()))) * actualCam.getZoom(),
								((int)(-(j + 1)* carte.getChunksSize() * 0.5) - (int)((i) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
						g.draw(p);
					}
				}
			}
		}else{
			LayeredChunkMap layeredChunk = (LayeredChunkMap)chunk;
			//Dessinons les layers.
			Random r = new Random();
			g.setColor(new Color(0f, 0f, 1f));
			g.drawLine(0, 0, 0, -layeredChunk.getLayer(0) * actualCam.getZoom());
			
			for(int i = 0;  i < layeredChunk.getLayersCount() - 1; i++){
				if(i % 3 == 0)
					g.setColor(new Color(1f, 0f, 0f));
				else if(i % 3== 1)
					g.setColor(new Color(0f, 1f, 0f));
				else if(i % 3== 2)
					g.setColor(new Color(0f, 0f, 1f));
				g.drawLine(0, -layeredChunk.getLayer(i) * actualCam.getZoom(), 0, -layeredChunk.getLayer(i+1) * actualCam.getZoom());
			}
			if((layeredChunk.getLayersCount()-1) % 3 == 0)
				g.setColor(new Color(1f, 0f, 0f));
			else if((layeredChunk.getLayersCount() - 1)% 3== 1)
				g.setColor(new Color(0f, 1f, 0f));
			else if((layeredChunk.getLayersCount() - 1) % 3== 2)
				g.setColor(new Color(0f, 0f, 1f));
			
			g.drawLine(0, -layeredChunk.getLayer(layeredChunk.getLayersCount()-1) * actualCam.getZoom(),0, -layeredChunk.getSizeZ() * actualCam.getZoom());
		}
	}
	public void translateToObject(Graphics g, ObjetMap o){
		int i = o.getChunkX();
		int j = o.getChunkY();
		int k = o.getChunkZ();
		g.translate((float)(( + (float)(i * carte.getChunksSize()) - (float)(j * carte.getChunksSize()))
				+ ( + (float)(o.getPosX()) -  (float)(o.getPosY()))) * actualCam.getZoom(),

				(float)((float)-(j * carte.getChunksSize() * 0.5) - (float)(i * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()
						- (float)(o.getPosY() * 0.5) - (float)(o.getPosX() * 0.5)  - (float)(o.getPosZ())) * actualCam.getZoom());
		
	}
	public void untranslateToObject(Graphics g, ObjetMap o){
		int i = o.getChunkX();
		int j = o.getChunkY();
		int k = o.getChunkZ();
		g.translate(-(float)(( + (float)(i * carte.getChunksSize()) - (float)(j * carte.getChunksSize()))
				+ ( + (float)(o.getPosX()) -  (float)(o.getPosY()))) * actualCam.getZoom(),

				-(float)((float)-(j * carte.getChunksSize() * 0.5) - (float)(i * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()
						- (float)(o.getPosY() * 0.5) - (float)(o.getPosX() * 0.5)  - (float)(o.getPosZ())) * actualCam.getZoom());
		
	}
	public void translateToObjectImage(Graphics g, ObjetMap o, ObjetImage objetImg){
		if(objetImg.getParentAlias() != null){
			if(o.getImage(objetImg.getParentAlias()) != null){
				parentTranslateToObjectImage(g, o, o.getImage(objetImg.getParentAlias()));
			}
		}
		g.translate((float)(+objetImg.getDecalageX() - (float)(objetImg.getImageSizeInGameX() / 2)) * actualCam.getZoom(),
				(float)( +objetImg.getDecalageY() - (float)(objetImg.getImageSizeInGameY())) * actualCam.getZoom());
		
	}
	public void parentTranslateToObjectImage(Graphics g, ObjetMap o, ObjetImage objetImg){
		if(objetImg.getParentAlias() != null){
			if(o.getImage(objetImg.getParentAlias()) != null){
				parentTranslateToObjectImage(g, o, o.getImage(objetImg.getParentAlias()));
			}
		}
		
		g.translate((float)(+objetImg.getDecalageX()) * actualCam.getZoom(),
				(float)( +objetImg.getDecalageY()) * actualCam.getZoom());
		
		g.translate((- (float)(objetImg.getImageSizeInGameX() / 2) + objetImg.getRotationCenterX()) * actualCam.getZoom(), 
				(- (float)(objetImg.getImageSizeInGameY()) + objetImg.getRotationCenterY()) * actualCam.getZoom());
		g.rotate(1, 1, objetImg.getRotation());
		g.translate(-(- (float)(objetImg.getImageSizeInGameX() / 2) + objetImg.getRotationCenterX()) * actualCam.getZoom(), 
				-(- (float)(objetImg.getImageSizeInGameY()) + objetImg.getRotationCenterY()) * actualCam.getZoom());
		
	}
	public void untranslateToObjectImage(Graphics g, ObjetMap o, ObjetImage objetImg){
		g.translate(-(float)(+objetImg.getDecalageX() - (float)(objetImg.getImageSizeInGameX() / 2)) * actualCam.getZoom(),
				-(float)( +objetImg.getDecalageY() - (float)(objetImg.getImageSizeInGameY())) * actualCam.getZoom());
		
		if(objetImg.getParentAlias() != null){
			if(o.getImage(objetImg.getParentAlias()) != null){
				parentUntranslateToObjectImage(g, o, o.getImage(objetImg.getParentAlias()));
			}
		}
	}
	public void parentUntranslateToObjectImage(Graphics g, ObjetMap o, ObjetImage objetImg){
		g.translate((- (float)(objetImg.getImageSizeInGameX() / 2) + objetImg.getRotationCenterX()) * actualCam.getZoom(), 
				(- (float)(objetImg.getImageSizeInGameY()) + objetImg.getRotationCenterY()) * actualCam.getZoom());
		g.rotate(1, 1, -objetImg.getRotation());
		g.translate(-(- (float)(objetImg.getImageSizeInGameX() / 2) + objetImg.getRotationCenterX()) * actualCam.getZoom(), 
				-(- (float)(objetImg.getImageSizeInGameY()) + objetImg.getRotationCenterY()) * actualCam.getZoom());
		
		g.translate(-(float)(+objetImg.getDecalageX()) * actualCam.getZoom(),
				-(float)( +objetImg.getDecalageY()) * actualCam.getZoom());
		
		if(objetImg.getParentAlias() != null){
			if(o.getImage(objetImg.getParentAlias()) != null){
				parentUntranslateToObjectImage(g, o, o.getImage(objetImg.getParentAlias()));
			}
		}
		
	}
	public void drawObjectImage(Graphics g, ObjetMap o, ObjetImage objetImg, Image image, boolean checkScreen){
		int i = o.getChunkX();
		int j = o.getChunkY();
		int k = o.getChunkZ();
		
		//Calcul des points limites d'affichage en X et Y :
		int affichX = (int) ((int)(( + i * carte.getChunksSize() - (int)(j * carte.getChunksSize()))
				+ ( + o.getPosX() -  o.getPosY()+ (float)(objetImg.getImageSizeInGameX() / 2))) * actualCam.getZoom() + this.getWidth() / 2 -actualCam.getX() * actualCam.getZoom());
		
		int affichY = (int) ((int)((int)-(j * carte.getChunksSize() * 0.5) - (int)(i * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()
				- (int)(o.getPosY() * 0.5) - (int)(o.getPosX() * 0.5)  - o.getPosZ()) * actualCam.getZoom() + this.getHeight()/2  -actualCam.getY() * actualCam.getZoom());
		
		int posX = affichX +(int)((objetImg.getDecalageX() - (float)(objetImg.getImageSizeInGameX())) * actualCam.getZoom());
		int posY  = affichY + (int)((objetImg.getDecalageY()- (float)(objetImg.getImageSizeInGameY())) * actualCam.getZoom());
		
		if(!(affichX < 0 || posX  >= this.getWidth() || affichY < 0 || posY >= this.getHeight()) || !checkScreen){
			translateToObjectImage(g, o, objetImg);
				o.paintComponent(this, g, image, posX, posY, objetImg, actualCam);
			untranslateToObjectImage(g, o, objetImg);
		}
	}
	public void draw(Graphics g) {
		blackWhite.startShader();
		surlignPercent = 0;
		long temps = System.currentTimeMillis();
		float xcam =  (actualCam.getX() * actualCam.getZoom());
		float ycam =  (actualCam.getY() * actualCam.getZoom());
		//Cr?ation du fond
		g.setColor(Color.black);
		g.fillRect(getX(), getY(), this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.translate(getX(),getY());
		g.translate(this.getWidth()/2, this.getHeight()/2);
		g.translate(-xcam, -ycam);
		
		//initialisation des variables pour les calculs de positions.
		float transX = this.getWidth() / 2 +(-actualCam.getX() * actualCam.getZoom());
		float transY = this.getHeight() / 2 + (-actualCam.getY() * actualCam.getZoom());
		int chunkX = (int)(( - transX - transY - carte.getChunksSize() / 2) / (carte.getChunksSize() *  actualCam.getZoom()));
		int chunkY = (int)((+transX - transY - carte.getChunksSize() / 2) / (carte.getChunksSize() *  actualCam.getZoom()));
		if(this.getRacine() instanceof Jeu){
			chunkX = ((Jeu) this.getRacine()).getPlayer().getChunkX();
			chunkY = ((Jeu) this.getRacine()).getPlayer().getChunkY();
		}
		else{
			chunkX = 0;
			chunkY = 0;
		}
		int iTemp = (int)(this.getWidth() / (Math.sqrt(carte.getChunksSize() *  actualCam.getZoom()) / 2));
		int jTemp = (int)(this.getHeight() / (Math.sqrt(carte.getChunksSize() *  actualCam.getZoom()) / 2));
		
		//R?initialisation de l'objet s?l?ctionn?
		setSurlign(false);
		surlignObject = null;
		
		ArrayList<ObjetMap> toShow = new ArrayList<ObjetMap>();
		
		for(int i = iTemp + chunkX, i2 = - iTemp+ chunkX; i > i2; i--){
			for(int j = iTemp + chunkY, j2 = - jTemp + chunkY; j > j2; j--){
				if(i >= 0 && j >= 0 && i < carte.getMapSizeX() && j < carte.getMapSizeY()){
					for (int k = carte.getMapSizeZ()-1, k2 = 0; k >= k2; k--) {
						// Si une partie du chunk est ? l'?cran, on affiche
						if(!((( + (i + 1) * carte.getChunksSize() - (int)((j - 1) * carte.getChunksSize())))* actualCam.getZoom() + this.getWidth()/2  -actualCam.getX() * actualCam.getZoom()<= 0 
								|| ( + (i) * carte.getChunksSize() - (int)((j + 1) * carte.getChunksSize())) * actualCam.getZoom() + this.getWidth()/2  -actualCam.getX() * actualCam.getZoom()>= this.getWidth()
								|| ((-(j + 1) * carte.getChunksSize() * 0.5) - (int)((i + 1) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom() + this.getHeight()/2 -actualCam.getY() * actualCam.getZoom()>= this.getHeight()
								||((-(j) * carte.getChunksSize() * 0.5) - (int)((i) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom() + this.getHeight()/2 -actualCam.getY() * actualCam.getZoom()<= 0)) // && (i < xView || xView == 0) && (j < yView || yView == 0))
						{	
							//carte.getChunks()[i][j][k].trier();
							carte.getChunks()[i][j][k].setDrawing(true);
							g.setColor(chunkcolor); 
							
							Iterator it =  carte.getChunks()[i][j][k].getContenuIterator();
							ObjetMap o = null;
							
							
							
							//Affichage du contenu
							do{
								o = (ObjetMap) it.getNextElement();
								if(this.hasToBeDrawed(o, true)){
									toShow.add(o);
								}
								// Si ce n'est pas fini 
								
							}while(o != null);
							
							//D?limitage des chunks
							/*Polygon p = new Polygon();
							p.addPoint((+ ( + i * carte.getChunksSize() - (int)(j * carte.getChunksSize()))) * actualCam.getZoom(),
									((int)-(j * carte.getChunksSize() * 0.5) - (int)(i * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
							p.addPoint(( + ( + (i + 1) * carte.getChunksSize() - (int)(j * carte.getChunksSize()))) * actualCam.getZoom(),
									((int)-(j * carte.getChunksSize() * 0.5)-(int)((i + 1) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
							p.addPoint(( + ( + (i + 1) * carte.getChunksSize() - (int)((j  + 1)* carte.getChunksSize()))) * actualCam.getZoom(),
									((int)(-(j + 1)* carte.getChunksSize() * 0.5) - (int)((i + 1) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
							p.addPoint(( + ( + (i) * carte.getChunksSize() + (int)(-(j  + 1)* carte.getChunksSize()))) * actualCam.getZoom(),
									((int)(-(j + 1)* carte.getChunksSize() * 0.5) - (int)((i) * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()) * actualCam.getZoom());
							g.draw(p);*/
							//Initialisation de l'iterateur
							
							//System.out.println("Temps d'affichage de map : " + (System.currentTimeMillis() - tempsPrecMap) + "ms");
							//System.out.println(nbobj);
						}
					}
				}
			}
		}
		int id = -1;
		
		int chunkSize = carte.getChunksSize();
		// Tri.
		for (int j1 = 1; j1 < toShow.size(); j1++) {
			ObjetMap cle = toShow.get(j1);
			int i1 = j1 - 1;
			ObjetMap other = toShow.get(i1);
			long distanceA = (long) (Math.pow(cle.getAbsPosZ(chunkSize) + cle.getSizeZ(), 2) + Math.pow(cle.getAbsPosY(chunkSize) + cle.getSizeY(), 2) + Math.pow(cle.getAbsPosX(chunkSize) + cle.getSizeX(), 2));
			long distanceB = (long) (Math.pow(other.getAbsPosZ(chunkSize) + other.getSizeZ(), 2) + Math.pow(other.getAbsPosY(chunkSize) + other.getSizeY(), 2) + Math.pow(other.getAbsPosX(chunkSize) + other.getSizeX(), 2));
			while (i1 > -1
					&& (distanceA > distanceB)) {
				i1--;
				if(i1 > - 1){
				other = toShow.get(i1);
				distanceB = (long) (Math.pow(other.getAbsPosZ(chunkSize) + other.getSizeZ(), 2) + Math.pow(other.getAbsPosY(chunkSize) + other.getSizeY(), 2) + Math.pow(other.getAbsPosX(chunkSize) + other.getSizeX(), 2));
				}
			}
			toShow.remove(cle);
			toShow.add(i1 + 1, cle);
		}
		for (int j1 = 1; j1 < toShow.size(); j1++) {
			ObjetMap cle = toShow.get(j1);
			int i1 = j1 - 1;
			int endPosZ = cle.getAbsPosZ(chunkSize) + cle.getSizeZ();
			int posY = cle.getAbsPosY(chunkSize);
			int posX = cle.getAbsPosX(chunkSize);
			while (i1 > -1
					&& (endPosZ <= toShow.get(i1).getAbsPosZ(chunkSize)
					|| (posY >= toShow.get(i1).getAbsPosY(chunkSize)
					+ toShow.get(i1).getSizeY()) || (posX >= toShow
					.get(i1).getAbsPosX(chunkSize) + toShow.get(i1).getSizeX()))) {
				i1--;
			}
			toShow.remove(cle);
			toShow.add(i1 + 1, cle);
		}
		
		Iterator it1 = new ArrayIterator(toShow);
		ObjetMap o1 = null;
		//Affichage du contenu
		do{
			o1 = (ObjetMap) it1.getNextElement();
			switch(debugMode){
				case 0:
					drawObject(g,o1,false, true);
				break;
				case 1:
					drawObject(g,o1, true, true);
				break;
				case 2:
					if(o1 != null){
						translateToObject(g, o1);
						
						drawLines(g, o1);
						
						untranslateToObject(g, o1);
						
					}
				break;
			}
			// Si ce n'est pas fini 
			
		}while(o1 != null);
		
		if(!(this.parent instanceof Jeu)){
			for(int i = 0; i < carte.getMapSizeX(); i++){
				for(int j = 0; j < carte.getMapSizeY(); j++){
					for(int k = 0; k < carte.getMapSizeZ(); k++){
						Iterator it2 = new ArrayIterator(this.getCarte().getChunk(i,j,k).getTeleporters());
						ObjetMap o2 = null;
						//Affichage du contenu
						do{
							o2 = (ObjetMap) it2.getNextElement();
							switch(debugMode){
								case 0:
									drawObject(g,o2,false, true);
								break;
								case 1:
									drawObject(g,o2, true, true);
								break;
								case 2:
									if(o2 != null){
										translateToObject(g, o2);
										
										drawLines(g, o2);
										
										untranslateToObject(g, o2);
										
									}
								break;
							}
							// Si ce n'est pas fini 
							
						}while(o2 != null);
					}
				}
			}
		}
		/*
		 * D?sactiv? le temps de la version Snapshot MiniMap
		if(this.parent instanceof Jeu){
			((Jeu)(this.parent)).getPlayer().setOpacity(0.5f);
			drawObject(g, ((Jeu)(this.parent)).getPlayer(), debugMode);
			((Jeu)(this.parent)).getPlayer().setOpacity(1f);
			
		}*/
		//this.drawMapLines(g);
		g.translate(xcam, ycam);
		g.translate(-this.getWidth()/2, -this.getHeight()/2);
		g.translate(-getX(),-getY());
		//Affichage de la GUI
		for(int i1 = 0; i1 < this.getComponents().size(); i1++)
			this.getComponents().get(i1).draw(g);
		
		//Si on veut afficher les informations de vitesse
		if(GameMain.options.isGameSpeedPrint())
			System.out.println("Affichage Map : " + (System.currentTimeMillis() - temps) + "ms");
		
		//System.out.println("Temps d'affichage : " + (System.currentTimeMillis() - tempsPrec) + "ms");
		Shader.forceFixedShader();
	}
	private boolean hasToBeDrawed(ObjetMap o, boolean checkScreen) {
		if(o != null){
			int i = o.getChunkX();
			int j = o.getChunkY();
			int k = o.getChunkZ();
			
			int baseX = (int) ((int)(( + i * carte.getChunksSize() - (int)(j * carte.getChunksSize()))
					+ ( + o.getPosX() -  o.getPosY())) * actualCam.getZoom() + this.getWidth() / 2 -actualCam.getX() * actualCam.getZoom());
			
			int baseY = (int) ((int)((int)-(j * carte.getChunksSize() * 0.5) - (int)(i * carte.getChunksSize() * 0.5) - k * carte.getChunksSize()
					- (int)(o.getPosY() * 0.5) - (int)(o.getPosX() * 0.5)  - o.getPosZ()) * actualCam.getZoom() + this.getHeight()/2  -actualCam.getY() * actualCam.getZoom());
			
			//Not an accurate solution, but it's more performant.
			int maxX = (int) (baseX + (o.getSizeY()) * actualCam.getZoom());
			int minX = (int) (baseX - (o.getSizeX()) * actualCam.getZoom());
			
			int maxY = baseY;
			int minY = (int) (baseY - (o.getSizeZ() + o.getSizeX() * 0.5 + o.getSizeY() * 0.5) * actualCam.getZoom());
			
			if(!(maxX < 0 || minX  >= this.getWidth() || maxY < 0 || minY >= this.getHeight()) || !checkScreen){
				return true;
			}
		}
		return false;
	}

	public float getZoom(){
		return actualCam.getZoom();
	}
	/**
	 * @return the actualCam
	 */
	public Camera getActualCam() {
		return actualCam;
	}
	/**
	 * @param actualCam the actualCam to set
	 */
	public void setActualCam(Camera actualCam) {
		this.actualCam = actualCam;
	}

	/**
	 * @return the cartes
	 */
	public ChunkMap getCarte() {
		return carte;
	}

	/**
	 * @param cartes the cartes to set
	 */
	public void setCarte(ChunkMap carte) {
		this.carte = carte;
	}

	/**
	 * @return the surlign
	 */
	public boolean isSurlign() {
		return surlign;
	}

	/**
	 * @param surlign the surlign to set
	 */
	public void setSurlign(boolean surlign) {
		this.surlign = surlign;
	}

	/**
	 * @return the surlignObject
	 */
	public ObjetMap getSurlignObject() {
		return surlignObject;
	}

	/**
	 * @param surlignObject the surlignObject to set
	 */
	public void setSurlignObject(ObjetMap surlignObject) {
		this.surlignObject = surlignObject;
	}

	public int getSurlignPercent() {
		return surlignPercent;
	}

	public void setSurlignPercent(int surlignPercent) {
		this.surlignPercent = surlignPercent;
	}




}
