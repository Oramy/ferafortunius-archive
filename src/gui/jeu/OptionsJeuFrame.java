package gui.jeu;

import gui.Button;
import gui.CheckBox;
import gui.CheckBoxManager;
import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.InternalFrame;
import gui.Label;
import gui.ModeJeu;
import gui.Slider;
import gui.Text;
import gui.TextDisplayMode;
import gui.layouts.GridLayout;
import observer.ActionListener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import Level.OptionsJeuLoader;


public class OptionsJeuFrame extends InternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4042887366897323980L;
	private CheckBox vSync;
	private CheckBox windowed;
	private CheckBoxManager boxManager;
	private Slider textDisplayModeSlider;
	private Text textDisplayMode;
	private Label ip;
	private OptionsJeu options;
	public OptionsJeuFrame(int posX, int posY, int width, int height,
			String title, Container parent) {
		super(posX, posY, width, height, title, parent);
		
	}
	public void update(GameContainer gc, int x, int y){
		super.update(gc, x, y);
		switch(textDisplayModeSlider.getValue()){
			case 0: 
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.0")); //$NON-NLS-1$
				break;
			case 1: 
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.1")); //$NON-NLS-1$
				break;
			case 2: 
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.2")); //$NON-NLS-1$
				break;
			case 3: 
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.3")); //$NON-NLS-1$
				break;
		}
	}
	public OptionsJeuFrame(String title, OptionsJeu options2, Container p) {
		super(p.getSizeX() / 2 - 250, p.getSizeY() / 2 - 250, 500,500, title, p);
		this.options = options2;
		boxManager = new CheckBoxManager(10,0,400,50, getContainer());
		boxManager.setBackground(Container.alpha); //$NON-NLS-1$
		vSync = new CheckBox(Messages.getString("OptionsJeuFrame.5"), boxManager); //$NON-NLS-1$
		vSync.setCheck(options.isVsync());
		vSync.setY(0);
		windowed = new CheckBox(Messages.getString("OptionsJeuFrame.6"), boxManager); //$NON-NLS-1$
		windowed.setY(25);
		windowed.setCheck(options.isWindowed());
		
		boxManager.addComponent(vSync);
		boxManager.addComponent(windowed);
		getContainer().addComponent(boxManager);
		textDisplayModeSlider = new Slider(3, container);
		textDisplayModeSlider.setY(50);
		textDisplayModeSlider.setSizeX(100);
		textDisplayModeSlider.setX(10);
		textDisplayMode = new Text("", container); //$NON-NLS-1$
		textDisplayMode.setY(45);
		textDisplayMode.setX(120);
		switch(options.getTextDisplayMode()){
			case Slowly:
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.8")); //$NON-NLS-1$
				textDisplayModeSlider.setValue(0);
				break;
			case Normal:
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.9")); //$NON-NLS-1$
				textDisplayModeSlider.setValue(1);
				break;
			case Fast:
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.10")); //$NON-NLS-1$
				textDisplayModeSlider.setValue(2);
				break;
			case Instantly:
				textDisplayMode.setText(Messages.getString("OptionsJeuFrame.11")); //$NON-NLS-1$
				textDisplayModeSlider.setValue(3);
				break;
		}
		
		getContainer().addComponent(textDisplayModeSlider);
		getContainer().addComponent(textDisplayMode);
		ContainerWithBords saveApply = new ContainerWithBords(container.getSizeX() / 2, container.getSizeY() - 30, container.getSizeX() / 2  -1, 30, getContainer());
		saveApply.setActualLayout(new GridLayout(2,1));
		Button save = new Button(Messages.getString("OptionsJeuFrame.12"), saveApply); //$NON-NLS-1$
		save.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				options.setVsync(vSync.isCheck());
				options.setWindowed(windowed.isCheck());
				switch(textDisplayModeSlider.getValue()){
				case 0: 
					options.setTextDisplayMode(TextDisplayMode.Slowly);
					break;
				case 1: 
					options.setTextDisplayMode(TextDisplayMode.Normal);
					break;
				case 2: 
					options.setTextDisplayMode(TextDisplayMode.Fast);
					break;
				case 3: 
					options.setTextDisplayMode(TextDisplayMode.Instantly);
					break;
				}
				
				OptionsJeuLoader.saveOptions(options);
			}
		});
		Button openToLan = new Button(Messages.getString("OptionsJeuFrame.13"), getContainer()); //$NON-NLS-1$
		openToLan.setY(100);
		openToLan.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				Jeu jeu = (Jeu) c.getRacine();
				jeu.setServer(!jeu.isServer());
				if(jeu.isServer())
					((Button)(c)).setName(Messages.getString("OptionsJeuFrame.14")); //$NON-NLS-1$
				else
					((Button)(c)).setName(Messages.getString("OptionsJeuFrame.15")); //$NON-NLS-1$
			}
		});
		getContainer().addComponent(openToLan);
		Button playMulti = new Button(Messages.getString("OptionsJeuFrame.16"), getContainer()); //$NON-NLS-1$
		playMulti.setY(100);
		playMulti.setX(200);
		playMulti.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				Jeu jeu = (Jeu) c.getRacine();
				jeu.setClient(!jeu.isClient());
				if(jeu.isClient())
					((Button)(c)).setName(Messages.getString("OptionsJeuFrame.17")); //$NON-NLS-1$
				else
					((Button)(c)).setName(Messages.getString("OptionsJeuFrame.18")); //$NON-NLS-1$
			}
		});
		getContainer().addComponent(playMulti);
		
		ip  = new Label(Messages.getString("OptionsJeuFrame.19"), getContainer()); //$NON-NLS-1$
		ip.setY(150);
		ip.setSizeX(400);
		ip.setSizeY(30);
		getContainer().addComponent(ip);
		
		Button apply = new Button(Messages.getString("OptionsJeuFrame.20"), saveApply); //$NON-NLS-1$
		apply.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent c){
				Jeu jeu = (Jeu) c.getRacine();
				jeu.applyOptions(options);
				try {
					jeu.getGm().getApp().reinit();
				} catch (SlickException e) {
					
					e.printStackTrace();
				}
				jeu.getGm().setMode(ModeJeu.Jeu, jeu.getGm().getApp());
				jeu.getGm().setJeu(jeu);
				
			}
		});
		saveApply.addComponent(save);
		saveApply.addComponent(apply);
		getContainer().addComponent(saveApply);
	}
	/**
	 * @return the ip
	 */
	public Label getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(Label ip) {
		this.ip = ip;
	}

}
