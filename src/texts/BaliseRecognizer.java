package texts;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import texts.converters.BaliseConverter;
import texts.converters.BaliseConvertersList;

public class BaliseRecognizer {
	
	public static void main(String[] args){
		String text = "Texte<color r=\"0.3f\"; g=\"0.5f\"; b=\"1f\"; a=\"0.5f\"; />text<1>texte2</1><2><text4 text=\"mouha\"; />texte3<3></2>autre</3>";
		ArrayList<Balise> results = recognize(text);
		System.out.println(removeAllBalise(text));
		for(Balise b : results){
				System.out.println(removeAllBalise(text).substring(0, b.beginIndex) + ":" + b.getName());
				BaliseConverter converter = BaliseConvertersList.getBaliseConverter(b.getName());
				if(converter != null){
					Color c = (Color) converter.convert(b);
					System.out.println(c);
				}
		}
	}
	
	/**
	 * Recognize all of the balises in that text. It also removes the balises strings.
	 * @param text the text to evaluate.
	 * @return a lists of the balise.
	 */
	public static ArrayList<Balise> recognize(String text){
		ArrayList<Balise> balises = new ArrayList<Balise>();
		Balise balise = getFirstBalise(text);
		while(balise != null){
			balises.add(balise);
			text = removeBalise(text, balise.getName());
			balise = getFirstBalise(text);
		}
		
		return balises;
	}
	
	/**
	 * Remove the selected balise in the {@link text }
	 * @param text text to evaluate
	 * @param balise balise to remove
	 * @return new text.
	 */
	public static String removeBalise(String text, String balise){
		String textRet = text.replaceFirst("</"+ balise +">", "");
		textRet = textRet.replaceFirst("(.*)<" + balise + "[^/>]*>(.*)", "$1$2");
		textRet = textRet.replaceFirst("<" + balise + "[^>]* />", "");
		return textRet;
	}
	
	public static String removeAllBalise(String text){
		String textRet = text.replaceAll("<[^>]*>", "");
		return textRet;
	}
	
	/**
	 * Get the balise with that name, 
	 *
	 */
	public static Balise getBalise(String balise, String text, boolean alone){
		String research = "(.*)((<" + balise + " .*/>)|(<"+ balise +"[^/>]*>))(.*)";
		
		Balise newBalise = null;
		if(text.matches(research)){
			 newBalise = new Balise(balise, true);
			
			if(!alone){
				String workWith = text.replaceFirst("</"+balise+">", "").replaceFirst(research,"$2");
				if(hasAttributes(workWith)){
					newBalise = new Balise(balise, getAttributes(workWith.replace(balise + " ", "")));
				}
				int beginIndex =  text.replaceFirst(research, "$1$2").length();
				int beginIndexBaliseless = removeAllBalise(text.substring(0, beginIndex)).length();
				newBalise.beginIndex = beginIndexBaliseless;
				newBalise.endIndex = text.replaceFirst("(.*)</"+balise+">", "$1").length();
				System.out.println(text.substring(0, newBalise.endIndex));
				System.out.println(newBalise.beginIndex + newBalise.getName());
				workWith = text.substring(0, text.indexOf("</"+balise+">")).replaceFirst("</"+balise+">", "").replaceFirst("(.*)(<"+balise+".*)", "$2");
				addTextAttribute(newBalise, workWith);
			}
			else{
				String workWith = text.replaceFirst(research,"$3");
				
				if(hasAttributes(workWith)){
					newBalise = new Balise(balise, getAttributes(workWith.replace(balise + " ", "")));
				}
			}
		}
		return newBalise;
	}
	
	/**
	 * Get the first balise in this text.
	 * @param text the text to search in.
	 */
	private static Balise getFirstBalise(String text){
		//Balise to search
		String research = ".*(<([^/>]*) {0,1}/{0,1}>).*";
		
		Balise balise = null;
		String baliseName = "";
		if(text.matches(research)){
			baliseName = text.replaceAll(research, "$2");
			if(baliseName.indexOf(" ") != - 1)
				baliseName = baliseName.substring(0, baliseName.indexOf(" "));
		}
		if(text.replaceAll(research, "$1").contains(" />"))
			balise = getBalise(baliseName, text, true);
		else
			balise = getBalise(baliseName, text, false);
		return balise;
	}
	
	/**
	 * Add a text attribute of a not alone balise on a Balise object.
	 * @param balise
	 * @param text
	 */
	private static void addTextAttribute(Balise balise, String text){
		String research = "<[^>]*>";
		String value = text.replaceAll(research, "");
		balise.addAttribute("text", value);
	}
	
	/**
	 * Tell if there is attributes to get or not.
	 * @param balise balise to work on.
	 * @return result.
	 */
	private static boolean hasAttributes(String balise){
		String research = ".*=\".*\";.*";
		if(balise.matches(research))
			return true;
		return false;
	}
	
	/**
	 * Allow to remove all balise signs of a String.
	 * @param balise the balise to work on
	 * @return the result.
	 */
	private static String removeBaliseSigns(String balise){
		String workCopy = balise;
		workCopy = workCopy.replaceAll("(</)|(<)|( />)|(/>)|(>)", "");
		return workCopy;
	}
	
	/**
	 * Get all attributes of the first balise with that name
	 * @param balise name of the balise
	 * @return attributes' list.
	 */
	private static ArrayList<Attribute> getAttributes(String balise){
		//Liste des attributs
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		
		//Getting a list of names and attributes.
		String result = removeBaliseSigns(balise);
		
		
		//Research one.
		String attributeResearch = "(([^;]*)=\"([^;]*)\";) *";
		String attributeNameResult = " \n$2=$3";
				
		result = result.replaceAll(attributeResearch, attributeNameResult);
		
		//Changing this list in real objects.
		while(result.indexOf("\n") != -1){
			
			//Index of marks
			int index = result.indexOf("\n");
			int indexOfEquals = result.indexOf("=");
			
			//Getting name
			String name = result.substring(index + 1, indexOfEquals);
			while(name.startsWith(" ")){
				name = name.substring(1, name.length() - 1);
			}
			
			//Updating string list
			result = result.substring(indexOfEquals + 1);
			
			//Getting value.
			String value = "";
			
			if(result.indexOf("\n") == -1)
				value = result;
			else
				value = result.substring(0, result.indexOf("\n"));
			
			
			//Adding attribute.
			Attribute attribute = new Attribute(name, value);
			attributes.add(attribute);
			
		}
		return attributes;
	}
	
}
