package texts;

import java.util.ArrayList;

public class BaliseRecognizer {
	
	public static void main(String[] args){
		String text = "Texte<color r=\"0.3f\"; g=\"0.5f\"; b=\"1f\";>text<color>texte2</color></color>";
		Balise balise = getBalise("color", text, false);
		//System.out.println(balise.getAttributes().get(1).getValue());
		text = text.replace("<color>", "");
		text = text.replace("</color>|", "");
		System.out.println(balise.getAttributes().get(0).getValue());
		balise = getBalise("color", text, false);
		System.out.println(balise.getAttributes().get(3).getValue());
	}
	
	public static Balise getBalise(String baliseName, String text, boolean alone){
		return getFirstBalise(baliseName, text, alone);
	}
	/**
	 * Get the first balise with that name in this text.
	 */
	private static Balise getFirstBalise(String balise, String text, boolean alone){
		String research = "(.*)((<" + balise + " .*/>)|(<"+ balise +".*>))(.*)";
		
		Balise newBalise = null;
		if(text.matches(research)){
		    newBalise = new Balise(balise, true);
			String workWith = text.replace("</"+balise+">", "").replaceFirst(research,"$2");
			if(hasAttributes(workWith)){
				newBalise = new Balise(balise, getAttributes(workWith.replace(balise + " ", "")));
			}
			if(!alone){
				workWith = text.replaceAll("</"+balise+">", "").replaceFirst(research,"$2$5");
				addTextAttribute(newBalise, workWith);
			}
		}
		return newBalise;
	}
	/**
	 * Add a text attribute of a not alone balise on a Balise object.
	 * @param balise
	 * @param text
	 */
	private static void addTextAttribute(Balise balise, String text){
		String research = "(.*)<" + balise.getName() + ".*>";
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
