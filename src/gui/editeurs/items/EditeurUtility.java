package gui.editeurs.items;

import gui.Container;
import gui.editeurs.Messages;
import gui.inputs.IntLabel;
import gui.layouts.GridLayout;
import gui.widgets.WBonusList;
import gui.widgets.Widget;
import Items.Utility;

public class EditeurUtility extends EditeurItemBasic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IntLabel maxUseNumber;
	private Widget bonus;
	public EditeurUtility(Utility editItem, int x, int y, int sizeX, int sizeY,
			Container parent) {
		super(editItem, x, y, sizeX, sizeY, parent);
		this.setActualLayout(new GridLayout(1,2));
		maxUseNumber = new IntLabel(Messages.getString("EditeurUtility.0"), this); //$NON-NLS-1$
		this.addComponent(maxUseNumber);
		bonus = new WBonusList(editItem.getBonus(), this);
		this.addComponent(bonus);
	}

}
