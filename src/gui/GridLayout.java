package gui;


public class GridLayout extends Layout {
	private int sizeX, sizeY;
	private int hgap;
	private int vgap;
	public GridLayout(int width, int height){
		sizeX = width;
		sizeY = height;
		hgap = 0;
		vgap = 0;
	}
	public void addComponent(Container container, FComponent c) {
		this.container = container;
		container.getComponents().add(c);
		updateLayout();
	}
	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * @param sizeX the sizeX to set
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	/**
	 * @return the hgap
	 */
	public int getHgap() {
		return hgap;
	}
	/**
	 * @param hgap the hgap to set
	 */
	public void setHgap(int hgap) {
		this.hgap = hgap;
	}
	/**
	 * @return the vgap
	 */
	public int getVgap() {
		return vgap;
	}
	/**
	 * @param vgap the vgap to set
	 */
	public void setVgap(int vgap) {
		this.vgap = vgap;
	}
	@Override
	public void updateLayout() {
		int tempPosX = 0;
		int tempPosY = 0;
		if(container != null){
			for(FComponent c : container.getComponents()){
				if(tempPosX != sizeX && tempPosY != sizeY){
					if(c instanceof Chooser)
					{
						if(!((Chooser)c).isShowed()){
							c.setBounds(tempPosX * (container.getWidth() / sizeX) + hgap / 2, 
									tempPosY * (container.getHeight() / sizeY) + vgap / 2, 
									container.getWidth() / sizeX - hgap, 
									container.getHeight() / sizeY - vgap);
							tempPosX++;
							if(tempPosX == sizeX){
								tempPosX = 0;
								tempPosY++;
							}
						}
					}
					else{
						c.setBounds(tempPosX * (container.getWidth() / sizeX) + hgap / 2, 
								tempPosY * (container.getHeight() / sizeY) + vgap / 2, 
								container.getWidth() / sizeX - hgap, 
								container.getHeight() / sizeY - vgap);
						tempPosX++;
						if(tempPosX == sizeX){
							tempPosX = 0;
							tempPosY++;
						}
					}
				}
			}
		}
	}
}
