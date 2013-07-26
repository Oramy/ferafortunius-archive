package gui.editeurs;

import gui.Button;
import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.IntLabel;
import gui.Label;
import gui.ProgrammTextArea;

import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import ObjetMap.Animation;
import ObjetMap.TimedScript;


public class TimedScriptEditor extends ContainerWithBords{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TimedScript tsCible;
	private Animation anim;
	private IntLabel firstFrame;
	private IntLabel framesSize;
	private Label name;
	private ProgrammTextArea script;
	private Button add, remove;
	private Button monter;
	private Button descendre;
	private Button newScript;
	public TimedScriptEditor(Animation anim, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(x, y, sizeX, sizeY, parent);
		this.anim = anim;
		init();
		
	}
	public void init(){
		tsCible = new TimedScript(0,0,"",""); //$NON-NLS-1$ //$NON-NLS-2$
		
		name = new Label(0,0, getSizeX() / 2, 40,Messages.getString("TimedScriptEditor.2"), this); //$NON-NLS-1$
		this.addComponent(name);
		
		script = new ProgrammTextArea(0, 40, getSizeX()/2, getSizeY() - 40, this);
		this.addComponent(script);
		
		firstFrame = new IntLabel(getSizeX() / 2, 0, getSizeX()/4, 40, "",Messages.getString("TimedScriptEditor.4"), this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(firstFrame);
		
		framesSize = new IntLabel(getSizeX() / 4*3, 0, getSizeX()/4, 40, "",Messages.getString("TimedScriptEditor.6"), this); //$NON-NLS-1$ //$NON-NLS-2$
		this.addComponent(framesSize);
		
		add = new Button(Messages.getString("TimedScriptEditor.7"), this); //$NON-NLS-1$
		add.setBounds(getSizeX()/2, 40, getSizeX() / 4, 40);
		add.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				tsCible = tsCible.clone();
				anim.getScripts().add(tsCible);
			}
		});
		this.addComponent(add);
		
		remove = new Button(Messages.getString("TimedScriptEditor.8"), this); //$NON-NLS-1$
		remove.setBounds(getSizeX()/4*3, 40, getSizeX() / 4, 40);
		remove.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				anim.getScripts().remove(tsCible);
			}
		});
		this.addComponent(remove);
		
		newScript = new Button(Messages.getString("TimedScriptEditor.9"), this); //$NON-NLS-1$
		newScript.setBounds(getSizeX()/2, 80, getSizeX() / 4, 40);
		newScript.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				tsCible = new TimedScript(0, 0, "","");
				setTsCible(anim, tsCible);
			}
		});
		this.addComponent(newScript);
		
		//Monter
		monter = new Button(Messages.getString("BasicAnimationEditor.11"),  this); //$NON-NLS-1$
		monter.setBounds(getSizeX() / 4*3, 120, getSizeX() / 4, 40);
		monter.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				int index = anim.getScripts().indexOf(tsCible);
				if(index > 0){
					anim.getScripts().remove(tsCible);
					anim.getScripts().add(index - 1, tsCible);
				}
			}
		});
		this.addComponent(monter);
		
		//Descendre
		descendre = new Button(Messages.getString("BasicAnimationEditor.12"),  this); //$NON-NLS-1$
		descendre.setBounds(getSizeX() / 2, 120, getSizeX() / 4, 40);
		descendre.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				int index = anim.getScripts().indexOf(tsCible);
				if(index < anim.getScripts().size()){
					anim.getScripts().remove(tsCible);
					anim.getScripts().add(index + 1, tsCible);
				}
			}
		});
		this.addComponent(descendre);
		
		
	}
	public void setTsCible(Animation anim, TimedScript ts){
		this.anim = anim;
		this.tsCible = ts;
		name.getInput().setContenu(ts.getName());
		script.getInput().setContenu(ts.getScript());
		firstFrame.getInput().setContenu(ts.getFirstFrame() + ""); //$NON-NLS-1$
		framesSize.getInput().setContenu(ts.getFramesSize() + ""); //$NON-NLS-1$
		
		
	}
	public void update(GameContainer gc, int x, int y)
	{
		super.update(gc, x, y);
		tsCible.setName(name.getInput().getContenu());
		tsCible.setScript(script.getInput().getContenu());
		tsCible.setFirstFrame(firstFrame.getValue());
		tsCible.setFramesSize(framesSize.getValue());
	}
	/**
	 * @return the anime
	 */
	public Animation getAnim() {
		return anim;
	}
	/**
	 * @param anime the anime to set
	 */
	public void setAnim(Animation anim) {
		this.anim = anim;
	}
}
