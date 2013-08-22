package texts;

import java.util.ArrayList;

public class BaliseRecognizer {
	/**
	 * Recognize all of the balises in that text. It also removes the balises strings.
	 * @param text the text to evaluate.
	 * @return a lists of the balise.
	 */
	public static ArrayList<Balise> recognize(String textOrig){
		ArrayList<Balise> balises = new ArrayList<Balise>();
		String text = textOrig;
		Balise balise = getFirstBalise(text);
		text = removeFirstBalise(text);
		while(balise != null){
			balises.add(balise);
			balise = getFirstBalise(text);
			text = removeFirstBalise(text);
		}
		
		return balises;
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
		String research = "([^</>]*)((<" + balise + " .*/>)|(<"+ balise +"[^</>]*>))(.*)";
		
		Balise newBalise = null;
		if(text.matches(research)){
			 newBalise = new Balise(balise, true);
			
			if(!alone){
				int beginIndex =  text.replaceFirst(research, "$1$2").length();
				int beginIndexBaliseless = removeAllBalise(text.substring(0, beginIndex)).length();
				String workWith =text.substring(text.replaceFirst(research, "$1").length()).replaceFirst("</"+balise+">(.*)", "");
				if(hasAttributes(workWith)){
					newBalise = new Balise(balise, getAttributes(workWith.replace(balise + " ", "")));
				}
				newBalise.beginIndex = beginIndexBaliseless;
				newBalise.endIndex = text.substring(beginIndex).replaceFirst("(.*)</"+balise+">", "$1").length();
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
		String research = "([^</>]*)(<([^</>]*)/?>)(.*)";
		
		Balise balise = null;
		String baliseName = "";
		if(text.matches(".*"+research+".*")){
			baliseName = text.replaceFirst(research, "$3");
			if(baliseName.indexOf(" ") != - 1)
				baliseName = baliseName.substring(0, baliseName.indexOf(" "));
		}
		if(text.replaceFirst(research, "$2").contains("/>"))
			balise = getBalise(baliseName, text, true);
		else
			balise = getBalise(baliseName, text, false);
		
		text = text.replaceFirst(research, "$1$4").replaceFirst("</"+baliseName + ">", "");
		return balise;
	}
	/**
	 * Remove the first balise in the text
	 * @param text the text to search in.
	 */
	private static String removeFirstBalise(String text){
		//Balise to search
		String research = "([^</>]*)(<([^</>]*)/?>)(.*)";
		
		String baliseName = "";
		if(text.matches(".*"+research+".*")){
			baliseName = text.replaceFirst(research, "$3");
			if(baliseName.indexOf(" ") != - 1)
				baliseName = baliseName.substring(0, baliseName.indexOf(" "));
		}
		text = text.replaceFirst(research, "$1$4").replaceFirst("</"+baliseName + ">", "");
		return text;
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
