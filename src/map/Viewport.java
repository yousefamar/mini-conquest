package map;

import core.MiniConquest;
import utils.Vec2D;

public class Viewport {

	public int viewOffsetX = 0, viewOffsetY = 0;
	public float viewPitch = 2, viewYaw = 0;
	
	public int getViewCentreX() { //REMOVE?!?!?!?
		return viewOffsetX + MiniConquest.screenDims.width/2;
	}

	public int getViewCentreY() { //TODO: Decide on a fixed centre.
		return viewOffsetY + (MiniConquest.screenDims.height-80)/2;
	}
	
	public void pan(int x, int y) {
		viewOffsetX += x;
		viewOffsetY += y;
	}
	
	public void rotate(int x, int y) {
		//TODO: Optimise.
		float newPitch = viewPitch + (((float) y)/20F);
		if (newPitch<1) newPitch = 1;
		if (newPitch>2) newPitch = 2;
		viewPitch = newPitch;
		float newYaw = viewYaw + (float) Math.toRadians(((float) x)/2F);
		while (newYaw<0) newYaw += 2*Math.PI;
		while (newYaw>2*Math.PI) newYaw -= 2*Math.PI;
		viewYaw = newYaw;	
	}
	
	public Vec2D positionToPerspective(int x, int y) {
		return new Vec2D(x, y).translate(-getViewCentreX(), -getViewCentreY()).rotateZ(-viewYaw).scaleXY(1, viewPitch*0.5F).translate(getViewCentreX(), getViewCentreY());
	}
	
	public Vec2D perspectiveToPosition(int x, int y) {
		return new Vec2D(x, y).translate(viewOffsetX-getViewCentreX(), viewOffsetY-getViewCentreY()).scaleXY(1, (1/viewPitch)*2).rotateZ(viewYaw).translate(getViewCentreX(), getViewCentreY());
	}
}