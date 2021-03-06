package gui.editeurs.objetmaps;

import gui.Container;
import gui.ContainerWithBords;
import gui.FComponent;
import gui.Slider;
import gui.Text;
import gui.buttons.Button;
import gui.buttons.CheckBox;
import gui.editeurs.Editeur;
import gui.editeurs.Messages;
import gui.editeurs.PanneauApercu;
import gui.inputs.IntLabel;
import gui.inputs.Label;
import gui.layouts.GridLayout;
import observer.ActionListener;

import org.newdawn.slick.GameContainer;

import Level.Camera;
import Level.ObjetMapLoader;
import ObjetMap.BasicEntity;
import ObjetMap.BasicObjetMap;
import ObjetMap.Entity;
import ObjetMap.ObjetMap;

public class BasicObjetMapEditor extends ContainerWithBords {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5836918770123153407L;
	// Objets
	private ObjetMap emptyObject;
	private Entity emptyEntity;

	// GUI
	private Container saveCont;
	private Button save;
	private Button exportInAnim;
	private Button newObject;
	private Button newEntity;
	private Label name;
	private Label path;
	private String savePath = ""; //$NON-NLS-1$
	private Container size;
	private IntLabel sizeXLab;
	private IntLabel sizeYLab;
	private IntLabel sizeZLab;
	private IntLabel colorMaskR, colorMaskG, colorMaskB;
	private Text opacityText;
	private Slider opacity;
	private Container checkCont;
	private CheckBox invisible;
	private CheckBox fly;
	private CheckBox updatable;
	private CheckBox applyZShadow;
	private Container apercuCont;
	private PanneauApercu apercu;

	public void update(GameContainer gc, int x, int y) {
		super.update(gc, x, y);
		//Mise ? jour des donn?es
		updateData();
		savePath = path.getInput().getContenu();

	}
	public void updateData(){
		getObj().setSizeX(sizeXLab.getValue());
		getObj().setSizeY(sizeYLab.getValue());
		getObj().setSizeZ(sizeZLab.getValue());
		getObj().setInvisible(invisible.isCheck());
		getObj().setFly(invisible.isCheck());
		getObj().setUpdate(updatable.isCheck());
		getObj().setApplyZShadow(applyZShadow.isCheck());
		getObj().setNom(name.getInput().getContenu());
		getObj().getMaskColor().r = (float) colorMaskR.getValue() / 255.0f;
		getObj().getMaskColor().g = (float) colorMaskG.getValue() / 255.0f;
		getObj().getMaskColor().b = (float) colorMaskB.getValue() / 255.0f;
		getObj().setOpacity(((float) opacity.getValue()) / 1000);
		opacityText
				.setText(Messages.getString("BasicObjetMapEditor.text.0") + ((float) opacity.getValue()) / 1000); //$NON-NLS-1$
	}
	public BasicObjetMapEditor(ObjetMap obj2, int x, int y, int sizeX,
			int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		
		emptyObject = new BasicObjetMap(0, 0, 0, 0, 0, 0);
		emptyEntity = new BasicEntity(0, 0, 0, 0, 0, 0);
		// Aper?u
		apercuCont = new ContainerWithBords(0, 0, sizeX, sizeY / 6 * 4,
				this);

		apercu = new PanneauApercu(null, obj2, 10, 10, apercuCont.getSizeX() - 20,
				apercuCont.getSizeY() - 20, apercuCont);
		if (this.getObj().getImage().size() != 0) {
			apercu.setActualCam(new Camera(0, -this.getObj().getImage().get(0)
					.getImageSizeInGameY() / 2, 1f, null));
		}
		apercu.getCarte().getChunks()[0][0][0].addContenu(this.getObj());
		apercuCont.addComponent(apercu);

		this.addComponent(apercuCont);

		// Checkable
		checkCont = new ContainerWithBords(sizeX / 2, sizeY / 6 * 4, sizeX / 2,
				sizeY / 6, this);
		checkCont.setActualLayout(new GridLayout(1, 6));
		((GridLayout) checkCont.getActualLayout()).setHgap(10);

		invisible = new CheckBox(
				Messages.getString("BasicObjetMapEditor.text.1"), checkCont); //$NON-NLS-1$
		invisible.setX(10);
		invisible.setY(2);
		invisible.setCheck(this.getObj().isInvisible());
		checkCont.addComponent(invisible);

		fly = new CheckBox(
				Messages.getString("BasicObjetMapEditor.text.2"), checkCont); //$NON-NLS-1$
		fly.setX(10);
		fly.setY(2);
		fly.setCheck(this.getObj().isFly());
		checkCont.addComponent(fly);

		updatable = new CheckBox(
				Messages.getString("BasicObjetMapEditor.text.3"), checkCont); //$NON-NLS-1$
		updatable.setX(10);
		updatable.setY(2);
		updatable.setCheck(this.getObj().isUpdate());
		checkCont.addComponent(updatable);
		
		applyZShadow = new CheckBox(Messages.getString("BasicObjetMapEditor.text.18"), checkCont); //$NON-NLS-1$
		applyZShadow.setX(10);
		applyZShadow.setY(2);
		applyZShadow.setCheck(this.getObj().isApplyZShadow());
		checkCont.addComponent(applyZShadow);
		

		Button extend = new Button(Messages.getString("BasicObjetMapEditor.text.19"), checkCont); //$NON-NLS-1$
		extend.setX(10);
		extend.setY(2);
		extend.getAction().add(new ActionListener(){

			@Override
			public void actionPerformed(FComponent c) {
				getObj().extend(1.01f);
				sizeXLab.setValue(getObj().getSizeX());
				sizeYLab.setValue(getObj().getSizeY());
				sizeZLab.setValue(getObj().getSizeZ());
			}
			
		});
		checkCont.addComponent(extend);
		
		Button reduce = new Button(Messages.getString("BasicObjetMapEditor.text.20"), checkCont); //$NON-NLS-1$
		reduce.setX(10);
		reduce.setY(2);
		reduce.getAction().add(new ActionListener(){

			@Override
			public void actionPerformed(FComponent c) {
				getObj().reduce(1.01f);
				sizeXLab.setValue(getObj().getSizeX());
				sizeYLab.setValue(getObj().getSizeY());
				sizeZLab.setValue(getObj().getSizeZ());
			}
			
		});
		checkCont.addComponent(reduce);

		this.addComponent(checkCont);
		// Save container
		saveCont = new ContainerWithBords(0, sizeY / 6 * 4, sizeX / 2,
				sizeY / 6, this);
		name = new Label(25, 0, saveCont.getSizeX() - 50,
				saveCont.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.4"), "", saveCont); //$NON-NLS-1$ //$NON-NLS-2$
		name.getInput().setContenu(this.getObj().getNom());
		saveCont.addComponent(name);

		path = new Label(25, saveCont.getSizeY() / 3, saveCont.getSizeX() - 50,
				saveCont.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.5"), "", saveCont); //$NON-NLS-1$ //$NON-NLS-2$
		saveCont.addComponent(path);

		save = new Button(
				Messages.getString("BasicObjetMapEditor.text.6"), saveCont); //$NON-NLS-1$
		save.setBounds(25, saveCont.getSizeY() / 3 * 2,
				(saveCont.getSizeX() - 50) / 2, saveCont.getSizeY() / 6);
		save.getAction().add(new ActionListener() {
			public void actionPerformed(FComponent c) {
				save();
			}
		});
		saveCont.addComponent(save);

		exportInAnim = new Button(
				Messages.getString("BasicObjetMapEditor.text.7"), saveCont); //$NON-NLS-1$
		exportInAnim.setBounds(25, saveCont.getSizeY() / 6 * 5,
				(saveCont.getSizeX() - 50) / 2, saveCont.getSizeY() / 6);
		exportInAnim.getAction().add(new ActionListener() {
			public void actionPerformed(FComponent c) {
				exportInAnim();
			}
		});
		saveCont.addComponent(exportInAnim);

		newObject = new Button(
				Messages.getString("BasicObjetMapEditor.text.8"), 25 + (saveCont.getSizeX() - 50) / 2, saveCont.getSizeY() / 3 * 2, (saveCont.getSizeX() - 50) / 2, saveCont.getSizeY() / 6, saveCont); //$NON-NLS-1$
		newObject.getAction().add(new ActionListener() {
			public void actionPerformed(FComponent c) {
				setObj(emptyObject.clone());
			}
		});
		saveCont.addComponent(newObject);

		newEntity = new Button(
				Messages.getString("BasicObjetMapEditor.text.9"), 25 + (saveCont.getSizeX() - 50) / 2, saveCont.getSizeY() / 6 * 5, (saveCont.getSizeX() - 50) / 2, saveCont.getSizeY() / 6, saveCont); //$NON-NLS-1$
		newEntity.getAction().add(new ActionListener() {
			public void actionPerformed(FComponent c) {
				setObj(emptyEntity.clone());
			}
		});
		saveCont.addComponent(newEntity);
		this.addComponent(saveCont);

		size = new ContainerWithBords(0, sizeY / 6 * 5, sizeX, sizeY / 6, this);

		this.sizeXLab = new IntLabel(5, 0, size.getSizeX() / 2 - 10,
				size.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.10"), size); //$NON-NLS-1$
		this.sizeXLab.getInput().setContenu(this.getObj().getSizeX() + ""); //$NON-NLS-1$
		size.addComponent(this.sizeXLab);

		this.sizeYLab = new IntLabel(5, size.getSizeY() / 3,
				size.getSizeX() / 2 - 10, size.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.11"), size); //$NON-NLS-1$
		this.sizeYLab.getInput().setContenu(this.getObj().getSizeY() + ""); //$NON-NLS-1$
		size.addComponent(this.sizeYLab);

		this.sizeZLab = new IntLabel(5, size.getSizeY() / 3 * 2,
				size.getSizeX() / 2 - 10, size.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.12"), size); //$NON-NLS-1$
		this.sizeZLab.getInput().setContenu(this.getObj().getSizeZ() + ""); //$NON-NLS-1$
		size.addComponent(this.sizeZLab);

		opacityText = new Text(
				Messages.getString("BasicObjetMapEditor.text.0") + obj2.getOpacity(), size); //$NON-NLS-1$
		opacityText.setY(0);
		opacityText.setX(size.getSizeX() / 2 + 5);
		size.addComponent(opacityText);

		this.opacity = new Slider(1000, (int) (obj2.getOpacity() * 1000), size);
		opacity.setX(size.getSizeX() / 2 + 5);
		opacity.setSizeX(size.getSizeX() / 2 - 10);
		opacity.setY(20);
		size.addComponent(opacity);

		this.colorMaskR = new IntLabel(size.getSizeX() / 2 + 5,
				size.getSizeY() / 3, ((size.getSizeX() / 2) - 10) / 2,
				size.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.14"), size); //$NON-NLS-1$
		this.colorMaskR.getInput().setContenu(
				((int) (this.getObj().getMaskColor().r * 255)) + ""); //$NON-NLS-1$
		size.addComponent(this.colorMaskR);

		this.colorMaskG = new IntLabel(size.getSizeX() / 2 + 5
				+ ((size.getSizeX() / 2) - 10) / 2, size.getSizeY() / 3,
				((size.getSizeX() / 2) - 10) / 2, size.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.15"), size); //$NON-NLS-1$
		this.colorMaskG.getInput().setContenu(
				((int) (this.getObj().getMaskColor().g * 255)) + ""); //$NON-NLS-1$
		size.addComponent(this.colorMaskG);

		this.colorMaskB = new IntLabel(size.getSizeX() / 2 + 5,
				size.getSizeY() / 3 * 2, ((size.getSizeX() / 2) - 10) / 2,
				size.getSizeY() / 3,
				Messages.getString("BasicObjetMapEditor.text.16"), size); //$NON-NLS-1$
		this.colorMaskB.getInput().setContenu(
				((int) (this.getObj().getMaskColor().b * 255)) + ""); //$NON-NLS-1$
		size.addComponent(this.colorMaskB);

		this.addComponent(size);

	}

	protected void save() {
		if (this.getObj() instanceof Entity)
			ObjetMapLoader.saveObject(this.getObj(),
					"data/ObjetMap/entity" + savePath + ".obj"); //$NON-NLS-1$ //$NON-NLS-2$
		else
			ObjetMapLoader.saveObject(this.getObj(),
					"data/ObjetMap/objet" + savePath + ".obj"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	protected void exportInAnim() {
		Editeur edit = ((Editeur) (this.getRacine()));
		edit.getEditeurAnimationOnglet().clickPressed();
		edit.getEditeurAnimationOnglet().clickReleased();
		edit.getEditeurAnimation().setObjetCible(getObj().clone());
	}

	/**
	 * @return the opacity
	 */
	public Slider getOpacity() {
		return opacity;
	}

	/**
	 * @param opacity
	 *            the opacity to set
	 */
	public void setOpacity(Slider opacity) {
		this.opacity = opacity;
	}

	/**
	 * @return the opacityText
	 */
	public Text getOpacityText() {
		return opacityText;
	}

	/**
	 * @param opacityText
	 *            the opacityText to set
	 */
	public void setOpacityText(Text opacityText) {
		this.opacityText = opacityText;
	}

	/**
	 * @return the obj
	 */
	public ObjetMap getObj() {
		return ((Editeur) this.getRacine()).getWorkedObj();
	}

	/**
	 * @param obj
	 *            the obj to set
	 */
	public void setObj(ObjetMap obj) {
		((EditeurObjetMap) parent).setObjetCible(obj);
	}
}
