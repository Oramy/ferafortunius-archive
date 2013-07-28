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
			app.setMusicOn(false);
			app.setMusicVolume(1.0f);
			app.setSoundOn(true);
			app.setTargetFrameRate(300);
			app.start();
		} catch (SlickException e) {
			
			e.printStackTrace();
		}
	}

}