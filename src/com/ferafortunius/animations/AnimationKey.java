package com.ferafortunius.animations;

import ObjetMap.ObjetMap;

public interface AnimationKey{
	public long getBeginning();
	public long getDuration();
	public void execute(ObjetMap o, long time);
	public AnimationKey cloneAnimation();
}
