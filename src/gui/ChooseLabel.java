package gui;

import java.io.File;

import javax.swing.JFileChooser;

import org.newdawn.slick.UnicodeFont;

public class ChooseLabel extends Label{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String sourceFolder;
	protected Button searchButton;
	public ChooseLabel(String sourceFolder,Container parent) {
		super(parent);
		init(sourceFolder);
	}
	public ChooseLabel(String sourceFolder, String intitule, String contenu,
			Container parent) {
		super(intitule,contenu,parent);
		init(sourceFolder);
	}
	public void init(String sourceFolder){
		this.sourceFolder = sourceFolder;
		
		searchButton = new Button("...", this.sizeX - 40, this.sizeY / 2 - 8, 40, 16, this); //$NON-NLS-1$
		
		searchButton.getAction().add(new Action(){
			public void actionPerformed(FComponent e){
				File f = new File(getSourceFolder()); //$NON-NLS-1$
				System.out.println(f.getAbsolutePath());
				if(f.exists() && f.isDirectory()){
					JFileChooser fc = new JFileChooser(f.getAbsolutePath());
					int returnVal = fc.showOpenDialog(fc);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            getInput().setContenu(getSourceFolder() + file.getName()); //$NON-NLS-1$
			        }
				}
			}
		});
		this.addComponent(searchButton);
		
	}
	public void setSourceFolder(String sourceFolder){
		this.sourceFolder = sourceFolder;
		searchButton.getAction().clear();
		searchButton.getAction().add(new Action(){
			public void actionPerformed(FComponent e){
				File f = new File(getSourceFolder()); //$NON-NLS-1$
				JFileChooser fc = new JFileChooser(f.getAbsolutePath());
				int returnVal = fc.showOpenDialog(fc);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            getInput().setContenu(file.getPath().substring(file.getPath().lastIndexOf("Items"))); //$NON-NLS-1$
		            getInput().setContenu(getInput().getContenu().replaceAll("\\\\", "/")); //$NON-NLS-1$ //$NON-NLS-2$
		        }
			}
		});
	}
	public void updateSize(){
		//Moins d'accesseurs, plus de lisibilité
		//Ecriture de base.
		UnicodeFont font = FontRessources.getFonts().text;
			
		if(getInput().getSizeX() - 40 < font.getWidth(intitule)){
			getInput().setX(font.getWidth(intitule) + 5);
			getInput().setSizeX(getSizeX() -  font.getWidth(intitule) - 55);
			getInput().setSizeY(sizeY);
		}
		if(intitule.equals("")){
			getInput().setX(0);
			getInput().setSizeX(sizeX - 40);
			getInput().setSizeY(sizeY);
		}
		searchButton.setX(this.sizeX - 40);
		searchButton.setY(this.sizeY / 2 - 8);
	}
	public String getSourceFolder(){
		return sourceFolder;
	}
}
