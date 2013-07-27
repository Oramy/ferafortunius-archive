package texts;

import java.util.ArrayList;

public interface TextAttributeRecognizer {
	public ArrayList<TextAttribute> recognize(String text);
	
	public String getBalise();
}
