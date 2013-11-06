package autre;
import gui.GameMain;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class Start {

	public static void main(String[] args) {
		AppGameContainer app;
		
		GameMain fen = new GameMain();
		try {
			app = new AppGameContainer(fen);
			fen.setApp(app);
			app.setIcons(new String[]{"Images/iconeVerySmall.png", "Images/iconeSmall.png"});
			app.setMusicOn(false);
			app.setMusicVolume(1.0f);
			app.setSoundOn(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			
			e.printStackTrace();
		}
	}

}