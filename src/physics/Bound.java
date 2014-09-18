package physics;

import gfx.Renderable;
import utils.Vec2D;

public abstract class Bound implements Renderable, Cloneable {

	public abstract boolean collidesWithBound(Bound bound);
	public abstract Vec2D getProjectionVectorWithBound(Bound bound);
	
	public Bound clone() {
		try {
			return (Bound) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}