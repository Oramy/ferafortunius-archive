package com.ferafortunius.animations;

import ObjetMap.BasicObjetMap;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;

import com.oramy.balises.Balise;
import com.oramy.balises.BaliseRecognizer;
import com.oramy.balises.converters.BaliseReactor;

public class AnimationReactor implements BaliseReactor{
	private int cursor;
	@Override
	public void react(Object obj, Balise balise) {
		if(obj instanceof ObjetMap){
			ObjetMap o = (ObjetMap)obj;
			String id = balise.getAttribute("image").getValue();
			System.out.println(id);
			ObjetImage imageData = o.getImage().get(0);
			if(imageData != null){
				System.out.println(balise.getAttribute("x").getValue());
				int x = 0;
				if(balise.getAttribute("x") != null)
					x = (int) Float.parseFloat(balise.getAttribute("x").getValue());
				
				int y = 0;
				if(balise.getAttribute("y") != null)
					y =(int) Float.parseFloat(balise.getAttribute("y").getValue());
				
				float rot = 0f;
				if(balise.getAttribute("rot") != null)
					rot = Float.parseFloat(balise.getAttribute("rot").getValue());
				
				float ext = 1f;
				if(balise.getAttribute("ext") != null)
					ext = Float.parseFloat(balise.getAttribute("ext").getValue());
				
				imageData.move(x, y);
				imageData.rotate(rot);
				imageData.extend(ext);
			}
		}
	}

}
