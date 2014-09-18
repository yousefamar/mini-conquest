package entities;

import utils.Vec2D;
import map.World;
import gfx.Renderable;

public abstract class Entity implements Comparable<Entity>, Cloneable, Renderable {

	public World world;
	public int x, y;
	public int xRot, yRot;
	
	public Entity(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Vec2D vec) {
		this.setPosition(vec.x, vec.y);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateRotatedPostion(double viewAngle) {
		Vec2D rotVec = world.viewport.positionToPerspective(x, y); //Note: Offset for clouds/height effect.
		xRot = rotVec.x;
		yRot = rotVec.y;
	}
	
	@Override
	public int compareTo(Entity entity) {
		return yRot - entity.yRot;
	}
	
	public Entity clone() {
		try {
			return (Entity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}