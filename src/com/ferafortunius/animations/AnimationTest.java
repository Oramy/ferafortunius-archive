package com.ferafortunius.animations;

import ObjetMap.Animation;
import ObjetMap.BasicObjetMap;
import ObjetMap.ObjetImage;
import ObjetMap.ObjetMap;

public class AnimationTest {

	public static void main(String[] args) {
		
		//Création d'un objet
		ObjetMap obj = new BasicObjetMap(0,0,0,0,0,0);
		
		//Création d'une image appelée bras.
		obj.getImage().add(new ObjetImage("bras", 0,0));
		obj.getImage().get(0).setAlias("bras");
		
		
		//Création d'une animation faisant bouger le bras.
		Animation a = AnimationIO.loadAnimation("animationDeFou.xml");
		
		AnimationIO.writeAnimation("animationDeFou.xml", a);
		for(int i = 0; i < 100; i++){
			a.getCompressScript(obj);
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
