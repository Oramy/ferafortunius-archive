package com.ferafortunius.animations;

import ObjetMap.ObjetMap;

import com.oramy.balises.Balise;
import com.oramy.balises.converters.BaliseReactor;

public class AnimationReactor implements BaliseReactor{

	@Override
	public void react(Object obj, Balise balise) {
		if(obj instanceof ObjetMap){
			ObjetMap o = (ObjetMap)obj;
			balise.getAttribute("");
			if(balise.getAttribute("mode").equals("move")){
				
			}
			else if(balise.getAttribute("mode").equals("rotate")){
				
			}
		}
	}

}
