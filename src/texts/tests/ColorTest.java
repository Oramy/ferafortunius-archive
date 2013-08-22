package texts.tests;

import java.util.ArrayList;

import texts.Balise;
import texts.BaliseRecognizer;
import texts.converters.BaliseConverter;
import texts.converters.BaliseConvertersList;
import texts.converters.BaliseReactor;

public class ColorTest implements BaliseReactor{
	public static void main(String[] args){
		ColorTest test = new ColorTest();
		
		String text = "<body><p>Premier texte.</p><link href=\"ferafortunius.fr\"; /><p>Deuxième texte.</p></body>";
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long lastTimeStamp = System.currentTimeMillis();

		ArrayList<Balise> results = BaliseRecognizer.recognize(text);
		
		System.out.println("Conversion de ("+(System.currentTimeMillis() - lastTimeStamp) + "): \n" + text);
		
		lastTimeStamp = System.currentTimeMillis();
		
		for(Balise b : results){
				BaliseConverter converter = BaliseConvertersList.getBaliseConverter(b.getName());
				if(converter  != null)
					test.react(converter.convert(b), b);
				else
					test.react(null, b);
		}
		System.out.println("Lecture des balises ("+(System.currentTimeMillis() - lastTimeStamp) + "): \n" + text);
		
		lastTimeStamp = System.currentTimeMillis();
			for(int i = 0; i < 1000; i++){
				results = BaliseRecognizer.recognize(text);
				for(Balise b : results){
						BaliseConverter converter = BaliseConvertersList.getBaliseConverter(b.getName());
						if(converter  != null)
							test.react(converter.convert(b), b);
						else
							test.react(null, b);
				}
			}
		System.out.println("Conversion * 1000 et Lecture * 1000 des balises("+(System.currentTimeMillis() - lastTimeStamp) + "): \n" + text);
		
		lastTimeStamp = System.currentTimeMillis();
		
		results = BaliseRecognizer.recognize(text);
		
		for(int i = 0; i < 1000; i++){
			for(Balise b : results){
					BaliseConverter converter = BaliseConvertersList.getBaliseConverter(b.getName());
					if(converter  != null)
						test.react(converter.convert(b), b);
					else
						test.react(null, b);
			}
		}
		System.out.println("Conversion *1 et Lecture * 1000 des balises ("+(System.currentTimeMillis() - lastTimeStamp) + "): \n" + text);
		
		
		
	}

	@Override
	public void react(Object o, Balise b) {
		System.out.println(b.getName());
	}
}
