package gui.editeurs;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;

public class AnimationReader {
	
	public static final String ANIMATION_FOLDER = "Animations/";
	public static void executeAnimation(String path, ObjetMap obj, long time){
	
		try{
		//Chargement du fichier.
			File fXmlFile = new File(ANIMATION_FOLDER + path);
			
			//Si le fichier existe : 
			if(fXmlFile.exists())
			{
				
				//On charge le lecteur XML
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;
				
					dBuilder = dbFactory.newDocumentBuilder();
				
				//On converti le document XML
				Document doc = dBuilder.parse(fXmlFile);
				executeAnimation(path, obj, doc, time);
				//On compresse le document
			}
		} catch (ParserConfigurationException e1) {
			
			e1.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public static void executeAnimation(String path, ObjetMap obj, Document doc, long time){
		doc.getDocumentElement().normalize();
		
		
		//Obgenir le noeud des textures
		Node textureAtlas = doc.getFirstChild();
		
		//Obtenir chaque mouvements.
		NodeList sprites = textureAtlas.getChildNodes();
		
		//Chargement de chaque mouvements.
		for(int i = 0, c1 = sprites.getLength(); i < c1; i++){
			//Si c'est bien un mouvements
			if(sprites.item(i).getNodeName().equals("move")){ //$NON-NLS-1$
				//On créé un accès rapide aux attributs.
				NamedNodeMap attributes = sprites.item(i).getAttributes();
				
				//On enregistre le nom.
				String nom = attributes.getNamedItem("name").getTextContent(); //$NON-NLS-1$
				
				
				//On enregistre la position x
				float x = Float.parseFloat(attributes.getNamedItem("x").getTextContent()); //$NON-NLS-1$
				
				//On enregistre la pos Y
				float y = Float.parseFloat(sprites.item(i).getAttributes().getNamedItem("y").getTextContent()); //$NON-NLS-1$
				
				//Saving beginning frame.
				long beginning = Long.parseLong(sprites.item(i).getAttributes().getNamedItem("begin").getTextContent()); //$NON-NLS-1$
				
				//Saving duration
				long duration = Long.parseLong(sprites.item(i).getAttributes().getNamedItem("duration").getTextContent()); //$NON-NLS-1$
				
				//Création d'un nouvel objet avec les données récupérées.
			//	obj.getImage(nom).move(x, y);
			}
		}
			
	}
}
