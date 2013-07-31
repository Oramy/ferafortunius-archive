package texts.converters;

import java.util.ArrayList;
import java.util.HashSet;

public class BaliseConvertersList {
	private static HashSet<BaliseConverter> converters;
	
	private static ArrayList<String> balisesName;
	
	/**
	 * Return the baliseConverter's ID with that baliseName associated.
	 * @param baliseName the baliseName.
	 * @return the baliseConverters's ID.
	 */
	private static int getBaliseConverterIndex(String baliseName){
		//Une variable d'incrémentaiton
		int count = 0;
		
		//L'id de la balise.
		int id = -1;
		
		//Recherche.
		for(String name : getBalisesName()){
			if(name.equals(baliseName))
				id = count;
			count ++;
		}
		return id;
	}
	/**
	 * Link the two objects together
	 * @param baliseConverter the BaliseConverter to link
	 * @param baliseName the balise name to link
	 */
	public static void linkConverters(BaliseConverter baliseConverter, String baliseName){
		getConverters().add(baliseConverter);
		getBalisesName().add(baliseName);
	}
	/**
	 * @see getBaliseConverterIndex()
	 * @param baliseName the baliseName
	 * @return the baliseConverter associated.
	 */
	public static BaliseConverter getBaliseConverter(String baliseName){
		return (BaliseConverter) getConverters().toArray()[getBaliseConverterIndex(baliseName)];
	}
	/**
	 * 
	 * @return a list of all converters.
	 */
	private static HashSet<BaliseConverter> getConverters(){
		if(converters == null)
		{
			converters = new HashSet<BaliseConverter>();
		}
		return converters;
	}
	/**
	 * 
	 * @return a list of all associated names.
	 */
	public static ArrayList<String> getBalisesName(){
		if(balisesName == null)
			balisesName = new ArrayList<String>();
		return balisesName;
	}
}
