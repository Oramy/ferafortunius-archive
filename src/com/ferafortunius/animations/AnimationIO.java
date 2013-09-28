package com.ferafortunius.animations;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ObjetMap.Animation;

public class AnimationIO {
	
	public static final String ANIMATION_FOLDER = "data/Animations/";
	
	public static boolean OVER_WRITING = false;
	public static void writeAnimation(String path, Animation animation){
		try {
		//Chargement du fichier.
			File fXmlFile = new File(ANIMATION_FOLDER + path);
			
			//Si le fichier existe : 
			if(fXmlFile.exists())
			{
				fXmlFile.delete();
			}
			fXmlFile.createNewFile();
			//On charge le lecteur XML
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			
				dBuilder = dbFactory.newDocumentBuilder();
			
			//On converti le document XML
			Document doc = dBuilder.newDocument();
			
			//Creation de l'animation
			Node animationNode = doc.createElement("animation");
			
			doc.appendChild(animationNode);
			
			
			//Creation des paramètres généraux
			Element parameters = doc.createElement("parameters");
			
			Attr name = doc.createAttribute("name");
			name.setNodeValue(animation.getNom());
			Attr framesSize = doc.createAttribute("framesSize");
			framesSize.setNodeValue(animation.getFramesSize() + "");
			
			parameters.setAttributeNode(name);
			parameters.setAttributeNode(framesSize);
			
			animationNode.appendChild(parameters);
			//Ecriture des mouvements
			
			for(AnimationKey key: animation.getKeys()){
				if(key instanceof Move){
					Move move = (Move)key;
					//Creation des paramètres généraux
					Element moveNode = doc.createElement("move");
					
					Attr alias = doc.createAttribute("alias");
					alias.setNodeValue(move.getImageAlias());
					
					Attr beginning = doc.createAttribute("begin");
					beginning.setNodeValue(move.getBeginning() + "");
					
					Attr duration = doc.createAttribute("duration");
					duration.setNodeValue(move.getDuration() + "");
					
					Attr x = doc.createAttribute("x");
					x.setNodeValue(move.x + "");
					Attr y = doc.createAttribute("y");
					y.setNodeValue(move.y + "");
					Attr rotation = doc.createAttribute("rotation");
					rotation.setNodeValue(move.rotation + "");
					
					moveNode.setAttributeNode(alias);
					moveNode.setAttributeNode(beginning);
					moveNode.setAttributeNode(duration);
					moveNode.setAttributeNode(x);
					moveNode.setAttributeNode(y);
					moveNode.setAttributeNode(rotation);
					
					animationNode.appendChild(moveNode);
				}
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			 transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			   transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ANIMATION_FOLDER + path));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Animation loadAnimation(String path){
	
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
				return loadAnimation(path, doc);
				//On compresse le document
			}
		} catch (ParserConfigurationException e1) {
			
			e1.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	public static Animation loadAnimation(String path, Document doc){
		doc.getDocumentElement().normalize();
		
		
		//Obgenir le noeud des textures
		Node animation = doc.getFirstChild();
		
		//Obtenir chaque mouvements.
		NodeList sprites = animation.getChildNodes();
		
		Animation a = new Animation("", 0);
		
		if(sprites.item(1).getNodeName().equals("parameters")){
			//On créé un accès rapide aux attributs
			NamedNodeMap attributes = sprites.item(1).getAttributes();
			
			//On enregistre le nom.
			String nom = attributes.getNamedItem("name").getTextContent(); //$NON-NLS-1$
			
			int framesSize = Integer.valueOf(attributes.getNamedItem("framesSize").getTextContent());
			
			a  = new Animation(nom, framesSize);
		}
		//Chargement de chaque mouvements.
		for(int i = 0, c1 = sprites.getLength(); i < c1; i++){
			
			
			//Si c'est bien un mouvements
			if(sprites.item(i).getNodeName().equals("move")){ //$NON-NLS-1$
				//On créé un accès rapide aux attributs.
				NamedNodeMap attributes = sprites.item(i).getAttributes();
				
				String alias = attributes.getNamedItem("alias").getTextContent();
				
				float rotation = Float.parseFloat(attributes.getNamedItem("rotation").getTextContent()); //$NON-NLS-1$
				//On enregistre la position x
				float x = Float.parseFloat(attributes.getNamedItem("x").getTextContent()); //$NON-NLS-1$
				
				//On enregistre la pos Y
				float y = Float.parseFloat(sprites.item(i).getAttributes().getNamedItem("y").getTextContent()); //$NON-NLS-1$
				
				//Saving beginning frame.
				long beginning = Long.parseLong(sprites.item(i).getAttributes().getNamedItem("begin").getTextContent()); //$NON-NLS-1$
				
				//Saving duration
				long duration = Long.parseLong(sprites.item(i).getAttributes().getNamedItem("duration").getTextContent()); //$NON-NLS-1$
				
				//Création d'un nouvel objet avec les données récupérées.
				a.getKeys().add(new Move(beginning, duration, x,y, rotation, alias));
			}
		}
		return a;	
	}
}
