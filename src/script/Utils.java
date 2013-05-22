package script;

import org.newdawn.slick.Color;

public class Utils {
	public static boolean searchFor(String cible, String source){
		return source.contains(cible);
	}
	public static boolean searchAndDelete(String cible, String source){
		if(source.contains(cible)){
			source.replace(cible, "");
			return true;
		}
		return false;
	}
	public static String convertColor(Color cible){
		String s = "";
		s = "[c]";
		s += "r="+cible.r;
		s += "g="+cible.g;
		s += "b="+cible.b;
		s += "a="+cible.a;
		s += "[/c]";
		return s;
	}
}
