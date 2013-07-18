package autre;
import gui.GameMain;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class Start {

	public static GameMain fen;
	public static void main(String[] args) {
			/*for(int i = 1; i < 21; i++)
			{
				c.chargerNiveau(i, true);
				System.out.println(i);
			}*/
		
		/*
            try {
            	if(FFConnection.getInstance() != null)
            	{
            		ResultSet utilisateur = FFConnection.getInstance().executeQuery("SELECT * FROM utilisateur");
				while(utilisateur.next()){
					System.out.println(utilisateur.getString("Pseudo"));
				}
				ResultSet blog = FFConnection.getInstance().executeQuery("SELECT * FROM blog");
				while(blog.next()){
					System.out.println(blog.getString("Titre"));
				}
            	}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		*/
		/**//*
		        FTPClient client = new FTPClient();
		        FileInputStream fis = null;
		        FileOutputStream fos = null;
		        try {
		            client.connect("ftpperso.free.fr");
		            
		            
		            client.login("ferafortunius", "lcmhsams");

		            //
		            // Create an InputStream of the file to be uploaded
		            //
		            String filename = "buisson.png";
		            fis = new FileInputStream(filename);
		            fos = new FileOutputStream("uploads/"+filename);

		            //
		            // Store file to server
		            //
		           
		            client.retrieveFile("buisson.png", fos);
		            client.logout();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (fis != null) {
		                    fis.close();
		                }
		                client.disconnect();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    */
		//ouverture de la fenetre
		/*AFTs afts = new AFTs("Images/AFTs.png");
		AFTs ff = new AFTs("Images/Ferafortunius0.3.01.png");*/
		/*ScriptEngineManager manager = new ScriptEngineManager(); 
	    ScriptEngine moteur = manager.getEngineByName("rhino"); 
	    Scanner sc = new Scanner(System.in);
	    try { 
	     
	      Bindings bindings = moteur.getBindings(ScriptContext.ENGINE_SCOPE); 
	      bindings.clear(); 
	      Terre objet = new Terre(0,0,0,0,0,0);
	      //Ajout de la variable entree dans le script
	      bindings.put("objet", objet);
	      //Execution du script entr�e
	      moteur.eval(sc.nextLine(), bindings);
	      objet = (Terre)bindings.get("vari"); 
	    } catch (ScriptException e) { 
	      e.printStackTrace(); 
	    } */
		
		AppGameContainer app;
		GameMain fen = new GameMain();
		try {
			app = new AppGameContainer(fen);
			fen.setApp(app);
			app.setMusicOn(true);
			app.setMusicVolume(1.0f);
			app.setSoundOn(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			
			e.printStackTrace();
		}
		/*AFTs aftsBye = new AFTs("Images/AFTs.png");*/
	}

}