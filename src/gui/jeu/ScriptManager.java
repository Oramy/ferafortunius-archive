package gui.jeu;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptManager {
	public static final ScriptEngineManager managerScript = new ScriptEngineManager();
	public static final ScriptEngine moteurScript = managerScript
				.getEngineByName("nashorn");
}
