package autre;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public final class PressePapiers implements ClipboardOwner {
	  public void lostOwnership(Clipboard cb, Transferable t) {
	  
	  }

	  public void setClipboardContents(String s) {
	    StringSelection ss = new StringSelection(s);
	    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
	    cb.setContents(ss, this);
	    }

	  public String getClipboardContents() {
	    Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

	    try {
	      if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	        String text = (String)t.getTransferData(DataFlavor.stringFlavor);
	        return text;
	        }
	      } 
	    catch (UnsupportedFlavorException e) {
	      System.out.println(e);
	      }
	    catch (IOException e) {
	      System.out.println(e);
	      }
	    return "";
	    }
}
