package GUI;

import java.awt.event.MouseEvent;

public interface BoutonAction {
	public void declencheClick(MouseEvent e);

	public void declencheDragged(MouseEvent e);

	public void declencheMove(MouseEvent e);

	public void declencheEntered(MouseEvent e);

	public void declencheExited(MouseEvent e);

	public void declenchePressed(MouseEvent e);

	public void declencheRealeased(MouseEvent e);
	
}
