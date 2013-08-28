package gui.editeurs;

import observer.ActionListener;
import gui.Container;
import gui.FComponent;
import gui.buttons.Button;
import gui.buttons.CheckBox;
import gui.inputs.IntLabel;
import Level.ChunkMap;

public class NewMapContainer extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Editeur edit;
	private IntLabel chunkx, chunky,chunkz, chunksize;
	private Button newMap;
	private CheckBox layeredChunkMap;
	public NewMapContainer(int x, int y, int sizeX, int sizeY, Container parent) {
		super(x, y, sizeX, sizeY, parent);
		edit = ((Editeur)(parent.getRacine()));
		this.parent = parent;
		
		chunkx = new IntLabel(0,0,sizeX / 3, 40, Messages.getString("NewMapContainer.0"), this); //$NON-NLS-1$
		this.addComponent(chunkx);
		
		chunky = new IntLabel(sizeX/3,0,sizeX / 3, 40, Messages.getString("NewMapContainer.1"), this); //$NON-NLS-1$
		this.addComponent(chunky);
		
		chunkz = new IntLabel(sizeX/3*2,0,sizeX / 3, 40, Messages.getString("NewMapContainer.2"), this); //$NON-NLS-1$
		this.addComponent(chunkz);
		
		chunksize = new IntLabel(0,40,sizeX / 2, 40, Messages.getString("NewMapContainer.3"), this); //$NON-NLS-1$
		this.addComponent(chunksize);
		
		layeredChunkMap = new CheckBox("Chunk par couche", this);
		layeredChunkMap.setBounds(sizeX / 2,40,sizeX / 2, 40);
		this.addComponent(layeredChunkMap);
		
		newMap = new Button(Messages.getString("NewMapContainer.4"), sizeX/2, 80,sizeX / 2, 40,  this); //$NON-NLS-1$
		newMap.getAction().add(new ActionListener(){
			public void actionPerformed(FComponent e){
				int size = chunksize.getValue();
				int x = chunkx.getValue();
				int y = chunky.getValue();
				int z = chunkz.getValue();
				edit.getEditeurMap().setCarte(new ChunkMap(size, x, y, z, layeredChunkMap.isCheck()));
				EditeurMap editM =  edit.getEditeurMap();
				editM.getCarte().getChunk(0,0,0).addContenu(editM.getEditChoice());
			}
		});
				
		this.addComponent(newMap);
		
	}
	
}
