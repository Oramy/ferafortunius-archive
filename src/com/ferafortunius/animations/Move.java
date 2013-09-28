package com.ferafortunius.animations;

import ObjetMap.ObjetMap;

public class Move implements AnimationKey, Cloneable{
	protected float x, y;
	protected float rotation;
	protected long duration, beginning;
	protected String imageAlias;
	protected boolean activate;
	public Move(long duration, float x, float y, float rotation, String imageAlias){
		init(0, duration, x, y, rotation, imageAlias);
	}
	public Move(long beginning, long duration, float x, float y, float rotation, String imageAlias){
		init(beginning, duration, x, y, rotation, imageAlias);
	}

	private void init(long beginning, long duration, float x, float y,
			float rotation, String imageAlias) {
		this.x = x;
		this.imageAlias = imageAlias;
		this.y = y;
		this.rotation = rotation;
		this.beginning = beginning;
		this.duration = duration;
		activate = true;
	}
	@Override
	public long getBeginning() {
		return beginning;
	}
	@Override
	public long getDuration() {
		return duration;
	}
	@Override
	public void execute(ObjetMap o, long time) {
		if(time >= beginning && time <= beginning + duration && activate){
			o.getImage(imageAlias).moveIn(x, y, rotation, duration);
			activate = false;
		}
	}
	@Override
	public AnimationKey cloneAnimation() {
		AnimationKey clone = null;
		try {
			clone = (AnimationKey) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clone;
	}
	public String getImageAlias() {
		return imageAlias;
	}
	public void setImageAlias(String imageAlias) {
		this.imageAlias = imageAlias;
	}
}
