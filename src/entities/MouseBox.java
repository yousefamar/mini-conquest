package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import physics.AABB;
import utils.Vec2D;
import map.World;

public class MouseBox extends Entity {//implements Tangible {

	private AABB aabb;
	
	public MouseBox(World world) {
		super(world, 0, 0);
		aabb = new AABB(0, 0, 32, 32);
	}
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		aabb.setCentre(x, y);
	}

	@Override
	public void render(Graphics2D gfx) {
		gfx.setColor(Color.WHITE);
		Vec2D coords = world.viewport.positionToPerspective(x, y);
		gfx.drawString((aabb.x+aabb.width/2)+", "+(aabb.y+aabb.height/2), coords.x-68, coords.y-20);
	}
/*
	@Override
	public void onCollisionWithEntity(Entity entity) {
	}

	@Override
	public float getMass() {
		return -1;
	}

	@Override
	public AABB getAABB() {
		return aabb;
	}
*/
}