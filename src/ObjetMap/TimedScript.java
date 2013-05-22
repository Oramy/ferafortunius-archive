package ObjetMap;

import java.io.Serializable;

public class TimedScript implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 723171956320269662L;
	private int firstFrame;
	private int framesSize;
	private String name;
	private String script;
	public TimedScript(int firstFrame, int size, String name, String script){
		this.firstFrame = firstFrame;
		this.framesSize = size;
		this.name = name;
		this.script = script;
	}
	public TimedScript clone(){
		TimedScript t = null;
		try {
			t = (TimedScript) super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		return t;
	}
	
	public boolean hastoBe(int cursor){
		if(cursor >= firstFrame && cursor < firstFrame + framesSize)
			return true;
		return false;
	}
	/**
	 * @return the firstFrame
	 */
	public int getFirstFrame() {
		return firstFrame;
	}
	/**
	 * @param firstFrame the firstFrame to set
	 */
	public void setFirstFrame(int firstFrame) {
		this.firstFrame = firstFrame;
	}
	/**
	 * @return the framesSize
	 */
	public int getFramesSize() {
		return framesSize;
	}
	/**
	 * @param framesSize the framesSize to set
	 */
	public void setFramesSize(int framesSize) {
		this.framesSize = framesSize;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}
	/**
	 * @param script the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}
}
